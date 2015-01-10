	package org.usfirst.frc.team1977.robot.commands;

import org.usfirst.frc.team1977.robot.input.OI;
import org.usfirst.frc.team1977.robot.subsystems.Drive;
import org.usfirst.frc.team1977.robot.subsystems.Pneumatics;

import edu.wpi.first.wpilibj.command.Command;

/**
 *@author Loveland High Robotics 1977
 *@author Evan Stewart
 *A shell parent class extending Command in order to provide a few extra, general purpose characteristics
 *to each of the commands used on the robot.  In particular, inherited access to all subsystems and other
 *important robot elements, such as the OI.
 */
public abstract class CommandBase extends Command {
	
	protected static OI oi;
	protected static Drive drive;
	protected static Pneumatics pneumatics;

   public static void init() {
	   oi = OI.getInstance();
	   drive = Drive.getInstance();
	   pneumatics = Pneumatics.getInstance();
   }
}