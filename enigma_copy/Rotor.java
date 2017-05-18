package enigma;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/** Class that represents a rotor in the enigma machine.
 *  @author Luis Alba
 */
class Rotor {

    /** Possible names of a normal Rotor. */
    public static final Set<String> REG_ROTORS = new HashSet<String>() {
        {
            add("I");
            add("II");
            add("III");
            add("IV");
            add("V");
            add("VI");
            add("VII");
            add("VIII");
        }
    };

    /** Possible names of a FixedRotor. */
    public static final Set<String> FIXED_ROTORS = new HashSet<String>() {
        {
            add("BETA");
            add("GAMMA");
        }
    };

    /** Possible names of a Reflector. */
    public static final Set<String> REFLECTORS = new HashSet<String>() {
        {
            add("B");
            add("C");
        }
    };

    /** Rotor constructor with a forwardMapping, backwardMapping, and notches. */
    Rotor(Map<Integer, Integer> forward,
                 Map<Integer, Integer> back,
                 Set<Integer> notches) {
        _setting = 0;
        _forwardMap = forward;
        _backwardMap = back;
        _notches = notches;
    }

    /** Size of alphabet used for plaintext and ciphertext. */
    static final int ALPHABET_SIZE = 26;

    /** Assuming that P is an integer in the range 0..25, returns the
     *  corresponding upper-case letter in the range A..Z. */
    static char toLetter(int p) {
        return (char) (p + 'A');
    }

    /** Assuming that C is an upper-case letter in the range A-Z, return the
     *  corresponding index in the range 0..25. Inverse of toLetter. */
    static int toIndex(char c) {
        return (c - 'A');
    }

    /** Returns true iff this rotor has a ratchet and can advance. */
    boolean advances() {
        return true;
    }

    /** Returns true iff this rotor has a left-to-right inverse. */
    boolean hasInverse() {
        return true;
    }

    /** Return my current rotational setting as an integer between 0
     *  and 25 (corresponding to letters 'A' to 'Z').  */
    int getSetting() {
        return _setting;
    }

    /** Set getSetting() to POSN.  */
    void set(int posn) {
        assert 0 <= posn && posn < ALPHABET_SIZE;
        _setting = posn;
    }

    /** Set the notches of a rotor. */
    void setNotches(Set<Integer> positions) {
        _notches = positions;
    }

    /** Return the conversion of P (an integer in the range 0..25)
     *  according to my permutation. */
    int convertForward(int p) {
        return convertChar(p, _forwardMap);
    }

    /** Return the conversion of E (an integer in the range 0..25)
     *  according to the inverse of my permutation. */
    int convertBackward(int e) {
        return convertChar(e, _backwardMap);
    }

    /** Return an integer determined by rotor's setting and mapping. */
    int convertChar(int c, Map<Integer, Integer> map) {
        c += _setting;
        c %= 26;
        int k = map.get(c);
        k -= _setting;
        if (k < 0) {
            k += 26;
        }
        return k;
    }

    /** Returns true iff I am positioned to allow the rotor to my left
     *  to advance. */
    boolean atNotch() {
        return _notches.contains(getSetting());
    }

    /** Advance me one position. */
    void advance() {
        _setting += 1;
        if (_setting >= 26) {
            _setting = 0;
        }
    }

    /** Creates a Rotor with specification from PermutationData. */
    static Rotor generateRotor(String rotor) {
        String[] specRotor = getRotor(rotor);

        char[] forward = specRotor[1].toCharArray();
        Map<Integer, Integer> forwardMap = getMap(forward);

        char[] backward = specRotor[2].toCharArray();
        Map<Integer, Integer> backwardMap = getMap(backward);

        char[] notchChar = specRotor[3].toCharArray();
        Set<Integer> notches = getNotches(notchChar);

        return new Rotor(forwardMap, backwardMap, notches);
    }

    /** Returns an array of correct Rotor with its specifications from PermutationData. */
    static String[] getRotor(String rotor) {
        String[] specInfo = null;
        for (int k = 0; k < PermutationData.ROTOR_SPECS.length; k++) {
            if (PermutationData.ROTOR_SPECS[k][0].equals(rotor)) {
                specInfo = PermutationData.ROTOR_SPECS[k];
                break;
            }
        }
        return specInfo;
    }

    /** Returns mapping of rotor from PermutationData. */
    static Map<Integer, Integer> getMap(char[] charMap) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < charMap.length; i++) {
            map.put(i, toIndex(charMap[i]));
        }
        return map;
    }

    /** Returns a HashSet of notches of a Rotor. */
    static Set<Integer> getNotches(char[] charN) {
        Set<Integer> notches = new HashSet<Integer>();
        for (char aCharN : charN) {
            notches.add(toIndex(aCharN));
        }
        return notches;
    }

    /** My current setting (index 0..25, with 0 indicating that 'A'
     *  is showing). */
    private int _setting;

    /** Mapping of a rotor going forward represented as integers. */
    private Map<Integer, Integer> _forwardMap;

    /** Mapping of a rotor going backwards represented as integers. */
    private Map<Integer, Integer> _backwardMap;

    /** Position of notch(es) represented as integers. */
    private Set<Integer> _notches;

}
