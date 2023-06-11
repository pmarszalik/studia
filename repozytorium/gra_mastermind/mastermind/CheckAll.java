package mastermind;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/*
Ten test spawdza, czy każda gra się dobrze kończy - czyli czy udaje się zgadnąć.
Z ciekawości wypisuje też ile ruchów zajęło to Randomowi i Bestowi - widać różnicę!
 */

class CheckAll {
    @Test
    void testAll(){
        Color[] colors = Color.values();
        int i = 1;
        int x1 = 0;
        int x2 = 0;
        for (Color color1 : colors) {
            for (Color color2 : colors) {
                for (Color color3 : colors) {
                    for (Color color4 : colors) {
                        Composition composition = new Composition(new Color[]{color1, color2, color3, color4});
                        AnyComposition playerRandom = new AnyComposition();
                        AnyComposition playerBest = new BestComposition();
                        Game game1 = new Game(composition);
                        Game game2 = new Game(composition);
                        int a = game1.play(playerBest);
                        int b = game2.play(playerRandom);
                        System.out.println("Test" + i + " ");
                        System.out.println("Best score: " + a + " | " + "Random score " + b);
                        assertNotEquals(0, a);
                        assertNotEquals(0, b);
                        x1 += a;
                        x2 += b;
                        i ++;
                    }
                }
            }
        }
        System.out.println(x1 + "/625 ");
        System.out.println(x2 + "/625 ");

    }
}