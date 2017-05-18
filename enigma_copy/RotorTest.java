package enigma;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Created by LuisAlba on 6/26/16.
 * Testing basic functionality of the Rotor class.
 *      1. toLetterTest
 *      2. toIndexTest
 *      3. advanceTest
 *      4. FixedRotorTest
 *      5. ReflectorTest
 */
public class RotorTest {

    @Test
    public void toLetterTest() {
        assertEquals(Rotor.toLetter(4), 'E');
        assertEquals(Rotor.toLetter(20), 'U');
        assertEquals(Rotor.toLetter(0), 'A');
        assertEquals(Rotor.toLetter(24), 'Y');
        assertEquals(Rotor.toLetter(16), 'Q');
    }

    @Test
    public void toIndexTest() {
        assertEquals(Rotor.toIndex('E'), 4);
        assertEquals(Rotor.toIndex('K'), 10);
        assertEquals(Rotor.toIndex('Z'), 25);
        assertEquals(Rotor.toIndex('L'), 11);
        assertEquals(Rotor.toIndex('R'), 17);
    }

    @Test
    public void advanceTest() {
        Rotor testRotor = Rotor.generateRotor("III");
        assertEquals(0, testRotor.getSetting());
        testRotor.set(6);
        assertTrue(testRotor.getSetting() == 6);
        assertTrue(testRotor.advances());
    }

    @Test
    public void FixedRotorTest() {
        FixedRotor testFixed = FixedRotor.generateFixedRotor("BETA");
        assertEquals(0, testFixed.getSetting());
        testFixed.set(5);
        assertTrue(testFixed.getSetting() == 5);
        assertFalse(testFixed.advances());
        assertFalse(testFixed.atNotch());
    }

    @Test
    public void ReflectorTest() {
        Reflector reflect = Reflector.generateReflector("B");
        assertFalse(reflect.hasInverse());
    }

}
