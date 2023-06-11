package mastermind;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ALotOfSmallTests {
    @Test
    void hintSimple(){
        Composition com1 = new Composition(new Color[]{Color.RED,Color.YELLOW,Color.BLUE,Color.GREEN});
        Composition com2 = new Composition(new Color[]{Color.RED,Color.RED,Color.RED,Color.RED});
        AnyComposition player1 = new BestComposition();
        AnyComposition player2 = new AnyComposition();

        Hint hint1 = player1.hint(com1, com2);
        Hint hint2 = player1.hint(com2, com1);
        Hint hint3 = player2.hint(com1, com2);
        Hint hint4 = player2.hint(com2, com1);


        assertEquals( 1,hint1.goodColorGoodSlot());
        assertEquals(0, hint1.goodColorBadSlot());

        assertEquals(1,hint2.goodColorGoodSlot());
        assertEquals(0, hint2.goodColorBadSlot());

        assertEquals( 1,hint3.goodColorGoodSlot());
        assertEquals(0, hint3.goodColorBadSlot());

        assertEquals( 1,hint4.goodColorGoodSlot());
        assertEquals(0, hint4.goodColorBadSlot());
    }
    @Test
    void hintCompareAllToOne(){
        Composition com1 = new Composition(new Color[]{Color.RED,Color.YELLOW,Color.GREEN,Color.BLUE});
        AnyComposition player1 = new BestComposition();
        AnyComposition player2 = new AnyComposition();
        Color[] colors = Color.values();
        for (Color color1 : colors) {
            for (Color color2 : colors) {
                for (Color color3 : colors) {
                    for (Color color4 : colors) {
                        Composition com2 = new Composition(new Color[]{color1, color2, color3, color4});

                        Hint hint1 = player1.hint(com1, com2);
                        Hint hint2 = player1.hint(com2, com1);
                        Hint hint3 = player2.hint(com1, com2);
                        Hint hint4 = player2.hint(com2, com1);


                        assertEquals(hint4.goodColorGoodSlot(), hint1.goodColorGoodSlot());
                        assertEquals(hint4.goodColorBadSlot(), hint1.goodColorBadSlot());

                        assertEquals(hint2.goodColorGoodSlot(), hint3.goodColorGoodSlot());
                        assertEquals(hint2.goodColorBadSlot(), hint3.goodColorBadSlot());

                        assertEquals(hint4.goodColorGoodSlot(), hint2.goodColorGoodSlot());
                        assertEquals(hint4.goodColorBadSlot(), hint2.goodColorBadSlot());
                    }
                }
            }
        }
    }
    @Test
    void trialGameAny(){
        Color[] composition = new Color[]{Color.RED,Color.BLUE,Color.RED,Color.RED};
        Composition com = new Composition(composition);

        AnyComposition player = new AnyComposition();
        Game game = new Game(com);
        assertNotEquals(0, game.play(player));
    }
    @Test
    void trialGameBest(){
        Color[] composition = new Color[]{Color.RED,Color.RED,Color.RED,Color.RED};
        Composition com = new Composition(composition);

        AnyComposition player = new BestComposition();
        Game game = new Game(com);
        assertNotEquals(0, game.play(player));
    }
}