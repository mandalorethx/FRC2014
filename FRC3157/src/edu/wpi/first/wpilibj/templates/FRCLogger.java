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
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import javax.microedition.io.Connector;

/**
 * Singleton logging class -- sorry Matt but it has state! User can logs
 * messages at various levels, set the minimum level for logs, and can change
 * what phase the robot is in
 *
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
    private static final String OLD_FILE = LOG_FILE + "_OLD" + LOG_EXT;
    private static final String NEW_FILE = LOG_FILE + "_NEW" + LOG_EXT;

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
        if (instance == null) {
            instance = new FRCLogger();
        }
        return instance;
    }

    /**
     * Initializes the connections -- if possible
     */
    private void initialize() {
        if(!FRCConfig.EN_LOG){
            return;
        }
        try {
            manageLogFile();    // Will throw IOException if something happens
            
            System.out.println("Opening Log File: file:///" + NEW_FILE);
            fc = (FileConnection) Connector.open(
                    "file:///" + NEW_FILE, Connector.WRITE);
            outStream = fc.openDataOutputStream();

            hasError = false;   // Be explicit!
        } catch (IOException e) {
            // Undo any connections made
            hasError = true;

            try {
                if (outStream != null) {
                    outStream.close();
                    outStream = null;
                }

                if (fc != null) {
                    fc.close();
                    fc = null;
                }
            } catch (IOException ioe) {
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
     * Prepares a new log file to be written
     * @throws IOException if something goes wrong!
     */
    private void manageLogFile() throws IOException {
        if (Protocol.exists(NEW_FILE)) {
            copyLog(NEW_FILE, OLD_FILE);
        }
        else
        {
            Protocol.create(NEW_FILE);
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
        try
        {
            Protocol p = new Protocol();
            if (!Protocol.exists(toFile))
                Protocol.create(toFile);
            
            in = new BufferedReader(
                    new InputStreamReader(
                            ((FileConnection)p.open(
                                    "file",
                                    "//" + fromFile,
                                    Connector.READ,
                                    false)).openDataInputStream()));
            
            out = new BufferedWriter(
                    new OutputStreamWriter(
                            ((FileConnection)p.open(
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
     * Is the robot in teleop mode?
     *
     * @param inTeleop - true if in teleop; false otherwise
     */
    public void inTeleopMode(boolean inTeleop) {
        this.teleop = inTeleop;
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
        System.out.println(msg);
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

        // Write to file
        try {
            outBuffer.write(clock.get() + "s [" + levelName + "] ("
                    + (this.teleop ? "TELEOP" : "AUTONOMOUS") + ") - "
                    + msg + "\n");
            outBuffer.flush();
        } catch (IOException e) {
            // Nothing we can do...! But code shouldn't be too concerned
            // with inability to log.
        }
    }

    /**
     * Closes the logger if possible
     */
    public void close() {
        try {
            clock.stop();

            if (outBuffer != null) {
                outBuffer.close();
                outBuffer = null;
            }

            if (outStreamWriter != null) {
                outStreamWriter.close();
                outStreamWriter = null;
            }

            if (outStream != null) {
                outStream.close();
                outStream = null;
            }

            if (fc != null) {
                fc.close();
                fc = null;
            }
        } catch (IOException ioe) {
        }
    }
}
