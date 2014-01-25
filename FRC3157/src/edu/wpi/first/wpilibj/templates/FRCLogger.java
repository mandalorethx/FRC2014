/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import com.sun.squawk.io.BufferedWriter;
import com.sun.squawk.microedition.io.FileConnection;
import edu.wpi.first.wpilibj.Timer;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import javax.microedition.io.Connector;

/**
 *
 * @author Matt
 */
public class FRCLogger {
    
    private static final String LOG_FILE="FRC_LOG.txt";
    
    private static FileConnection fc;
    private static DataOutputStream outStream;
    private static BufferedWriter outBuffer;
    private static OutputStreamWriter outStreamWriter;
    private static Timer clock;
    
    public static void initialize(){
        try{
            fc=(FileConnection)Connector.open("File;///" + LOG_FILE, Connector.WRITE);
            outStream=fc.openDataOutputStream();
            outStreamWriter=new OutputStreamWriter(outStream);
            outBuffer=new BufferedWriter(outStreamWriter);
            clock.start();
        }catch(IOException e){
            //TODO//
        }
    }
    public static void log(String msg){
        try{
            outBuffer.write("["+ clock.get() +"] - " + msg + "\n");
            outBuffer.flush();
        }catch(IOException e){
            //TODO//
        }
    }
    public static void close(){
        try{
            if( outBuffer != null )
                outBuffer.close();
        }catch(IOException e){
            //TODO//
        }
    }
}
