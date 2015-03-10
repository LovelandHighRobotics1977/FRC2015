package org.usfirst.frc.team1977.robot;

import edu.wpi.first.wpilibj.DriverStation;

/**
 * Struct class that contains static constants for all relevant hardware inputs
 * on the robot (RRIO, PCM, etc). Also contains XBox controller mappings in an
 * interior class.
 * 
 * @author Loveland High Robotics 1977
 * @author Evan Stewart
 */
public class RobotMap {
	// TODO: Ultimately segregate by type or subsystem? Going with type for now,
	// based on legacy code; both are present within naming convention, so the
	// distinction is not major.
	// |>===== CAN Devicenums =====<|
	// Power Distribution Panel
	public static final int PDP_DEVICENUM = 0; //TODO: Reflash to this from 0 to 1
	// Pneumatic Control Module
	public static final int PCM_DEVICENUM = 0; //TODO: Reflash to this from 0 to 1
	// Talon SRX
	public static final int LIFT_WINCH_TALONSRX_DEVICENUM = 0; //TODO: Reflash to this from 0 to 1
	// |>===== PWM Out =====<|
	// Talon
	public static final int DRIVE_FRONT_LEFT_TALON = 0;
	public static final int DRIVE_FRONT_RIGHT_TALON = 1;
	public static final int DRIVE_BACK_LEFT_TALON = 2;
	public static final int DRIVE_BACK_RIGHT_TALON = 3;
	// |>===== Relay Out =====<|
	// Spike Relay
	// |>===== Digital In ======<|
	// Digital Limit
	public static final int CMMRE_FORWARD_LIMIT = 0;
	public static final int CMMRE_BACKWARD_LIMIT = 1;
	public static final int LIFT_BOTTOM_LIMIT = 2;
	public static final int LIFT_TOP_LIMIT = 3;
	public static final int GRASPER_OPEN_REED = 4;
	public static final int GRASPER_WIDE_REED = 5;
	public static final int GRASPER_MID_REED = 6;
	public static final int GRASPER_THIN_REED = 7;
	public static final int GRASPER_CLOSE_REED = 8;
	public static final int GRASPER_SUCTION_LIMIT = 9;
	// |>===== Analog In =====<|
	// |>===== Pneumatic Control Module =====<|
	// Double Solenoid
	public static final int GRASPER_PISTON_FORWARD = 1;
	public static final int GRASPER_PISTON_REVERSE = 0;
	public static final int GRASPER_SUCTION_FORWARD = 3;
	public static final int GRASPER_SUCTION_REVERSE = 2;

	/**
	 * An interior class of the RobotMap which contains static constants
	 * corresponding to the mapping of inputs on the XBox 360 controllers.
	 * 
	 * @author Loveland High Robotics 1977
	 * @author Gavin Stewart
	 * @author Ali Persings
	 */
	public class XBoxMappings {

		/**
		 * The axis number for the left horizontal axis of the XBox 360.
		 */
		public static final int XBOX_LEFT_X = 0;
		/**
		 * The axis number for the left vertical axis of the XBox 360.
		 */
		public static final int XBOX_LEFT_Y = 1;
		/**
		 * The axis number for the trigger buttons of the XBox 360.
		 */
		public static final int XBOX_LEFT_TRIGGER = 2;
		/**
		 * The axis number for the trigger buttons of the XBox 360.
		 */
		public static final int XBOX_RIGHT_TRIGGER = 3;
		/**
		 * The axis number for the right horizontal axis of the XBox 360.
		 */
		public static final int XBOX_RIGHT_X = 4;
		/**
		 * The axis number for the right vertical axis of the XBox 360.
		 */
		public static final int XBOX_RIGHT_Y = 5;
		/**
		 * The channel of the A button.
		 */
		public static final int BUTTON_A = 1;
		/**
		 * The channel of the B button.
		 */
		public static final int BUTTON_B = 2;
		/**
		 * The channel of the X button.
		 */
		public static final int BUTTON_X = 3;
		/**
		 * The channel of the Y button.
		 */
		public static final int BUTTON_Y = 4;
		/**
		 * The channel of the left shoulder button.
		 */
		public static final int BUTTON_SHOULDER_LEFT = 5;
		/**
		 * The channel of the right shoulder button.
		 */
		public static final int BUTTON_SHOULDER_RIGHT = 6;
		/**
		 * The channel of the back button.
		 */
		public static final int BUTTON_BACK = 7;
		/**
		 * The channel of the start button.
		 */
		public static final int BUTTON_START = 8;
		/**
		 * The channel of the left thumb button.
		 */
		public static final int BUTTON_THUMB_LEFT = 9;
		/**
		 * The channel of the right thumb button.
		 */
		public static final int BUTTON_THUMB_RIGHT = 10;
	};
}
