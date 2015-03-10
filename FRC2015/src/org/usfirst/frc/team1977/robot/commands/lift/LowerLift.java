package org.usfirst.frc.team1977.robot.commands.lift;

import org.usfirst.frc.team1977.robot.commands.CommandBase;

/**
 *
 */
public class LowerLift extends CommandBase {

    public LowerLift() {
        requires(lift);
    }

    protected void initialize() {
    	if (!lift.liftFullyLowered()) {
    		lift.setWinch(-1);
    	}
    }

    protected void execute() {
    	if (lift.getWinchSpeed() != -1 && !lift.liftFullyLowered()) {
    		lift.setWinch(-1);
    	}
    }

    protected boolean isFinished() {
        //return lift.liftFullyLowered();
    	return false;
    }

    protected void end() {
    	lift.stopWinch();
    }
    
    protected void interrupted() {
    	lift.stopWinch();
    }
}
