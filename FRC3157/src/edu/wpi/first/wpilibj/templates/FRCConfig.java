/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import com.sun.squawk.io.BufferedReader;
import com.sun.squawk.microedition.io.FileConnection;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.microedition.io.Connector;

/**
 * An implementation of the config file for the robot
 * @author Programming Subteam
 */
public class FRCConfig {
    
    public static final String CONFIG_FILE = "FRC_CONFIG.txt";
    public static double kMAX_MOTOR_SPEED = 100;
    public static double kMAX_ERROR = 0.1;
    public static double kMAX_SHOOTER_POWER = 0.9;
    public static double kMIN_SHOOTER_POWER = 0.7;
    public static double kSLEEP_TIME = 500.0;
    public static double kENCODER_PPR = 1440; // I am 85-90% sure this is correct based on model number.
    public static int kCALIBRATE_BUTTON = 0;
    public static int kLEFT_ENCODER_PORT_1= 0;
    public static int kLEFT_ENCODER_PORT_2= 1;
    public static int kRIGHT_ENCODER_PORT_1= 2;
    public static int kRIGHT_ENCODER_PORT_2= 3;
    public static double fMaxShooterPower = 0.9;
    public static int dLeftMotorSlot = 0;
    public static int dRightMotorSlot = 1;
    public static int dLeftDriverPort = 0;
    public static int dRightDriverPort = 1;
    public static int dCoDriverPort = 2;
    public static double kMAX_MOTOR_POWER = 0.9;
    public static int kLEFT_MOTOR_SLOT = 0;
    public static int kRIGHT_MOTOR_SLOT = 1;
    public static int kLEFT_DRIVER_STICK=0;
    public static int kRIGHT_DRIVER_STICK=1;
    public static int kCO_DRIVER_STICK=2;
    public static int dPressureSlot=0;
    public static int dRelaySlot=1;
    public static int dLeftShooterSlot=2;
    public static int dRightShooterSlot=3;
    public static int dPinShooterSlot=4;
    public static int kDRIVE_STRAIGHT_BUTTON = 0;
    public static int kMAGIC_SHOOT_CATCH = 2;
    public static int kSHOOTER_BUTTON = 0;
    public static int kGRABBER_BUTTON = 3;
    public static int kMANUAL_SHOOTER = 4;
    public static int kMANUAL_PIN = 5;
    public static int kMANUAL_ON = 6;
    public static int kMANUAL_OFF = 7;
    public static double kSTEER_P = 0;
    public static double kSTEER_I = 0;
    public static double kSTEER_D = 0;
    public static int kAUTOTURN_LEFT = 3;
    public static int kAUTOTURN_RIGHT = 4;
    public static double kAUTON_DELAY = 0;
    public static double kAUTON_MOVE_DELAY=3000;
    public static double kAUTON_MOVE_TIME=2000;
    public static double kAUTON_FIRE_TIME=1000;
    public static boolean kRUN_AUTONOMOUS = true;
    public static double kFIRING_TIME = 500;
    public static double kMOTOR_SPEED = 0.9;
    public static double kMOVE_TIME = 2000;
    
