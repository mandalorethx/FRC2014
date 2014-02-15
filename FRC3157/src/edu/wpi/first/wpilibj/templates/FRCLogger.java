/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import com.sun.squawk.io.BufferedReader;
import com.sun.squawk.io.BufferedWriter;
import com.sun.squawk.io.j2me.file.Protocol;
import com.sun.squawk.microedition.io.FileConnection;
import edu.wpi.first.wpilibj.Timer;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import javax.microedition.io.Connector;

/**
 * Singleton logging class -- sorry Matt but it has state! User can logs
 * messages at various levels, set the minimum level for logs, and can change
 * what phase the robot is in
 *
 * @author Programming Subteam
 */
public class FRCLogger {

    private static boolean LOCK = false;
    
    private static FRCLogger instance;

    public static final int DEBUG = 1;
    public static final int INFO = 2;
    public static final int WARNING = 3;
    public static final int ERROR = 4;
    
    public static final int DISABLED = 0;
    public static final int AUTONOMOUS = 1;
    public static final int TELEOP = 2;

    private static final String LOG_FILE = "FRC_LOG";
    private static final String LOG_EXT = ".txt";
    private static final String OLD_FILE = LOG_FILE + "_OLD" + LOG_EXT;
    private static final String NEW_FILE = LOG_FILE + "_NEW" + LOG_EXT;
    
    private Protocol fileHandler;
    private BufferedWriter outBuffer;

    private Timer clock;
    private int minimumLevel = 0;
    private int phase = DISABLED;
    private boolean hasError = true;

    /**
     * Initializes the logger
     *
     * @return true if the logger is successfully created; false otherwise
     */
    private FRCLogger() {
        initialize();
    }

    /**
     * As per Singleton pattern, returns the logger instance if it is already
     * instantiated
     *
     * @return the logger
     */
    public static FRCLogger getInstance() {
        while(LOCK){ Thread.yield(); }
        LOCK = true;
        if (instance == null) {
            instance = new FRCLogger();
        }
        LOCK = false;
        return instance;
    }

    /**
     * Initializes the connections -- if possible
     */
    private void initialize() {
        if(!FRCConfig.EN_LOG){
            System.out.println("Logging not enabled");
            hasError = true;
            return;
        }
        try {
            System.out.println("Logging enabled");
            manageLogFile();    // Will throw IOException if something happens
            
            System.out.println("Opening Log File: file://" + NEW_FILE);
            fileHandler = new Protocol();
            outBuffer = new BufferedWriter(
                    new OutputStreamWriter(
                            ((FileConnection)fileHandler.open(
                                    "file",
                                    "//" + NEW_FILE,
                                    Connector.WRITE,
                                    false)).openDataOutputStream()));
            Calendar c = Calendar.getInstance(TimeZone.getTimeZone("EST"));
            outBuffer.write("<--- " 
                    + c.getTime().toString() 
                    + " --->\n");
            outBuffer.flush();

            hasError = false;   // Be explicit!
        } catch (IOException e) {
            // Undo any connections made
            hasError = true;
            
            try {
                if (fileHandler != null) {
                    fileHandler.close();
                    fileHandler = null;
                }
            } catch (IOException ioe) {
                System.out.println("Unable to close file handler protocol");
                System.out.println(ioe.getMessage());
            }
            System.out.println("Unable to initialize log!");
            System.out.println(e.getMessage());
            return;
        }
        clock = new Timer();
        clock.start();
    }

    /**
     * Prepares a new log file to be written
     * @throws IOException if something goes wrong!
     */
    private void manageLogFile() throws IOException {
        if (Protocol.exists(NEW_FILE)) {
            copyLog(NEW_FILE, OLD_FILE);
        }
        else
        {
            // Must write placeholder text to successfully create file
            Protocol.create(NEW_FILE);
            Protocol p = null;
            BufferedWriter out = null;
            try
            {
                p = new Protocol();
                out = new BufferedWriter(
                    new OutputStreamWriter(
                            ((FileConnection)p.open(
                                    "file",
                                    "//" + NEW_FILE,
                                    Connector.WRITE,
                                    false)).openDataOutputStream()));
                out.write("<Insert Log File Here>");
                out.flush();
            } catch(IOException ioe)
            {
                throw ioe;
            } finally {
                if ( p != null )
                {
                    try
                    {
                        p.close();
                    } catch(IOException e)
                    {
                        System.out.println("Unable to close protocol.");
                    }
                }
                if ( out != null )
                {
                    try
                    {
                        out.close();
                    } catch(IOException e)
                    {
                        System.out.println("Unable to close out stream.");
                    }
                }
            }
        }
    }
    
