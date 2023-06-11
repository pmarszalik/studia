package Machiatto;

public class Warunek extends Instrukcja{
    private Wyrażenie pierwsze, drugie;
    private String operator;
    private Blok blok;

    public Warunek(Wyrażenie pierwsze, Wyrażenie drugie, String operator, Blok blok){
        this.pierwsze = pierwsze;
        this.drugie = drugie;
        this.blok = blok;
        this.operator = operator;
    }

    @Override
    public void wykonaj(Blok blokZewnętrzny, Program program) throws DzieleniePrzezZero,  PrzysłonienieZmiennej, NieMaZmiennej, NastępnaInstrukcja, Wartościowanie{

        if(program.sprawdzaj){
            program.licznikInstrukcji ++;
            if(program.doKiedy == program.licznikInstrukcji){
                if(program.wartościowanie){
                    String liczba = String.valueOf(program.licznikInstrukcji - 1);
                    program.w(this.blok.zmienne, this.blok.czyZmienne, this.blok.głębokość);
                    program.wyzeruj();
                    throw new Wartościowanie("\nPowyżej mamy wartościowanie programu po wykonaniu " + liczba + " instrukcji przed wykonaniem operacji WARUNEK\n");
                }
                else{
                    String liczba = String.valueOf(program.licznikInstrukcji - 1);
                    program.wyzeruj();
                    throw new NastępnaInstrukcja("\nNastępną instrukcją po wykonaniu " + liczba + " instrukcji jest instrukcja WARUNEK\n");
                }
            }
        }

        int lewe = 0;
        int prawe = 0;
        try{
            lewe = pierwsze.oblicz(blokZewnętrzny.zmienne, blokZewnętrzny.czyZmienne);
            prawe = drugie.oblicz(blokZewnętrzny.zmienne, blokZewnętrzny.czyZmienne);
        }
        catch(DzieleniePrzezZero E){
            throw E;
        }
        
        boolean czyWykonywać = false;
        switch(operator){
            case "=":
                if(lewe == prawe)
                    czyWykonywać = true;
                break;
            case "<>":
                if(lewe != prawe)
                    czyWykonywać = true;
                break;
            case ">":
                if(lewe > prawe)
                    czyWykonywać = true;
                break;
            case "<":
                if(lewe < prawe)
                    czyWykonywać = true;
                break;
            case ">=":
                if(lewe >= prawe)
                    czyWykonywać = true;
                break;
            case "<=":
                if(lewe <= prawe)
                    czyWykonywać = true;
                break;
            default:

        }

        if(czyWykonywać){                 
            Instrukcja pomocnicza;
            for(int i = 0; i < this.blok.instrukcje.size();i++){
                pomocnicza = this.blok.instrukcje.get(i);
                pomocnicza.wykonaj(blokZewnętrzny, program);
            }
        }
    }

}
