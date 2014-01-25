/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;

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
    
    public static final int kAUTON_WAIT=0;
    public static final int kAUTON_FIRE=1;
    public static final int kAUTON_WAIT_2=2;
    public static final int kAUTON_MOVE=3;
    public static final int kAUTON_SLEEP=4;
    
    public static final double kAUTON_DELAY=0;
    public static final double kAUTON_MOVE_DELAY=3000;
    public static final double kAUTON_MOVE_TIME=2000;
    public static final double kAUTON_FIRE_TIME=1000;
    
    public static final boolean kRUN_AUTONOMOUS = true;
    
    public Timer autonTimer;
    public int dAutonState;
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

    public void autonomousInit(){
        autonTimer=new Timer();
        autonTimer.start();
        dAutonState=0;
    }
    
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        if( !kRUN_AUTONOMOUS ) {
            return;
        }
        
        switch(dAutonState){
            case kAUTON_WAIT:
                if (autonTimer.get()>=kAUTON_DELAY){
                    dAutonState++;
                    autonTimer.reset();
                }
                break;
            case kAUTON_FIRE:
                InputData.shooterButtonPressed=true;
                if(autonTimer.get()>=kAUTON_FIRE_TIME){
                    dAutonState++;
                    autonTimer.reset();
                    InputData.shooterButtonPressed=false;
                }
                break;
            case kAUTON_WAIT_2:
                if(autonTimer.get()>=kAUTON_MOVE_DELAY){
                    dAutonState++;
                    autonTimer.reset();
                }
                break;
            case kAUTON_MOVE:
                InputData.leftDriverStick[1]=1.0;
                InputData.rightDriverStick[1]=1.0;
                InputData.bDriveStraightPressed=true;
                if(autonTimer.get()>=kAUTON_MOVE_TIME){
                    dAutonState++;
                    autonTimer.reset();
                }
                break;  
            case kAUTON_SLEEP:
                InputData.leftDriverStick[1]=0;
                InputData.rightDriverStick[1]=0;
                InputData.bDriveStraightPressed=false;
                autonTimer.stop();
                break;
            default:
                ScreenOutput.screenWrite("unknown AutonState");
                FRCLogger.getInstance().logError("unknown autonTime");
                break;
        }
        
        think.processInputs();
        output.setOutputs();
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
