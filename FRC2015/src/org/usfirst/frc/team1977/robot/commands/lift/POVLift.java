package org.usfirst.frc.team1977.robot.commands.lift;

import org.usfirst.frc.team1977.robot.commands.CommandBase;

/**
 *
 */
public class POVLift extends CommandBase {

    public POVLift() {
        requires(lift);
    }

    protected void initialize() {
    	//Do nothing
    }

    protected void execute() {
    	double theta = oi.getDriveJoystick().getPOV(0);
    	System.out.println("Theta " + theta);
    	if (theta > 180 /*&& !lift.liftFullyLowered()*/) {
    		double x = Math.sin(theta);
    		System.out.println(x);
    		lift.setWinch(x);
    	} else if (theta < 180 && theta >= 0 /*&& !lift.liftFullyRaised()*/) {
    		double x = Math.sin(theta);
    		System.out.println(x);
    		lift.setWinch(x);
    	} else {
    		lift.stopWinch();
    	}
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	lift.stopWinch();
    }

    protected void interrupted() {
    	lift.stopWinch();
    }
}
