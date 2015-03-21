package org.usfirst.frc.team1977.robot;

import java.util.ArrayList;

import org.usfirst.frc.team1977.robot.commands.CommandBase;
import org.usfirst.frc.team1977.robot.input.OI;
import org.usfirst.frc.team1977.robot.input.VisionClient;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	/**
	 * The onboard VIAIR 90C pneumatic compressor. Given that multiple
	 * subsystems may have pneumatic elements, the hardware reference for this
	 * is delegated to the overarching robot class.
	 */
	private Compressor compressor;
	/**
	 * Store a instance of the BIA to be accessible to the rest of the robot
	 * processes from this class. To be honest, I'm not sure why
	 * BuiltInAccelerometer isn't singleton to begin with.
	 */
	private static BuiltInAccelerometer builtInAccelerometer;
	// TODO: Make this an actual thing
	private Command autonomousCommand;
	int i;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		// Initialize the CommandBase, which will also initialize all active
		// subsystems
		CommandBase.init();
		OI.getInstance().init();
		compressor = new Compressor(); // Once initialized, the PCM will
										// automatically handle operation.
		builtInAccelerometer = new BuiltInAccelerometer();
		VisionClient.getInstance();
		// instantiate the command used for the autonomous period
	}

	public void autonomousInit() {
		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		i = 0;
	}

	/**
	 * This function is called when the disabled button is hit. You can use it
	 * to reset subsystems before shutting down.
	 */
	public void disabledInit() {

	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		/**i++;
		if (i % 500 == 0) {
			i = 0;
			ArrayList<VisionClient.Stack> stacks = VisionClient.getInstance().getVisibleStacks(true);
			for (VisionClient.Stack s : stacks) {
				System.out.println("STACK DATA: X:" + s.getPixelX() + ", Y: " + s.getPixelY() + ", NumStacked: " + s.getNumStacked() + ".");
			}
		}**/
	}

	/**
	 * 
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
	}

	/**
	 * Provide robot-wide access to a single instance of the BIA interface to
	 * avoid redundant initializations and instances.
	 * 
	 * @return The Robot class's interface to the RRio accelerometer.
	 */
	public static BuiltInAccelerometer getAccelerometer() {
		return builtInAccelerometer;
	}
}
