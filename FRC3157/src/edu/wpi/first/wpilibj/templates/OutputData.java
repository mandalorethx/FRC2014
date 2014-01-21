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
public class OutputData {
    public static double leftMotorVal=0;
    public static double rightMotorVal=0;
    
    public static double leftShooterVal=0;
    public static double rightShooterVal=0;
    
    public static double leftGrabberVal=0;
    public static double rightGrabberVal=0;
    
    
    public static void reset() {
        leftMotorVal=0;
        rightMotorVal=0;
        
        leftShooterVal=0;
        rightShooterVal=0;
        
        leftGrabberVal=0;
        rightGrabberVal=0;
    }
    
}
