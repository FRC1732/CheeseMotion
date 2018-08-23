package frc.robot.examplepaths;

import frc.robot.auto.examplemodes.GearThenHopperShootModeBlue;
import frc.robot.examplepaths.profiles.PathAdapter;
import frc.robot.lib.util.math.RigidTransform2d;
import frc.robot.lib.util.math.Rotation2d;
import frc.robot.lib.util.math.Translation2d;
import frc.robot.lib.util.control.Path;

/**
 * Path from the blue boiler side peg to the blue hopper.
 * 
 * Used in GearThenHopperShootModeBlue
 * 
 * @see GearThenHopperShootModeBlue
 * @see PathContainer
 */
public class BoilerGearToHopperBlue implements PathContainer {

	@Override
	public Path buildPath() {
		return PathAdapter.getBlueHopperPath();
	}

	@Override
	public RigidTransform2d getStartPose() {
		return new RigidTransform2d(new Translation2d(116, 209), Rotation2d.fromDegrees(0.0));
	}

	@Override
	public boolean isReversed() {
		return false;
	}

}
