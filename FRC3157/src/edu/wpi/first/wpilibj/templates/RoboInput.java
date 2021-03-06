/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

/**
 * loads all inputs into inpuData for roboThink
 *
 * @author Programming Subteam
 */
public class RoboInput {
    
    /**
     * The left joystick
     */
    public Joystick leftDrive;

    /**
     * The right joystick
     */
    public Joystick rightDrive;

    /**
     * The co-driver joystick
     */
    public Joystick coDrive;

    /**
     * Measures the speed of the left motor
     */
    public Encoder leftMotorEncoder;

    /**
     * Measures the speed of the right motor
     */
    public Encoder rightMotorEncoder;

    /**
     * Timer for the encoder
     */
    public Timer encoderTimer;
    /**
     * Checks if shooter is fully retracted
     */
    public DigitalInput shooterSwitchRet;

    public static boolean bLeftMotorEncoderFound = false;
    public static boolean bRightMotorEncoderFound = false;
    public static boolean bShooterSwitchFound = false;
    public static boolean bCameraFound = false;
    
    
    
    /**
     * Initializes the RoboInput object with specified joystick inputs
     *
     * @param leftDrive - left driver joystick input
     * @param rightDrive - right driver joystick input
     * @param coDrive - co-driver joystick input
     */
    public void initialize(int leftDrive, int rightDrive, int coDrive) {
        System.out.println( "Left: " + leftDrive + " Right: " + rightDrive + " Co: " + coDrive );
        
        this.leftDrive = new Joystick(leftDrive);
        this.rightDrive = new Joystick(rightDrive);
        this.coDrive = new Joystick(coDrive);
        
        encoderTimer = new Timer();
        encoderTimer.start();
        
        try{
            this.leftMotorEncoder = new Encoder(FRCConfig.SLOT_LEFT_ENCODER_1, FRCConfig.SLOT_LEFT_ENCODER_2);
            this.leftMotorEncoder.start();
            bLeftMotorEncoderFound = true;
        }catch(Exception e){
            System.out.println("unable to connect to the left Encoder");
            FRCLogger.getInstance().logError("unable to connect to the left Encoder");
            bLeftMotorEncoderFound = false;
        }
        try{
            this.rightMotorEncoder = new Encoder(FRCConfig.SLOT_RIGHT_ENCODER_1, FRCConfig.SLOT_RIGHT_ENCODER_2);
            this.rightMotorEncoder.start();
            bRightMotorEncoderFound = true;
        }catch(Exception e){
            System.out.println("unable to connect to the right Encoder");
            FRCLogger.getInstance().logError("unable to connect to the right Encoder");
            bRightMotorEncoderFound = false;
        }
     
        
        try {
            if(FRCConfig.EN_SWITCHES == true){
                this.shooterSwitchRet = new DigitalInput(FRCConfig.SLOT_SHOOTER_SWITCH);
                bShooterSwitchFound = true;
            }else{
                bShooterSwitchFound = false;
            }
        } catch (Exception e) {
            System.out.println("unable to connect to the shooter switch");
            FRCLogger.getInstance().logError("unable to connect to the shooter switch");
            bShooterSwitchFound = false;
        }
        
        try {
            if(FRCConfig.EN_CAMERA){
                InputData.camProcessor = new FRCImage();
                bCameraFound = true;
            } else{
                bCameraFound = false;
            }
        } catch( Exception e ) {
            System.out.println("unable to connect to the camera");
            FRCLogger.getInstance().logError("unable to connect to the camera");
            bCameraFound = false;
        }
    }