    private static FileConnection fc;
    private static DataInputStream inStream;
    private static BufferedReader inBuffer;
    private static InputStreamReader inStreamReader;

    
    /**
     * Initializes the static variables for the config class
     * @return true if the config file is successfully loaded; false otherwise
     */
    public static boolean initialize(){
        openFile(CONFIG_FILE);
        boolean fileDone = false;
        try{
            while(!fileDone)
            {
                String line = inBuffer.readLine();
                if(line == null){
                    fileDone=true;
                    break;
                }
                int delim=line.indexOf("=");
                if(delim!=-1){
                    String varName=line.substring(0,delim);
                    String value=line.substring(delim+1,line.length()-1);
                    
                    if(varName.equals("fMaxShooterPower")){
                        fMaxShooterPower=Double.parseDouble(value);
                    }else if(varName.equals("dLeftMotorSlot")){
                        dLeftMotorSlot=Integer.parseInt(value);
                    }else if(varName.equals("dRightMotorSlot")){
                        dRightMotorSlot=Integer.parseInt(value);
                    }else if(varName.equals("dLeftDriverPort")){
                        dLeftDriverPort=Integer.parseInt(value);
                    }else if(varName.equals("dRightDriverPort")){
                        dRightDriverPort=Integer.parseInt(value);
                    }else if(varName.equals("dcoDriverPort")){
                        dCoDriverPort=Integer.parseInt(value);
                    }else if(varName.equals("kMAX_MOTOR_SPEED")){
                        kMAX_MOTOR_SPEED=Integer.parseInt(value);
                    }else if(varName.equals("kMAX_ERROR")){
                        kMAX_ERROR=Integer.parseInt(value);
                    }else if(varName.equals("kMAX_SHOOTER_POWER")){
                        kMAX_SHOOTER_POWER=Integer.parseInt(value);
                    }else if(varName.equals("kMIN_SHOOTER_POWER")){
                        kMIN_SHOOTER_POWER=Integer.parseInt(value);                    
                    }else if(varName.equals("kSLEEP_TIME")){
                        kSLEEP_TIME=Integer.parseInt(value);
                    }else if(varName.equals("kENCODER_PPR")){
                        kENCODER_PPR=Integer.parseInt(value);
                    }else if(varName.equals("kCALIBRATE_BUTTON")){
                        kCALIBRATE_BUTTON=Integer.parseInt(value);
                    }else if(varName.equals("kLEFT_ENCODER_PORT_1")){
                        kLEFT_ENCODER_PORT_1=Integer.parseInt(value);
                    }else if(varName.equals("kLEFT_ENCODER_PORT_2")){
                        kLEFT_ENCODER_PORT_2=Integer.parseInt(value);
                    }else if(varName.equals("kRIGHT_ENCODER_PORT_1")){
                        kRIGHT_ENCODER_PORT_1=Integer.parseInt(value);
                    }else if(varName.equals("kRIGHT_ENCODER_PORT_2")){
                        kRIGHT_ENCODER_PORT_2=Integer.parseInt(value);
                    }else if(varName.equals("kMAX_MOTOR_POWER")){
                        kMAX_MOTOR_POWER=Integer.parseInt(value);
                    }else if(varName.equals("kLEFT_MOTOR_SLOT")){
                        kLEFT_MOTOR_SLOT=Integer.parseInt(value);
                    }else if(varName.equals("kRIGHT_MOTOR_SLOT")){
                        kRIGHT_MOTOR_SLOT=Integer.parseInt(value);
                    }else if(varName.equals("kLEFT_DRIVER_STICK")){
                        kLEFT_DRIVER_STICK=Integer.parseInt(value);
                    }else if(varName.equals("kRIGHT_DRIVER_STICK=1;")){
                        kRIGHT_DRIVER_STICK=Integer.parseInt(value);
                    }else if(varName.equals("kCO_DRIVER_STICK")){
                        kCO_DRIVER_STICK=Integer.parseInt(value);
                    }else if(varName.equals("dPressureSlot")){
                        dPressureSlot=Integer.parseInt(value);
                    }else if(varName.equals("dRelaySlot")){
                        dRelaySlot=Integer.parseInt(value);
                    }else if(varName.equals("dLeftShooterSlot")){
                        dLeftShooterSlot=Integer.parseInt(value);
                    }else if(varName.equals("dRightShooterSlot")){
                        dRightShooterSlot=Integer.parseInt(value);
                    }else if(varName.equals("dPinShooterSlot")){
                        dPinShooterSlot=Integer.parseInt(value);
                    }else if(varName.equals("kDRIVE_STRAIGHT_BUTTON")){
                        kDRIVE_STRAIGHT_BUTTON=Integer.parseInt(value);
                    }else if(varName.equals("kMAGIC_SHOOT_CATCH")){
                        kMAGIC_SHOOT_CATCH=Integer.parseInt(value);
                    }else if(varName.equals("kSHOOTER_BUTTON")){
                        kSHOOTER_BUTTON=Integer.parseInt(value);
                    }else if(varName.equals("kGRABBER_BUTTON")){
                        kGRABBER_BUTTON=Integer.parseInt(value);
                    }else if(varName.equals("kMANUAL_SHOOTER")){
                        kMANUAL_SHOOTER=Integer.parseInt(value);
                    }else if(varName.equals("kMANUAL_PIN")){
                        kMANUAL_PIN=Integer.parseInt(value);
                    }else if(varName.equals("kMANUAL_ON")){
                        kMANUAL_ON=Integer.parseInt(value);
                    }else if(varName.equals("kMANUAL_OFF")){
                        kMANUAL_OFF=Integer.parseInt(value);
                    }else if(varName.equals("kSTEER_I")){
                        kSTEER_I=Double.parseDouble(value);
                    }else if(varName.equals("kSTEER_P")){
                        kSTEER_P=Double.parseDouble(value);
                    }else if(varName.equals("kSTEER_D")){
                        kSTEER_D=Double.parseDouble(value);
                    }else if(varName.equals("kAUTOTURN_LEFT")){
                        kAUTOTURN_LEFT=Integer.parseInt(value);
                    }else if(varName.equals("kAUTOTURN_RIGHT")){
                        kAUTOTURN_RIGHT=Integer.parseInt(value);
                    }else if(varName.equals("kAUTON_DELAY")){
                        kAUTON_DELAY=Double.parseDouble(value);
                    }else if(varName.equals("kAUTON_MOVE_DELAY")){
                        kAUTON_MOVE_DELAY=Double.parseDouble(value);
                    }else if(varName.equals("kAUTON_MOVE_TIME")){
                        kAUTON_MOVE_TIME=Double.parseDouble(value);
                    }else if(varName.equals("kAUTON_FIRE_TIME")){
                        kAUTON_FIRE_TIME=Double.parseDouble(value);
                    }else if(varName.equals("kRUN_AUTONOMOUS")){
                        kRUN_AUTONOMOUS = value.toLowerCase().equals("true");
                    }else if(varName.equals("kFIRING_TIME")){
                        kFIRING_TIME = Double.parseDouble(value);
                    }else if(varName.equals("kMOTOR_SPEED")){
                        kMOTOR_SPEED = Double.parseDouble(value);
                    }else if(varName.equals("kMOVE_TIME")){
                        kMOVE_TIME = Double.parseDouble(value);
                    }
                }
            }return true;
        }catch(Exception e){
            return false;
        }
        finally
        {
            closeFile();
        }
    }
    
    /**
     * Helper function that opens the config file
     * @param fileName - the name of the config file
     */
    private static void openFile(String fileName){
        try{
            fc = (FileConnection) Connector.open("file:///" + fileName, Connector.READ);
            inStream = fc.openDataInputStream();
            inStreamReader = new InputStreamReader( inStream );
            inBuffer = new BufferedReader(inStreamReader);
        }catch(IOException e){
            System.out.println("could not open file " + fileName);
        }
    }    
    
    /**
     * Closes the file if possible
     */
    private static void closeFile(){
        try{
            if(inBuffer != null){
                inBuffer.close();
            }
            if(inStreamReader != null){
                inStreamReader.close();
            }
            if(inStream != null){
                inStream.close();
            }
            if(fc != null){
                fc.close();
            }
        }catch(IOException e){
            
        }
    }
}
