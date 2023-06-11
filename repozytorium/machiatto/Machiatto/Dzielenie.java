package Machiatto;

public class Dzielenie extends Wyrażenie{
    private Wyrażenie a,b;
    
    @Override
    public int oblicz(int zmienne[], int czyZmienne[]) throws DzieleniePrzezZero, NieMaZmiennej{
        try{
            if(b.oblicz(zmienne, czyZmienne) == 0)
                throw new DzieleniePrzezZero("\nBłąd przy wyliczaniu wyrażenia podczas wywołania funkcji Dzielenie :: dzielenie przez zero\n");
        }
        catch(DzieleniePrzezZero E){
            throw E;
        }
        return a.oblicz(zmienne, czyZmienne) / b.oblicz(zmienne, czyZmienne);
    }

    public Dzielenie(Wyrażenie x, Wyrażenie y){
        this.a = x;
        this.b = y;
    }
}
