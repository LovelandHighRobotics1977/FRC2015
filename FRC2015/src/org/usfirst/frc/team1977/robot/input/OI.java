package org.usfirst.frc.team1977.robot.input;

import org.usfirst.frc.team1977.robot.commands.pneumatics.FireTestPiston;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	private static OI instance;
	
	private XBoxController driveJoystick;
	
	public OI() {
		driveJoystick = new XBoxController(0);
	}
	
	public void init() {
		driveJoystick.yWhenPressed(new FireTestPiston(false));
		driveJoystick.aWhenPressed(new FireTestPiston(true));
	}
	
	public static OI getInstance() {
		if (instance == null) {
			instance = new OI();
		}
		return instance;
	}
	
	public XBoxController getDriveJoystick() {
		return driveJoystick;
	}
}

