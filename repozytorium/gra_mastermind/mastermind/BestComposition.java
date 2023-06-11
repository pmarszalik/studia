package mastermind;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BestComposition extends AnyComposition{

    public BestComposition(){
        super();
    }

    @Override
    public Composition nextMove(){
        int answerSize = 1000000;
        int weight;
        if(possible.size() == 0)
            throw new RuntimeException("nextMove z BestComposition pusty");
        Composition answer = possible.get(0);
        for(Composition possibleComposition : possible){
            Map<Hint,Integer> mapa = new HashMap<>();
            weight = 0;
            for(Composition composition : possible){
                Hint hint = this.hint(composition, possibleComposition);
                if(mapa.containsKey(hint)){
                    mapa.put(hint, mapa.get(hint)+1);
                }
                else{
                    mapa.put(hint,1);
                }
            }
            for(int i : mapa.values()){
                weight = Math.max(weight, i);
            }
            if(weight < answerSize){
                answerSize = weight;
                answer = possibleComposition;
            }
        }
        return answer;
    }
}
