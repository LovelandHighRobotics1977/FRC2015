package org.usfirst.frc.team1977.robot.input;

import org.usfirst.frc.team1977.robot.RobotMap;
import org.usfirst.frc.team1977.robot.subsystems.Lift;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.Timer;

/**
 * An interface for the six-divet two-switch rotary encoder custom-machined for
 * the lift (throughout the code the unoffical name for this sensor unit is
 * CMMRE, for Custom Machined Mechanical Rotary Encoder). Direction cannot
 * reliably be determined with just the two limit setup featured here, and is
 * thusly pulled from the relevant motor in order count accurately. For the
 * moment, runs on an experimental thread-based model that should handle updates
 * in parallel to the main control thread; testing will be necessary in order to
 * determine if this will damage performance on the new Rio, and if switching to
 * a controlled-update based model will be necessary.
 * 
 * @author Loveland High Robotics 1977
 * @author Evan Stewart
 */
public class RotaryEncoder extends SensorBase implements Runnable {
	/**
	 * Singleton instance reference.
	 */
	private static RotaryEncoder instance;
	/**
	 * Constant time value corresponding to the time allowed in milliseconds for
	 * random switch output oscillations as a result of state changes. After a
	 * change is first registered in either digital switch, wait this amount of
	 * time before checking to ensure that the obtained value accurately
	 * represents the new state.
	 */
	private static final long SWITCH_BOUNCE_CHECK_MILLIS = 10;
	/**
	 * The number of counts corresponding to an inch in vertical motion on the
	 * lift. TODO: Find the circumference of the encoder cylinder in order to
	 * determine this; the current value is a placeholder.
	 */
	private static final double COUNTS_PER_INCH = 4;
	// Hardware interface references.
	private Lift lift;
	private DigitalInput forwardLimit;
	private DigitalInput backwardLimit;
	/**
	 * Thread to control updates of the CMMRE unit. Nearly all related methods
	 * are synchronized (including the update process, due to the memory access
	 * involved); instead of boosted efficiency, the purpose of this thread is
	 * simply to separate CMMRE processes from the main robot control loop.
	 */
	private Thread updateThread;
	// Runtime variables.
	private boolean enabled = false;
	private int count = 0;
	private boolean forwardChangeTrigger = false;
	private boolean backwardChangeTrigger = false;
	private boolean forwardLastState;
	private boolean backwardLastState;
	private long fctTime = 0;
	private long bctTime = 0;

	/**
	 * Construct a new instance of RotaryEncoder. Only one CMMRE sensor unit
	 * should exist on the robot; as such, this interface class is singleton,
	 * and this constructor method should only be privately accessed through a
	 * call to getInstance().
	 */
	private RotaryEncoder() {
		forwardLimit = new DigitalInput(RobotMap.CMMRE_FORWARD_LIMIT);
		backwardLimit = new DigitalInput(RobotMap.CMMRE_BACKWARD_LIMIT);
		forwardLastState = forwardLimit.get();
		backwardLastState = backwardLimit.get();
		// Lift is initialized in enable() so as to prevent recursion. In
		// theory, initializing CommandBase should guarantee that Lift will
		// already be constructed, but it is preferable to avoid the risk
		// altogether.
	}

	/**
	 * Public access to the singleton instance of the CMMRE interface class.
	 * Call this method statically to construct the class and to obtain access
	 * to its methods from any other robot process.
	 * 
	 * @return The singleton instance of the RotaryEncoder class.
	 */
	public static RotaryEncoder getInstance() {
		if (instance == null) {
			instance = new RotaryEncoder();
		}
		return instance;
	}

	/**
	 * A method to initialize and deploy the update loop of the CMMRE. If the
	 * sensor interface is not enabled prior to use, its output values will not
	 * be updated in real time, and thusly the output it exposes to the robot
	 * will be both inaccurate and meaningless.
	 */
	public synchronized void enable() {
		if (lift == null) {
			lift = Lift.getInstance();
		}
		enabled = true;
		updateThread = new Thread(this);
		updateThread.start();

	}

	/**
	 * Updates the count of the encoder by checking the state of each digital
	 * switch and referencing it against past recorded states, read timeouts to
	 * account for switch state bounce, and the motor directions recorded by the
	 * Lift subsystem. To be accessed privately by the updateThread.
	 */
	private synchronized void update() {
		boolean forward = forwardLimit.get();
		boolean backward = backwardLimit.get();
		int increment = lift.getWinchSpeed() > 0 ? 1 : -1;
		if (lift.liftFullyLowered()) {
			reset();
		} else {
			if (forward != forwardLastState && !forwardChangeTrigger) {
				forwardChangeTrigger = true;
				fctTime = System.currentTimeMillis();
			} else if (forwardChangeTrigger
					&& (System.currentTimeMillis() - fctTime) >= SWITCH_BOUNCE_CHECK_MILLIS) {
				if (forward)
					count += increment;
				forwardLastState = forward;
				forwardChangeTrigger = false;
			}
			if (backward != backwardLastState && !backwardChangeTrigger) {
				backwardChangeTrigger = true;
				fctTime = System.currentTimeMillis();
			} else if (backwardChangeTrigger
					&& (System.currentTimeMillis() - fctTime) >= SWITCH_BOUNCE_CHECK_MILLIS) {
				if (backward)
					count += increment;
				backwardLastState = backward;
				backwardChangeTrigger = false;
			}
		}
	}

	/**
	 * Resets the count of the CMMRE along with any other relevant status and
	 * read values. Use to zero the sensor.
	 */
	public synchronized void reset() {
		count = 0;
		forwardLastState = forwardLimit.get();
		backwardLastState = backwardLimit.get();
		forwardChangeTrigger = false;
		backwardChangeTrigger = false;
	}

	/**
	 * Stops and joins the updateThread of the CMMRE, if it is running. Use to
	 * eliminate unnecessary processing if readout from the sensor is no longer
	 * needed.
	 */
	public synchronized void disable() {
		if (updateThread != null && enabled) {
			try {
				enabled = false;
				updateThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Process to be carried out cyclically by the sensor's private update loop.
	 * Calls the relevant methods to keep the sensor values up to date for
	 * exposure to other robot processes.
	 */
	public void run() {
		while (enabled) {
			update();
		}
	}

	/**
	 * Returns the last measured count readout for the CMMRE sensor. Count is
	 * updated in real time in a separate thread; this method's purpose is to
	 * expose the self-updating value to external robot processes.
	 * 
	 * @return The last recorded measurement of the encoder's raw count.
	 */
	public synchronized int getCount() {
		return count;
	}

	/**
	 * Get the last measured count readout converted to inches of vertical
	 * motion on the lift by the hard-coded ratio stored in this class.
	 * 
	 * @return The encoder count converted to inches of vertical motion
	 */
	public synchronized double getInches() {
		return (double) count / COUNTS_PER_INCH;
	}
}
