package org.usfirst.frc.team1977.robot.commands.auto;

import org.usfirst.frc.team1977.robot.commands.drive.DriveStop;
import org.usfirst.frc.team1977.robot.commands.drive.DriveTime;
import org.usfirst.frc.team1977.robot.commands.grasper.FireGrasper;
import org.usfirst.frc.team1977.robot.commands.lift.RaiseLift;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PickUpMove extends CommandGroup {
    
	// Picks up tote and moves it to auto zone
	// Time needs to be adjusted
	// Speed needs to be adjusted as well
    public PickUpMove() {
       addSequential( new DriveTime( 0, 0.5, 500 ) );
       addSequential( new FireGrasper() );
       addParallel( new DriveTime(0, 1, 1500) );
       addSequential( new RaiseLift() );
    }
    
    protected void end() {
    	addSequential( new DriveStop() );
    }

}
