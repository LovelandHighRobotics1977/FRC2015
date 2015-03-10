package org.usfirst.frc.team1977.robot.commands.grasper;

import org.usfirst.frc.team1977.robot.commands.CommandBase;

/**
 *
 */
public class ReleaseSuction extends CommandBase {

    public ReleaseSuction() {
        requires(grasper);
    }

    protected void initialize() {
    	if (grasper.isSuctionEngaged()) {
    		grasper.releaseSuction();
    	}
    }

    protected void execute() {
    	//Do nothing
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    	grasper.setSuctionEngaged(false);
    }

    protected void interrupted() {
    	grasper.setSuctionEngaged(false);
    }
}
