/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

/**
 *
 * @author FRCUser
 */
public class InputData {
    // index 0 is X, index 1 is Y, index 2 is Z
    public static double leftDriverStick[]={0,0,0};
    public static double rightDriverStick[]={0,0,0};
    public static double coDriverStick[]={0,0,0};
    
    public static boolean shooterButtonPressed=false;
    public static boolean grabberButttonPressed=false;    
    
    public static boolean bShooterExt=false;
    public static boolean bShooterRet=false;
  
    public static int leftMotorEncoderVal;
    public static int rightMotorEncoderVal;
    
    public static double fLeftEncoderTime;
    public static double fRightEncoderTime;

    public static boolean bDriveStraightPressed = false;
    
    public static boolean bAutoShootAndCatch = false;
    
    public static boolean bManualShoot = false;
    public static boolean bManualPin = false;
    
    public static boolean bPower = false;
    
    public static boolean bAutoturnLeft = false;
    public static boolean bAutoturnRight = false;
    
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
