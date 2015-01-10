package org.usfirst.frc.team1977.robot.commands.pneumatics;

import org.usfirst.frc.team1977.robot.commands.CommandBase;
import org.usfirst.frc.team1977.robot.subsystems.Pneumatics;

/**
 *
 */
public class FireTestPiston extends CommandBase {
	
	private final boolean retract;

    public FireTestPiston(boolean retract) {
        requires(Pneumatics.getInstance());
        this.retract = retract;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (retract) {
    		pneumatics.retractTestPiston();
    	} else {
    		pneumatics.extendTestPiston();
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//Do nothing
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
