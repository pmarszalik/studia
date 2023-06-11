package Machiatto;

public abstract class Instrukcja {
    abstract void wykonaj(Blok blokZewnętrzny, Program program) throws DzieleniePrzezZero, PrzysłonienieZmiennej, NieMaZmiennej, NastępnaInstrukcja, Wartościowanie;
}
