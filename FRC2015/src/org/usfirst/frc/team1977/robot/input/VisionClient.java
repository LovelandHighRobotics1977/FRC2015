package org.usfirst.frc.team1977.robot.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import edu.wpi.first.wpilibj.SensorBase;

/**
 * @author Loveland High Robotics 1977
 * @author Evan Stewart
 * 		A network client class designed to interface with the Pi-based vision processing server.  Receives packets
 * 		containing CV data in real time and exposes them to the robot command loop by request.
 */
public class VisionClient extends SensorBase {
	
	private static final String PI_IP = "10.19.77.91"; //YOLO
	
	private URL piURL;
	private URLConnection piConnection;
	
	private String serverOut = null;
	
	public VisionClient() {
		try {
			piURL = new URL(PI_IP);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public void read() {
		try {
			piConnection = piURL.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(piConnection.getInputStream()));
			String inputLine;
			String readOut = "";
			while ((inputLine = br.readLine()) != null) {
				serverOut.concat(inputLine);
			}
			if (readOut != serverOut) {
				serverOut = readOut;
				interpret();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void interpret() {
		//TODO: Write stuff to decode CV data from the HTML
	}

}
