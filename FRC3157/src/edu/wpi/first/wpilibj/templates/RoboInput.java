/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author FRCUser
 */
public class RoboInput {
    
    public Joystick leftDrive;
    public Joystick rightDrive;
    public Joystick coDrive;
    
    public Encoder leftMotorEncoder;
    public Encoder rightMotorEncoder;
    
    public static final int kCALIBRATE_BUTTON = 0;
    
    public static int kSHOOTER_BUTTON=0;
    public static int kGRABBER_BUTTON=3;
    
    public static final int[] kLEFT_ENCODER_PORTS={0,1};
    public static final int[] kRIGHT_ENCODER_PORTS={2,3};
    
    public Timer encoderTimer;
    
    public void initialize( int leftDrive, int rightDrive, int coDrive ){
        this.leftDrive=new Joystick( leftDrive );
        this.rightDrive=new Joystick( rightDrive );
        this.coDrive=new Joystick( coDrive );
        
        encoderTimer = new Timer();
        encoderTimer.start();
        
        this.leftMotorEncoder=new Encoder( kLEFT_ENCODER_PORTS[0],kLEFT_ENCODER_PORTS[1]);
        this.rightMotorEncoder=new Encoder( kRIGHT_ENCODER_PORTS[0],kRIGHT_ENCODER_PORTS[1]);
        this.leftMotorEncoder.start();
        this.rightMotorEncoder.start();
    }
    
    public void gatherInputs(){
        InputData.leftDriverStick[0]=this.leftDrive.getX();
        InputData.leftDriverStick[1]=this.leftDrive.getY();
        InputData.leftDriverStick[2]=this.leftDrive.getZ();
        InputData.rightDriverStick[0]=this.rightDrive.getX();
        InputData.rightDriverStick[1]=this.rightDrive.getY();
        InputData.rightDriverStick[2]=this.rightDrive.getZ();
        InputData.coDriverStick[0]=this.coDrive.getX();
        InputData.coDriverStick[1]=this.coDrive.getY();
        InputData.coDriverStick[2]=this.coDrive.getZ();
        
        InputData.shooterButtonPressed=this.coDrive.getRawButton(kSHOOTER_BUTTON);
        InputData.grabberButttonPressed=this.coDrive.getRawButton(kGRABBER_BUTTON);
        getEncoderVals();
        
        InputData.bCalibrateButtonPressed=this.leftDrive.getRawButton(kCALIBRATE_BUTTON)||this.rightDrive.getRawButton(kCALIBRATE_BUTTON);
    }
    
    public void getEncoderVals(){
        InputData.leftMotorEncoderVal=leftMotorEncoder.get();
        InputData.rightMotorEncoderVal=rightMotorEncoder.get();
        InputData.fLeftEncoderTime = encoderTimer.get();
        InputData.fRightEncoderTime = encoderTimer.get();
        leftMotorEncoder.reset();
        rightMotorEncoder.reset();
        encoderTimer.reset();
    }
}
