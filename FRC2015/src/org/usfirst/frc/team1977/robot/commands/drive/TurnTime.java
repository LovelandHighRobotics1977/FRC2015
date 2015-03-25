package org.usfirst.frc.team1977.robot.commands.drive;

import org.usfirst.frc.team1977.robot.commands.CommandBase;

/**
 *Turn for amount of time
 */
public class TurnTime extends CommandBase {
	
	long startTime;
	long endTime;
	
    public TurnTime( long time ) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires( drive );
    	startTime = System.currentTimeMillis();
    	endTime = startTime + time;
    }

    // Called just before this Command runs the first time
    protected void initialize() {

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	drive.drive( 0, 0, 1 );
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return ( System.currentTimeMillis() >= endTime );
    }

    // Called once after isFinished returns true
    protected void end() {
    	drive.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	drive.stop();
    }
}
