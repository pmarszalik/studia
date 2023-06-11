package mastermind;

import java.util.ArrayList;
import java.util.Objects;

public class AnyComposition implements PossibleCompositions {

    protected ArrayList<Composition> possible;

    public AnyComposition() {
        possible = new ArrayList<>();
        Color[] colors = Color.values();

        for (Color color1 : colors) {
            for (Color color2 : colors) {
                for (Color color3 : colors) {
                    for (Color color4 : colors) {
                        Composition composition = new Composition(new Color[]{color1, color2, color3, color4});
                        possible.add(composition);
                    }
                }
            }
        }
    }
    public ArrayList<Composition> getPossibleCompositions(){
        return possible;
    }
    public int size(){ return possible.size(); }
    @Override
    public Composition nextMove() throws RuntimeException{
        if(possible.size() == 0)
            throw new RuntimeException("nextMove z AnyComposition pusty zbior");
        return possible.get(0);
    }

    @Override
    public void update(Composition composition, Hint hint){
        ArrayList<Composition> newPossible = new ArrayList<>();
        for(Composition possibleComposition : possible){
            Hint helperHint = this.hint(possibleComposition, composition);
            if(hint.equals(helperHint)){
                newPossible.add(possibleComposition);
            }
        }
        if(newPossible.size() == 0)
            throw new RuntimeException("update pusty zbior");
        possible.clear();
        possible.addAll(newPossible);
    }

    public Hint hint(Composition composition1, Composition composition2){
        int goodColorBadPlace = 0;
        int goodColorAndPlace = 0;
        boolean alreadyAdded;
        Color[] colors1 = composition1.composition();
        Color[] colors2 = composition2.composition();
        for(int i = 0; i < 4; i ++){
            alreadyAdded = false;
            if(colors1[i].equals(colors2[i])) {
                goodColorAndPlace++;
                alreadyAdded = true;

            }
            for(int j = 0; j < 4; j ++){
                if(colors1[i].equals(colors2[j]) && i != j && !alreadyAdded){
                    goodColorBadPlace ++;
                    alreadyAdded = true;
                }
            }
        }
        int goodColorBadPlace0 = 0;
        int goodColorAndPlace0 = 0;
        for(int i = 0; i < 4; i ++){
            alreadyAdded = false;
            if(colors2[i].equals(colors1[i])) {
                goodColorAndPlace0 ++;
                alreadyAdded = true;
            }
            for(int j = 0; j < 4; j ++){
                if(colors2[i].equals(colors1[j]) && i != j && !alreadyAdded){
                    goodColorBadPlace0 ++;
                    alreadyAdded = true;
                }
            }
        }
        goodColorAndPlace = Math.min(goodColorAndPlace, goodColorAndPlace0);
        goodColorBadPlace = Math.min(goodColorBadPlace, goodColorBadPlace0);
        return new Hint(goodColorAndPlace, goodColorBadPlace);
    }
}
