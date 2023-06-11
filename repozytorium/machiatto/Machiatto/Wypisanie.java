package Machiatto;

public class Wypisanie extends Instrukcja{
    private Wyrażenie wyrażenie;

    public Wypisanie(Wyrażenie wyrażenie){
        this.wyrażenie = wyrażenie;
    }

    @Override
    public void wykonaj(Blok blokZewnętrzny, Program program) throws DzieleniePrzezZero, NieMaZmiennej, NastępnaInstrukcja, Wartościowanie{

        if(program.sprawdzaj){
            program.licznikInstrukcji ++;
            if(program.doKiedy == program.licznikInstrukcji){
                if(program.wartościowanie){
                    String liczba = String.valueOf(program.licznikInstrukcji - 1);
                    program.wyzeruj();
                    program.w(blokZewnętrzny.zmienne, blokZewnętrzny.czyZmienne, blokZewnętrzny.głębokość);
                    throw new Wartościowanie("\nPowyżej mamy wartościowanie programu po wykonaniu " + liczba + " instrukcji przed wykonaniem operacji WYPISANIE\n");
                }
                else{
                    String liczba = String.valueOf(program.licznikInstrukcji - 1);
                    program.wyzeruj();
                    throw new NastępnaInstrukcja("\nNastępną instrukcją po wykonaniu " + liczba + " instrukcji jest instrukcja WYPISANIE\n");
                }
            }
        }

        try{
            if(!program.wartościowanie)
                System.out.println(this.wyrażenie.oblicz(blokZewnętrzny.zmienne, blokZewnętrzny.czyZmienne));
        }
        catch(DzieleniePrzezZero E){
            throw E;
        }
    }
}
