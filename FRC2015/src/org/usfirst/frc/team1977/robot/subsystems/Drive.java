package org.usfirst.frc.team1977.robot.subsystems;

import org.usfirst.frc.team1977.robot.RobotMap;
import org.usfirst.frc.team1977.robot.commands.drive.UserDrive;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *@author Loveland High Robotics 1977
 *@author Evan Stewart
 *Temporary mecanum based drive SS.  May or may not be replaced by the
 *end of the design cycle.
 */
public class Drive extends Subsystem {
	
	private static Drive instance;
    
    private Talon frontLeft;
    private Talon frontRight;
    private Talon backLeft;
    private Talon backRight;
    
    private UserDrive userDrive;
    
    private boolean speedToggle = false;
    private double voltageCoefficient = 1;
    private double turnPowerCoefficient = 1;

    public Drive() {
    	frontLeft = new Talon(RobotMap.DRIVE_FRONT_LEFT_TALON);
    	frontRight = new Talon(RobotMap.DRIVE_FRONT_RIGHT_TALON);
    	backLeft = new Talon(RobotMap.DRIVE_BACK_LEFT_TALON);
    	backRight = new Talon(RobotMap.DRIVE_BACK_RIGHT_TALON);
    }
    
    public void initDefaultCommand() {
       userDrive = new UserDrive();
       setDefaultCommand(userDrive);
    }
    
    public static Drive getInstance() {
    	if (instance == null) {
    		instance = new Drive();
    	}
    	return instance;
    }
    
    /**
     * Set the four drive victors based upon the specified power values 
     * multiplied by the drivetrain voltage coefficient.
     * @param hPower Horizontal (strafing) power.  Pulled from the X axis of the
     * left stick.
     * @param vPower Vertical (forward) power.  Pulled from the Y axis of the 
     * left stick.
     * @param turn Turning power.  Pulled from the left and right triggers.
     */
    public void drive(double hPower, double vPower, double turn) {
        frontLeft.set((vPower - hPower + (turn * turnPowerCoefficient)) * voltageCoefficient);
        frontRight.set((-vPower - hPower + (turn * turnPowerCoefficient)) * voltageCoefficient);
        backLeft.set((vPower + hPower + (turn * turnPowerCoefficient)) * voltageCoefficient);
        backRight.set((-vPower + hPower + (turn * turnPowerCoefficient)) * voltageCoefficient);
    }
    
    public void stop() {
    	drive(0, 0, 0);
    }
    
    public boolean isSpeedToggle() {
    	return speedToggle;
    }
    
    public void setSpeedToggle(boolean speedToggle) {
    	this.speedToggle = speedToggle;
    	if (speedToggle) {
    		voltageCoefficient = 0.5;
    	} else {
    		voltageCoefficient = 1.0;
    	}
    }
}

