#include <stdio.h>
#include <malloc.h>
#include <math.h>
#include <assert.h>
#include <stdbool.h>

#define epsilon 1e-10 // dokladnosc

typedef struct {
	int nastepny; // jesli to zgiecie, to indeks figury z ktorej powstala
	double c1;		//jesli to figura to -1 dla prostokata i -2 dla kola
	double d1;
	double c2;
	double d2;
}figura;

typedef struct {
	double x;
	double y;
}punkt;

punkt zrob_punkt(double a1, double b1)
{
	punkt p = {a1, b1};
	return p;
}

bool soe(double a, double b) // "smaller or equal"
{
	return ((a - b) < epsilon);
}

punkt odbij(punkt a, figura k)
{
	double dx, dy, ka, ca, kk, ck, przeciecie;
	dx = k.c2 - k.c1;
	dy = k.d2 - k.d1;
	if(fabs(dx) < epsilon) // jesli to prosta pionowa x = c
		a.x = 2 * k.c1 - a.x;
	else if(fabs(dy) < epsilon) // jesli to prosta pozioma y = c
		a.y = 2 * k.d1 - a.y;
	else // jesli to prosta ax + b dla a != 0; licze prostopadla i przeciecie
	{
		ka = - dx / dy;    
		ca = a.y - ka * a.x;
		kk = dy / dx;
		ck = k.d1 - kk * k.c1;

		przeciecie = (ck - ca) / (ka - kk);
		a.x = 2 * przeciecie - a.x;
		a.y = ka * a.x + ca;
	}
	return a;
}

bool czy_punkt_w_figurze(punkt a, figura f)
{
	if(f.nastepny == -1) // prostokat
	{
		if(soe(f.c1, a.x) && soe(a.x, f.c2))
			if(soe(f.d1, a.y) && soe(a.y, f.d2))
				return true;
		return false;
	}
	else // kolo
	{
		double dx,dy;
		dx = a.x - f.c1;
		dy = a.y - f.d1;
		if(soe(dx * dx + dy * dy, f.c2 * f.c2))
			return true;
		return false;
	}
}

bool czy_punkt_na_prostej(punkt a, figura k)
{
	double da,db,dc,dd;
	da = a.x - k.c1;
	db = a.y - k.d1;
	dc = k.c2 - k.c1;
	dd = k.d2 - k.d1;
	return (fabs(da * dd - db * dc) < epsilon);
}

bool czy_punkt_jest_z_lewej(punkt a, figura k)
{
	double da,db,dc,dd;
	da = a.x - k.c1;
	db = a.y - k.d1;
	dc = k.c2 - k.c1;
	dd = k.d2 - k.d1;
	return (da * dd - db * dc < epsilon);
}

/*
rekurencyjnie wyliczam wynik dla danego punktu
jesli k to figura (nie zagiecie) to zwracam 1 jak jest w srodku a 0 ja nie jest
jesli k to zagiecie to sprawdzam czy moj punkt jest na prostej i jak jest to go nie odbijam
dalej gdy wiem ze nie jest na prostej to patrze czy jest z dobrej strony - 
- i jak jest z dobrej (lewej) to go odbijam i zwracam sume wynikow dla odbitego i tego dla kolejnego zagiecia (lub figury)
jak jest ze zlej strony to zwracam 0 bo bedzie on poza figura

a - punkt dla ktorego wyliczam wynik
k - zagiecie o ktore odbijam punkt lub ostateczna figura
*tab - tablica z wpisanymi figurami z wejscia
*/
int oblicz_wynik(punkt a, figura k, figura *tab)
{
	if(k.nastepny < 0)
		return czy_punkt_w_figurze(a, k);
	if(czy_punkt_na_prostej(a, k))
		return oblicz_wynik(a, tab[k.nastepny], tab);
	if(czy_punkt_jest_z_lewej(a, k))
		return oblicz_wynik(a, tab[k.nastepny], tab) + 
				oblicz_wynik(odbij(a, k), tab[k.nastepny], tab);
	return 0;
}

int main()
{
	int ktora, wynik, n, q;
	char literka;
	double k1, k2, l1, l2, pro;
	long unsigned int n0;

	assert(scanf("%d %d", &n, &q));
	n0 = (long unsigned int)(n + 2);
	figura *figury = (figura*) malloc(n0 * sizeof(figura));
	
	for(int i = 0; i < n; i ++)
	{
		assert(scanf(" %c", &literka));
		if(literka == 'P')
		{
			assert(scanf("%lf %lf %lf %lf", &k1, &l1, &k2, &l2));
			figury[i] = (figura){.nastepny = -1, .c1 = k1, .d1 = l1, .c2 = k2, .d2 = l2};
		}
		else if(literka == 'K')
		{
			assert(scanf("%lf %lf %lf", &k1, &l1, &pro));
			figury[i] = (figura){.nastepny = -2, .c1 = k1, .d1 = l1, .c2 = pro, .d2 = 0};
		}
		else
		{
			assert(scanf("%d %lf %lf %lf %lf", &ktora, &k1, &l1, &k2, &l2));
			figury[i] = (figura){.nastepny = ktora - 1, .c1 = k1, .d1 = l1, .c2 = k2, .d2 = l2};
		}
	}
	
	for(int i = 0; i < q; i ++)
	{
		assert(scanf("%d %lf %lf", &ktora, &k1, &l1));
		wynik = oblicz_wynik(zrob_punkt(k1,l1), figury[ktora - 1], figury);
		printf("%d\n", wynik);
	}

	free(figury);

	return 0;
}
