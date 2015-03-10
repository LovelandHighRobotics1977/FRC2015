package org.usfirst.frc.team1977.robot.subsystems;

import org.usfirst.frc.team1977.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Grasper extends Subsystem {
	public enum State {
		Open, Closed_Jam, Closed_Tote_Wide, Closed_Bin, Closed_Tote_Long, Closed_Full;
	}

	/**
	 * Singleton instance reference for the subsystem
	 */
	private static Grasper instance;
	// Pneumatic interfaces
	private DoubleSolenoid grasperPiston;
	private DoubleSolenoid suction; // TODO: Are we using this?
	// Sensor Interfaces
	private DigitalInput openReed; // TODO: Are we using this?
	private DigitalInput wideToteReed;
	private DigitalInput binReed;
	private DigitalInput longToteReed;
	private DigitalInput closeReed;
	private DigitalInput suctionLimit;
	// Runtime variables
	private State state;
	private boolean suctionEngaged = false;

	// TODO: Associate compressor with this system? Or with the general robot?
	// TODO: Any sensor interfaces? Possibly (a) reed switch(s)?

	/**
	 * Create a new instance of the Grasper subsystem, initializing all relevant
	 * actuator and sensor references. Should only be called internally by
	 * getInstance().
	 */
	private Grasper() {
		grasperPiston = new DoubleSolenoid(RobotMap.GRASPER_PISTON_FORWARD,
				RobotMap.GRASPER_PISTON_REVERSE);
		suction = new DoubleSolenoid(RobotMap.GRASPER_SUCTION_FORWARD,
				RobotMap.GRASPER_SUCTION_REVERSE);
		wideToteReed = new DigitalInput(RobotMap.GRASPER_WIDE_REED);
		binReed = new DigitalInput(RobotMap.GRASPER_MID_REED);
		longToteReed = new DigitalInput(RobotMap.GRASPER_THIN_REED);
		suctionLimit = new DigitalInput(RobotMap.GRASPER_SUCTION_LIMIT);
		// The grasper is assumed to begin in one of the two absolute states;
		// any other state is assumed jammed
		//state = !closeReed.get() ? State.Closed_Full
		//		: !openReed.get() ? State.Open : State.Closed_Jam;
	}

	/**
	 * Access the singleton instance of the Grasper subsystem. Will also
	 * function to construct it if necessary.
	 * 
	 * @return Singleton instance of the Grasper subsystem
	 */
	public static Grasper getInstance() {
		if (instance == null) {
			instance = new Grasper();
		}
		return instance;
	}

	/**
	 * Sets the piston solenoid to the forward state, extending the piston and
	 * opening the grasper.
	 */
	public void firePiston() {
		grasperPiston.set(DoubleSolenoid.Value.kForward);
	}

	/**
	 * Sets the piston solenoid to the reverse state, retracting the piston and
	 * closing the grasper.
	 */
	public void retractPiston() {
		grasperPiston.set(DoubleSolenoid.Value.kReverse);
	}

	/**
	 * Get whether or not the grasper piston is extended; i.e. whether or not
	 * the grasper is open. Checks the state of the solenoid as opposed to
	 * external sensor readout.
	 * 
	 * @return True if the solenoid is in forward state (piston extended); false
	 *         otherwise.
	 */
	public boolean pistonIsExtended() {
		return grasperPiston.get().equals(DoubleSolenoid.Value.kForward);
	}

	/**
	 * Sets the suction solenoid to the forward state, applying suction. May or
	 * may not actually adhere to a surface.
	 */
	public void fireSuction() {
		suction.set(DoubleSolenoid.Value.kForward);
	}

	/**
	 * Sets the suction solenoid to the reverse state, releasing any generated
	 * suction in the mechanism.
	 */
	public void releaseSuction() {
		suction.set(DoubleSolenoid.Value.kReverse);
	}

	/**
	 * Get whether or not the solenoid for the suction is in forward position,
	 * or whether or not the suction has been fired so as to create a vacuum.
	 * Note that this will not tell whether or not the suction has actually
	 * attached to a surface.
	 * 
	 * @return True if the solenoid is in a forward state (i.e. suction has been
	 *         fired); false otherwise
	 */
	public boolean suctionIsActive() {
		return suction.get().equals(DoubleSolenoid.Value.kForward);
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public boolean getOpenReed() {
		return !openReed.get();
	}
	
	public boolean getWideReed() {
		return !wideToteReed.get();
	}

	public boolean getMidReed() {
		return !binReed.get();
	}

	public boolean getThinReed() {
		return !longToteReed.get();
	}

	public boolean getCloseReed() {
		return !closeReed.get();
	}
	
	public boolean getSuctionLimit() {
		return suctionLimit.get();
	}
	
	public boolean isSuctionEngaged() {
		return suctionEngaged;
	}
	
	public void setSuctionEngaged(boolean suctionEngaged) {
		this.suctionEngaged = suctionEngaged;
	}

	protected void initDefaultCommand() {
		setDefaultCommand(null); // No default command
	}
}
