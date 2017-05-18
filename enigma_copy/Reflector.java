package enigma;

import java.util.Map;

/** Class that represents a reflector in the enigma.
 *  @author Luis Alba
 */
class Reflector extends Rotor {

    /** Reflector Constructor with a forwardMap, no backwardMap
     *  and no notches. */
    Reflector(Map<Integer, Integer> forward) {
        super(forward, null, null);
    }

    /** Returns Reflector from PermutationData given the name. */
    static Reflector generateReflector(String name) {
        String[] specRotor = getRotor(name);

        char[] forward = specRotor[1].toCharArray();
        Map<Integer, Integer> forwardMap = getMap(forward);
        return new Reflector(forwardMap);
    }

    @Override
    boolean hasInverse() {
        return false;
    }

    /** Returns a useless value; should never be called. */
    @Override
    int convertBackward(int unused) {
        throw new UnsupportedOperationException();
    }

}
