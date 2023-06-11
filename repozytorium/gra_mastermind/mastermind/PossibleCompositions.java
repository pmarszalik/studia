package mastermind;

/**
 * Zbiór układów, które jeszcze mogą być prawidłowe.
 */
public interface PossibleCompositions {
    /**
     * Aktualizuje przechowywany zbiór układów. Rzuca RuntimeException w przypadku wykrycia niezgodności z historią gry.
     * @param composition Układ zagrany przez gracza zgadującego.
     * @param hint Odpowiedź gracza kodującego.
     */
    void update(Composition composition, Hint hint);

    /**
     * @return Pewien układ, jaki aktualnie może zagrać gracz zgadujący.
     */
    Composition nextMove();
}
