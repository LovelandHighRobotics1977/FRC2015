package org.usfirst.frc.team1977.robot.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import edu.wpi.first.wpilibj.SensorBase;

/**
 * Basic utility class containing methods to access and read from a HTTP web
 * address on the robot's network. Essentially a Java implementation of cURL on
 * the robot. All relevant addresses that will need to be accessed are stored as
 * constants.
 * 
 * @author Loveland High Robotics 1977
 * @author Evan Stewart
 */
public class WebClient extends SensorBase {

	public static final String CVPI = "http://10.19.77.3:8888/tote/data"; // TODO:
	// Change to reflect final readout page; also, update IP to fit Robot bridge
	// conventions.
	public static final String ACCPI = "10.8.101.131:8888/accel/raw"; // TODO:

	// Actually figure this out instead of making it up based on assumptions and
	// guesses.

	/**
	 * Opens a URLConnection to the supplied address and returns the raw output
	 * found as a string.
	 * 
	 * @param address
	 *            The address on the robot's network to access; the constants
	 *            saved in the WebClient class should encompass most targets.
	 * @return The raw output from the address, or an error if connection could
	 *         not be made.
	 */
	public String read(String address) {
		try {
			URLConnection connection = new URL(address).openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String inputLine;
			String readout = "";
			while ((inputLine = br.readLine()) != null) {
				readout += inputLine;
			}
			return readout;
		} catch (IOException e) {
			e.printStackTrace();
			return "Error communicating with webhost at address " + address;
		}
	}

}
