package Machiatto;

public class Deklaracja extends Instrukcja{
    private char zmienna;
    private Wyrażenie wyrażenie;

    public Deklaracja(char zmienna, Wyrażenie wyrażenie){
        this.zmienna = zmienna;
        this.wyrażenie = wyrażenie;
    }

    @Override
    public void wykonaj(Blok blokZewnętrzny, Program program) throws DzieleniePrzezZero, PrzysłonienieZmiennej, NieMaZmiennej, NastępnaInstrukcja, Wartościowanie{

        if(program.sprawdzaj){
            program.licznikInstrukcji ++;
            if(program.doKiedy == program.licznikInstrukcji){
                if(program.wartościowanie){
                    String liczba = String.valueOf(program.licznikInstrukcji - 1);
                    program.w(blokZewnętrzny.zmienne, blokZewnętrzny.czyZmienne, blokZewnętrzny.głębokość);
                    program.wyzeruj();
                    throw new Wartościowanie("\nPowyżej mamy wartościowanie programu po wykonaniu " + liczba + " instrukcji przed wykonaniem operacji DEKLARACJA\n");
                }
                else{
                    String liczba = String.valueOf(program.licznikInstrukcji - 1);
                    program.wyzeruj();
                    throw new NastępnaInstrukcja("\nNastępną instrukcją po wykonaniu " + liczba + " instrukcji jest instrukcja DEKLARACJA\n");
                }
            }
        }

        int x = this.wyrażenie.oblicz(blokZewnętrzny.zmienne, blokZewnętrzny.czyZmienne);
        
        int zmiennaInt = (int)zmienna - 97;
        if(blokZewnętrzny.czyZmienne[zmiennaInt] < 2){
            blokZewnętrzny.czyZmienne[zmiennaInt] = 2;
            blokZewnętrzny.zmienne[zmiennaInt] = x;
            blokZewnętrzny.głębokość[zmiennaInt] = 1;
        }
        else{
            throw new PrzysłonienieZmiennej("\nBłąd przy wykonywaniu Instrukcji podczas wywołania funkcji Deklaracja :: próba zadeklarowania zmiennej, która już została zadeklarowana\n");
        }   
    }
}
