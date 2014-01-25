/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author FRCUser
 */
public class RoboThink {
    
    public static final double kMAX_MOTOR_SPEED = 100;
    
    public static final double kMAX_MOTOR_POWER = 0.9;
    public static final double kMAX_ERROR = 0.1;
    /*
    public static final int kFIRE_WAITING = 0;
    public static final int kFIRE_EXTEND = 1;
    public static final int kFIRE_RETRACT = 2;
    public static final int kFIRE_SLEEP = 3;
    */
    public static final int kCATCH_WAITING = 0;
    public static final int kCATCH_EXTEND = 1;
    public static final int kCATCH_RETRACT = 2;
    public static final int kCATCH_SLEEP = 3;
    public static int dCatchStep = 0;
    public static final double kFIRING_TIME = 500;
    public static final double kMOTOR_SPEED = 0.9;
    public static final double kMOVE_TIME = 2000;
    
    public static final double kMAX_SHOOTER_POWER = 0.9;
    public static final double kMIN_SHOOTER_POWER = 0.7;
    public static final double kSLEEP_TIME = 500.0;
    
    public static final double kENCODER_PPR = 4096;
    
    public static double leftMultiplier=-1*kMAX_SHOOTER_POWER;
    public static double rightMultiplier=kMAX_SHOOTER_POWER;
    
    public int dFiringStep=0;
    public Timer thinkTimer;
    
    public double fLeftMotorSpeed;
    public double fRightMotorSpeed;
    
    public RoboThink(){
        dFiringStep = 0;
        thinkTimer = new Timer();
    }
    
    public void processInputs(){
        if( InputData.leftDriverStick[1] > 0 ) {
            OutputData.leftMotorVal=leftMultiplier*(InputData.leftDriverStick[1]*InputData.leftDriverStick[1]);
        } else {
            OutputData.leftMotorVal=-1*leftMultiplier*(InputData.leftDriverStick[1]*InputData.leftDriverStick[1]);
        }
         if( InputData.rightDriverStick[1] > 0 ) {
            OutputData.rightMotorVal=rightMultiplier*(InputData.rightDriverStick[1]*InputData.rightDriverStick[1]);
        } else {
            OutputData.rightMotorVal=-1*rightMultiplier*(InputData.rightDriverStick[1]*InputData.rightDriverStick[1]);
        }
         /*
         if( InputData.shooterButtonPressed && dFiringStep == kFIRE_WAITING){
             dFiringStep = kFIRE_EXTEND;
         }
         */
         fire();
        
        if(InputData.grabberButttonPressed == true){
            OutputData.leftGrabberVal = 1.0;
            OutputData.rightGrabberVal = 1.0;
        }else{
            OutputData.leftGrabberVal = 0;
            OutputData.rightGrabberVal = 0;
        }
      fRightMotorSpeed=CalcMotorSpeed(InputData.rightMotorEncoderVal, InputData.fRightEncoderTime);  
      fLeftMotorSpeed=CalcMotorSpeed(InputData.leftMotorEncoderVal, InputData.fLeftEncoderTime);
      
      if(InputData.bDriveStraightPressed == true){
          steerStraight();
      }
    }
   
