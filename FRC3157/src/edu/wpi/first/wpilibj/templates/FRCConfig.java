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
    public static double kENCODER_PPR = 4096;
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
                    String value=line.substring(delim+1,line.length());
                    
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
                    }
                }
            }
            return true;
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
