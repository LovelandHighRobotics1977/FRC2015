package org.usfirst.frc.team1977.robot.commands.grasper;

import org.usfirst.frc.team1977.robot.commands.CommandBase;
import org.usfirst.frc.team1977.robot.subsystems.Grasper;

/**
 *
 */
public class FireGrasper extends CommandBase {

	/**
	 * The amount of frames through which a reed switch must remain tripped
	 * before it is considered to be an authentic stimulus.
	 */
	private static final int REED_TRIGGER_COUNT = 5;
	/**
	 * The amount of time with no reed trips considered sufficient for the
	 * entirety of the firing process to have terminated.
	 */
	private static final long TIMEOUT_MILLIS = 1000;
	// Runtime values
	private boolean wideTote = false;
	private boolean bin = false;
	private boolean longTote = false;
	private boolean fullClose = false;
	private int wideToteCount = 0;
	private int binCount = 0;
	private int longToteCount = 0;
	private int fullCloseCount = 0;
	private long lastTrip = 0;

	public FireGrasper() {
		requires(grasper);
	}

	protected void initialize() {
		lastTrip = System.currentTimeMillis();
		if (grasper.getState().equals(Grasper.State.Open)) {
			grasper.firePiston();
		}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (grasper.getWideReed()) {
			if (++wideToteCount >= REED_TRIGGER_COUNT) {
				wideTote = true;
			}
		} else {
			wideToteCount = 0;
		}
		if (grasper.getMidReed()) {
			if (++binCount >= REED_TRIGGER_COUNT) {
				bin = true;
			}
		} else {
			binCount = 0;
		}
		if (grasper.getThinReed()) {
			if (++longToteCount >= REED_TRIGGER_COUNT) {
				longTote = true;
			}
		} else {
			longToteCount = 0;
		}
		if (grasper.getCloseReed()) {
			if (++fullCloseCount >= REED_TRIGGER_COUNT) {
				fullClose = true;
			}
		} else {
			fullCloseCount = 0;
		}
		if (wideToteCount + binCount + longToteCount > 0) {
			lastTrip = System.currentTimeMillis();
		}
	}

	protected boolean isFinished() {
		return fullClose || (System.currentTimeMillis() - lastTrip) >= TIMEOUT_MILLIS;
	}

	protected void end() {
		if (fullClose) {
			grasper.setState(Grasper.State.Closed_Full);
		} else if (longTote) {
			grasper.setState(Grasper.State.Closed_Tote_Long);
		} else if (bin) {
			grasper.setState(Grasper.State.Closed_Bin);
		} else if (wideTote) {
			grasper.setState(Grasper.State.Closed_Tote_Wide);
		} else {
			grasper.setState(Grasper.State.Closed_Jam);
		}
	}

	protected void interrupted() {
		if (fullClose) {
			grasper.setState(Grasper.State.Closed_Full);
		} else if (longTote) {
			grasper.setState(Grasper.State.Closed_Tote_Long);
		} else if (bin) {
			grasper.setState(Grasper.State.Closed_Bin);
		} else if (wideTote) {
			grasper.setState(Grasper.State.Closed_Tote_Wide);
		} else {
			grasper.setState(Grasper.State.Closed_Jam);
		}
	}
}