    /*
    public void fire(){
        // Power = MAX * raw + (1 - raw) * MIN
        double shooterPower = kMAX_SHOOTER_POWER * InputData.coDriverStick[2] +
                (1 - InputData.coDriverStick[2]) * kMIN_SHOOTER_POWER;
        switch( dFiringStep ) {
            case kFIRE_WAITING:
                break;
            case kFIRE_EXTEND:
                if(InputData.bShooterExt == true) {
                    dFiringStep = kFIRE_SLEEP;
                    thinkTimer.start();
                    break;
                }
                OutputData.leftShooterVal=shooterPower;
                OutputData.rightShooterVal=shooterPower;
                break;
            case kFIRE_SLEEP:
                if(thinkTimer.get()>=kSLEEP_TIME){
                    dFiringStep = kFIRE_RETRACT;
                }
                OutputData.leftShooterVal=0;
                OutputData.rightShooterVal=0;
                break;
            case kFIRE_RETRACT:
                OutputData.leftShooterVal=-1.0*shooterPower;
                OutputData.rightShooterVal=-1.0*shooterPower;
                if(InputData.bShooterRet == true){
                    OutputData.leftShooterVal=0;
                    OutputData.rightShooterVal=0;
                    dFiringStep = kFIRE_WAITING;
                }
                break;
            default:
                break;
        }
    }
    */
    public void fire(){
        if(InputData.shooterButtonPressed){
            OutputData.bPullPin=true;
        }else if(!InputData.bShooterRet){
            OutputData.bStartShooter=false;
        }else{
            OutputData.bPullPin=false;
            OutputData.bStartShooter=true;
        }
    }
    public double CalcMotorSpeed(int encoderCount, double encoderTime){
        double speed = 0;
        speed = (double)encoderCount/(double)kENCODER_PPR;
        speed/= ((double)encoderTime/60000);
        return speed;    
    }
    
    public void steerStraight(){
        double expectPowerLeft = OutputData.leftMotorVal*kMAX_MOTOR_SPEED;
        double expectPowerRight = OutputData.rightMotorVal*kMAX_MOTOR_SPEED;
        
        if(fLeftMotorSpeed < (1.0- kMAX_ERROR) * expectPowerLeft || fLeftMotorSpeed > (1+kMAX_ERROR) *expectPowerLeft){
            double dif = (expectPowerLeft - fLeftMotorSpeed) / expectPowerLeft;
            OutputData.leftMotorVal += dif;
            if(OutputData.leftMotorVal < -1*kMAX_MOTOR_POWER){
                OutputData.leftMotorVal = -1*kMAX_MOTOR_POWER;
            }
            if(OutputData.leftMotorVal > kMAX_MOTOR_POWER){
                OutputData.leftMotorVal = kMAX_MOTOR_POWER;
            }
        }
        if(fRightMotorSpeed < (1.0- kMAX_ERROR) * expectPowerRight || fRightMotorSpeed > (1+kMAX_ERROR) *expectPowerRight){
            double dif = (expectPowerRight - fRightMotorSpeed) / expectPowerRight;
            OutputData.rightMotorVal += dif;
            if(OutputData.rightMotorVal < -1*kMAX_MOTOR_POWER){
                OutputData.rightMotorVal = -1*kMAX_MOTOR_POWER;
            }
            if(OutputData.rightMotorVal > kMAX_MOTOR_POWER){
                OutputData.rightMotorVal = kMAX_MOTOR_POWER;
            }
        }
    }
    public void catchAndShoot(){
        switch(dCatchStep){
            case kCATCH_WAITING:
                break;
            case kCATCH_EXTEND:
                InputData.shooterButtonPressed = true;
                thinkTimer.reset();
                thinkTimer.start();
                dCatchStep++;
                break;
            case kCATCH_RETRACT:
                if(thinkTimer.get() >= kFIRING_TIME){
                    InputData.shooterButtonPressed = false;
                    InputData.leftDriverStick[1] = kMOTOR_SPEED;
                    InputData.rightDriverStick[1] = kMOTOR_SPEED;
                    thinkTimer.reset();
                    dCatchStep++;
                }else{ 
                    InputData.shooterButtonPressed = true;
                }break;
            case kCATCH_SLEEP:
                if(thinkTimer.get() >= kMOVE_TIME){
                    InputData.leftDriverStick[1] = 0;
                    InputData.rightDriverStick[1] = 0;
                    thinkTimer.reset();
                    thinkTimer.stop();
                    dCatchStep = kCATCH_WAITING;
                }else{
                    InputData.leftDriverStick[1] = kMOTOR_SPEED;
                    InputData.rightDriverStick[1] = kMOTOR_SPEED;
                }break;
        }
    }
}
