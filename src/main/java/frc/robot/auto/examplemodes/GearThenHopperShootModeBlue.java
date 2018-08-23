package frc.robot.auto.examplemodes;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.auto.AutoModeBase;
import frc.robot.auto.AutoModeEndedException;
import frc.robot.auto.exampleactions.CorrectPoseAction;
import frc.robot.auto.exampleactions.DrivePathAction;
import frc.robot.auto.exampleactions.ResetPoseFromPathAction;
import frc.robot.examplepaths.BoilerGearToHopperBlue;
import frc.robot.examplepaths.PathContainer;
import frc.robot.examplepaths.StartToBoilerGearBlue;
import frc.robot.examplepaths.profiles.PathAdapter;
import frc.robot.lib.util.math.RigidTransform2d;

/**
 * Scores the preload gear onto the boiler-side peg then deploys the hopper and
 * shoots all 60 balls (10 preload + 50 hopper).
 * 
 * This was the primary autonomous mode used at SVR, St. Louis Champs, and FOC.
 * 
 * @see AutoModeBase
 */
public class GearThenHopperShootModeBlue extends AutoModeBase {

	@Override
	protected void routine() throws AutoModeEndedException {
		PathContainer gearPath = new StartToBoilerGearBlue();
		double start = Timer.getFPGATimestamp();
		runAction(new ResetPoseFromPathAction(gearPath));
		runAction(new DrivePathAction(gearPath));

		// runAction(new ParallelAction(
		// Arrays.asList(new Action[] { new DrivePathAction(gearPath), new
		// ActuateHopperAction(true), })));

		// runAction(new ParallelAction(Arrays.asList(new Action[] { new
		// SetFlywheelRPMAction(2900.0), // spin up flywheel
		// // to save time
		// new ScoreGearAction(), new DeployIntakeAction(true) })));
		runAction(new CorrectPoseAction(RigidTransform2d.fromTranslation(PathAdapter.getBlueGearCorrection())));
		runAction(new DrivePathAction(new BoilerGearToHopperBlue()));
		System.out.println("Shoot Time: " + (Timer.getFPGATimestamp() - start));
		// runAction(new BeginShootingAction());
		// runAction(new WaitAction(15));
	}
}
