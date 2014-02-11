/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;

/**
 * Takes processes input from RoboThink and directs it to other components of
 * the robot
 *
 * @author FRCUser
 */
public class RoboOutput {

    public Victor driveLeft; //left drive motor
    public Victor driveRight; //right drive motor

    public Compressor airCompressor; //Air compressor for shooting
    public Solenoid leftShooter; //Controls the state of the air compressor
    public Solenoid rightShooter; //Controls the state of the air compressor
    public Solenoid grabberLeftExtend;
    public Solenoid grabberRightExtend;
    //public Solenoid pinShooter; //Locking mechanism for the shooter arms

    public Victor grabberLeft; //Controls the grabber arms to pick up the ball
    public Victor grabberRight; //Controls the grabber arms to pick up the ball
    
    public static boolean bLeftDriveFound = false;
    public static boolean bRightDriveFound = false;
    public static boolean bAirCompressorFound = false;
    public static boolean bLeftShooterFound = false;
    public static boolean bRightShooterFound = false;
    public static boolean bGrabberExtenderFound = false;
    public static boolean bLeftGrabberFound = false;
    public static boolean bRightGrabberFound = false;
    
    /**
     * Initializing variables, and starts the air compressor
     *
     * @param left Motor #
     * @param right Motor #
     */
    public void initialize(int left, int right) {
        System.out.println("Left: " + left + " Right: " + right);
        
        try {
            DigitalModule digiMod = DigitalModule.getInstance(DigitalModule.getDefaultDigitalModule());
        } catch( Exception e) {
            System.out.println("***UNABLE TO FIND DIGITAL MODULE!***");
        }
        
        System.out.println("Creating Victors NOW: ");
        
        try{
            System.out.println("\tLeft: ");
            this.driveLeft = new Victor(left);
            bLeftDriveFound = true;
        }catch(Exception e){
            System.out.println("unable to connect to left drive motor: " + e.toString());
            FRCLogger.getInstance().logError("unable to connect to left drive motor: " + e.toString());
            bLeftDriveFound = false;
        }
        try{
            System.out.println("\tRight: ");
            this.driveRight = new Victor(right);
            bRightDriveFound = true;
        }catch(Exception e){
            System.out.println("unable to connect to right drive motor: " + e.toString());
            FRCLogger.getInstance().logError("unable to connect to right drive motor: " + e.toString());
            bRightDriveFound = false;
        }
        
        try{
            this.airCompressor = new Compressor(FRCConfig.SLOT_PRESSURE, +FRCConfig.SLOT_COMPRESSOR_RELAY);
            this.airCompressor.start();
            bAirCompressorFound = true;
        }catch(Exception e){
            System.out.print("unable to connect to air compressor");
            FRCLogger.getInstance().logError("unable to connect to air compressor");
            bAirCompressorFound = true;
        }
        
        try{
            this.leftShooter = new Solenoid(FRCConfig.SLOT_LEFT_SHOOTER);
            bLeftShooterFound = true;
        }catch(Exception e){
            System.out.println("unable to connect to left Solenoid");
            FRCLogger.getInstance().logError("unable to connect to left Solenoid");
            bRightShooterFound = false;
        }
        try{
            this.rightShooter = new Solenoid(FRCConfig.SLOT_RIGHT_SHOOTER);
            bRightShooterFound = true;
        }catch(Exception e){
            System.out.println("unable to connect to right Solenoid");
            FRCLogger.getInstance().logError("unable to connect to right Solenoid");
            bRightShooterFound = false;
        }
        
        try{
            this.grabberLeft = new Victor(FRCConfig.SLOT_LEFT_GRABBER_MOTOR);
            bLeftGrabberFound = true;
        }catch(Exception e){
            System.out.println("unable to connect to left grabber motor Victor");
            FRCLogger.getInstance().logError("unable to connect to left grabber motor Victor");
            bLeftGrabberFound = false;
        }
        
        try{
            this.grabberRight = new Victor(FRCConfig.SLOT_RIGHT_GRABBER_MOTOR);
            bRightGrabberFound = true;
        }catch(Exception e){
            System.out.println("unable to connect to right grabber motor Victor");
            FRCLogger.getInstance().logError("unable to connect to right grabber motor Victor");
            bRightGrabberFound = false;
        }
        
        try{
            this.grabberLeftExtend = new Solenoid(FRCConfig.SLOT_GRABBER_EXTEND);
            bGrabberExtenderFound = true;
        }catch(Exception e){
            System.out.println("unable to connect to middle Solenoid");
            FRCLogger.getInstance().logError("unable to connect to middle Solenoid");
            bGrabberExtenderFound = false;
        }
        //this.pinShooter=new Solenoid( FRCConfig.SLOT_PIN_SHOOTER );
    }

    /**
     * Sets drive motors and grabber motors power level Sets the solenoid state
     * to off or on Turns the shooter pin off or on
     */
    public void setOutputs() {
        
        // System.out.println( "Left In: " + OutputData.leftMotorVal + "|| Right In: " + OutputData.rightMotorVal);
        
        this.driveLeft.set(OutputData.leftMotorVal);
        this.driveRight.set(OutputData.rightMotorVal);
        
        // System.out.println( "Left Out: " + this.driveLeft.get() + "|| Right Out: " + this.driveRight.get());
        /*
         this.shooterLeft.set(OutputData.leftShooterVal);
         this.shooterRight.set(OutputData.rightShooterVal);
         */
        
        if(bLeftGrabberFound && bRightGrabberFound == true){
            this.grabberLeft.set(OutputData.leftGrabberVal);
            this.grabberRight.set(OutputData.rightGrabberVal);
        }

        if(bLeftShooterFound && bRightShooterFound == true){
            if( OutputData.bStartShooter ) {
                System.out.println("Starting Shooter TRUE");
            }

            this.leftShooter.set(OutputData.bStartShooter);
            this.rightShooter.set(OutputData.bStartShooter);
        }
        
        if(bGrabberExtenderFound = true){
            this.grabberLeftExtend.set(OutputData.bLeftGrabberExtend);
        }
        //this.pinShooter.set(OutputData.bPullPin);
    }
}
