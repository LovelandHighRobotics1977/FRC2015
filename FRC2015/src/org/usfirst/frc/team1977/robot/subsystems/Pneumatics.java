package org.usfirst.frc.team1977.robot.subsystems;

import org.usfirst.frc.team1977.robot.RobotMap;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Pneumatics extends Subsystem {
	
	private static Pneumatics instance;
    
	private Compressor compressor;
    private DoubleSolenoid testPiston;

    public Pneumatics() {
    	compressor = new Compressor(); //As a result of the PCM, will charge automatically when necessary
    	testPiston = new DoubleSolenoid(RobotMap.SOLENOID_TEST_PISTON_FORWARD, RobotMap.SOLENOID_TEST_PISTON_REVERSE);
    }
    
    public static Pneumatics getInstance() {
    	if (instance == null) {
    		instance = new Pneumatics();
    	}
    	return instance;
    }
    
    public void initDefaultCommand() {
    	//Seeing as Pneumatics is an aggregate and situational system, there is no default command
    }
    
    public void extendTestPiston() {
    	testPiston.set(DoubleSolenoid.Value.kForward);
    }
    
    public void retractTestPiston() {
    	testPiston.set(DoubleSolenoid.Value.kReverse);
    }
    
    
}

