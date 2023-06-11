package Machiatto;

public class Pętla extends Instrukcja {
    private  char zmienna;
    private Wyrażenie ograniczenie;
    private Blok blok;

    public Pętla(char zmienna, Wyrażenie ograniczenie, Blok blok){
        this.zmienna = zmienna;
        this.blok = blok;
        this.ograniczenie = ograniczenie;
    }

    @Override
    public void wykonaj(Blok blokZewnętrzny, Program program) throws DzieleniePrzezZero, PrzysłonienieZmiennej, NieMaZmiennej, NastępnaInstrukcja, Wartościowanie{

        if(program.sprawdzaj){
            program.licznikInstrukcji ++;
            if(program.doKiedy == program.licznikInstrukcji){
                if(program.wartościowanie){
                    String liczba = String.valueOf(program.licznikInstrukcji - 1);
                    program.w(this.blok.zmienne, this.blok.czyZmienne, this.blok.głębokość);
                    program.wyzeruj();
                    throw new Wartościowanie("\nPowyżej mamy wartościowanie programu po wykonaniu " + liczba + " instrukcji przed wykonaniem operacji PĘTLA\n");
                }
                else{
                    String liczba = String.valueOf(program.licznikInstrukcji - 1);
                    program.wyzeruj();
                    throw new NastępnaInstrukcja("\nNastępną instrukcją po wykonaniu " + liczba + " instrukcji jest instrukcja PĘTLA\n");
                }
            }
        }

        int x = (int)this.zmienna - 97;

        for(int i = 0; i < 26; i ++){
            if(blokZewnętrzny.czyZmienne[i] > 0){
                this.blok.czyZmienne[i] = 2;
                this.blok.zmienne[i] = blokZewnętrzny.zmienne[i];
                this.blok.głębokość[i] = blokZewnętrzny.głębokość[i] + 1;
            }
        }
        this.blok.głębokość[x] = 1;
        this.blok.czyZmienne[x] = 2;

        int ograniczenieInt = 0;
        try{
            ograniczenieInt = this.ograniczenie.oblicz(blokZewnętrzny.zmienne, blokZewnętrzny.czyZmienne);
        }
        catch(DzieleniePrzezZero E){
            throw E;
        }
        
        for(int j = 0; j < ograniczenieInt; j ++){
            this.blok.zmienne[x] = j;
            Instrukcja pomocnicza;
            for(int i = 0; i < this.blok.instrukcje.size(); i ++){
                pomocnicza = this.blok.instrukcje.get(i);
                pomocnicza.wykonaj(this.blok, program);
            }
        }
        for(int i = 0; i < 26; i ++){
            if(i != x){
                blokZewnętrzny.zmienne[i] = this.blok.zmienne[i];
                blokZewnętrzny.czyZmienne[i] = this.blok.czyZmienne[i];
            }
            this.blok.zmienne[i] = 0;
            this.blok.czyZmienne[i] = 0;
        }
    }
}
