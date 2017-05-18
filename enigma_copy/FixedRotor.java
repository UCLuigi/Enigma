package enigma;

import java.util.Map;

/** Class that represents a rotor that has no ratchet and does not advance.
 *  @author Luis Alba
 */
class FixedRotor extends Rotor {

    /** FixedRotor constructor with a forwardMapping, backwardMapping,
     *  and no notches. */
    FixedRotor(Map<Integer, Integer> forward,
                      Map<Integer, Integer> backward) {
        super(forward, backward, null);
    }

    /** Returns a FixedRotor according name in PermutationData. */
    static FixedRotor generateFixedRotor(String name) {
        String[] specRotor = getRotor(name);

        char[] forward = specRotor[1].toCharArray();
        Map<Integer, Integer> forwardMap = getMap(forward);

        char[] backward = specRotor[2].toCharArray();
        Map<Integer, Integer> backwardMap = getMap(backward);

        return new FixedRotor(forwardMap, backwardMap);
    }

    @Override
    boolean advances() {
        return false;
    }

    @Override
    boolean atNotch() {
        return false;
    }

    /** Fixed rotors do not advance. */
    @Override
    void advance() {
    }


}
