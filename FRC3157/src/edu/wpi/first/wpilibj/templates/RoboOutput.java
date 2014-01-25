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
 *
 * @author FRCUser
 */
public class RoboOutput {
    
    public Victor driveLeft;
    public Victor driveRight;
    
    public static final int dPressureSlot=0;
    public static final int dRelaySlot=1;
    public Compressor airCompressor;
    public Solenoid leftShooter;
    public Solenoid rightShooter;
    public Solenoid pinShooter;
    public static final int dLeftShooterSlot=2;
    public static final int dRightShooterSlot=3;
    public static final int dPinShooterSlot=4;
    
    public Victor grabberLeft;
    public Victor grabberRight;
        
    
    public void initialize( int left, int right ){
        this.driveLeft=new Victor( left );
        this.driveRight=new Victor( right );
        this.airCompressor=new Compressor( dPressureSlot,dRelaySlot );
        this.airCompressor.start();
        this.leftShooter=new Solenoid( dLeftShooterSlot );
        this.rightShooter=new Solenoid( dRightShooterSlot );
        this.pinShooter=new Solenoid( dPinShooterSlot );
    }
    
    public void setOutputs(){
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
        this.pinShooter.set(OutputData.bPullPin);
    }
}
