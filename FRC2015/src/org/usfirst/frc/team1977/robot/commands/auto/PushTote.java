package org.usfirst.frc.team1977.robot.commands.auto;

import org.usfirst.frc.team1977.robot.commands.drive.DriveStop;
import org.usfirst.frc.team1977.robot.commands.drive.DriveTime;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PushTote extends CommandGroup {
	// Time needs to be adjusted
	// Speed needs to be adjusted
	// Drive straight forward and push tote into zone
	public PushTote() {
		addSequential( new DriveTime( 0, 0.75, 2000 ) );
	}
	
	protected void end() {
		addSequential( new DriveStop() );
	}
}
