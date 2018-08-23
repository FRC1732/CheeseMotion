package frc.robot.main;

import java.util.function.Supplier;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import frc.robot.lib.util.math.Rotation2d;
import frc.robot.loops.Loop;
import frc.robot.loops.Looper;

// see cheesypoof drivetrain code. Implementation of motor controllers and sensors left to the user
public class Drive extends Subsystem {

	private static Drive mInstance = new Drive();

	public static Drive getInstance() {
		return mInstance;
	}

	// The robot drivetrain's various states.
	public enum DriveControlState {
		OPEN_LOOP, // open loop voltage control
		VELOCITY_SETPOINT, // velocity PID control
		PATH_FOLLOWING, // used for autonomous driving
	}

	private TalonSRX leftMaster;
	private VictorSPX left1;
	private VictorSPX left2;

	private TalonSRX rightMaster;
	private VictorSPX right1;
	private VictorSPX right2;

	private AHRS ahrs = new AHRS(SPI.Port.kMXP, (byte) 200);

	public Drive() {
		leftMaster = new TalonSRX(0);
		left1 = new VictorSPX(1);
		left1.set(ControlMode.Follower, leftMaster.getDeviceID());
		left2 = new VictorSPX(2);
		left2.set(ControlMode.Follower, leftMaster.getDeviceID());

		rightMaster = new TalonSRX(15);
		right1 = new VictorSPX(14);
		right1.set(ControlMode.Follower, rightMaster.getDeviceID());
		right2 = new VictorSPX(13);
		right2.set(ControlMode.Follower, rightMaster.getDeviceID());

		// insert actual sensors here
		leftEncoder = this::left;
		rightEncoder = this::right;
		leftEncoderRate = this::leftVel;
		rightEncoderRate = this::rightVel;
		// get yaw from navx board (use cheesypoof wrapper, it is a lot better than what
		// kuai labs makes
		// see their 2017 code)
		gyro = this::angle;
		// configure motors in he
		// see cheesypoof code
		mPathFollower = new DrivePathFollower(this);
	}

	private double left() {
		return leftMaster.getSelectedSensorPosition(0);
	}

	private double right() {
		return rightMaster.getSelectedSensorPosition(0);
	}

	private double leftVel() {
		return leftMaster.getSelectedSensorVelocity(0);
	}

	private double rightVel() {
		return rightMaster.getSelectedSensorVelocity(0);
	}

	private Rotation2d angle() {
		return Rotation2d.fromDegrees(ahrs.getAngle());
	}

	private DriveControlState mDriveControlState;
	public final DrivePathFollower mPathFollower;

	private Supplier<Double> leftEncoder;
	private Supplier<Double> rightEncoder;
	private Supplier<Double> leftEncoderRate;
	private Supplier<Double> rightEncoderRate;
	private Supplier<Rotation2d> gyro;

	private final Loop mLoop = new Loop() {
		@Override
		public void onStart(double timestamp) {
			synchronized (Drive.this) {
				// initialize drive
				leftMaster.set(ControlMode.Velocity, 0);
				rightMaster.set(ControlMode.Velocity, 0);
			}
		}

		@Override
		public void onLoop(double timestamp) {
			synchronized (Drive.this) {
				switch (mDriveControlState) {
				case OPEN_LOOP:
					return;
				case VELOCITY_SETPOINT:
					return;
				case PATH_FOLLOWING:
					if (mPathFollower != null) {
						mPathFollower.updatePathFollower(timestamp);
					}
					return;
				default:
					System.out.println("Unexpected drive control state: " + mDriveControlState);
					break;
				}
			}
		}

		@Override
		public void onStop(double timestamp) {
			stop();
		}
	};

	public DriveControlState getDriveControlState() {
		return mDriveControlState;
	}

	public double getLeftDistanceInches() {
		return leftEncoder.get();
	}

	public double getRightDistanceInches() {
		return rightEncoder.get();
	}

	public double getLeftVelocityInchesPerSec() {
		return leftEncoderRate.get();
	}

	public double getRightVelocityInchesPerSec() {
		return rightEncoderRate.get();
	}

	// following 2 methods are necessary to be synchronized if using cheesypoof navx
	// class

	public synchronized Rotation2d getGyroAngle() {
		return gyro.get();
	}

	public synchronized void setGyroAngle(Rotation2d angle) {
		// reset gyro
		// gyro = () -> Rotation2d.fromDegrees(0);
		
		ahrs.zeroYaw();
		ahrs.setAngleAdjustment(angle.getDegrees());
	}

	public void configureTalonsForSpeedControl() {
		if (!usesTalonVelocityControl(mDriveControlState)) {
			// configure motor controllers for speed control here
			// no clue
			System.out.println("I hope I didn't need to do anything here");
			mDriveControlState = DriveControlState.VELOCITY_SETPOINT;
		}
	}

	public synchronized void setVelocitySetpoint(double left_inches_per_sec, double right_inches_per_sec) {
		configureTalonsForSpeedControl();
		mDriveControlState = DriveControlState.VELOCITY_SETPOINT;
		updateVelocitySetpoint(left_inches_per_sec, right_inches_per_sec);
	}

	private synchronized void updateVelocitySetpoint(double left_inches_per_sec, double right_inches_per_sec) {
		if (usesTalonVelocityControl(mDriveControlState)) {
			final double max_desired = Math.max(Math.abs(left_inches_per_sec), Math.abs(right_inches_per_sec));
			final double scale = max_desired > Constants.kDriveHighGearMaxSetpoint
					? Constants.kDriveHighGearMaxSetpoint / max_desired
					: 1.0;

			// set velocity setpoint here
			leftMaster.set(ControlMode.Velocity, left_inches_per_sec * scale);
			rightMaster.set(ControlMode.Velocity, right_inches_per_sec * scale);
		} else {
			System.out.println("Hit a bad velocity control state");

			// set velocity setpoint to 0
			leftMaster.set(ControlMode.Velocity, 0);
			rightMaster.set(ControlMode.Velocity, 0);
		}
	}

	protected static boolean usesTalonVelocityControl(DriveControlState state) {
		if (state == DriveControlState.VELOCITY_SETPOINT || state == DriveControlState.PATH_FOLLOWING) {
			return true;
		}
		return false;
	}

	@Override
	public synchronized void stop() {
		leftMaster.set(ControlMode.Velocity, 0);
		rightMaster.set(ControlMode.Velocity, 0);
	}

	@Override
	public void registerEnabledLoops(Looper in) {
		in.register(mLoop);
	}

	@Override
	public void zeroSensors() {
		// zero the sensors
		leftMaster.setSelectedSensorPosition(0, 0, 0);
		rightMaster.setSelectedSensorPosition(0, 0, 0);
		ahrs.zeroYaw();
		ahrs.setAngleAdjustment(0);
	}
}
