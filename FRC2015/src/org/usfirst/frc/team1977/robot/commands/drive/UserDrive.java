package org.usfirst.frc.team1977.robot.commands.drive;

import org.usfirst.frc.team1977.robot.commands.CommandBase;
import org.usfirst.frc.team1977.robot.subsystems.Pneumatics;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SensorBase;


/**
 *
 */
public class UserDrive extends CommandBase {
	
	BuiltInAccelerometer bia = new BuiltInAccelerometer();
	
    public UserDrive() {
        requires(drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double hPower = oi.getDriveJoystick().getLeftX();
        double vPower = oi.getDriveJoystick().getLeftY();
        double turn = (oi.getDriveJoystick().getLeftTriggerAxis() * 0.7) - (oi.getDriveJoystick().getRightTriggerAxis() * 0.7);
      
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
        
        oi.getDriveJoystick().setRumble(Joystick.RumbleType.kLeftRumble, (float) Math.abs(bia.getX()));
        oi.getDriveJoystick().setRumble(Joystick.RumbleType.kRightRumble, (float) Math.abs(bia.getY()));

        /**if (Robot.isSafetyLocked()) {
            drive.drive(-hPower / 2.0, -vPower / 2.0, -turn / 2.0);
        } else {
            drive.drive(-hPower, -vPower, -turn);
        }**/
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
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
