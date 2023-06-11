import Machiatto.*;
import Machiatto.Zmienna;

/*
 * To jest wrapper na cały program.
 * Tutaj łapię wyjątki i tutaj mogę odpalić program z odpluskiwaczem lub bez.
 * Program mogę odpalić odpowiednimi istrucjami z treści zadania.
 * Funkcjonalności debuggera są metodami klasy Program.
 * Sposób tworzenia programu w Machiatto w mojej implementacji można zobaczyć w funkcji Main.
 */

public class Main {
    public static void main(String args[]){
        Program program = new Program();
        
        Instrukcja deklaracjaN = new Deklaracja('n', new Liczba(30));
        
        Instrukcja deklaracjaP = new Deklaracja('p', new Liczba(1));
        Instrukcja przypisanieK = new Przypisanie('k', new Dodawanie(new Zmienna('k'), new Liczba(2)));
        Instrukcja przypisanieI = new Przypisanie('i', new Dodawanie(new Zmienna('i'), new Liczba(2)));
        Instrukcja wypisanieK = new Wypisanie(new Zmienna('k'));
        Instrukcja przypisaniePna0 = new Przypisanie('p', Liczba.ZERO ) ;

        Wyrażenie KmoduloI = new Modulo(new Zmienna('k'), new Zmienna('i')) ;

        Blok blokWG = new Blok(false);
        blokWG.dodajInstrukcje(przypisaniePna0);

        Instrukcja warunekG = new Warunek(KmoduloI, Liczba.ZERO, "=", blokWG);
        Blok blokPW = new Blok(false);

        blokPW.dodajInstrukcje(przypisanieI);
        blokPW.dodajInstrukcje(warunekG);

        Instrukcja pętlaW = new Pętla('i', new Odejmowanie(new Zmienna('k'), new Liczba(2)), blokPW);
        Blok blokWD = new Blok(false);
        blokWD.dodajInstrukcje(wypisanieK);

        Instrukcja warunekD = new Warunek(new Zmienna('p'), Liczba.JEDEN, "=", blokWD);

        Blok blok = new Blok(false);

        blok.dodajInstrukcje(deklaracjaP);
        blok.dodajInstrukcje(przypisanieK);
        blok.dodajInstrukcje(pętlaW);
        blok.dodajInstrukcje(warunekD);

        Instrukcja pom = blok;
        Blok blokPZ = new Blok(false);
        blokPZ.dodajInstrukcje(pom) ;

        Instrukcja pętlaZ = new Pętla('k', new Odejmowanie(new Zmienna('n'), Liczba.JEDEN), blokPZ);

        program.program.dodajInstrukcje(deklaracjaN);
        program.program.dodajInstrukcje(pętlaZ);

        
        // program.s(10);
        // program.d(10);

        // program.s(100);
        // program.d(10);       
                                // tak wywołuję program
        // program.s(100);
        // program.d(10);

        // program.s(125);
        // program.d(10);
        
        // program.e();


        
        program.c();          // albo tak
        

        // program.s(100);
        // program.d(10);
        // program.s(1);
        // program.s(1);        // albo tak
        // program.s(1);
        // program.s(1);
        // program.d(5);
        // program.e();
        
    }
}
