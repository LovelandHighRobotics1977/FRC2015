package org.usfirst.frc.team1977.robot.subsystems;

import org.usfirst.frc.team1977.robot.RobotMap;
import org.usfirst.frc.team1977.robot.commands.lift.POVLift;
import org.usfirst.frc.team1977.robot.input.RotaryEncoder;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author Loveland High Robotics 1977
 * @author Evan Stewart
 */
public class Lift extends Subsystem {

	private static Lift instance;
	// Constants
	private static final int WINCH_RAMP_RATE = 12; // volts/second
	private static final boolean WINCH_BRAKE = false;
	private static final long WINCH_TIMEOUT = 250;
	// Sensor references
	private RotaryEncoder winchEnc;
	private DigitalInput lowerSwitch;
	private DigitalInput upperSwitch;
	// Motor references
	private CANTalon winchMotor;
	// Runtime Values
	private long forwardLastRun = 0;
	private long backwardLastRun = 0; // Values to control lift timeout

	private Lift() {
		winchEnc = RotaryEncoder.getInstance();
		lowerSwitch = new DigitalInput(RobotMap.LIFT_BOTTOM_LIMIT);
		upperSwitch = new DigitalInput(RobotMap.LIFT_TOP_LIMIT);
		winchMotor = new CANTalon(RobotMap.LIFT_WINCH_TALONSRX_DEVICENUM);
		winchMotor.setVoltageRampRate(WINCH_RAMP_RATE);
		winchMotor.enableBrakeMode(WINCH_BRAKE);
	}

	public static Lift getInstance() {
		if (instance == null) {
			instance = new Lift();
		}
		return instance;
	}
	
	public void setWinch(double speed) {
		//TODO: Does the SRX ramping state automatically prevent rapid toggle?  Is this necessary?
		if (speed > 0 && System.currentTimeMillis() - forwardLastRun >= WINCH_TIMEOUT) {
			winchMotor.set(speed);
		} else if (speed < 0 && System.currentTimeMillis() - backwardLastRun >= WINCH_TIMEOUT) {
			winchMotor.set(speed);
		} else { // speed = 0, stop
			if (winchMotor.getSpeed() > 0) { //Forward
				forwardLastRun = System.currentTimeMillis();
			} else if (winchMotor.getSpeed() < 0) { //Backward
				backwardLastRun = System.currentTimeMillis();
			}
			winchMotor.set(speed);
		}
	}

	public void stopWinch() {
		setWinch(0);
	}

	public double getWinchSpeed() {
		return winchMotor.getSpeed();
	}

	public boolean liftFullyLowered() {
		return !lowerSwitch.get();
	}

	public boolean liftFullyRaised() {
		return !upperSwitch.get();
	}

	protected void initDefaultCommand() {
		//setDefaultCommand(new POVLift()); 
	}
}
