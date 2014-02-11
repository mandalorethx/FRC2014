/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import com.sun.squawk.io.BufferedWriter;
import com.sun.squawk.io.j2me.file.Protocol;
import com.sun.squawk.microedition.io.FileConnection;
import edu.wpi.first.wpilibj.Timer;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import javax.microedition.io.Connector;

/**
 * Singleton logging class -- sorry Matt but it has state!
 * User can logs messages at various levels, set the minimum level for logs,
 * and can change what phase the robot is in
 * @author Programming Subteam
 */
public class FRCLogger {
    
    private static FRCLogger instance;
     
    public static final int DEBUG = 1;
    public static final int INFO = 2;
    public static final int WARNING = 3;
    public static final int ERROR = 4;
    
    private static final String LOG_FILE = "FRC_LOG";
    private static final String LOG_EXT = ".txt";
    
    private FileConnection fc;
    private DataOutputStream outStream;
    private OutputStreamWriter outStreamWriter;
    private BufferedWriter outBuffer;
    
    private Timer clock;
    private int minimumLevel = 0;
    private boolean teleop = false;
    
    private boolean hasError = false;
    
    /**
     * Initializes the logger
     * @return true if the logger is successfully created; false otherwise
     */
    private FRCLogger()
    {
        initialize();
    }
    
    /**
     * As per Singleton pattern, returns the logger instance if it is already
     * instantiated
     * @return the logger
     */
    public static FRCLogger getInstance()
    {
        if( instance == null )
            instance = new FRCLogger();
        return instance;
    }
    
    /**
     * Initializes the connections -- if possible
     */
    private void initialize()
    {
        try{
            String fileName = manageLogFile();
            System.out.println("Opening Log File: file:///" + fileName);
            fc = (FileConnection)Connector.open(
                    "file:///" + fileName, Connector.WRITE);
            outStream=fc.openDataOutputStream();
           
            hasError = false;   // Be explicit!
        }catch(IOException e){
            // Undo any connections made
            hasError = true;
            
            try
            {
                if( outStream != null )
                {
                    outStream.close();
                    outStream = null;
                }

                if( fc != null )
                {
                    fc.close();
                    fc = null;
                }
            }
            catch(IOException ioe) {
                System.out.println("unable to close log");
                System.out.println(ioe.getMessage());
            }
            System.out.println("unable to open log");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return;
        }
        
        outStreamWriter = new OutputStreamWriter(outStream);
        outBuffer = new BufferedWriter(outStreamWriter);
        clock = new Timer();
        clock.start();
    }
    
    /**
     * Determines which log file to write to and cleans up the log created
     * 2 iterations ago.
     * @return the log file name to write
     */
    private String manageLogFile()
    {
        String log1 = LOG_FILE + "_1" + LOG_EXT;
        String log2 = LOG_FILE + "_2" + LOG_EXT;
        String log3 = LOG_FILE + "_3" + LOG_EXT;
        boolean log1Exists = Protocol.exists(log1);
        boolean log2Exists = Protocol.exists(log2);
        boolean log3Exists = Protocol.exists(log3);
        
        Protocol p = new Protocol();
        
        if (log3Exists && log1Exists)
        {
            try
            {
                p.open("file", ":///" + log3, Connector.WRITE, false);
                p.delete();
            } catch(IOException ioe) {
                try { p.close(); }
                catch(IOException ioe2) {}
            }
            if( !log2Exists ) {
                try {
                    Protocol.create(log2);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } 
            }
            System.out.println( log2 + ":" + Protocol.exists(log2) );
            return log2;
        }
        
        else if (log2Exists && log3Exists)
        {
            try
            {
                p.open("file", ":///" + log2, Connector.WRITE, false);
                p.delete();
            } catch(IOException ioe) {
                try { p.close(); }
                catch(IOException ioe2) {}
            }
            if( !log1Exists ) {
                try {
                    Protocol.create(log1);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } 
            }
            System.out.println( log1 + ":" + Protocol.exists(log1) );
            return log1;
        }
        
        else if (log1Exists && log2Exists)
        {
            try
            {
                p.open("file", ":///" + log1, Connector.WRITE, false);
                p.delete();
            } catch(IOException ioe) {
                try { p.close(); }
                catch(IOException ioe2) {}
            }
            if( !log3Exists ) {
                try {
                    Protocol.create(log3);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } 
            }
            System.out.println( log3 + ":" + Protocol.exists(log3) );
            return log3;
        }
        
        else
        {
            if( !log1Exists ) {
                try {
                    Protocol.create(log1);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } 
            }
            System.out.println( log1 + ":" + Protocol.exists(log1) );
            return log1;
        }        
    }
    
    /**
     * Sets the minimum log level
     * @param level - the log level
     */
    public void setLogLevel(int level)
    {
        if( level < DEBUG || level > ERROR )
            return;
        this.minimumLevel = level;
    }
    
    /**
     * Is the robot in teleop mode?
     * @param inTeleop - true if in teleop; false otherwise
     */
    public void inTeleopMode(boolean inTeleop)
    {
        this.teleop = inTeleop;
    }
    
    /**
     * Logs a debugging message
     * @param msg - a message
     */
    public void logDebug(String msg)
    {
        log(msg, DEBUG);
    }
    
    /**
     * Logs an information message
     * @param msg - a message
     */
    public void logInfo(String msg)
    {
        log(msg, INFO);
    }
    
    /**
     * Logs a warning message
     * @param msg - a message
     */
    public void logWarning(String msg)
    {
        log(msg, WARNING);
    }
    
    /**
     * Logs an error message
     * @param msg - a message
     */
    public void logError(String msg)
    {
        log(msg, ERROR);
    }
    
    /**
     * Logs a message
     * @param msg - a string
     * @param level - the log level
     */
    private void log(String msg, int level){
        // Can't do anything if there was a problem
        System.out.println( msg );
        if( hasError )
        {
            initialize();
            if( hasError ) return;  // Still!
        } 
        
        // Log level ok?
        if( level < minimumLevel ) return;
        
        // Derive level
        String levelName = "None";
        switch(level)
        {
            case(DEBUG):
                levelName = "DEBUG";
                break;
            case(INFO):
                levelName = "INFO";
                break;
            case(WARNING):
                levelName = "WARNING";
                break;
            case(ERROR):
                levelName = "ERROR";
                break;
        }
        
        // Write to file
        try{
            outBuffer.write(clock.get() + "s [" + levelName + "] ("
                    + (this.teleop ? "TELEOP" : "AUTONOMOUS") + ") - " 
                    + msg + "\n");
            outBuffer.flush();
        }catch(IOException e){
            // Nothing we can do...! But code shouldn't be too concerned
            // with inability to log.
        }
    }
    
    /**
     * Closes the logger if possible
     */
    public void close(){
        try
        {
            clock.stop();
            
            if( outBuffer != null )
            {
                outBuffer.close();
                outBuffer = null;
            }
            
            if( outStreamWriter != null )
            {
                outStreamWriter.close();
                outStreamWriter = null;
            }
            
            if( outStream != null )
            {
                outStream.close();
                outStream = null;
            }

            if( fc != null )
            {
                fc.close();
                fc = null;
            }
        }catch(IOException ioe){}
    }
}
