package frc.robot.main;

/**
 * A list of constants used by the rest of the robot code. This include physics
 * constants as well as constants determined through calibrations.
 */
public class Constants {
	public static final double kLooperDt = 0.005;

	/* ROBOT PHYSICAL CONSTANTS */

	// Wheels
	public static final double kDriveWheelDiameterInches = 6.0;
	public static final double kTrackWidthInches = 23.5;
	public static final double kTrackScrubFactor = 1;

	// Geometry
	public static final double kCenterToFrontBumperDistance = 18.25;
	public static final double kCenterToIntakeDistance = 26.5;
	public static final double kCenterToRearBumperDistance = 18.25;
	public static final double kCenterToSideBumperDistance = 16.5;

	/* CONTROL LOOP GAINS */

	// PID gains for drive velocity loop (HIGH GEAR)
	// Units: setpoint, error, and output are in inches per second.
	public static final double kDriveHighGearVelocityKp = 1.2;
	public static final double kDriveHighGearVelocityKi = 0.0;
	public static final double kDriveHighGearVelocityKd = 6.0;
	public static final double kDriveHighGearVelocityKf = .15;
	public static final int kDriveHighGearVelocityIZone = 0;
	public static final double kDriveHighGearVelocityRampRate = 240.0;
	public static final double kDriveHighGearNominalOutput = 0.5;
	public static final double kDriveHighGearMaxSetpoint = 17.0 * 12.0; // 17 fps

	// PID gains for drive velocity loop (LOW GEAR)
	// Units: setpoint, error, and output are in inches per second.
	public static final double kDriveLowGearPositionKp = 1.0;
	public static final double kDriveLowGearPositionKi = 0.002;
	public static final double kDriveLowGearPositionKd = 100.0;
	public static final double kDriveLowGearPositionKf = .45;
	public static final int kDriveLowGearPositionIZone = 700;
	public static final double kDriveLowGearPositionRampRate = 240.0; // V/s
	public static final double kDriveLowGearNominalOutput = 0.5; // V
	public static final double kDriveLowGearMaxVelocity = 6.0 * 12.0 * 60.0 / (Math.PI * kDriveWheelDiameterInches);
	// 6 fps in RPM
	public static final double kDriveLowGearMaxAccel = 18.0 * 12.0 * 60.0 / (Math.PI * kDriveWheelDiameterInches);
	// 18 fps/s in RPM/s

	public static final double kDriveVoltageCompensationRampRate = 0.0;

	// Path following constants
	public static final double kMinLookAhead = 12.0; // inches
	public static final double kMinLookAheadSpeed = 9.0; // inches per second
	public static final double kMaxLookAhead = 24.0; // inches
	public static final double kMaxLookAheadSpeed = 120.0; // inches per second
	public static final double kDeltaLookAhead = kMaxLookAhead - kMinLookAhead;
	public static final double kDeltaLookAheadSpeed = kMaxLookAheadSpeed - kMinLookAheadSpeed;

	public static final double kInertiaSteeringGain = 0.0;
	// angular velocity command is multiplied by this gain * our speed in inches per sec
	public static final double kSegmentCompletionTolerance = 0.1; // inches
	public static final double kPathFollowingMaxAccel = 120.0; // inches per second^2
	public static final double kPathFollowingMaxVel = 120.0; // inches per second
	public static final double kPathFollowingProfileKp = 5.00;
	public static final double kPathFollowingProfileKi = 0.03;
	public static final double kPathFollowingProfileKv = 0.02;
	public static final double kPathFollowingProfileKffv = 1.0;
	public static final double kPathFollowingProfileKffa = 0.05;
	public static final double kPathFollowingGoalPosTolerance = 0.75;
	public static final double kPathFollowingGoalVelTolerance = 12.0;
	public static final double kPathStopSteeringDistance = 9.0;

}