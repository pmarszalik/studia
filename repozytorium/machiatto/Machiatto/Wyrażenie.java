package Machiatto;

public abstract class Wyrażenie {

    public abstract int oblicz (int zmienne[], int czyZmienne[]) throws DzieleniePrzezZero, NieMaZmiennej;
}