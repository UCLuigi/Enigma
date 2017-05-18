package enigma;

import java.io.*;

/** Enigma simulator.
 *  @author Luis Alba
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;


public final class Main {

    /** Process a sequence of encryptions and decryptions, as
     *  specified in the input from the standard input.  Print the
     *  results on the standard output. Exits normally if there are
     *  no errors in the input; otherwise with code 1. */
    public static void main(String[] args) {
        Machine M;
        BufferedReader input = null;
        try {
            input = new BufferedReader(
                    new InputStreamReader(new FileInputStream(args[0])));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("No such file found.");
        }
        String outputFilename;
        if (args.length >= 2) {
            outputFilename = args[1];
        } else {
            outputFilename = "output.txt";
        }

        M = null;

        try {
            while (true) {
                String line = input.readLine();
                if (line == null) {
                    break;
                }
                if (isConfigurationLine(line)) {
                    M = new Machine();
                    configure(M, line);
                } else {
                    if (M != null) {
                        writeMessageLine(M.convert(standardize(line)),
                                outputFilename);
                    } else {
                        throw new EnigmaException("Machine has no configuration");
                    }
                }
            }
        } catch (IOException excp) {
            System.err.printf("Input error: %s%n", excp.getMessage());
            System.exit(1);
        }
    }

    /** Return true iff LINE is an Enigma configuration line. */
    private static boolean isConfigurationLine(String line) {
        return !(line.length() <= 0 || line.equals("")) && line.charAt(0) == '*';
    }

    /** Returns boolean on whether configuration from line is possible. */
    private static boolean checkConfiguration(String line) {
        String[] config = configParse(line);
        String reflector = config[1];
        String fixed = config[2];
        String rotor3 = config[3];
        String rotor4 = config[4];
        String rotor5 = config[5];
        String setting = config[6];
        if (config.length != 7) {
            return false;
        } else if (!Rotor.REFLECTORS.contains(reflector)) {
            return false;
        } else if (!Rotor.FIXED_ROTORS.contains(fixed)) {
            return false;
        } else if (!Rotor.REG_ROTORS.contains(rotor3)) {
            return false;
        } else if (!Rotor.REG_ROTORS.contains(rotor4) || rotor4.equals(rotor3)
                || rotor4.equals(rotor5)) {
            return false;
        } else if (!Rotor.REG_ROTORS.contains(rotor5) || rotor5.equals(rotor4)
                || rotor5.equals(rotor3)) {
            return false;
        } else {
            return !(!setting.matches("[A-Z]+") || setting.length() != 4);
        }
    }

    /** Returns String array split by spaces from line. */
    public static String[] configParse(String line) {
        String[] config = line.split(" ");
        return config;
    }

    /** Configure M according to the specification given on CONFIG,
     *  which must have the format specified in the assignment. */
    private static void configure(Machine M, String config) {
        if (checkConfiguration(config)) {
            M.setRotors(config);
        } else {
            throw new EnigmaException("Configuration is not possible");
        }
    }

    /** Return the result of converting LINE to all upper case,
     *  removing all blanks and tabs.  It is an error if LINE contains
     *  characters other than letters and blanks.
     *  Looked on Stack Overflow for regEx patterns. */
    private static String standardize(String line) {
        if (line.matches("[a-zA-Z\\s]+") || line.equals("")) {
            line = line.replaceAll("\\s", "");
            return line.toUpperCase();
        } else {
            throw new EnigmaException("Line contains characters other than letters and blanks.");
        }
    }

    /** Write MSG in groups of five to out file (except that the last
     *  group may have fewer letters). */
    private static void writeMessageLine(String msg, String filename) {
        try {
            FileWriter writer = new FileWriter(filename, true);
            String outputString = ""; 
            for (int i = 0; i < msg.length(); i += 5) {
                outputString += msg.substring(i, Math.min(i + 5, msg.length()));
                if (i + 5 < msg.length()) {
                    outputString += " ";
                }
            }
            writer.write(outputString + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("IOException when writing: " + e);
        }
    }

}

