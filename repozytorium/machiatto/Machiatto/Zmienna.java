package Machiatto;

public class Zmienna extends Wyrażenie{
    private char zmienna;
    
    @Override
    public int oblicz(int zmienne[], int czyZmienne[]) throws NieMaZmiennej{
        int x = (int)zmienna - 97;
        if(czyZmienne[x] > 0)
            return zmienne[x];
        else{
            throw new NieMaZmiennej("\nBłąd przy wyliczaniu Wyrażenia podczas wywołania funkcji Zmienna :: odwołanie do zmiennej, która nie została zadeklarowana\n");
        }
    }

    public Zmienna(char zmienna){
        this.zmienna = zmienna;
    }
}

