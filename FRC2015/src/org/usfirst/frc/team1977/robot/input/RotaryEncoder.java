package org.usfirst.frc.team1977.robot.input;

import org.usfirst.frc.team1977.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SensorBase;

/**
 * @author Loveland High Robotics 1977
 * @author Evan Stewart An interface for the six-divet two-switch rotary encoder
 *         custom-machined for the lift. Direction is to be determined by the
 *         sequence in which the two limits are tripped. For the moment, runs on
 *         an experimental thread-based model that should handle updates in
 *         parallel to the main control thread; testing will be necessary in
 *         order to determine if this will damage performance on the new Rio,
 *         and if switching to a controlled-update based model will be
 *         necessary.        
 *         
 *TODO: Find a good way of determining direction using only two switches.         
 */
public class RotaryEncoder extends SensorBase implements Runnable {

	private static RotaryEncoder instance;

	private static final long STOP_THRESHOLD_MILLIS = 100;

	private DigitalInput forwardLimit;
	private DigitalInput backwardLimit;
	private Thread updateThread;

	private boolean enabled = false;
	private int count = 0;
	private boolean forwardLastPressed = false;
	private boolean backwardLastPressed = false;
	private boolean lastTriggeredForward = false;
	private boolean firstTigger = true;

	public enum Direction {
		FORWARD, STATIC, REVERSE;
	}
	private Direction dir = Direction.STATIC;

	public RotaryEncoder() {
		forwardLimit = new DigitalInput(RobotMap.LIFT_ENCODER_FORWARD_LIMIT);
		backwardLimit = new DigitalInput(RobotMap.LIFT_ENCODER_BACKWARD_LIMIT);
	}

	public static RotaryEncoder getInstance() {
		if (instance == null) {
			instance = new RotaryEncoder();
		}
		return instance;
	}

	public synchronized void enable() {
		enabled = true;
		updateThread = new Thread(this);
		updateThread.start();

	}

	private synchronized void update() {
		boolean forward = forwardLimit.get();
		boolean backward = backwardLimit.get();
		if (!forward && !forwardLastPressed) {
			count++;
			forwardLastPressed = true;
		} else if (forward) {
			forwardLastPressed = false;
		} else if (backward && !backwardLastPressed) {
			count++;
			backwardLastPressed = true;
		} else if (backward) {
			backwardLastPressed = false;
		}
		
	}

	public synchronized void reset() {
		dir = Direction.STATIC;
		count = 0;
		forwardLastPressed = false;
		backwardLastPressed = false;
		lastTriggeredForward = false;
		firstTigger = true;
	}

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

	public void run() {
		while (enabled) {
			update();
		}
	}

	public synchronized Direction getDirection() {
		return dir;
	}
	

	public synchronized int getCount() {
		return count;
	}
}
