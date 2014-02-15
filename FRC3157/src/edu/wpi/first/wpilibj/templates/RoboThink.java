/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Timer;

/**
 * processes inputs to create a more reliable output
 *
 * @author FRCUser
 */
public class RoboThink {

    /*
     these are steps to the catcAndShoot function
     */
    public static final int kCATCH_WAITING = 0;
    public static final int kCATCH_EXTEND = 1;
    public static final int kCATCH_RETRACT = 2;
    public static final int kCATCH_SLEEP = 3;
    /*
     This one is the current step
     */
    public static int dCatchStep = 0;
    
    /**
     * this is a general timer for RoboThink functions
     */
    public Timer thinkTimer;

    /*
     RPM values calculated from the encoders
     */
    public double fLeftMotorSpeed;
    public double fRightMotorSpeed;

    /*
     PID error values
     */
    public double fLeftLastError = 0;
    public double fRightLastError = 0;
    public double fLeftError = 0;
    public double fRightError = 0;
    public double fLastDistanceError = 0;
    public double fDistanceError = 0;

    public RoboThink() {
        thinkTimer = new Timer();
    }

    /**
     * Connects buttons to functions Takes in InputData and changes the
     * OutputData
     */
    public void processInputs() {
        try {
            ScreenOutput.clrLine(4);
            ScreenOutput.screenWrite("Left: " + InputData.leftDriverStick[1], 4);
            ScreenOutput.clrLine(5);
            ScreenOutput.screenWrite("Right: " + InputData.rightDriverStick[1], 5);
        } catch(Exception e) {
            
        }
        
        if (InputData.leftDriverStick[1] > 0) { 
            OutputData.leftMotorVal = FRCConfig.kLEFT_MOTOR_MULTIPLIER * (InputData.leftDriverStick[1] * InputData.leftDriverStick[1]) * FRCConfig.kMAX_SHOOTER_POWER;
        } else {
            OutputData.leftMotorVal = -1 * FRCConfig.kLEFT_MOTOR_MULTIPLIER * (InputData.leftDriverStick[1] * InputData.leftDriverStick[1]) * FRCConfig.kMAX_SHOOTER_POWER;
        }
        if (InputData.rightDriverStick[1] > 0) {
            OutputData.rightMotorVal = FRCConfig.kRIGHT_MOTOR_MULTIPLIER * (InputData.rightDriverStick[1] * InputData.rightDriverStick[1]) * FRCConfig.kMAX_SHOOTER_POWER;
        } else {
            OutputData.rightMotorVal = -1 * FRCConfig.kRIGHT_MOTOR_MULTIPLIER * (InputData.rightDriverStick[1] * InputData.rightDriverStick[1]) * FRCConfig.kMAX_SHOOTER_POWER;
        }

        if (InputData.bAutoShootAndCatch == true) {
            catchAndShoot();
        }

        if (InputData.bPower == true) {
            //OutputData.bPullPin=InputData.bManualPin;
            OutputData.bStartShooter = InputData.bManualShoot;
        } else {
            fire();
        }

        if (InputData.grabberButttonPressed == true) {
            OutputData.leftGrabberVal = 1.0;
            OutputData.rightGrabberVal = 1.0;
        } else {
            OutputData.leftGrabberVal = 0;
            OutputData.rightGrabberVal = 0;
        }
        
        fRightMotorSpeed = CalcMotorSpeed(InputData.rightMotorEncoderVal, InputData.fRightEncoderTime);
        fLeftMotorSpeed = CalcMotorSpeed(InputData.leftMotorEncoderVal, InputData.fLeftEncoderTime);

        if (InputData.bDriveStraightPressed == true) {
            if (FRCConfig.kSTEER_P == 0 && FRCConfig.kSTEER_I == 0 && FRCConfig.kSTEER_D == 0) {
                steerStraight();
            } else {
                steerStraightPID();
            }
        } else if( InputData.bDistanceCorrect ) {
            if( FRCConfig.kDISTANCE_P == 0 && FRCConfig.kDISTANCE_I == 0 && FRCConfig.kDISTANCE_D == 0 ) {
                adjustPositionPID( 0 ); // TODO - add real distance from camera
            } else {
                adjustPosition( 0 ); // TODO - Add real distance from camera
            }
        } else {
            fLeftError = 0;
            fRightError = 0;
            fLeftLastError = 0;
            fRightLastError = 0;
        }
        
        OutputData.bLeftGrabberExtend = InputData.bGrabberExtendButtonPressed;
        OutputData.bRightGrabberExtend = InputData.bGrabberExtendButtonPressed;

        OutputData.bCarLockReverse = InputData.bCarLockRelay;
        
        ScreenOutput.clrLine(0);
        ScreenOutput.screenWrite("Left Motor Speed: " + fLeftMotorSpeed, 0);
        ScreenOutput.clrLine(1);
        ScreenOutput.screenWrite("Right Motor Speed: " + fRightMotorSpeed, 1);

        //Sets motor value for testing purposes
         // testMode(); - TDOD add back in
    }

