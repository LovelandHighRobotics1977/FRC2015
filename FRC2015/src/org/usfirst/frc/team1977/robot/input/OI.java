package org.usfirst.frc.team1977.robot.input;

import org.usfirst.frc.team1977.robot.commands.drive.SpeedToggle;
import org.usfirst.frc.team1977.robot.commands.grasper.FireGrasper;
import org.usfirst.frc.team1977.robot.commands.grasper.ReleaseGrasper;
import org.usfirst.frc.team1977.robot.commands.lift.LowerLift;
import org.usfirst.frc.team1977.robot.commands.lift.RaiseLift;

/**
 * Struct class primarily storing joystick interface references and command
 * mappings. Initializing this is necessary to link most commands to their
 * relevant user input events, and as such neglecting to do so will inhibit most
 * robot functionality.
 * 
 * @author Loveland High Robotics 1977
 * @author Evan Stewart
 */
public class OI {
	/**
	 * Singleton instance reference
	 */
	private static OI instance;
	// XBox Controller references
	private XBoxController driveJoystick;
	private XBoxController manipulatorJoystick; // For lack of a better name.

	/**
	 * Constructs the OI class, initializing all relevant references. To be
	 * called privately from getInstance().
	 */
	private OI() {
		driveJoystick = new XBoxController(0);
		manipulatorJoystick = new XBoxController(1);
	}

	/**
	 * Sets up the joystick mappings stored in OI. If OI is not initialized,
	 * user input during teleop will be largely ignored for certain subsystems.
	 */
	public void init() {
		// TODO: Map joystick inputs to commands here.
		driveJoystick.leftThumbWhenPressed(new SpeedToggle());
		driveJoystick.xWhenPressed(new ReleaseGrasper());
		driveJoystick.bWhenPressed(new FireGrasper());
		driveJoystick.rightWhileHeld(new RaiseLift());
		driveJoystick.leftWhileHeld(new LowerLift());
		//driveJoystick.yWhenPressed(new EngageSuction());
		//driveJoystick.aWhenPressed(new ReleaseSuction());
	}

	/**
	 * Access the singleton instance of OI, constructing it if necessary.
	 * 
	 * @return The singleton instance of OI.
	 */
	public static OI getInstance() {
		if (instance == null) {
			instance = new OI();
		}
		return instance;
	}

	/**
	 * Access the interface for the XBox controller associated with the
	 * drivetrain and relevant systems.
	 * 
	 * @return The drive joystick interface.
	 */
	public XBoxController getDriveJoystick() {
		return driveJoystick;
	}

	/**
	 * Access the interface for the XBox controller associated with the lift,
	 * grasper, and other manipulator systems.
	 * 
	 * @return The manipulator joystick interface.
	 */
	public XBoxController getManipulatorJoystick() {
		return manipulatorJoystick;
	}
}
