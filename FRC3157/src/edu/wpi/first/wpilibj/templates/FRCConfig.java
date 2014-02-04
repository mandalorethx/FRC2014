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
 * An implementation of the config file for the robot These variables are in
 * FRC_CONFIG.txt In FRC_CONFIG.txt:no space between = signs = signs in
 * FRC_CONFIG.txt are used as a delimiter between variable and value In
 * FRC_CONFIG.txt:each variable is separated by an enter
 *
 * @author Programming Subteam
 */
public class FRCConfig {

    public static final String CONFIG_FILE = "FRC_CONFIG.txt";

    public static double kMAX_MOTOR_SPEED = 100;
    public static double kMAX_ERROR = 0.1;
    public static double kMAX_SHOOTER_POWER = 0.9;
    public static double kMIN_SHOOTER_POWER = 0.7;
    public static double kSLEEP_TIME = 500.0;
    // I am 85-90% sure this is correct based on model number.
    public static double kENCODER_PPR = 1440;
    public static int SLOT_LEFT_ENCODER_1 = 0;
    public static int SLOT_LEFT_ENCODER_2 = 1;
    public static int SLOT_RIGHT_ENCODER_1 = 2;
    public static int SLOT_RIGHT_ENCODER_2 = 3;
    public static double fMAX_SHOOTER_POWER = 0.9;
    public static double kMAX_MOTOR_POWER = 0.9;
    public static int SLOT_LEFT_MOTOR = 0;
    public static int SLOT_RIGHT_MOTOR = 1;
    public static int SLOT_LEFT_DRIVER_JOYSTICK = 0;
    public static int SLOT_RIGHT_DRIVER_JOYSTICK = 1;
    public static int SLOT_CO_DRIVER_JOYSTICK = 2;
    public static int SLOT_PRESSURE = 0;
    public static int SLOT_COMPRESSOR_RELAY = 1;
    public static int SLOT_LEFT_SHOOTER = 2;
    public static int SLOT_RIGHT_SHOOTER = 3;
    //public static int SLOT_PIN_SHOOTER=4;

