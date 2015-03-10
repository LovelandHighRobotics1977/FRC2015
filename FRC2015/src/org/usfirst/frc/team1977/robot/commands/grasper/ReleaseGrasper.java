package org.usfirst.frc.team1977.robot.commands.grasper;

import org.usfirst.frc.team1977.robot.commands.CommandBase;
import org.usfirst.frc.team1977.robot.subsystems.Grasper;

/**
 *
 */
public class ReleaseGrasper extends CommandBase {

	/**
	 * The amount of time required to have elapsed without tripping the open
	 * reed for it to be assumed that the grasper is jammed.
	 */
	private static final long TIMEOUT_MILLIS = 1000;
	// Runtime values
	private long retractTime = 0;

	public ReleaseGrasper() {
		requires(grasper);
	}

	protected void initialize() {
		//if (!grasper.getState().equals(Grasper.State.Open)) {
			grasper.retractPiston();
			//retractTime = System.currentTimeMillis();
		//}
	}

	protected void execute() {
	}

	protected boolean isFinished() {
		//return (grasper.getOpenReed() || (System.currentTimeMillis() - retractTime) >= TIMEOUT_MILLIS);
		return true;
	}

	protected void end() {
		//grasper.setState(grasper.getOpenReed() ? Grasper.State.Open : Grasper.State.Closed_Jam);
	}

	protected void interrupted() {
		//grasper.setState(grasper.getOpenReed() ? Grasper.State.Open : Grasper.State.Closed_Jam);
	}
}
