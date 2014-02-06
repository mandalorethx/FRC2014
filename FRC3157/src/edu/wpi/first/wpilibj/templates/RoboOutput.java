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
        this.driveLeft = new Victor(left);
        this.driveRight = new Victor(right);
        this.airCompressor = new Compressor(FRCConfig.SLOT_PRESSURE, +FRCConfig.SLOT_COMPRESSOR_RELAY);
        this.airCompressor.start();
        this.leftShooter = new Solenoid(FRCConfig.SLOT_LEFT_SHOOTER);
        this.rightShooter = new Solenoid(FRCConfig.SLOT_RIGHT_SHOOTER);
        this.grabberLeftExtend = new Solenoid(FRCConfig.SLOT_GRABBER_EXTEND);
        //this.pinShooter=new Solenoid( FRCConfig.SLOT_PIN_SHOOTER );
    }

    /**
     * Sets drive motors and grabber motors power level Sets the solenoid state
     * to off or on Turns the shooter pin off or on
     */
    public void setOutputs() {
        this.driveLeft.set(OutputData.leftMotorVal);
        this.driveRight.set(OutputData.rightMotorVal);
        /*
         this.shooterLeft.set(OutputData.leftShooterVal);
         this.shooterRight.set(OutputData.rightShooterVal);
         */
        this.grabberLeft.set(OutputData.leftGrabberVal);
        this.grabberRight.set(OutputData.rightGrabberVal);

        this.leftShooter.set(OutputData.bStartShooter);
        this.rightShooter.set(OutputData.bStartShooter);
        
        this.grabberLeftExtend.set(OutputData.bLeftGrabberExtend);
        this.grabberRightExtend.set(OutputData.bRightGrabberExtend);
        //this.pinShooter.set(OutputData.bPullPin);
    }
}
