package Machiatto;

public class Przypisanie extends Instrukcja{
    private char zmienna;
    private Wyrażenie wyrażenie;

    public Przypisanie(char zmienna, Wyrażenie wyrażenie){
        this.zmienna = zmienna;
        this.wyrażenie = wyrażenie;
    }

    @Override
    public void wykonaj(Blok blokZewnętrzny, Program program) throws DzieleniePrzezZero, NieMaZmiennej, PrzysłonienieZmiennej, NastępnaInstrukcja, Wartościowanie{

        if(program.sprawdzaj){
            program.licznikInstrukcji ++;
            if(program.doKiedy == program.licznikInstrukcji){
                if(program.wartościowanie){
                    String liczba = String.valueOf(program.licznikInstrukcji - 1);
                    program.w(blokZewnętrzny.zmienne, blokZewnętrzny.czyZmienne, blokZewnętrzny.głębokość);
                    program.wyzeruj();
                    throw new Wartościowanie("\nPowyżej mamy wartościowanie programu po wykonaniu " + liczba + " instrukcji przed wykonaniem operacji PRZYPISANIE\n");
                }
                else{
                    String liczba = String.valueOf(program.licznikInstrukcji - 1);
                    program.wyzeruj();
                    throw new NastępnaInstrukcja("\nNastępną instrukcją po wykonaniu " + liczba + " instrukcji jest instrukcja PRZYPISANIE\n");
                }
            }
        }

        int x = 0;
        try{
            x = this.wyrażenie.oblicz(blokZewnętrzny.zmienne, blokZewnętrzny.czyZmienne);
        }
        catch(DzieleniePrzezZero E){
            throw E;
        }
        int zmiennaInt = (int)zmienna - 97;
        if(blokZewnętrzny.czyZmienne[zmiennaInt] > 0)
            blokZewnętrzny.zmienne[zmiennaInt] = x;
        else{
            throw new NieMaZmiennej("\nBłąd przy wykonywaniu Instrukcji podczas wywołania funkcji Przypisanie :: odwołanie do zmiennej, która nie została zadeklarowana\n");
        }
    }
}
