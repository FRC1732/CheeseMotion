package frc.robot.examplepaths;

import frc.robot.auto.examplemodes.GearThenHopperShootModeBlue;
import frc.robot.examplepaths.profiles.PathAdapter;
import frc.robot.lib.util.math.RigidTransform2d;
import frc.robot.lib.util.control.Path;

/**
 * Path from the blue alliance wall to the blue boiler peg.
 * 
 * Used in GearThenHopperShootModeBlue
 * 
 * @see GearThenHopperShootModeBlue
 * @see PathContainer
 */
public class StartToBoilerGearBlue implements PathContainer {

	@Override
	public Path buildPath() {
		return PathAdapter.getBlueGearPath();
	}

	@Override
	public RigidTransform2d getStartPose() {
		return PathAdapter.getBlueStartPose();
	}

	@Override
	public boolean isReversed() {
		return true;
	}
}