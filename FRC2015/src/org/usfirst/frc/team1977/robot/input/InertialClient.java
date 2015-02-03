package org.usfirst.frc.team1977.robot.input;

/**
 * Sensor interface for retrieving and storing inertial data from the Pi server
 * and corresponding accelerometer/compass/gyro unit. Contains values
 * corresponding to the instantaneous acceleration, facing, and rotation of the
 * sensor.
 * 
 * @author Loveland High Robotics 1977
 * @author Evan Stewart
 */
public class InertialClient {
	/**
	 * Singleton instance reference for the sensor interface.
	 */
	private static InertialClient instance;
	// WebClient utility for HTTP access to the Pi server
	private WebClient wc;

	/**
	 * Constructs the InertialClient server interface, initializing an relevant
	 * utilities and references. Called privately via getInstance().
	 */
	private InertialClient() {
		wc = new WebClient();
	}

	/**
	 * Access the singleton instance of the InertialClient sensor interface,
	 * constructing it if necessary.
	 * 
	 * @return The singleton instance of InertialClient.
	 */
	public static InertialClient getInstance() {
		if (instance == null) {
			instance = new InertialClient();
		}
		return instance;
	}

	/**
	 * Access the Pi server and read the latest inertial values to local memory.
	 * If the sensor is not updated before a read, readouts will not be
	 * accurate.
	 */
	public void update() {
		parse(wc.read(WebClient.ACCPI));
	}

	/**
	 * Deconstructs the raw XML strings from the Pi server and extracts the
	 * relevant values to local fields for exposure to external robot processes.
	 * Called privately during the update() process.
	 * 
	 * @param xml
	 *            The raw XML output from the Pi.
	 */
	private void parse(String xml) {
		// TODO: Establish XML format for inertial data.
	}

}
