/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

/**
 * Holds information from RoboThink for RoboOutput
 * @author Programing subteam
 */
public class OutputData {
    
    /**
     * Value the left motor will be set to 
     */
    public static double leftMotorVal=0;
    
    /**
     * Value the right motor will be set to
     */
    public static double rightMotorVal=0;
    
    /**
     * Should we begin shooting?
     */
    public static boolean bStartShooter=false;
    
    /**
     * Should we pull the pin
     */
    //public static boolean bPullPin=false;
    
    /**
     * Value the left grabber motor will be set to
     */
    public static double leftGrabberVal=0;
    
    /**
     * Value the right grabber motor will be set to
     */
    public static double rightGrabberVal=0;
    
    public static boolean bLeftGrabberExtend=false;
    public static boolean bRightGrabberExtend=false;
    
    public static boolean bCarLockForward = false;
    public static boolean bCarLockBackward = false;
    /**
     * Sets all fields to 0 or false respectively
     */
    public static void reset() {
        leftMotorVal=0;
        rightMotorVal=0;
        
        bStartShooter=false;
        
        //bPullPin=false;
        
        leftGrabberVal=0;
        rightGrabberVal=0;
    }
    
}
