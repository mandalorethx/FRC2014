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

    public static FRCLogger logger = FRCLogger.getInstance();
    
    public static final int kAUTON_WAIT = 0;
    public static final int kAUTON_FIRE = 1;
    public static final int kAUTON_WAIT_2 = 2;
    public static final int kAUTON_MOVE = 3;
    public static final int kAUTON_SLEEP = 4;
    public static boolean decreaseLastState = false;
    public static boolean increaseLastState = false;
    
    public Timer autonTimer;
    public int dAutonState;
    public static RoboInput input = null;
    public static RoboThink think = null;
    public static RoboOutput output = null;

    // This is a test line!
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        System.out.println("robotInit Start");
        configInit();
        ScreenOutput.initDriverStationScreen();
        System.out.println("robotInit End");
    }
    
    /**
     * @Override
     */
    public void disabledInit()
    {
        logger.changePhase(FRCLogger.DISABLED);
    }
    
    public void disabledPeriodic(){
        if( input.coDrive.getRawButton(FRCConfig.btnSTART_AUTON_MODE )){
            FRCConfig.kRUN_AUTONOMOUS = true;
        }else if( input.coDrive.getRawButton(FRCConfig.btnSTOP_AUTON_MODE)){
            FRCConfig.kRUN_AUTONOMOUS = false;
        }
        if( input.coDrive.getRawButton(FRCConfig.btnCONFIG_RELOAD_1) && ( input.coDrive.getRawButton(FRCConfig.btnCONFIG_RELOAD_2)) ) {
            configInit();
        }
        if(input.coDrive.getRawButton(FRCConfig.btnINCREASE_WAIT_TIME)&&increaseLastState == false){
            increaseLastState=true;
            FRCConfig.kAUTON_DELAY += FRCConfig.kAUTON_DELAY_STEP; 
        }else if(input.coDrive.getRawButton(FRCConfig.btnINCREASE_WAIT_TIME)&&increaseLastState == true){
            increaseLastState=false;
        }
        if(input.coDrive.getRawButton(FRCConfig.btnDECREASE_WAIT_TIME)&&decreaseLastState == false){
            decreaseLastState=true;
            FRCConfig.kAUTON_DELAY += -1.0 * FRCConfig.kAUTON_DELAY_STEP;
            if(FRCConfig.kAUTON_DELAY_STEP <= 0){
                FRCConfig.kAUTON_DELAY_STEP = 0;
            }
        }else if(input.coDrive.getRawButton(FRCConfig.btnDECREASE_WAIT_TIME)&&decreaseLastState == true){
            decreaseLastState=false;
        }
        
        if( FRCConfig.kRUN_AUTONOMOUS ) {
            ScreenOutput.clrLine( 0 );
            ScreenOutput.screenWrite("Autonomous is Enabled", 0);
        }else{
            ScreenOutput.clrLine( 0 );
            ScreenOutput.screenWrite("Autonomous is Disabled", 0);
        }
        ScreenOutput.clrLine( 1 );
        ScreenOutput.screenWrite("Wait Time: " + FRCConfig.kAUTON_DELAY_STEP, 1);
    }
    
    public void autonomousInit() {
        logger.changePhase(FRCLogger.AUTONOMOUS);
        autonTimer = new Timer();
        autonTimer.start();
        dAutonState = 0;
        InputData.bTestMode = false;
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        if (!FRCConfig.kRUN_AUTONOMOUS) {
            return;
        }

        // input.getEncoderVals();
        // InputData.bDriveStraightPressed = true;
        
        switch (dAutonState) {
            case kAUTON_WAIT:
                ScreenOutput.clrScreen();
                ScreenOutput.screenWrite("kAUTON_WAIT", 3);
                if (autonTimer.get() >= FRCConfig.kAUTON_DELAY / 1000.0 ) {
                    dAutonState++;
                    autonTimer.reset();
                }
                break;
            case kAUTON_FIRE:
                ScreenOutput.clrScreen();
                ScreenOutput.screenWrite("kAUTON_FIRE: " + autonTimer.get(), 3);
                InputData.shooterButtonPressed = true;
                if (autonTimer.get() >= FRCConfig.kAUTON_FIRE_TIME / 1000.0) {
                    dAutonState++;
                    autonTimer.reset();
                    InputData.shooterButtonPressed = false;
                }
                break;
            case kAUTON_WAIT_2:
                ScreenOutput.clrScreen();
                ScreenOutput.screenWrite("kAUTON_WAIT2", 3);
                if (autonTimer.get() >= FRCConfig.kAUTON_MOVE_DELAY / 1000.0 ) {
                    dAutonState++;
                    autonTimer.reset();
                }
                break;
            case kAUTON_MOVE:
                ScreenOutput.clrScreen();
                ScreenOutput.screenWrite("kAUTON_MOVE", 3);
                InputData.leftDriverStick[1] = 1.0;
                InputData.rightDriverStick[1] = 1.0;
                // InputData.bDriveStraightPressed = true;
                if (autonTimer.get() >= FRCConfig.kAUTON_MOVE_TIME / 1000.0 ) {
                    dAutonState++;
                    autonTimer.reset();
                }
                break;
            case kAUTON_SLEEP:
                ScreenOutput.clrScreen();
                ScreenOutput.screenWrite("kAUTON_SLEEP", 3);
                InputData.leftDriverStick[1] = 0;
                InputData.rightDriverStick[1] = 0;
                InputData.bDriveStraightPressed = false;
                autonTimer.stop();
                break;
            default:
                ScreenOutput.screenWrite("unknown AutonState");
                logger.logError("unknown autonTime");
                break;
        }

        think.processInputs();
        output.setOutputs();
    }

    public void teleopInit() {
        logger.changePhase(FRCLogger.TELEOP);
        InputData.bTestMode = false;
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        input.gatherInputs();
        think.processInputs();
        output.setOutputs();
    }

    public void testInit(){
        InputData.bTestMode = true;
    }
    
    /**
     * This function is called periodically during test mode
     */    
    public void testPeriodic() {
        input.gatherInputs();
        think.processInputs();
        output.setOutputs();        
    }
    
    public void configInit(){
     
        FRCConfig.initialize();

        if( input == null ) {
            input = new RoboInput();
            input.initialize(FRCConfig.SLOT_LEFT_DRIVER_JOYSTICK, FRCConfig.SLOT_RIGHT_DRIVER_JOYSTICK, FRCConfig.SLOT_CO_DRIVER_JOYSTICK);
        }if( think == null ) {
            think = new RoboThink();
        }if( output == null ) {
            output = new RoboOutput();
            output.initialize(FRCConfig.SLOT_LEFT_MOTOR, FRCConfig.SLOT_RIGHT_MOTOR);
        }

        InputData.reset();
        OutputData.reset();
                
          
    }

}