    /**
     * Automatically determines firing step first, while button is pressed, the
     * firing pin is pulled next, while the button is not pressed, it does not
     * fire and the pistons are retracted last, it recharges for the next launch
     */
    public void fire() {
        if (InputData.shooterButtonPressed) {
            OutputData.bStartShooter = true;
            ScreenOutput.clrLine(2);
            ScreenOutput.screenWrite("Fire Step: Firing Pistons (trigger pulled)", 2);
        } else {
            OutputData.bStartShooter = false;
            ScreenOutput.clrLine(2);
            ScreenOutput.screenWrite("Fire Step: Retracting (Switch not hit)", 2);
        }
    }

    /**
     * Calculates the speed of the motors using encoders
     *
     * @param encoderCount number of times the encoder detects something
     * @param encoderTime time since the last encoder reading
     * @return gives the speed of the motor in RPM
     */
    public double CalcMotorSpeed(int encoderCount, double encoderTime) {
        double speed;
        speed = (double) encoderCount / (double) FRCConfig.kENCODER_PPR;
        speed /= ((double) encoderTime / 60000);
        return speed;
    }

    /**
     * matches left motor speed to right motor speed uses PID values as defined
     * in FRCConfig file
     */
    public void steerStraightPID() {
        //double expectPower = (OutputData.leftMotorVal*FRCConfig.kMAX_MOTOR_SPEED + OutputData.rightMotorVal*FRCConfig.kMAX_MOTOR_SPEED) / 2.0;
        double expectPower = (OutputData.leftMotorVal
                + OutputData.rightMotorVal) / 2.0;
        double leftPower = fLeftMotorSpeed / FRCConfig.kMAX_MOTOR_SPEED;
        double rightPower = fRightMotorSpeed / FRCConfig.kMAX_MOTOR_SPEED;

        double currErrorLeft = expectPower - leftPower;
        double currErrorRight = expectPower - rightPower;

        double leftCorrect = FRCConfig.kSTEER_P * currErrorLeft + FRCConfig.kSTEER_I * fLeftLastError + FRCConfig.kSTEER_D * fLeftError;
        double rightCorrect = FRCConfig.kSTEER_P * currErrorRight + FRCConfig.kSTEER_I * fRightLastError + FRCConfig.kSTEER_D * fRightError;

        OutputData.leftMotorVal = leftCorrect;
        OutputData.rightMotorVal = rightCorrect;

        fLeftLastError = currErrorLeft;
        fRightLastError = currErrorRight;
        fLeftError += currErrorLeft;
        fRightError += currErrorRight;
        if (OutputData.leftMotorVal > FRCConfig.kMAX_MOTOR_POWER) {
            OutputData.leftMotorVal = FRCConfig.kMAX_MOTOR_POWER;
        } else if (OutputData.leftMotorVal < -1 * FRCConfig.kMAX_MOTOR_POWER) {
            OutputData.leftMotorVal = -1 * FRCConfig.kMAX_MOTOR_POWER;
        }
        if (OutputData.rightMotorVal > FRCConfig.kMAX_MOTOR_POWER) {
            OutputData.rightMotorVal = FRCConfig.kMAX_MOTOR_POWER;
        } else if (OutputData.rightMotorVal < -1 * FRCConfig.kMAX_MOTOR_POWER) {
            OutputData.rightMotorVal = -1 * FRCConfig.kMAX_MOTOR_POWER;
        }
    }

