package Machiatto;
import java.util.ArrayList;

/*
 * To jest wrapper na cały program.
 * Tutaj łapię wyjątki i tutaj mogę odpalić program z odpluskiwaczem lub bez.
 * Program mogę odpalić odpowiednimi istrucjami z treści zadania.
 * Funkcjonalności debuggera są metodami klasy Program.
 * Sposób tworzenia programu w Machiatto w mojej implementacji można zobaczyć w funkcji Main.
 */

public class Program {
    public Blok program;
    public int licznikInstrukcji;
    public int doKiedy;
    private int głębia;
    public boolean sprawdzaj;
    public boolean wartościowanie;
    public ArrayList<Blok> blokiDoZerowania;

    public Program(){
        this.program = new Blok(true);
        this.doKiedy = 1;
        this.blokiDoZerowania = new ArrayList<Blok>();
    }

    public void c(){   
        this.sprawdzaj = false;
        try{
            this.program.wykonaj(this.program, this);
            System.out.println("\nProgram wykonał się pomyślnie w trybie bez debuggera\n");
        }
        catch(Exception E){
            System.out.println("\nProgram próbował się wykonać w trybie w trybie bez debuggera, ale niestety zakończyło to się błędem, którego szczegóły widać poniżej:\n");
            System.out.println(E.getMessage());
        }
        finally{
            
        }
    }

    public void s(int ile){   
        this.sprawdzaj = true;
        this.wartościowanie = false;
        this.doKiedy += ile;
        this.licznikInstrukcji = 0;
        try{
            this.program.wykonaj(this.program, this);
            System.out.println("\nProgram wykonał się do końca\n");
        }
        catch(NastępnaInstrukcja E){
            System.out.println("\nPodana liczba kroków programu wykonała się pomyślnie w trybie debuggera\n");
            System.out.println(E.getMessage());
        }
        catch(Exception E){
            System.out.println("\nProgram próbował się wykonać w trybie w trybie debuggera, ale niestety zakończyło to się błędem, którego szczegóły widać poniżej:\n");
            System.out.println(E.getMessage());
        }
        finally{

        }
    }

    public void d(int głębokość){  
        this.sprawdzaj = true;
        this.wartościowanie = true;
        this.doKiedy = this.licznikInstrukcji;
        this.licznikInstrukcji = 0; 
        this.głębia = głębokość;
        try{
            this.program.wykonaj(this.program, this);
            System.out.println("\nProgram wykonał się do końca\n");
        }
        catch(Wartościowanie E){
            System.out.println(E.getMessage());
        }
        catch(Exception E){
            System.out.println("\nProgram próbował dostać się do wartośćiowania zmiennych, ale niestety zakończyło to się błędem, którego szczegóły widać poniżej:\n");
            System.out.println(E.getMessage());
        }
        finally{

        }
    }

    public void e(){   
        System.out.println("\nDebugowanie zakończone!\n");
    }

    public void w(int zmienne[], int czyZminene[], int głębokość[]){
        char zmienna;
        System.out.println("\n");
        for(int i = 0; i < 26; i ++){
            zmienna = (char)(97 + i);
            if(czyZminene[i] == 0 || this.głębia < głębokość[i]){
                System.out.println(zmienna + " = null");
            }
            else{
                System.out.println(zmienna + " = " + zmienne[i]);
            }
        }
    }

    public void wyzeruj(){
        Blok pomocniczy;
        for(int i = 0; i < this.blokiDoZerowania.size(); i ++){
            pomocniczy = this.blokiDoZerowania.get(i);
            pomocniczy.wyzeruj();
        }
    }
    public void dodajDoZerowania(Blok blok){
        this.blokiDoZerowania.add(blok);
    }
}