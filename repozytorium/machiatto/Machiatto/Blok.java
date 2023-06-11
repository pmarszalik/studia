package Machiatto;
import java.util.ArrayList;

public class Blok extends Instrukcja{
    private boolean czyProgram;
    public int czyZmienne[];
    public int zmienne[];
    public int głębokość[];
    public ArrayList<Instrukcja> instrukcje;

    public Blok(boolean czyProgram){
        this.czyProgram = czyProgram;
        this.czyZmienne = new int[26];
        this.zmienne = new int[26];
        this.głębokość = new int[26];
        this.instrukcje = new ArrayList<Instrukcja>();
    }

    @Override
    public void wykonaj(Blok blokZewnętrzny, Program program) throws DzieleniePrzezZero,  PrzysłonienieZmiennej, NieMaZmiennej, NastępnaInstrukcja, Wartościowanie{
        program.dodajDoZerowania(this);

        if(program.sprawdzaj){
            program.licznikInstrukcji ++;
            if(program.doKiedy == program.licznikInstrukcji){
                if(program.wartościowanie){
                    String liczba = String.valueOf(program.licznikInstrukcji - 1);
                    program.w(this.zmienne, this.czyZmienne, this.głębokość);
                    program.wyzeruj();
                    throw new Wartościowanie("\nPowyżej mamy wartościowanie programu po wykonaniu " + liczba + " instrukcji przed wykonaniem operacji BLOK\n");
                }
                else{
                    String liczba = String.valueOf(program.licznikInstrukcji - 1);
                    program.wyzeruj();
                    throw new NastępnaInstrukcja("\nNastępną instrukcją po wykonaniu " + liczba + " instrukcji jest instrukcja BLOK\n");
                }
            }
        }

        if(!czyProgram){
            for(int i = 0; i < 26; i ++){
                if(blokZewnętrzny.czyZmienne[i] == 1 || blokZewnętrzny.czyZmienne[i] == 2){
                    this.czyZmienne[i] = 1;
                    this.zmienne[i] = blokZewnętrzny.zmienne[i];
                    this.głębokość[i] = blokZewnętrzny.głębokość[i] + 1;
                }
            }
        }
        Instrukcja pomocnicza;
        for(int i = 0; i < this.instrukcje.size(); i ++){
            pomocnicza = instrukcje.get(i);
            pomocnicza.wykonaj(this, program);
        }
        if(!czyProgram){
            for(int i = 0; i < 26; i ++){
                if(this.czyZmienne[i] == 1)
                    blokZewnętrzny.zmienne[i] = this.zmienne[i];
            }
        }
        this.wyzeruj();
    }

    public void wyzeruj(){
        for(int i = 0; i < 26; i ++){
            this.czyZmienne[i] = 0;
            this.zmienne[i] = 0;
            this.głębokość[i] = 0;
        }
    }

    public void dodajInstrukcje(Instrukcja instrukcja){
        this.instrukcje.add(instrukcja);
    }
}
