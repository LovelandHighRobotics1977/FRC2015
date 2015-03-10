package org.usfirst.frc.team1977.robot.commands.drive;

import org.usfirst.frc.team1977.robot.Robot;
import org.usfirst.frc.team1977.robot.commands.CommandBase;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.Joystick;

/**
 * The default (and only) teleop command of the drivetrain subsystem. Takes live
 * input from the mapped XBox joystick in order to determine the vector values
 * with which to calculate and set the CIMs responsible for direct-driving the
 * four mecanums. Seeing as user input is handled within execute(), and that the
 * command is default for its subsystem, UserDrive should not be mapped or
 * referenced in OI.
 * 
 * @author Loveland High Robotics 1977
 * @author Evan Stewart
 */
public class UserDrive extends CommandBase {

	BuiltInAccelerometer bia;

	/**
	 * Create a new instance of UserDrive and establish its subsystem
	 * dependencies.
	 */
	public UserDrive() {
		requires(drive);
	}

	/**
	 * Prepare the UserDrive command for execution, resolving any necessary
	 * references.
	 */
	protected void initialize() {
		bia = Robot.getAccelerometer();
	}

	/**
	 * The execution cycle of UserDrive, to be run on a cycle-by-cycle basis.
	 * Retrieves the vectors from the XBox joystick and triggers, cuts off
	 * excessively low values, passes the vectors to the drive subsystem, and
	 * establishes the driver rumble output based on accelerometer data.
	 */
	protected void execute() {
		double hPower = oi.getDriveJoystick().getLeftX();
		double vPower = -oi.getDriveJoystick().getLeftY();
		double turn = (oi.getDriveJoystick().getLeftTriggerAxis() * 0.7)
				- (oi.getDriveJoystick().getRightTriggerAxis() * 0.7);

		if (Math.abs(hPower) < .2) {
			hPower = 0;
		}

		if (Math.abs(vPower) < .2) {
			vPower = 0;
		}

		if (Math.abs(turn) < .2) {
			turn = 0;
		}

		drive.drive(-hPower, -vPower, -turn);

		oi.getDriveJoystick().setRumble(Joystick.RumbleType.kLeftRumble,
				(float) Math.abs(bia.getX()));
		oi.getDriveJoystick().setRumble(Joystick.RumbleType.kRightRumble,
				(float) Math.abs(bia.getY()));
	}

	/**
	 * Returns true when the command should terminate; thusly, never returns
	 * true.
	 */
	protected boolean isFinished() {
		return false;
	}

	/**
	 * If UserDrive is forced to terminate, stop any moving drive motors.
	 */
	protected void end() {
		drive.stop();
	}

	/**
	 * If UserDrive is forced to terminate, stop any moving drive motors.
	 */
	protected void interrupted() {
		drive.stop();
	}
}
