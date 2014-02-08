/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Compressor;
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
    
    
    
    /**
     * Initializing variables, and starts the air compressor
     *
     * @param left Motor #
     * @param right Motor #
     */
    public void initialize(int left, int right) {
        System.out.println("Left: " + left + " Right: " + right);
        try{
            this.driveLeft = new Victor(left);
        }catch(Exception e){
            System.out.println("unable to connect to left drive motor");
            FRCLogger.getInstance().logError("unable to connect to left drive motor");
            e.printStackTrace();
        }
        try{
            this.driveRight = new Victor(right);
        }catch(Exception e){
            System.out.println("unable to connect to right drive motor");
            FRCLogger.getInstance().logError("unable to connect to right drive motor");
            e.printStackTrace();
        }
        
        try{
            this.airCompressor = new Compressor(FRCConfig.SLOT_PRESSURE, +FRCConfig.SLOT_COMPRESSOR_RELAY);
            this.airCompressor.start();
        }catch(Exception e){
            System.out.print("unable to connect to air compressor");
            FRCLogger.getInstance().logError("unable to connect to air compressor");
            e.printStackTrace();
        }
        
        try{
            this.leftShooter = new Solenoid(FRCConfig.SLOT_LEFT_SHOOTER);
        }catch(Exception e){
            System.out.println("unable to connect to left Solenoid");
            FRCLogger.getInstance().logError("unable to connect to left Solenoid");
            e.printStackTrace();
        }
        try{
        this.rightShooter = new Solenoid(FRCConfig.SLOT_RIGHT_SHOOTER);
        }catch(Exception e){
            System.out.println("unable to connect to right Solenoid");
            FRCLogger.getInstance().logError("unable to connect to right Solenoid");
            e.printStackTrace();
        }
        
        try{
        this.grabberLeftExtend = new Solenoid(FRCConfig.SLOT_GRABBER_EXTEND);
        }catch(Exception e){
            System.out.println("unable to connect to middle Solenoid");
            FRCLogger.getInstance().logError("unable to connect to middle Solenoid");
            e.printStackTrace();
        }
        //this.pinShooter=new Solenoid( FRCConfig.SLOT_PIN_SHOOTER );
    }

    /**
     * Sets drive motors and grabber motors power level Sets the solenoid state
     * to off or on Turns the shooter pin off or on
     */
    public void setOutputs() {
        
        System.out.println( "Left: " + OutputData.leftMotorVal + "|| Right: " + OutputData.rightMotorVal);
        
        this.driveLeft.set(OutputData.leftMotorVal);
        this.driveRight.set(OutputData.rightMotorVal);
        /*
         this.shooterLeft.set(OutputData.leftShooterVal);
         this.shooterRight.set(OutputData.rightShooterVal);
         */
        try {
            this.grabberLeft.set(OutputData.leftGrabberVal);
            this.grabberRight.set(OutputData.rightGrabberVal);
        } catch( Exception e ) {
            
        }

        try {
            this.leftShooter.set(OutputData.bStartShooter);
            this.rightShooter.set(OutputData.bStartShooter);
        } catch( Exception e) {
            
        }
        
        try {
            this.grabberLeftExtend.set(OutputData.bLeftGrabberExtend);
            this.grabberRightExtend.set(OutputData.bRightGrabberExtend);
        } catch( Exception e) {
            
        }
        //this.pinShooter.set(OutputData.bPullPin);
    }
}
