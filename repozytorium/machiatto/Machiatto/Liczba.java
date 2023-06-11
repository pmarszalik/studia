package Machiatto;

public class Liczba extends Wyrażenie{
    private int a;
    
    @Override
    public int oblicz(int zmienne[], int czyZmienne[]){
        return a;
    }

    public Liczba(int x){
        this.a = x;
    }

    public static final Liczba ZERO = new Liczba(0);
    public static final Liczba JEDEN = new Liczba(1);
}