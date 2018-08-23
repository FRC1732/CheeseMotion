package frc.robot.auto.exampleactions;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.examplepaths.PathContainer;
import frc.robot.lib.util.math.RigidTransform2d;
import frc.robot.main.Drive;
import frc.robot.main.RobotState;

/**
 * Resets the robot's current pose based on the starting pose stored in the
 * pathContainer object.
 * 
 * @see PathContainer
 * @see Action
 * @see RunOnceAction
 */
public class ResetPoseFromPathAction extends RunOnceAction {

	protected PathContainer mPathContainer;

	public ResetPoseFromPathAction(PathContainer pathContainer) {
		mPathContainer = pathContainer;
	}

	@Override
	public synchronized void runOnce() {
		RigidTransform2d startPose = mPathContainer.getStartPose();
		RobotState.getInstance().reset(Timer.getFPGATimestamp(), startPose);
		Drive.getInstance().setGyroAngle(startPose.getRotation());
	}
}
