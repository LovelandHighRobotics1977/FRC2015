package org.usfirst.frc.team1977.robot.commands.grasper;

import org.usfirst.frc.team1977.robot.commands.CommandBase;

/**
 *
 */
public class EngageSuction extends CommandBase {

    public EngageSuction() {
        requires(grasper);
    }

    protected void initialize() {
    	if (!grasper.isSuctionEngaged() && !grasper.getSuctionLimit()) {
    		grasper.fireSuction();
    	}
    }

    protected void execute() {
    	//Do nothing
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    	if (!grasper.getSuctionLimit()) {
    		grasper.setSuctionEngaged(true);
    	}
    }

    protected void interrupted() {
    	if (!grasper.getSuctionLimit()) {
    		grasper.setSuctionEngaged(true);
    	}
    }
}
