package org.usfirst.frc.team1977.robot.input;

import java.util.ArrayList;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
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

	public static class Stack {
		public static enum Facing {
			SHORTSIDE("Tote End"), LONGSIDE("Tote Side"), DIAGONAL("Tote Angled");
			
			private String name;
			
			private Facing(String name) {
				this.name = name;
			}
			
			public String toString() {
				return name;
			}
		}

		private Facing facing;
		private int px, py, area, numStacked;

		public Stack(int px, int py, int area, int numStacked, Facing facing) {
			this.px = px;
			this.py = py;
			this.area = area;
			this.numStacked = numStacked;
			this.facing = facing;
		}

		public int getPixelX() {
			return px;
		}

		public int getPixelY() {
			return py;
		}

		public int getPixelArea() {
			return area;
		}

		public int getNumStacked() {
			return numStacked;
		}

		public Facing getStackFacing() {
			return facing;
		}
	}

	/**
	 * Singleton instance reference for the VisionClient sensor. We shouldn't
	 * need more than one.
	 */
	private static VisionClient instance;
	// WebClient utility for reading from HTTP
	WebClient wc; // LOLOLOLOLOLOL WC?

	private ArrayList<Stack> visibleStacks;

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
		Builder b = new Builder();
		try {
			Document doc = b.build(xml, null);
			Element root = doc.getRootElement();
			if (root.getLocalName() != "tote-list") {
				System.out
						.println("Root element of XML document is not proper.");
				throw new Exception();
			}
			visibleStacks = new ArrayList<Stack>();
			Elements totes = root.getChildElements();
			for (int i = 0; i < totes.size(); i++) {
				Stack stack;
				boolean valid = true;
				int px = 0, py = 0, area = 0, numStacked = 0;
				Stack.Facing facing = null;
				Element te = totes.get(i);
				Elements toteData = te.getChildElements();
				for (int j = 0; j < toteData.size(); j++) {
					Element d = toteData.get(j);
					switch (d.getLocalName()) {
					case "type":
						switch (d.getValue()) {
						case "y-tote-longside":
							facing = Stack.Facing.LONGSIDE;
							break;
						case "y-tote-end":
							facing = Stack.Facing.SHORTSIDE;
							break;
						default:
							valid = false;
						}
						break;
					case "contour-area":
						area = Integer.parseInt(d.getValue());
						break;
					case "centroid-x":
						px = Integer.parseInt(d.getValue());
						break;
					case "centroid-y":
						py = Integer.parseInt(d.getValue());
						break;
					case "est-stacked": // TODO check this name
						numStacked = (int)Float.parseFloat(d.getValue());
						break;
					}
				}
				if (valid) {
					stack = new Stack(px, py, area, numStacked, facing);
					visibleStacks.add(stack);
				}
			}
		} catch (Exception ex) {
			System.out.println("XML parser has encountered an error:");
			ex.printStackTrace();
		}
	}

	/**
	 * Returns an arraylist of the currently visible stacks of totes recognized
	 * and exported by the pi.
	 * 
	 * @param update
	 *            Whether or not the pi should be queried for new data before
	 *            returning the local instance.
	 * @return The local list of tote stacks obtained from the pi.
	 */
	public ArrayList<Stack> getVisibleStacks(boolean update) {
		if (update)
			update();
		return visibleStacks;
	}

}
