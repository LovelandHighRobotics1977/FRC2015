package org.usfirst.frc.team1977.robot.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import edu.wpi.first.wpilibj.SensorBase;

/**
 * A network client class designed to interface with the Pi-based vision
 * processing server. Reads raw data through HTTP via the WebClient interface
 * and contents methods necessary for parsing and exposing the XML values.
 * 
 * @author Loveland High Robotics 1977
 * @author Evan Stewart
 */
public class VisionClient extends SensorBase {

	/**
	 * Singleton instance reference for the VisionClient sensor. We shouldn't
	 * need more than one.
	 */
	private static VisionClient instance;
	// WebClient utility for reading from HTTP
	WebClient wc; // LOLOLOLOLOLOL WC?

	// TODO: Figure out what values we actually need to store and expose here.

	/**
	 * Construct a new VisionClient sensor interface, initializing relevant
	 * utilities and references. Only to be accessed privately by getInstance().
	 */
	private VisionClient() {
		wc = new WebClient();
	}

	/**
	 * Access the singleton instance of the VisionClient sensor interface,
	 * constructing it if necessary.
	 * 
	 * @return The singleton instance of VisionClient.
	 */
	public static VisionClient getInstance() {
		if (instance == null) {
			instance = new VisionClient();
		}
		return instance;
	}

	/**
	 * Access the Pi server and read the latest CV values to local memory. If
	 * the sensor is not updated before a read, readouts will not be accurate.
	 */
	public void update() {
		parse(wc.read(WebClient.CVPI));
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
		// TODO: Standardize CV XML conventions so that we can write a parse
		// method
	}
	
	//TODO: Relevant getters and setters as soon as relevant fields are established.

}
