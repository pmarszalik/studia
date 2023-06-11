package mastermind;
/*
Zdaję sobie sprawę, że ta klasa nie jest najmądrzej napisana, ale ona jest tylko do testowania...
 */
public class Game {
    private final Composition correct;
    public Game(Composition composition){
        correct = composition;
    }
    public Composition correct(){ return correct; }
    int play(AnyComposition player){
        Color[] colorsCorrect = this.correct.composition();
        int pom = 0;
        int numerOfGuesses = 0;
        for(int i = 1; i < 20; i ++){
            Composition moveToDo = player.nextMove();
            player.update(moveToDo, player.hint(this.correct, moveToDo));
            Color[] colorsGuessed = moveToDo.composition();
            pom = 0;
            for(int j = 0; j < 4; j ++){
                if(colorsGuessed[j].equals(colorsCorrect[j])){
                    pom ++;
                }
            }
            if(pom == 4){
                numerOfGuesses = i;
                break;
            }
        }
        if(pom == 4)
            return numerOfGuesses;
        else
            return 0;
    }
}