    /**
     * Copies the fromFile to the toFile
     * PRECONDITION: fromFile exists
     * @param fromFile - source
     * @param toFile - destination
     * @throws IOException if unable to process the log files
     */
    private void copyLog(String fromFile, String toFile) throws IOException
    {
        BufferedWriter out = null;
        BufferedReader in = null;
        Protocol p1 = null;
        Protocol p2 = null;
        try
        {
            p1 = new Protocol();
            p2 = new Protocol();
            
            if (!Protocol.exists(toFile))
                Protocol.create(toFile);
            
            in = new BufferedReader(
                    new InputStreamReader(
                            ((FileConnection)p1.open(
                                    "file",
                                    "//" + fromFile,
                                    Connector.READ,
                                    false)).openDataInputStream()));
            
            out = new BufferedWriter(
                    new OutputStreamWriter(
                            ((FileConnection)p2.open(
                                    "file",
                                    "//" + toFile,
                                    Connector.WRITE,
                                    false)).openDataOutputStream()));
            
            int max = 1024;
            char[] buffer = new char[max];
            int charsRead = 0;
            while((charsRead = in.read(buffer, 0, max)) > 0)
            {
                out.write(buffer, 0, charsRead);
                out.flush();
            }
        } catch(IOException ioe)
        {
            throw ioe;
        } finally
        {
            if (in != null)
            {
                try {
                    in.close();
                } catch(IOException e)
                {
                    System.out.println("Unable to close the source file!");
                }
            }
            if (out != null)
            {
                try {
                    out.close();
                } catch(IOException e)
                {
                    System.out.println("Unable to close the dest file!");
                }
            }
            if (p1 != null)
            {
                try {
                    p1.close();
                } catch(IOException e)
                {
                    System.out.println("Unable to close the first protocol");
                }
            }
            if (p2 != null)
            {
                try {
                    p2.close();
                } catch(IOException e)
                {
                    System.out.println("Unable to close the second protocol");
                }
            }
        }
    }

    /**
     * Sets the minimum log level
     *
     * @param level - the log level
     */
    public void setLogLevel(int level) {
        if (level < DEBUG || level > ERROR) {
            return;
        }
        this.minimumLevel = level;
    }

    /**
     * Sets the phase of the competition
     *
     * @param phase - phase
     */
    public void changePhase(int phase)
    {
        if(phase >= DISABLED && phase <= TELEOP)
        {
            this.phase = phase;
            System.out.println("Entered phase " + phase);
        }
    }

    /**
     * Logs a debugging message
     *
     * @param msg - a message
     */
    public void logDebug(String msg) {
        log(msg, DEBUG);
    }

    /**
     * Logs an information message
     *
     * @param msg - a message
     */
    public void logInfo(String msg) {
        log(msg, INFO);
    }

    /**
     * Logs a warning message
     *
     * @param msg - a message
     */
    public void logWarning(String msg) {
        log(msg, WARNING);
    }

    /**
     * Logs an error message
     *
     * @param msg - a message
     */
    public void logError(String msg) {
        log(msg, ERROR);
    }

    /**
     * Logs a message
     *
     * @param msg - a string
     * @param level - the log level
     */
    private void log(String msg, int level) {
        // Can't do anything if there was a problem
        if(!FRCConfig.EN_LOG){
            return;
        }
        
        if (hasError) {
            initialize();
            if (hasError) {
                return;  // Still!
            }
        }

        // Log level ok?
        if (level < minimumLevel) {
            return;
        }

        // Derive level
        String levelName = "None";
        switch (level) {
            case (DEBUG):
                levelName = "DEBUG";
                break;
            case (INFO):
                levelName = "INFO";
                break;
            case (WARNING):
                levelName = "WARNING";
                break;
            case (ERROR):
                levelName = "ERROR";
                break;
        }
        
        // Derive phase of the competition
        String phaseName = "Unknown";
        switch (this.phase) {
            case (DISABLED):
                phaseName = "DISABLED";
                break;
            case (AUTONOMOUS):
                phaseName = "AUTONOMOUS";
                break;
            case (TELEOP):
                phaseName = "TELEOP";
                break;
        }

        // Write to file
        try {
            outBuffer.write(clock.get() + "s [" + phaseName + "] ("
                    + levelName + ") - " + msg + "\n");
            outBuffer.flush();
            System.out.println("[Logged]: " + msg);
        } catch (IOException e) {
            // Nothing we can do...! But code shouldn't be too concerned
            // with inability to log.
            System.out.println("[Missed Log]: " + msg);
            System.out.println("Because: " + e.getMessage());
        }
    }

    /**
     * Closes the logger if possible
     */
    public void close() {
        clock.stop();

        if (outBuffer != null) {
            try{ outBuffer.close(); } catch(IOException e) {}
            outBuffer = null;
        }
        
        if (fileHandler != null) {
            try{ fileHandler.close(); } catch(IOException e) {}
            fileHandler = null;
        }
    }
}
