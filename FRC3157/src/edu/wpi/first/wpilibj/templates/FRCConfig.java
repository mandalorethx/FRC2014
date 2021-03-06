/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import com.sun.squawk.io.BufferedReader;
import com.sun.squawk.microedition.io.FileConnection;
import com.sun.squawk.peripheral.INorFlashSector;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.microedition.io.Connector;

/**
 * An implementation of the config file for the robot These variables are in
 * FRC_CONFIG.txt In FRC_CONFIG.txt:no space between = signs = signs in
 * FRC_CONFIG.txt are used as a delimiter between variable and value In
 * FRC_CONFIG.txt:each variable is separated by an enter
 *
 * @author Programming Subteam
 */
public class FRCConfig {

    public static FRCLogger logger = FRCLogger.getInstance();
    
    public static final String CONFIG_FILE = "FRC_CONFIG.txt";

    public static double kMAX_MOTOR_SPEED = 100.0;
    public static double kMAX_ERROR = 0.1;
    public static double kMAX_SHOOTER_POWER = 0.9;
    public static double kMIN_SHOOTER_POWER = 0.7;
    public static double kSLEEP_TIME = 500.0;
    // I am 85-90% sure this is correct based on model number.
    public static double kENCODER_PPR = 1440.0;
    public static int SLOT_LEFT_ENCODER_1 = 1;
    public static int SLOT_LEFT_ENCODER_2 = 2;
    public static int SLOT_RIGHT_ENCODER_1 = 3;
    public static int SLOT_RIGHT_ENCODER_2 = 4;
    public static double fMAX_SHOOTER_POWER = 0.9;
    public static double kMAX_MOTOR_POWER = 0.9;
    public static int SLOT_LEFT_MOTOR = 1;
    public static int SLOT_RIGHT_MOTOR = 2;
    public static int SLOT_LEFT_DRIVER_JOYSTICK = 1;
    public static int SLOT_RIGHT_DRIVER_JOYSTICK = 2;
    public static int SLOT_CO_DRIVER_JOYSTICK = 3;
    public static int SLOT_PRESSURE = 5;
    public static int SLOT_COMPRESSOR_RELAY = 1;
    public static int LATCH_RET = 1;
    public static int LEFT_EXT = 3;
    public static int LEFT_RET = 4;
    public static int RIGHT_EXT = 5;
    public static int RIGHT_RET = 6;
    public static int SLOT_PRETTY_LIGHTS = 2;
    //public static int SLOT_PIN_SHOOTER=4;
    public static int SLOT_LEFT_GRABBER_MOTOR = 3;
    public static int SLOT_RIGHT_GRABBER_MOTOR = 4;

    public static int btnREV_DRIVE = 1;
    public static int btnMAGIC_SHOOT_CATCH = 2;
    public static int btnSHOOTER = 1;
    public static int btnPASS = 3;
    public static int btnMANUAL_SHOOTER = 4;
    //public static int btnMANUAL_PIN = 5;
    public static int btnMANUAL_ON = 6;
    public static int btnMANUAL_OFF = 7;
    public static double kSTEER_P = 0.0;
    public static double kSTEER_I = 0.0;
    public static double kSTEER_D = 0.0;
    public static int btnAUTOTURN_LEFT = 3;
    public static int btnAUTOTURN_RIGHT = 4;
    public static double kAUTON_DELAY = 0.0;
    public static double kAUTON_MOVE_DELAY = 3000;
    public static double kAUTON_MOVE_TIME = 2000;
    public static double kAUTON_FIRE_TIME = 1000;
    public static boolean kRUN_AUTONOMOUS = true;
    public static double kFIRING_TIME = 500.0;
    public static double kMOTOR_SPEED = 0.9;
    public static double kSHOOT_TIME = 1000;//we may need to change this
    public static double kMOVE_TIME = 2000.0;
    public static int SLOT_ANALOG = 0;
    public static int SLOT_DIO = 1;
    public static int SLOT_DO = 2;
    public static int SLOT_SHOOTER_SWITCH = 0;
    public static double kDISTANCE_P = 0.0;
    public static double kDISTANCE_I = 0.0;
    public static double kDISTANCE_D = 0.0;
    public static int btnAUTO_DISTANCE = 0;
    public static int btnCONFIG_RELOAD_1 = 8;
    public static int btnCONFIG_RELOAD_2 = 9;
    public static int btnINCREASE_WAIT_TIME = 5;
    public static int btnDECREASE_WAIT_TIME = 4;
    public static int btnSTART_AUTON_MODE = 3;
    public static int btnSTOP_AUTON_MODE = 2;
    public static int btnCAR_LOCK= 11;
    public static double kAUTON_DELAY_STEP = 500.0;
    public static int SLOT_GRABBER_EXTEND = 7;
    public static int SLOT_GRABBER_RETRACT = 8;
    public static int btnGRABBER_EXTEND = 10;
    public static boolean EN_CAMERA = true;
    public static boolean EN_LOG = true;
    public static boolean EN_CONFIG = true;
    public static boolean EN_SWITCHES = true;
    public static boolean EN_ENCODERS = true;
    public static int kLEFT_MOTOR_MULTIPLIER = -1;
    public static int kRIGHT_MOTOR_MULTIPLIER = 1;
    public static double kCO_DRIVE_VALUE = -0.5;
    public static double kGRABBER_RUN_TIME = 10000;
    public static double kCHARGE_TIME = 5000;
    public static double kRETRACT_TIME = 12000;
    public static double kEXTEND_TIME = 1000;
    public static int btnFORCE_FIRE_RETRACT = 2;
    public static boolean kPRECHARGE = true;
    
