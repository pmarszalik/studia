package Machiatto;

public class Dodawanie extends Wyrażenie{
    private Wyrażenie a,b;
    
    @Override
    public int oblicz(int zmienne[], int czyZmienne[])  throws DzieleniePrzezZero, NieMaZmiennej{
        return a.oblicz(zmienne, czyZmienne) + b.oblicz(zmienne, czyZmienne);
    }

    public Dodawanie(Wyrażenie x, Wyrażenie y){
        this.a = x;
        this.b = y;
    }
}