    /**
     * Populate input variables
     */
    public void gatherInputs() {
        
        InputData.leftDriverStick[0] = this.leftDrive.getX();
        InputData.leftDriverStick[1] = this.leftDrive.getY();
        InputData.leftDriverStick[2] = this.leftDrive.getZ();
        
        InputData.rightDriverStick[0] = this.rightDrive.getX();
        InputData.rightDriverStick[1] = this.rightDrive.getY();
        InputData.rightDriverStick[2] = this.rightDrive.getZ();
        
        InputData.coDriverStick[0] = this.coDrive.getX();
        InputData.coDriverStick[1] = this.coDrive.getY();
        InputData.coDriverStick[2] = this.coDrive.getZ();
        
        InputData.shooterButtonPressed = this.coDrive.getRawButton(FRCConfig.btnSHOOTER);
        if (InputData.shooterButtonPressed) {
            FRCLogger.getInstance().logInfo("Shooter Button Pressed");
        }
        InputData.passButtonPressed = this.coDrive.getRawButton(FRCConfig.btnPASS);
        if (InputData.passButtonPressed) {
            FRCLogger.getInstance().logInfo("Grabber Button Pressed");
        }
        getEncoderVals();
        
        InputData.bManualShoot = this.coDrive.getRawButton(FRCConfig.btnMANUAL_SHOOTER);
        if (InputData.bManualShoot) {
            FRCLogger.getInstance().logInfo("Manual Shoot Pressed");
        }
        if (this.coDrive.getRawButton(FRCConfig.btnMANUAL_ON)) {
            InputData.bPower = true;
            FRCLogger.getInstance().logInfo("Manual On");
        } else if (this.coDrive.getRawButton(FRCConfig.btnMANUAL_OFF)) {
            InputData.bPower = false;
            FRCLogger.getInstance().logInfo("Manual Off");
        }
        
        InputData.bRevButtonPressed = this.leftDrive.getRawButton(FRCConfig.btnREV_DRIVE) || this.rightDrive.getRawButton(FRCConfig.btnREV_DRIVE);
        if (InputData.bRevButtonPressed) {
            FRCLogger.getInstance().logInfo("Drive Reverse Pressed");
        }
        InputData.bAutoShootAndCatch = this.leftDrive.getRawButton(FRCConfig.btnMAGIC_SHOOT_CATCH) || this.rightDrive.getRawButton(FRCConfig.btnMAGIC_SHOOT_CATCH);
        if (InputData.bAutoShootAndCatch) {
            FRCLogger.getInstance().logInfo("Shoot and Catch Button Pressed");
        }
        InputData.bAutoturnLeft = this.leftDrive.getRawButton(FRCConfig.btnAUTOTURN_LEFT) || this.rightDrive.getRawButton(FRCConfig.btnAUTOTURN_LEFT);
        if (InputData.bAutoturnLeft) {
            FRCLogger.getInstance().logInfo("Drove Left");
        }
        InputData.bAutoturnRight = this.leftDrive.getRawButton(FRCConfig.btnAUTOTURN_RIGHT) || this.rightDrive.getRawButton(FRCConfig.btnAUTOTURN_RIGHT);
        if (InputData.bAutoturnRight) {
            FRCLogger.getInstance().logInfo("Drove Right");
        }
        InputData.bDistanceCorrect = this.leftDrive.getRawButton(FRCConfig.btnAUTO_DISTANCE) || this.rightDrive.getRawButton(FRCConfig.btnAUTO_DISTANCE);
        if (InputData.bDistanceCorrect) {
            FRCLogger.getInstance().logInfo("Distance Correction");
        }
        
        InputData.bForceRetreact = this.coDrive.getRawButton( FRCConfig.btnFORCE_FIRE_RETRACT );
        if( InputData.bForceRetreact ) {
            FRCLogger.getInstance().logInfo( "Force Retract Pressed" );
        }
        
        if(bShooterSwitchFound == true){
            InputData.bShooterRet = this.shooterSwitchRet.get();
        } else {
            InputData.bShooterRet = false;
        }
     
        if (InputData.bShooterRet) {
            FRCLogger.getInstance().logInfo("Shooter Retracted");
        }
        
        InputData.bGrabberExtendButtonPressed = this.coDrive.getRawButton(FRCConfig.btnGRABBER_EXTEND);
        

        InputData.bCarLockRelay = this.coDrive.getRawButton(FRCConfig.btnCAR_LOCK);
        if( InputData.bCarLockRelay ) {
        }
    }

    /**
     * Populates InputData's encoder properties
     */
    public void getEncoderVals() {
        if(bLeftMotorEncoderFound && bRightMotorEncoderFound && FRCConfig.EN_ENCODERS){ 
            InputData.leftMotorEncoderVal = leftMotorEncoder.get();
            InputData.rightMotorEncoderVal = rightMotorEncoder.get();
            // System.out.println( "Left: " + InputData.leftMotorEncoderVal + " | Right: " + InputData.rightMotorEncoderVal );
            InputData.fLeftEncoderTime = encoderTimer.get();
            InputData.fRightEncoderTime = encoderTimer.get();
            
            leftMotorEncoder.reset();
            rightMotorEncoder.reset();
            
            encoderTimer.reset();
        }
    }
}
