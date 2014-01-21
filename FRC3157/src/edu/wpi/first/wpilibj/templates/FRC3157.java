/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class FRC3157 extends IterativeRobot {
    
    public static int kLEFT_MOTOR_SLOT = 0;
    public static int kRIGHT_MOTOR_SLOT = 1;
    
    public static RoboInput input;
    public static RoboThink think;
    public static RoboOutput output;
    
    public static int kLEFT_DRIVER_STICK=0;
    public static int kRIGHT_DRIVER_STICK=1;
    public static int kCO_DRIVER_STICK=2;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        input=new RoboInput();
        think=new RoboThink();
        output=new RoboOutput();
        
        InputData.reset();
        OutputData.reset();
        
        output.initialize(kLEFT_MOTOR_SLOT, kRIGHT_MOTOR_SLOT);
        input.initialize(kCO_DRIVER_STICK, kCO_DRIVER_STICK, kCO_DRIVER_STICK);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        
    }

    public void teleopInit() {
        
    }
    
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        input.gatherInputs();
        think.processInputs();
        output.setOutputs();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
