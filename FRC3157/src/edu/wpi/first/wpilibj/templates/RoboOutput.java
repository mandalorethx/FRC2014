/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.Relay;
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
    public Solenoid leftShooterEXT; //Controls the state of the air compressor
    public Solenoid leftShooterRET;
    public Solenoid rightShooterEXT;//Controls the state of the air compressor
    public Solenoid rightShooterRET;
    public Solenoid grabberEXT;
    public Solenoid grabberRET;
    public Solenoid latchRET; //Locking mechanism for the shooter arms

    public Victor grabberLeft; //Controls the grabber arms to pick up the ball
    public Victor grabberRight; //Controls the grabber arms to pick up the ball

    public Relay testRelay;

    public DigitalModule digiMod;

    public static boolean bLeftDriveFound = false;
    public static boolean bRightDriveFound = false;
    public static boolean bAirCompressorFound = false;
    public static boolean bLeftRETShooterFound = false;
    public static boolean bRightRETShooterFound = false;
    public static boolean bLeftEXTShooterFound = false;
    public static boolean bRightEXTShooterFound = false;
    public static boolean bGrabberExtenderFound = false;
    public static boolean bLeftGrabberFound = false;
    public static boolean bRightGrabberFound = false;
    public static boolean bLatchFound = false;

    /**
     * Initializing variables, and starts the air compressor
     *
     * @param left Motor #
     * @param right Motor #
     */
    public void initialize(int left, int right) {
        System.out.println("Left: " + left + " Right: " + right);

        try {
            digiMod = DigitalModule.getInstance(DigitalModule.getDefaultDigitalModule());
            digiMod.setRelayForward(FRCConfig.SLOT_PRETTY_LIGHTS, true);
        } catch (Exception e) {
            System.out.println("***UNABLE TO FIND DIGITAL MODULE!***");
        }

        System.out.println("Creating Victors NOW: ");

        try {
            System.out.println("\tLeft: ");
            this.driveLeft = new Victor(left);
            bLeftDriveFound = true;
        } catch (Exception e) {
            System.out.println("unable to connect to left drive motor: " + e.toString());
            FRCLogger.getInstance().logError("unable to connect to left drive motor: " + e.toString());
            bLeftDriveFound = false;
        }
        try {
            System.out.println("\tRight: ");
            this.driveRight = new Victor(right);
            bRightDriveFound = true;
        } catch (Exception e) {
            System.out.println("unable to connect to right drive motor: " + e.toString());
            FRCLogger.getInstance().logError("unable to connect to right drive motor: " + e.toString());
            bRightDriveFound = false;
        }

        if( true ) {
            try {
                this.airCompressor = new Compressor(FRCConfig.SLOT_PRESSURE, FRCConfig.SLOT_COMPRESSOR_RELAY);
                this.airCompressor.start();
                bAirCompressorFound = true;
            } catch (Exception e) {
                System.out.print("unable to connect to air compressor");
                FRCLogger.getInstance().logError("unable to connect to air compressor");
                bAirCompressorFound = false;
            }
        } else {
            bAirCompressorFound = false;
        }
        

        try {
            this.leftShooterEXT = new Solenoid(FRCConfig.LEFT_EXT);
            bLeftEXTShooterFound = true;
        } catch (Exception e) {
            System.out.println("unable to connect to left extend Solenoid");
            FRCLogger.getInstance().logError("unable to connect to left extend Solenoid");
            bRightEXTShooterFound = false;
        }
        try {
            this.leftShooterRET = new Solenoid(FRCConfig.LEFT_RET);
            bLeftRETShooterFound = true;
        } catch (Exception e) {
            System.out.println("unable to connect to left retract Solenoid");
            FRCLogger.getInstance().logError("unable to connect to left retract Solenoid");
            bRightRETShooterFound = false;
        }
        try {
            this.rightShooterEXT = new Solenoid(FRCConfig.RIGHT_EXT);
            bRightEXTShooterFound = true;
        } catch (Exception e) {
            System.out.println("unable to connect to right extend Solenoid");
            FRCLogger.getInstance().logError("unable to connect to right extend Solenoid");
            bLeftEXTShooterFound = false;
        }
        try {
            this.rightShooterRET = new Solenoid(FRCConfig.RIGHT_RET);
            bRightRETShooterFound = true;
        } catch (Exception e) {
            System.out.println("unable to connect to right retract Solenoid");
            FRCLogger.getInstance().logError("unable to connect to right retract Solenoid");
            bLeftRETShooterFound = false;
        }

        try {
            this.grabberLeft = new Victor(FRCConfig.SLOT_LEFT_GRABBER_MOTOR);
            bLeftGrabberFound = true;
        } catch (Exception e) {
            System.out.println("unable to connect to left grabber motor Victor");
            FRCLogger.getInstance().logError("unable to connect to left grabber motor Victor");
            bLeftGrabberFound = false;
        }

        try {
            this.grabberRight = new Victor(FRCConfig.SLOT_RIGHT_GRABBER_MOTOR);
            bRightGrabberFound = true;
        } catch (Exception e) {
            System.out.println("unable to connect to right grabber motor Victor");
            FRCLogger.getInstance().logError("unable to connect to right grabber motor Victor");
            bRightGrabberFound = false;
        }

        try {
            this.grabberEXT = new Solenoid(FRCConfig.SLOT_GRABBER_EXTEND);
            this.grabberRET = new Solenoid(FRCConfig.SLOT_GRABBER_RETRACT);
            bGrabberExtenderFound = true;
        } catch (Exception e) {
            System.out.println("unable to connect to middle Solenoid");
            FRCLogger.getInstance().logError("unable to connect to middle Solenoid");
            bGrabberExtenderFound = false;

        }

        try {
            this.latchRET = new Solenoid(FRCConfig.LATCH_RET);
            bLatchFound = true;
        } catch (Exception e) {
            System.out.println("unable to connect to latch Solenoid");
            FRCLogger.getInstance().logError("unable to connect to latch Solenoid");
            bLatchFound = false;
        }
    }

    /**
     * Sets drive motors and grabber motors power level Sets the solenoid state
     * to off or on Turns the shooter pin off or on
     */
    public void setOutputs() {

        // System.out.println( "Left In: " + OutputData.leftMotorVal + "|| Right In: " + OutputData.rightMotorVal);
        this.driveLeft.set(OutputData.leftMotorVal);
        this.driveRight.set(OutputData.rightMotorVal);

        // processCompressor();
        
        // System.out.println( "Left Out: " + this.driveLeft.get() + "|| Right Out: " + this.driveRight.get());
        /*
         this.shooterLeft.set(OutputData.leftShooterVal);
         this.shooterRight.set(OutputData.rightShooterVal);
         */
        if (bLeftGrabberFound && bRightGrabberFound == true) {
            this.grabberLeft.set(OutputData.leftGrabberVal);
            this.grabberRight.set(OutputData.rightGrabberVal);
        }

        if (bLeftEXTShooterFound && bRightEXTShooterFound == true) {
            this.leftShooterEXT.set(OutputData.bStartShooter);
            this.rightShooterEXT.set(OutputData.bStartShooter);

            if (bLeftRETShooterFound && bRightRETShooterFound == true) {
                this.leftShooterRET.set(OutputData.bRetractShooter);
                this.rightShooterRET.set(OutputData.bRetractShooter);

                if (bGrabberExtenderFound = true) {
                    this.grabberRET.set(OutputData.bGrabberRET);
                    this.grabberEXT.set(OutputData.bGrabberEXT);
                }

                /*
                 if(OutputData.bCarLockReverse == true){
                 digiMod.setRelayReverse(1, true);
                 }else{
                 digiMod.setRelayReverse(1, false);
                 }
                 */
                if (bLatchFound == true) {
                    this.latchRET.set(OutputData.bReleaseLatch);
                }

            }

        }
    }
    
    public void processCompressor() {
        System.out.println("getDIO: " + digiMod.getDIO( FRCConfig.SLOT_PRESSURE) );
        if( digiMod.getDIO(FRCConfig.SLOT_PRESSURE) ) {
            digiMod.setRelayReverse(FRCConfig.SLOT_COMPRESSOR_RELAY, true);
        }
        else {
            digiMod.setRelayReverse(FRCConfig.SLOT_COMPRESSOR_RELAY, false);
        }
    }
}
