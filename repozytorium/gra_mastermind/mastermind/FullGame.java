package mastermind;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
/*
Tutaj można szczegółowo prześledzić tok danej gry
 */

class FullGame {
    @Test
    void oneFullGameRandom(){
        Composition com = new Composition(new Color[]{Color.RED,Color.RED,Color.YELLOW,Color.YELLOW});
        Game game = new Game(com);
        AnyComposition player = new AnyComposition();
        int numer = 0;
        int pom = 0;
        for(int i = 0; i < 20; i ++){
            Composition moveToDo = new Composition(player.nextMove().composition());
            Color[] colors = moveToDo.composition();
            numer ++;
            System.out.println("Ruch numer " + numer);
            for(int j = 0; j < 4;j ++){
                System.out.println(colors[j]);
            }
            System.out.println("A miales zgadnac: ");
            Color[] colors2 = game.correct().composition();
            for(int j = 0; j < 4;j ++){
                System.out.println(colors2[j]);
            }
            System.out.println("Takiego dostjeszz hinta: ");
            Hint hint = player.hint(moveToDo, game.correct());
            System.out.println(hint.goodColorGoodSlot());
            System.out.println(hint.goodColorBadSlot());
            pom = 0;
            for(int j = 0; j < 4; j ++){
                if(colors[j].equals(colors2[j])){
                    pom ++;
                }
            }
            if(pom == 4){
                break;
            }

            System.out.println("Rozmiar struktury przed = " + player.size());
            player.update(moveToDo, player.hint(moveToDo, game.correct()));
            System.out.println("Rozmiar struktury po = " + player.size());
            ArrayList<Composition> lista = player.getPossibleCompositions();
            for(int j = 0; j < lista.size(); j ++){
                System.out.println("Kombinacja numer " + j);
                Color[] colors1 = lista.get(j).composition();
                for(int k = 0; k < 4; k ++){
                    System.out.println(colors1[k]);
                }
                System.out.println("\n");
            }
            System.out.println("\n");
            System.out.println("\n");
        }
        if(pom == 4){
            System.out.println("Wygrana w " + numer + " ruchach\n");
        }
        else{
            System.out.println("ZLEEEE!\n");
        }
    }
    @Test
    void oneFullGameBest(){
        Composition com = new Composition(new Color[]{Color.RED,Color.RED,Color.YELLOW,Color.YELLOW});
        Game game = new Game(com);
        AnyComposition player = new BestComposition();
        int numer = 0;
        int pom = 0;
        for(int i = 0; i < 20; i ++){
            Composition moveToDo = new Composition(player.nextMove().composition());
            Color[] colors = moveToDo.composition();
            numer ++;
            System.out.println("Ruch numer " + numer);
            for(int j = 0; j < 4;j ++){
                System.out.println(colors[j]);
            }
            System.out.println("A miales zgadnac: ");
            Color[] colors2 = game.correct().composition();
            for(int j = 0; j < 4;j ++){
                System.out.println(colors2[j]);
            }
            System.out.println("Takiego dostjeszz hinta: ");
            Hint hint = player.hint(moveToDo, game.correct());
            System.out.println(hint.goodColorGoodSlot());
            System.out.println(hint.goodColorBadSlot());
            pom = 0;
            for(int j = 0; j < 4; j ++){
                if(colors[j].equals(colors2[j])){
                    pom ++;
                }
            }
            if(pom == 4){
                break;
            }

            System.out.println("Rozmiar struktury przed = " + player.size());
            player.update(moveToDo, player.hint(moveToDo, game.correct()));
            System.out.println("Rozmiar struktury po = " + player.size());
            ArrayList<Composition> lista = player.getPossibleCompositions();
            for(int j = 0; j < lista.size(); j ++){
                System.out.println("Kombinacja numer " + j);
                Color[] colors1 = lista.get(j).composition();
                for(int k = 0; k < 4; k ++){
                    System.out.println(colors1[k]);
                }
                System.out.println("\n");
            }
            System.out.println("\n");
            System.out.println("\n");
        }
        System.out.println("\n");
        if(pom == 4){
            System.out.println("Wygrana w " + numer + " ruchach\n");
        }
        else{
            System.out.println("NIEDOBRZE!\n");
        }
    }
}