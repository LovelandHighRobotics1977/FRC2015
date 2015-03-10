package org.usfirst.frc.team1977.robot.commands.lift;

import org.usfirst.frc.team1977.robot.commands.CommandBase;

/**
 *
 */
public class RaiseLift extends CommandBase {

	public RaiseLift() {
		requires(lift);
	}

	protected void initialize() {
		if (!lift.liftFullyRaised()) {
			lift.setWinch(1);
		}
	}

	protected void execute() {
		if (lift.getWinchSpeed() != 1 && !lift.liftFullyRaised()) {
			lift.setWinch(1);
		}
	}

	protected boolean isFinished() {
		//return lift.liftFullyRaised();
		return false;
	}

	protected void end() {
		lift.stopWinch();
	}

	protected void interrupted() {
		lift.stopWinch();
	}
}