    /**
     * matches left and right motor speeds uses percent error to determine
     * change required in other value
     */
    public void steerStraight() {
        double expectPowerLeft = OutputData.leftMotorVal * FRCConfig.kMAX_MOTOR_SPEED;
        double expectPowerRight = OutputData.rightMotorVal * FRCConfig.kMAX_MOTOR_SPEED;

        if (fLeftMotorSpeed < (1.0 - FRCConfig.kMAX_ERROR) * expectPowerLeft || fLeftMotorSpeed > (1 + FRCConfig.kMAX_ERROR) * expectPowerLeft) {
            double dif = (expectPowerLeft - fLeftMotorSpeed) / expectPowerLeft;
            OutputData.leftMotorVal += dif;
            if (OutputData.leftMotorVal < -1 * FRCConfig.kMAX_MOTOR_POWER) {
                OutputData.leftMotorVal = -1 * FRCConfig.kMAX_MOTOR_POWER;
            }
            if (OutputData.leftMotorVal > FRCConfig.kMAX_MOTOR_POWER) {
                OutputData.leftMotorVal = FRCConfig.kMAX_MOTOR_POWER;
            }
        }
        if (fRightMotorSpeed < (1.0 - FRCConfig.kMAX_ERROR) * expectPowerRight || fRightMotorSpeed > (1 + FRCConfig.kMAX_ERROR) * expectPowerRight) {
            double dif = (expectPowerRight - fRightMotorSpeed) / expectPowerRight;
            OutputData.rightMotorVal += dif;
            if (OutputData.rightMotorVal < -1 * FRCConfig.kMAX_MOTOR_POWER) {
                OutputData.rightMotorVal = -1 * FRCConfig.kMAX_MOTOR_POWER;
            }
            if (OutputData.rightMotorVal > FRCConfig.kMAX_MOTOR_POWER) {
                OutputData.rightMotorVal = FRCConfig.kMAX_MOTOR_POWER;
            }
        }
    }

    /**
     * magical function to throw, drive, and the catch the ball over the truss
     * first, start timer and begins shooting while button is pressed second,
     * wait for a predetermined amount of time, drives forward and resets the
     * timer while button is pressed third, wait for another predetermined
     * amount of time and stops moving
     */
    public void catchAndShoot() {
        switch (dCatchStep) {
            case kCATCH_EXTEND:
                InputData.shooterButtonPressed = true;
                thinkTimer.reset();
                thinkTimer.start();
                dCatchStep++;
                break;
            case kCATCH_RETRACT:
                if (thinkTimer.get() >= FRCConfig.kFIRING_TIME) {
                    InputData.shooterButtonPressed = false;
                    InputData.leftDriverStick[1] = FRCConfig.kMOTOR_SPEED;
                    InputData.rightDriverStick[1] = FRCConfig.kMOTOR_SPEED;
                    thinkTimer.reset();
                    dCatchStep++;
                } else {
                    InputData.shooterButtonPressed = true;
                }
                break;
            case kCATCH_SLEEP:
                if (thinkTimer.get() >= FRCConfig.kMOVE_TIME) {
                    InputData.leftDriverStick[1] = 0;
                    InputData.rightDriverStick[1] = 0;
                    thinkTimer.reset();
                    thinkTimer.stop();
                    dCatchStep = kCATCH_WAITING;
                } else {
                    InputData.leftDriverStick[1] = FRCConfig.kMOTOR_SPEED;
                    InputData.rightDriverStick[1] = FRCConfig.kMOTOR_SPEED;
                }
                break;
        }
    }

    /**
     * Moves the robot closer towards or farther away based on a consumed
     * distance
     *
     * @param distance - the length to travel (can be positive or negative)
     * @return the power to the motors
     */
    public double adjustPosition(double distance) {
        // (Naive implementation)
        if (distance > 0) {
            return FRCConfig.kMAX_MOTOR_POWER;
        } else if (distance < 0) {
            return -1 * FRCConfig.kMAX_MOTOR_POWER;
        }
        return 0;
    }

    public double adjustPositionPID(double distance) {
        double motorPower = FRCConfig.kDISTANCE_P * distance + FRCConfig.kDISTANCE_I
                * fLastDistanceError + FRCConfig.kDISTANCE_D * fDistanceError;
        if (motorPower > FRCConfig.kMAX_MOTOR_POWER) {
            motorPower = FRCConfig.kMAX_MOTOR_POWER;
        } else if (motorPower < -1 * FRCConfig.kMAX_MOTOR_POWER) {
            motorPower = FRCConfig.kMAX_MOTOR_POWER;
        }
        return motorPower;
    }
    
    public void testMode(){
        if(InputData.bTestMode == true){
            OutputData.leftMotorVal = 0;
            OutputData.rightMotorVal = 0;
        }
    }
}
