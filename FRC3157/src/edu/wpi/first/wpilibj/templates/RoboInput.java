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
      
    public Timer encoderTimer;
    
    public void initialize( int leftDrive, int rightDrive, int coDrive ){
        this.leftDrive=new Joystick( leftDrive );
        this.rightDrive=new Joystick( rightDrive );
        this.coDrive=new Joystick( coDrive );
        
        encoderTimer = new Timer();
        encoderTimer.start();
        
        this.leftMotorEncoder=new Encoder( FRCConfig.kLEFT_ENCODER_PORT_1,FRCConfig.kLEFT_ENCODER_PORT_2);
        this.rightMotorEncoder=new Encoder( FRCConfig.kRIGHT_ENCODER_PORT_1,FRCConfig.kRIGHT_ENCODER_PORT_2);
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
        
        InputData.shooterButtonPressed=this.coDrive.getRawButton(FRCConfig.btnSHOOTER);
        InputData.grabberButttonPressed=this.coDrive.getRawButton(FRCConfig.btnGRABBER);
        getEncoderVals();
   
        InputData.bManualShoot=this.coDrive.getRawButton(FRCConfig.btnMANUAL_SHOOTER);
        InputData.bManualPin=this.coDrive.getRawButton(FRCConfig.btnMANUAL_PIN);
        
        if(this.coDrive.getRawButton(FRCConfig.btnMANUAL_ON)){
            InputData.bPower = true;
        }else if(this.coDrive.getRawButton(FRCConfig.btnMANUAL_OFF)){
            InputData.bPower = false;
        }
        
        
        InputData.bDriveStraightPressed=this.leftDrive.getRawButton(FRCConfig.btnDRIVE_STRAIGHT)||this.rightDrive.getRawButton(FRCConfig.btnDRIVE_STRAIGHT);
        InputData.bAutoShootAndCatch=this.leftDrive.getRawButton(FRCConfig.btnMAGIC_SHOOT_CATCH)||this.rightDrive.getRawButton(FRCConfig.btnMAGIC_SHOOT_CATCH);
    
        InputData.bAutoturnLeft=this.leftDrive.getRawButton(FRCConfig.btnAUTOTURN_LEFT)||this.rightDrive.getRawButton(FRCConfig.btnAUTOTURN_LEFT);
        InputData.bAutoturnRight=this.leftDrive.getRawButton(FRCConfig.btnAUTOTURN_RIGHT)||this.rightDrive.getRawButton(FRCConfig.btnAUTOTURN_RIGHT);
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
