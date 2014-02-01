/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

/**
 * User input used by roboThink
 * @author Programming subteam
 */
public class InputData {
    
    /**
     * The coordinates for the left driver stick where
     * index 0 is X, index 1 is Y, index 2 is Z
     */
    public static double leftDriverStick[]={0,0,0};
    
    /**
     * The coordinates for the right driver stick where
     * index 0 is X, index 1 is Y, index 2 is Z
     */
    public static double rightDriverStick[]={0,0,0};
    
    /**
     * The coordinates for the co-driver stick where
     * index 0 is X, index 1 is Y, index 2 is Z
     */
    public static double coDriverStick[]={0,0,0};
    
    /**
     * Is the shooter button pressed?
     */
    public static boolean shooterButtonPressed=false;
    
    /**
     * Is the grabber button pressed?
     */
    public static boolean grabberButttonPressed=false;    
    
    /**
     * Is the shooter extracted
     */
    public static boolean bShooterExt=false;
    
    /**
     * Is the shooter retracted?
     */
    public static boolean bShooterRet=false;
  
    /**
     * The number of rotations performed by the right motor
     */
    public static int leftMotorEncoderVal;
    
    /**
     * The number of rotations performed by the right motor
     */
    public static int rightMotorEncoderVal;
    
    /**
     * The time elapsed over the last left encoder interval
     */
    public static double fLeftEncoderTime;
    
    /**
     * The time elapsed over the last right encoder interval
     */
    public static double fRightEncoderTime;

    /**
     * Are we driving straight?
     */
    public static boolean bDriveStraightPressed = false;
    
    /**
     * Is automatic shooting and catching enabled?
     */
    public static boolean bAutoShootAndCatch = false;
    
    /**
     * Are we shooting?
     */
    public static boolean bManualShoot = false;
    
    /**
     * Is the pin locked?
     */
    public static boolean bManualPin = false;
    
    /**
     * Are we shooting manually?
     */
    public static boolean bPower = false;
    
    /**
     * Are we turning left automatically (as opposed to manually turning?)
     */
    public static boolean bAutoturnLeft = false;
    
    /**
     * Are we turning right automatically (as opposed to manually turning?)
     */
    public static boolean bAutoturnRight = false;
    public static FRCImage camProcessor;
    
    /**
     * Sets all fields to 0 or false
     */
    public static void reset() {
        for(int ii=0; ii<3; ++ii){
            leftDriverStick[ii] = 0;
            rightDriverStick[ii] = 0;
            coDriverStick[ii] = 0;        
        }
        shooterButtonPressed=false;
        grabberButttonPressed=false;     
        
        leftMotorEncoderVal=0;
        rightMotorEncoderVal=0;
        
    }
}
