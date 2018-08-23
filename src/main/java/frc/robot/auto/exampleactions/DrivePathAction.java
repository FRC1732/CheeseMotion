package frc.robot.auto.exampleactions;

import frc.robot.examplepaths.PathContainer;
import frc.robot.main.Drive;
import frc.robot.lib.util.control.Path;

/**
 * Drives the robot along the Path defined in the PathContainer object. The
 * action finishes once the robot reaches the end of the path.
 * 
 * @see PathContainer
 * @see Path
 * @see Action
 */
public class DrivePathAction implements Action {

	private PathContainer mPathContainer;
	private Path mPath;
	private Drive mDrive = Drive.getInstance();

	public DrivePathAction(PathContainer p) {
		mPathContainer = p;
		mPath = mPathContainer.buildPath();
	}

	@Override
	public boolean isFinished() {
		return mDrive.mPathFollower.isDoneWithPath();
	}

	@Override
	public void update() {
		// Nothing done here, controller updates in mEnabedLooper in robot
	}

	@Override
	public void done() {
		// TODO: Perhaps set wheel velocity to 0?
	}

	@Override
	public void start() {
		mDrive.mPathFollower.setWantDrivePath(mPath, mPathContainer.isReversed());
	}
}
