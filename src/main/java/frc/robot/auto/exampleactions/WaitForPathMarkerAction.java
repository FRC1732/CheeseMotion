package frc.robot.auto.exampleactions;

import frc.robot.main.Drive;

/**
 * Waits for the robot to pass by a provided path marker (i.e. a waypoint on the
 * field). This action routinely compares to the crossed path markers provided
 * by the drivetrain (in Path Control mode) and returns if the parameter path
 * marker is inside the drivetrain's Path Markers Crossed list
 * 
 * @param A
 *            Path Marker to determine if crossed
 */
public class WaitForPathMarkerAction implements Action {

	private Drive mDrive = Drive.getInstance();
	private String mMarker;

	public WaitForPathMarkerAction(String marker) {
		mMarker = marker;
	}

	@Override
	public boolean isFinished() {
		return mDrive.mPathFollower.hasPassedMarker(mMarker);
	}

	@Override
	public void update() {
	}

	@Override
	public void done() {
	}

	@Override
	public void start() {
	}

}