    public static int btnDRIVE_STRAIGHT = 0;
    public static int btnMAGIC_SHOOT_CATCH = 2;
    public static int btnSHOOTER = 0;
    public static int btnGRABBER = 3;
    public static int btnMANUAL_SHOOTER = 4;
    //public static int btnMANUAL_PIN = 5;
    public static int btnMANUAL_ON = 6;
    public static int btnMANUAL_OFF = 7;
    public static double kSTEER_P = 0;
    public static double kSTEER_I = 0;
    public static double kSTEER_D = 0;
    public static int btnAUTOTURN_LEFT = 3;
    public static int btnAUTOTURN_RIGHT = 4;
    public static double kAUTON_DELAY = 0;
    public static double kAUTON_MOVE_DELAY = 3000;
    public static double kAUTON_MOVE_TIME = 2000;
    public static double kAUTON_FIRE_TIME = 1000;
    public static boolean kRUN_AUTONOMOUS = true;
    public static double kFIRING_TIME = 500;
    public static double kMOTOR_SPEED = 0.9;
    public static double kMOVE_TIME = 2000;
    public static int SLOT_ANALOG = 0;
    public static int SLOT_DIO = 1;
    public static int SLOT_DO = 2;
    public static int SLOT_SHOOTER_SWITCH = 0;
    public static double kDISTANCE_P = 0;
    public static double kDISTANCE_I = 0;
    public static double kDISTANCE_D = 0;
    public static int btnAUTO_DISTANCE = 0;
    public static int btnCONFIG_RELOAD_1 = 8;
    public static int btnCONFIG_RELOAD_2 = 9;
    public static int btnINCREASE_WAIT_TIME = 5;
    public static int btnDECREASE_WAIT_TIME = 4;
    public static int btnSTART_AUTON_MODE = 3;
    public static int btnSTOP_AUTON_MODE = 2;
    public static double kAUTON_DELAY_STEP = 500;
    public static int SLOT_GRABBER_EXTEND_1 = 1;
    public static int SLOT_GRABBER_EXTEND_2 = 2;
    public static int btnGRABBER_EXTEND = 10;
    
    
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
        openFile(CONFIG_FILE);
        boolean fileDone = false;
        try {
            while (!fileDone) {
                String line = inBuffer.readLine();
                if (line == null) {
                    fileDone = true;
                    break;
                }
                int delim = line.indexOf("=");
                if (delim != -1) {
                    String varName = line.substring(0, delim);
                    String value = line.substring(delim + 1, line.length() - 1);

                    if (varName.equals("fMaxShooterPower")) {
                        fMAX_SHOOTER_POWER = Double.parseDouble(value);
                    } else if (varName.equals("kMAX_MOTOR_SPEED")) {
                        kMAX_MOTOR_SPEED = Integer.parseInt(value);
                    } else if (varName.equals("kMAX_ERROR")) {
                        kMAX_ERROR = Integer.parseInt(value);
                    } else if (varName.equals("kMAX_SHOOTER_POWER")) {
                        kMAX_SHOOTER_POWER = Integer.parseInt(value);
                    } else if (varName.equals("kMIN_SHOOTER_POWER")) {
                        kMIN_SHOOTER_POWER = Integer.parseInt(value);
                    } else if (varName.equals("kSLEEP_TIME")) {
                        kSLEEP_TIME = Integer.parseInt(value);
                    } else if (varName.equals("kENCODER_PPR")) {
                        kENCODER_PPR = Integer.parseInt(value);
                    } else if (varName.equals("kLEFT_ENCODER_PORT_1")) {
                        SLOT_LEFT_ENCODER_1 = Integer.parseInt(value);
                    } else if (varName.equals("kLEFT_ENCODER_PORT_2")) {
                        SLOT_LEFT_ENCODER_2 = Integer.parseInt(value);
                    } else if (varName.equals("kRIGHT_ENCODER_PORT_1")) {
                        SLOT_RIGHT_ENCODER_1 = Integer.parseInt(value);
                    } else if (varName.equals("kRIGHT_ENCODER_PORT_2")) {
                        SLOT_RIGHT_ENCODER_2 = Integer.parseInt(value);
                    } else if (varName.equals("kMAX_MOTOR_POWER")) {
                        kMAX_MOTOR_POWER = Integer.parseInt(value);
                    } else if (varName.equals("kLEFT_MOTOR_SLOT")) {
                        SLOT_LEFT_MOTOR = Integer.parseInt(value);
                    } else if (varName.equals("kRIGHT_MOTOR_SLOT")) {
                        SLOT_RIGHT_MOTOR = Integer.parseInt(value);
                    } else if (varName.equals("kLEFT_DRIVER_STICK")) {
                        SLOT_LEFT_DRIVER_JOYSTICK = Integer.parseInt(value);
                    } else if (varName.equals("kRIGHT_DRIVER_STICK=1;")) {
                        SLOT_RIGHT_DRIVER_JOYSTICK = Integer.parseInt(value);
                    } else if (varName.equals("kCO_DRIVER_STICK")) {
                        SLOT_CO_DRIVER_JOYSTICK = Integer.parseInt(value);
                    } else if (varName.equals("dPressureSlot")) {
                        SLOT_PRESSURE = Integer.parseInt(value);
                    } else if (varName.equals("dRelaySlot")) {
                        SLOT_COMPRESSOR_RELAY = Integer.parseInt(value);
                    } else if (varName.equals("dLeftShooterSlot")) {
                        SLOT_LEFT_SHOOTER = Integer.parseInt(value);
                    } else if (varName.equals("dRightShooterSlot")) {
                        SLOT_RIGHT_SHOOTER = Integer.parseInt(value);
                        /*
                         }else if(varName.equals("dPinShooterSlot")){
                         SLOT_PIN_SHOOTER=Integer.parseInt(value);
                         */
                    } else if (varName.equals("kDRIVE_STRAIGHT_BUTTON")) {
                        btnDRIVE_STRAIGHT = Integer.parseInt(value);
                    } else if (varName.equals("kMAGIC_SHOOT_CATCH")) {
                        btnMAGIC_SHOOT_CATCH = Integer.parseInt(value);
                    } else if (varName.equals("kSHOOTER_BUTTON")) {
                        btnSHOOTER = Integer.parseInt(value);
                    } else if (varName.equals("kGRABBER_BUTTON")) {
                        btnGRABBER = Integer.parseInt(value);
                    } else if (varName.equals("kMANUAL_SHOOTER")) {
                        btnMANUAL_SHOOTER = Integer.parseInt(value);
                        /*
                         }else if(varName.equals("kMANUAL_PIN")){
                         btnMANUAL_PIN=Integer.parseInt(value);
                         */
                    } else if (varName.equals("kMANUAL_ON")) {
                        btnMANUAL_ON = Integer.parseInt(value);
                    } else if (varName.equals("kMANUAL_OFF")) {
                        btnMANUAL_OFF = Integer.parseInt(value);
                    } else if (varName.equals("kSTEER_I")) {
                        kSTEER_I = Double.parseDouble(value);
                    } else if (varName.equals("kSTEER_P")) {
                        kSTEER_P = Double.parseDouble(value);
                    } else if (varName.equals("kSTEER_D")) {
                        kSTEER_D = Double.parseDouble(value);
                    } else if (varName.equals("kAUTOTURN_LEFT")) {
                        btnAUTOTURN_LEFT = Integer.parseInt(value);
                    } else if (varName.equals("kAUTOTURN_RIGHT")) {
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
                        kDISTANCE_P = Integer.parseInt(value);
                    } else if (varName.equals("kDISTANCE_I")) {
                        kDISTANCE_I = Integer.parseInt(value);
                    } else if (varName.equals("kDISTANCE_D")) {
                        kDISTANCE_I = Integer.parseInt(value);
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
                    }else if (varName.equals("kAUTON_DELAY_STEP")){
                        kAUTON_DELAY_STEP = Integer.parseInt(value);
                    } else if (varName.equals("SLOT_GRABBER_EXTEND_1")){
                        SLOT_GRABBER_EXTEND_1 = Integer.parseInt(value);
                    } else if (varName.equals("SLOT_GRABBER_EXTEND_2")){
                        SLOT_GRABBER_EXTEND_2 = Integer.parseInt(value);
                    } else if (varName.equals("btnGRABBER_EXTEND")){
                        btnGRABBER_EXTEND = Integer.parseInt(value);
                    }
                }
            }
            return true;
        } catch (Exception e) {
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
    private static void openFile(String fileName) {
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
