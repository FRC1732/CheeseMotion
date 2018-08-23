package frc.robot.auto.examplemodes;

import frc.robot.auto.AutoModeBase;
import frc.robot.auto.AutoModeEndedException;
import frc.robot.auto.exampleactions.DrivePathAction;
import frc.robot.auto.exampleactions.ResetPoseFromPathAction;
import frc.robot.examplepaths.CenterGearToShootBlue;
import frc.robot.examplepaths.PathContainer;
import frc.robot.examplepaths.StartToCenterGearBlue;

/**
 * Scores the preload gear onto the center peg then shoots the 10 preloaded fuel
 * 
 * @see AutoModeBase
 */
public class CenterGearThenShootModeBlue extends AutoModeBase {

	@Override
	protected void routine() throws AutoModeEndedException {
		PathContainer gearPath = new StartToCenterGearBlue();
		runAction(new ResetPoseFromPathAction(gearPath));
		runAction(new DrivePathAction(gearPath));
		// runAction(new DeployIntakeAction());
		// runAction(new ScoreGearAction());
		runAction(new DrivePathAction(new CenterGearToShootBlue()));
		// runAction(new BeginShootingAction());
		// runAction(new WaitAction(15));
	}
}
