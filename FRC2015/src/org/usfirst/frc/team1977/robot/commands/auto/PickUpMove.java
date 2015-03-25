package org.usfirst.frc.team1977.robot.commands.auto;

import org.usfirst.frc.team1977.robot.commands.drive.DriveStop;
import org.usfirst.frc.team1977.robot.commands.drive.DriveTime;
import org.usfirst.frc.team1977.robot.commands.drive.TurnTime;
import org.usfirst.frc.team1977.robot.commands.grasper.FireGrasper;
import org.usfirst.frc.team1977.robot.commands.lift.RaiseLift;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PickUpMove extends CommandGroup {
    
	// Picks up tote, turn and moves it to auto zone
	// Time needs to be adjusted
	// Speed needs to be adjusted as well
    public PickUpMove() {
       addSequential( new FireGrasper() );
       addSequential( new RaiseLift() );
       addSequential( new TurnTime( 1000 ) );
       addSequential( new DriveTime( 0, 1, 15000 ) );
    }
    
    protected void end() {
    	addSequential( new DriveStop() );
    }

}
