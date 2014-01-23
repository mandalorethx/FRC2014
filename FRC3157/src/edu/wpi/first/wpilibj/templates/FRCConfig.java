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
 *
 * @author Matt
 */
public class FRCConfig {
    
    public static final String CONFIG_FILE = "FRC_CONFIG.txt";
    
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
    
    public static void initialize(String fileName){
        try{
            fc = (FileConnection) Connector.open("file:///" + fileName, Connector.READ);
            inStream = fc.openDataInputStream();
            inStreamReader = new InputStreamReader( inStream );
            inBuffer = new BufferedReader(inStreamReader);
        }catch(IOException e){
            System.out.println("could not open file " + fileName);
        }
    }    
    public static void closeFile(){
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
    public static void innitConfig(){
        initialize(CONFIG_FILE);
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
                    }
                    
                }           
            }
        }catch(Exception e){
        
        }
        closeFile();
    }
}
