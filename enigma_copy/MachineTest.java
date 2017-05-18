package enigma;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by LuisAlba on 6/26/16.
 * Testing basic functionality of Machine class
 *      1. MachineConstructorTest
 *      2. configParseTest
 */
public class MachineTest {

    @Test
    public void MachineConstructorTest() {
        Machine M = new Machine();
        Machine L = new Machine();
        assertNotEquals(M, L);

        Reflector ref = Reflector.generateReflector("B");
        FixedRotor fixed = FixedRotor.generateFixedRotor("GAMMA");
        Rotor rot3 = Rotor.generateRotor("I");
        Rotor rot4 = Rotor.generateRotor("II");
        Rotor rot5 = Rotor.generateRotor("VI");
        Rotor[] rotorConfig = {ref, fixed, rot3, rot4, rot5};
    }

    @Test
    public void configParseTest() {
        String configEx = "* B BETA III IV I AXLE";
        String[] configEx1 = Main.configParse(configEx);
        String[] configEqual = {"*", "B", "BETA", "III", "IV", "I", "AXLE"};
        assertArrayEquals(configEqual, configEx1);
        String[] configNotEqual = {"*", "C", "BETA", "III", "IV", "I", "AXLE"};
        assertFalse(Arrays.equals(configEx1, configNotEqual));
    }

}
