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

    public static final int kCATCH_WAITING = 0;
    public static final int kCATCH_EXTEND = 1;
    public static final int kCATCH_RETRACT = 2;
    public static final int kCATCH_SLEEP = 3;
    public static int dCatchStep = 0;
    
    
    public static double leftMultiplier=-1*FRCConfig.kMAX_SHOOTER_POWER;
    public static double rightMultiplier=FRCConfig.kMAX_SHOOTER_POWER;
    
    public int dFiringStep=0;
    public Timer thinkTimer;
    
    public double fLeftMotorSpeed;
    public double fRightMotorSpeed;
    
    public double fLeftLastError = 0;
    public double fRightLastError = 0;
    
    public double fLeftError = 0;
    public double fRightError = 0;
    
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

         if(InputData.bAutoShootAndCatch==true){
             catchAndShoot();
         }
         
         if(InputData.bPower==true){
            OutputData.bPullPin=InputData.bManualPin;
            OutputData.bStartShooter=InputData.bManualShoot;
        }else{
            fire();
        }
        
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
        if(FRCConfig.kSTEER_P==0 && FRCConfig.kSTEER_I==0 && FRCConfig.kSTEER_D==0){
            steerStraight();
        }else{
          steerStraightPID();
        }
      }else{
          fLeftError = 0;
          fRightError = 0;
          fLeftLastError = 0;
          fRightLastError = 0;
      }
      
      ScreenOutput.clrLine( 0 );
      ScreenOutput.screenWrite("Left Motor Speed: " + fLeftMotorSpeed, 0);
      ScreenOutput.clrLine( 1 );
      ScreenOutput.screenWrite("Right Motor Speed: " + fRightMotorSpeed, 1);
    }
   
    public void fire(){
        if(InputData.shooterButtonPressed){
            OutputData.bPullPin=true;
            ScreenOutput.clrLine(2);
            ScreenOutput.screenWrite("Fire Step: Pulling Pin (trigger pulled)", 2);
        }else if(!InputData.bShooterRet){
            OutputData.bStartShooter=false;
            ScreenOutput.clrLine(2);
            ScreenOutput.screenWrite("Fire Step: Retracting (Switch not hit)", 2);
        }else{
            OutputData.bPullPin=false;
            OutputData.bStartShooter=true;
            ScreenOutput.clrLine(2);
            ScreenOutput.screenWrite("Fire Step: Waiting (Charging Pstons)", 2);
        }
    }
    public double CalcMotorSpeed(int encoderCount, double encoderTime){
        double speed;
        speed = (double)encoderCount/(double)FRCConfig.kENCODER_PPR;
        speed/= ((double)encoderTime/60000);
        return speed;    
    }
    
    public void steerStraightPID() {
        double expectPower = (OutputData.leftMotorVal*FRCConfig.kMAX_MOTOR_SPEED + OutputData.rightMotorVal*FRCConfig.kMAX_MOTOR_SPEED) / 2.0;
        
        double currErrorLeft = expectPower-fLeftMotorSpeed;
        double currErrorRight = expectPower-fRightMotorSpeed;
        
        double leftCorrect = FRCConfig.kSTEER_P*currErrorLeft+FRCConfig.kSTEER_I*fLeftLastError+FRCConfig.kSTEER_D*fLeftError;
        double rightCorrect = FRCConfig.kSTEER_P*currErrorRight+FRCConfig.kSTEER_I*fRightLastError+FRCConfig.kSTEER_D*fRightError;
        
        OutputData.leftMotorVal += leftCorrect;
        OutputData.rightMotorVal += rightCorrect;
        
        fLeftLastError = currErrorLeft;
        fRightLastError = currErrorRight;
        fLeftError += currErrorLeft;
        fRightError += currErrorRight;
    }
    
    public void steerStraight(){
        double expectPowerLeft = OutputData.leftMotorVal*FRCConfig.kMAX_MOTOR_SPEED;
        double expectPowerRight = OutputData.rightMotorVal*FRCConfig.kMAX_MOTOR_SPEED;
        
        if(fLeftMotorSpeed < (1.0- FRCConfig.kMAX_ERROR) * expectPowerLeft || fLeftMotorSpeed > (1+FRCConfig.kMAX_ERROR) *expectPowerLeft){
            double dif = (expectPowerLeft - fLeftMotorSpeed) / expectPowerLeft;
            OutputData.leftMotorVal += dif;
            if(OutputData.leftMotorVal < -1*FRCConfig.kMAX_MOTOR_POWER){
                OutputData.leftMotorVal = -1*FRCConfig.kMAX_MOTOR_POWER;
            }
            if(OutputData.leftMotorVal > FRCConfig.kMAX_MOTOR_POWER){
                OutputData.leftMotorVal = FRCConfig.kMAX_MOTOR_POWER;
            }
        }
        if(fRightMotorSpeed < (1.0- FRCConfig.kMAX_ERROR) * expectPowerRight || fRightMotorSpeed > (1+FRCConfig.kMAX_ERROR) *expectPowerRight){
            double dif = (expectPowerRight - fRightMotorSpeed) / expectPowerRight;
            OutputData.rightMotorVal += dif;
            if(OutputData.rightMotorVal < -1*FRCConfig.kMAX_MOTOR_POWER){
                OutputData.rightMotorVal = -1*FRCConfig.kMAX_MOTOR_POWER;
            }
            if(OutputData.rightMotorVal > FRCConfig.kMAX_MOTOR_POWER){
                OutputData.rightMotorVal = FRCConfig.kMAX_MOTOR_POWER;
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
                if(thinkTimer.get() >= FRCConfig.kFIRING_TIME){
                    InputData.shooterButtonPressed = false;
                    InputData.leftDriverStick[1] = FRCConfig.kMOTOR_SPEED;
                    InputData.rightDriverStick[1] = FRCConfig.kMOTOR_SPEED;
                    thinkTimer.reset();
                    dCatchStep++;
                }else{ 
                    InputData.shooterButtonPressed = true;
                }break;
            case kCATCH_SLEEP:
                if(thinkTimer.get() >= FRCConfig.kMOVE_TIME){
                    InputData.leftDriverStick[1] = 0;
                    InputData.rightDriverStick[1] = 0;
                    thinkTimer.reset();
                    thinkTimer.stop();
                    dCatchStep = kCATCH_WAITING;
                }else{
                    InputData.leftDriverStick[1] = FRCConfig.kMOTOR_SPEED;
                    InputData.rightDriverStick[1] = FRCConfig.kMOTOR_SPEED;
                }break;
        }
    }
}
