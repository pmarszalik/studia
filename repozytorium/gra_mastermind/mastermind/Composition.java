package mastermind;

/**
 * Możliwy układ czterech pionków.
 */
public record Composition(Color[] composition) {
public Composition {
        if (composition.length != 4) {
        throw new RuntimeException("bad composition");
        }

        }

@Override
public Color[] composition() {
        return composition.clone();
        }
        }
