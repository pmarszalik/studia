package mastermind;

/**
 * Wskazówka otrzymana od gracza kodującego w odpowiedzi na układ zaproponowany przez gracza zgadującego.
 * @param goodColorGoodSlot Liczba pionków o dobrym kolorze, które znajdują się na dobrych miejscach.
 * @param goodColorBadSlot Liczba pionków o dobrym kolorze, które znajdują się na złych miejscach.
 */
public record Hint(int goodColorGoodSlot, int goodColorBadSlot) {
public Hint {
        if (goodColorGoodSlot < 0 || goodColorBadSlot < 0 || goodColorGoodSlot + goodColorBadSlot > 4) {
        throw new RuntimeException("bad hint");
        }
        }
        }