    private static FileConnection fc;
    private static DataInputStream inStream;
    private static BufferedReader inBuffer;
    private static InputStreamReader inStreamReader;

    /**
     * Initializes the static variables for the config class opens file, reads
     * it, and can then be used as variables in the project
     *
     * @return true if the config file is successfully loaded; false otherwise
     */
    public static boolean initialize() {
        if(!EN_CONFIG){
            return false;
        }
        openFile(CONFIG_FILE);
        boolean fileDone = false;
        try {
            while (!fileDone) {
                String line = inBuffer.readLine();//Error here
                // System.out.println( line );
                if (line == null) {
                    fileDone = true;
                    break;
                }
                int delim = line.indexOf("=");
                int eol = line.indexOf( ";" );
                if (delim != -1) {
                    String varName = line.substring(0, delim);
                    String value = line.substring(delim + 1, eol);

                    try {
                        if (varName.equals("fMAX_SHOOTER_POWER")) {
                            fMAX_SHOOTER_POWER = Double.parseDouble(value);
                        } else if (varName.equals("kMAX_MOTOR_SPEED")) {
                            kMAX_MOTOR_SPEED = Double.parseDouble(value);
                        } else if (varName.equals("kMAX_ERROR")) {
                            kMAX_ERROR = Double.parseDouble(value);
                        } else if (varName.equals("kMAX_SHOOTER_POWER")) {
                            kMAX_SHOOTER_POWER = Double.parseDouble(value);
                        } else if (varName.equals("kMIN_SHOOTER_POWER")) {
                            kMIN_SHOOTER_POWER = Double.parseDouble(value);
                        } else if (varName.equals("kSLEEP_TIME")) {
                            kSLEEP_TIME = Double.parseDouble(value);
                        } else if (varName.equals("kENCODER_PPR")) {
                            kENCODER_PPR = Double.parseDouble(value);
                        } else if (varName.equals("SLOT_LEFT_ENCODER_1")) {
                            SLOT_LEFT_ENCODER_1 = Integer.parseInt(value);
                        } else if (varName.equals("SLOT_LEFT_ENCODER_2")) {
                            SLOT_LEFT_ENCODER_2 = Integer.parseInt(value);
                        } else if (varName.equals("SLOT_RIGHT_ENCODER_1")) {
                            SLOT_RIGHT_ENCODER_1 = Integer.parseInt(value);
                        } else if (varName.equals("SLOT_RIGHT_ENCODER_2")) {
                            SLOT_RIGHT_ENCODER_2 = Integer.parseInt(value);
                        } else if (varName.equals("kMAX_MOTOR_POWER")) {
                            kMAX_MOTOR_POWER = Double.parseDouble(value);
                        } else if (varName.equals("SLOT_LEFT_MOTOR")) {
                            SLOT_LEFT_MOTOR = Integer.parseInt(value);
                        } else if (varName.equals("SLOT_RIGHT_MOTOR")) {
                            SLOT_RIGHT_MOTOR = Integer.parseInt(value);
                        } else if (varName.equals("SLOT_LEFT_DRIVER_JOYSTICK")) {
                            SLOT_LEFT_DRIVER_JOYSTICK = Integer.parseInt(value);
                        } else if (varName.equals("SLOT_RIGHT_DRIVER_JOYSTICK")) {
                            SLOT_RIGHT_DRIVER_JOYSTICK = Integer.parseInt(value);
                        } else if (varName.equals("SLOT_CO_DRIVER_JOYSTICK")) {
                            SLOT_CO_DRIVER_JOYSTICK = Integer.parseInt(value);
                        } else if (varName.equals("SLOT_PRESSURE")) {
                            SLOT_PRESSURE = Integer.parseInt(value);
                        } else if (varName.equals("SLOT_COMPRESSOR_RELAY")) {
                            SLOT_COMPRESSOR_RELAY = Integer.parseInt(value);
                        } else if (varName.equals("LATCH_RET")) {
                            LATCH_RET = Integer.parseInt(value);
                        } else if (varName.equals("LEFT_EXT")) {
                            LEFT_EXT = Integer.parseInt(value);
                        } else if (varName.equals("LEFT_RET")) {
                            LEFT_RET = Integer.parseInt(value);
                        } else if (varName.equals("RIGHT_EXT")) {
                            RIGHT_EXT = Integer.parseInt(value);
                        } else if (varName.equals("RIGHT_RET")) {
                            RIGHT_RET = Integer.parseInt(value);
                        } else if (varName.equals("btnREV_DRIVE")) {
                            btnREV_DRIVE = Integer.parseInt(value);
                        } else if (varName.equals("btnMAGIC_SHOOT_CATCH")) {
                            btnMAGIC_SHOOT_CATCH = Integer.parseInt(value);
                        } else if (varName.equals("btnSHOOTER")) {
                            btnSHOOTER = Integer.parseInt(value);
                        } else if (varName.equals("btnPASS")) {
                            btnPASS = Integer.parseInt(value);
                        } else if (varName.equals("btnMANUAL_SHOOTER")) {
                            btnMANUAL_SHOOTER = Integer.parseInt(value);
                        } else if (varName.equals("btnMANUAL_ON")) {
                            btnMANUAL_ON = Integer.parseInt(value);
                        } else if (varName.equals("btnMANUAL_OFF")) {
                            btnMANUAL_OFF = Integer.parseInt(value);
                        } else if (varName.equals("kSTEER_I")) {
                            kSTEER_I = Double.parseDouble(value);
                        } else if (varName.equals("kSTEER_P")) {
                            kSTEER_P = Double.parseDouble(value);
                        } else if (varName.equals("kSTEER_D")) {
                            kSTEER_D = Double.parseDouble(value);
                        } else if (varName.equals("btnAUTOTURN_LEFT")) {
                            btnAUTOTURN_LEFT = Integer.parseInt(value);
                        } else if (varName.equals("btnAUTOTURN_RIGHT")) {
                            btnAUTOTURN_RIGHT = Integer.parseInt(value);
                        } else if (varName.equals("kAUTON_DELAY")) {
                            kAUTON_DELAY = Double.parseDouble(value);
                        } else if (varName.equals("kAUTON_MOVE_DELAY")) {
                            kAUTON_MOVE_DELAY = Double.parseDouble(value);
                        } else if (varName.equals("kAUTON_MOVE_TIME")) {
                            kAUTON_MOVE_TIME = Double.parseDouble(value);
                        } else if (varName.equals("kAUTON_FIRE_TIME")) {
                            kAUTON_FIRE_TIME = Double.parseDouble(value);
                        } else if (varName.equals("kRUN_AUTONOMOUS")) {
                            kRUN_AUTONOMOUS = value.toLowerCase().equals("true");
                        } else if (varName.equals("kFIRING_TIME")) {
                            kFIRING_TIME = Double.parseDouble(value);
                        } else if (varName.equals("kMOTOR_SPEED")) {
                            kMOTOR_SPEED = Double.parseDouble(value);
                        } else if (varName.equals("kSHOOT_TIME")){
                            kSHOOT_TIME = Double.parseDouble(value);
                        } else if (varName.equals("kMOVE_TIME")) {
                            kMOVE_TIME = Double.parseDouble(value);
                        } else if (varName.equals("SLOT_ANALOG")) {
                            SLOT_ANALOG = Integer.parseInt(value);
                        } else if (varName.equals("SLOT_DIO")) {
                            SLOT_DIO = Integer.parseInt(value);
                        } else if (varName.equals("SLOT_DO")) {
                            SLOT_DO = Integer.parseInt(value);
                        } else if (varName.equals("SLOT_SHOOTER_SWITCH")) {
                            SLOT_SHOOTER_SWITCH = Integer.parseInt(value);
                        } else if (varName.equals("kDISTANCE_P")) {
                            kDISTANCE_P = Double.parseDouble(value);
                        } else if (varName.equals("kDISTANCE_I")) {
                            kDISTANCE_I = Double.parseDouble(value);
                        } else if (varName.equals("kDISTANCE_D")) {
                            kDISTANCE_I = Double.parseDouble(value);
                        } else if (varName.equals("btnAUTO_DISTANCE")) {
                            btnAUTO_DISTANCE = Integer.parseInt(value);
                        } else if (varName.equals("btnCONFIG_RELOAD_1")){
                            btnCONFIG_RELOAD_1 = Integer.parseInt(value);
                        } else if (varName.equals("btnCONFIG_RELOAD_2")){
                            btnCONFIG_RELOAD_2 = Integer.parseInt(value);
                        }else if (varName.equals("btnINCREASE_WAIT_TIME")){
                            btnINCREASE_WAIT_TIME = Integer.parseInt(value);
                        }else if (varName.equals("btnDECREASE_WAIT_TIME")){
                            btnDECREASE_WAIT_TIME = Integer.parseInt(value);
                        }else if (varName.equals("btnSTART_AUTON_MODE")){
                            btnSTART_AUTON_MODE = Integer.parseInt(value);
                        }else if (varName.equals("btnSTOP_AUTON_MODE")){
                            btnSTOP_AUTON_MODE = Integer.parseInt(value);
                        }else if (varName.equals("btnCAR_LOCK_REVERSE")){
                            btnCAR_LOCK = Integer.parseInt(value);
                        }else if (varName.equals("kAUTON_DELAY_STEP")){
                            kAUTON_DELAY_STEP = Double.parseDouble(value);
                        } else if (varName.equals("SLOT_GRABBER_EXTEND")){
                            SLOT_GRABBER_EXTEND = Integer.parseInt(value);
                        } else if (varName.equals("btnGRABBER_EXTEND")){
                            btnGRABBER_EXTEND = Integer.parseInt(value);
                        } else if (varName.equals("SLOT_LEFT_GRABBER_MOTOR")) {
                            SLOT_LEFT_GRABBER_MOTOR = Integer.parseInt(value);
                        } else if (varName.equals("SLOT_RIGHT_GRABBER_MOTOR")) {
                            SLOT_RIGHT_GRABBER_MOTOR = Integer.parseInt(value);
                        } else if (varName.equals("EN_CAMERA")){
                            EN_CAMERA = value.toLowerCase().equals("true");
                        } else if (varName.equals("EN_LOG")){
                            EN_LOG = value.toLowerCase().equals("true");
                        } else if (varName.equals("EN_CONFIG")){
                            EN_CONFIG = value.toLowerCase().equals("true");
                        } else if (varName.equals("EN_SWITCHES")){
                            EN_SWITCHES = value.toLowerCase().equals("true");
                        }else if (varName.equals("kLEFT_MOTOR_MULTIPLIER")){
                            kLEFT_MOTOR_MULTIPLIER = Integer.parseInt(value);
                        }else if (varName.equals("kRIGHT_MOTOR_MULTIPLIER")){
                            kRIGHT_MOTOR_MULTIPLIER = Integer.parseInt(value);
                        }else if (varName.equals("EN_ENCODERS")){
                            EN_ENCODERS = value.toLowerCase().equals("true");
                        }else if (varName.equals("kCO_DRIVE_VALUE")){
                            kCO_DRIVE_VALUE = Double.parseDouble(value);
                        }else if (varName.equals("kGRABBER_RUN_TIME")){
                            kGRABBER_RUN_TIME = Double.parseDouble(value);
                        }else if (varName.equals("SLOT_PRETTY_LIGHTS")){
                            SLOT_PRETTY_LIGHTS = Integer.parseInt(value);
                        }else if(varName.equals("kCHARGE_TIME")){
                            kCHARGE_TIME = Double.parseDouble(value);
                        }else if(varName.equals("kRETRACT_TIME")){
                            kRETRACT_TIME = Double.parseDouble(value);
                        }else if(varName.equals("kEXTEND_TIME")){
                            kEXTEND_TIME = Double.parseDouble(value);
                        }else if(varName.equals("btnFORCE_FIRE_RETRACT")){
                            btnFORCE_FIRE_RETRACT = Integer.parseInt(value);
                        }else if(varName.equals("kPRECHARGE")){
                            kPRECHARGE = value.toLowerCase().equals("true");
                        }else{
                            System.out.println("No variable found: " + line);
                            logger.logError("No variable found: " + line);
                        }
                    } catch ( Exception e ) {
                        System.out.println("Unable to parse line: " + line);
                        System.out.println( "\tvarName: " + varName + " = " + value );
                        System.out.println( "\t" + e.toString() );
                    }
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            closeFile();
        }
    }
    /**
     * Helper function that opens the config file
     *
     * @param fileName - the name of the config file
     */
        
    private static void openFile (String fileName){
        try {
            fc = (FileConnection) Connector.open("file:///" + fileName, Connector.READ);
            inStream = fc.openDataInputStream();
            inStreamReader = new InputStreamReader(inStream);
            inBuffer = new BufferedReader(inStreamReader);
        } catch (IOException e) {
            System.out.println("could not open file " + fileName);
        }
    }

    /**
     * Closes the file if possible
     */
    private static void closeFile() {
        try {
            if (inBuffer != null) {
                inBuffer.close();
            }
            if (inStreamReader != null) {
                inStreamReader.close();
            }
            if (inStream != null) {
                inStream.close();
            }
            if (fc != null) {
                fc.close();
            }
        } catch (IOException e) {

        }
    }
}
