package enigma;

import java.util.Collections;

/** Class that represents a complete enigma machine.
 *  @author Luis Alba
 */
class Machine {

    /** Array of rotors Machine is configured to. */
    private Rotor[] _rotors = new Rotor[5];

    /** Set my rotors to (from left to right) ROTORS.  Initially, the rotor
     *  settings are all 'A'. */
    void replaceRotors(Rotor[] rotors) {
        for (int k = 0; k < rotors.length; k++) {
            _rotors[k] = rotors[k];
        }
    }

    /** Set my rotors according to SETTING, which must be a string of four
     *  upper-case letters. The first letter refers to the leftmost
     *  rotor setting.  */
    void setRotors(String setting) {

        String[] config = Main.configParse(setting);

        Reflector reflector = Reflector.generateReflector(config[1]);

        FixedRotor fixed = FixedRotor.generateFixedRotor(config[2]);
        fixed.set(Rotor.toIndex(config[6].charAt(0)));

        Rotor rotor3 = Rotor.generateRotor(config[3]);
        rotor3.set(Rotor.toIndex(config[6].charAt(1)));
        rotor3.setNotches(Collections.emptySet());

        Rotor rotor4 = Rotor.generateRotor(config[4]);
        rotor4.set(Rotor.toIndex(config[6].charAt(2)));

        Rotor rotor5 = Rotor.generateRotor(config[5]);
        rotor5.set(Rotor.toIndex(config[6].charAt(3)));

        Rotor[] rotorConfig = {reflector, fixed, rotor3, rotor4, rotor5};

        replaceRotors(rotorConfig);
    }

    /** Returns a converted character in a Machine on one input where the
     * rotors are setup before beginning conversion. */
    char convertChar(char c) {
        boolean checkInitialNotch = _rotors[3].atNotch();
        boolean checkNextNotch = _rotors[4].atNotch();

        if (checkInitialNotch) {
            _rotors[2].advance();

            _rotors[3].advance();

        } else if (checkNextNotch) {
            _rotors[3].advance();
        }

        _rotors[4].advance();

        int convertedChar = Rotor.toIndex(c);

        for (int i = _rotors.length - 1; i >= 0; i--) {
            convertedChar = _rotors[i].convertForward(convertedChar);
        }
        for (int k = 1; k < _rotors.length; k++) {
            convertedChar = _rotors[k].convertBackward(convertedChar);
        }
        return Rotor.toLetter(convertedChar);
    }

    /** Returns the encoding/decoding of MSG, updating the state of
     *  the rotors accordingly. */
    String convert(String msg) {
        String str = "";
        char[] characters = msg.toCharArray();
        for (int i = 0; i < characters.length; i++) {
            str += convertChar(characters[i]);
        }
        return str;
    }
}
