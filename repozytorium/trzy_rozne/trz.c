#include <stdio.h>
#include <math.h>
#include <assert.h>

/*
code reveiwer: Wojtek Malecha
*/

int n;
long int ma,mi,pom;   // maksymalny min z (d1,d2) i minimalny max z (d1,d2)
int d[1000006],a[1000006],s[1000006];
int p1[1000006],p2[1000006];  // tablica pomocnicza
int l1[1000006],l2[1000006];  // pierwszy/drugi najdalszy/najblizszy na lewo rozny od i
int r1[1000006],r2[1000006]; // pierwszy/drugi najdalszy/najblizszy na prawo rozny od i


long int max(long int a0, long int b0) // rzutuje na longa na wszelki wypadek
{
	if(a0 >= b0)
		return a0;

	return b0;
}

long int min(long int a0, long int b0) // tu tez
{
	if(b0 >= a0)
		return a0;

	return b0;
}

void oblicz_2_najdalsze()
{
	int naj1,naj2,naj3; // 1szy, 2gi  i 3ci najdalszy hotel
	naj1 = -1;			// ALE utrzymuje niezmiennik ze naj1,naj2,naj3
	naj2 = -1;			// sa parami roznych rodzajow
	naj3 = -1;

	for(int i=0;i<n;i++)
	{
		p1[i] = -1;
		p2[i] = -1;
	}

	for(int i=0;i<n;i++)
	{
		if(naj1 == -1)  // gdy nie ma najdalszego
			naj1 = i;
		else if(naj2 == -1)		// gdy jest tylko 1szy
		{
			if(a[naj1] != a[i])
			{
				p1[i] = naj1;
				naj2 = i;
			}
		}
		else if(naj3 == -1) // gdy jest 1szy i 2gi
		{
			if(a[naj1] != a[i])
			{
				p1[i] = naj1;
				if(a[naj2] != a[i])
				{
					p2[i] = naj2;
					naj3 = i;
				}
			}
			else
			{
				p1[i] = naj2;
			}
		}
		else   // gdy sa wszystie 3 najdalsze
		{
			if(a[naj1] == a[i])
			{
				p1[i] = naj2;
				p2[i] = naj3;
			}
			else if(a[naj2] == a[i])
			{
				p1[i] = naj1;
				p2[i] = naj3;
			}
			else
			{
				p1[i] = naj1;
				p2[i] = naj2;
			}
		}
	}
}

void oblicz_2_najblizsze()
{
	int naj1,naj2,naj3; // 1szy, 2gi  i 3ci najblizszy hotel
	naj1 = -1;			// ALE utrzymuje niezmiennik ze naj1,naj2,naj3
	naj2 = -1;			// sa parami roznych rodzajow
	naj3 = -1;

	for(int i=0;i<n;i++)
	{
		p1[i] = -1;
		p2[i] = -1;
	}

	for(int i=0;i<n;i++)
	{
		if(naj1 == -1) // gdy nie mam 1szego
			naj1 = i;
		else if(naj2 == -1) // gdy mam juz 1szgeo ale nie mam 2giego  
		{
			if(a[naj1] != a[i])
			{
				p1[i] = naj1;
				naj2 = naj1;
			}
		}
		else if(naj3 == -1)  // gdy mam juz 1szego i 2giego ale nie 3ciego
		{
			if(a[naj2] != a[i] && a[naj1] != a[i])
			{
				p1[i] = naj1;
				p2[i] = naj2;
				naj3 = naj2;
				naj2 = naj1;
			}
			else if(a[naj2] == a[i])
			{
				p1[i] = naj1;
				naj2 = naj1;
			}
			else
				p1[i] = naj2;
			
		}
		else  // gdy mam wszystkich
		{
			if(a[naj2] == a[i])
			{
				p1[i] = naj1;
				p2[i] = naj3;
				naj2 = naj1;
			}
			else if(a[naj1] == a[i])
			{
				p1[i] = naj2;
				p2[i] = naj3;
			}
			else
			{
				p1[i] = naj1;
				p2[i]=  naj2;
				naj3 = naj2;
				naj2 = naj1;
			}
		}
		naj1 = i;  // ustawiam tego w ktorym jestem na najblizszego
	}
}

int main()
{
	assert(scanf("%d",&n));

	for(int i=0;i<n;i++)
	{
		assert(scanf("%d", &s[i]));
		assert(scanf("%d", &d[i]));
		a[i] = s[i];
	}

	oblicz_2_najdalsze();
	
	for(int i=0;i<n;i++)
	{
		l1[i] = p1[i];
		l2[i] = p2[i];
		a[i] = s[n - 1 - i];  // obracam tablice
	}
	
	oblicz_2_najdalsze(); // licze to dla obroconej zeby znalezc teraz r1,r2
	
	for(int i=0;i<n;i++)
	{
		if(p1[i] == -1)
			r1[n - 1 - i] = -1;
		else
			r1[n - 1 - i] = n - 1 - p1[i];

		if(p2[i] == -1)
			r2[n - 1 - i] = -1;
		else
			r2[n - 1 - i] = n - 1 - p2[i];
	}

	ma = 0;

	for(int i=0;i<n;i++)  // w tej petli obliczam najlepsza taka trojke jesli i jest srodkowym hotelem
	{
		if(r1[i] == -1 || l1[i] == -1)  // wtedy nie ma takiej trojki
			continue;
		else if(s[r1[i]] != s[l1[i]])  // wtedy to jest istotnie najlepsza trojka
			ma = max(ma, min(d[r1[i]] - d[i], d[i] - d[l1[i]]));
		else  // wtedy musze sprawdzil 2 pozostale mozliwe najlepsze trojki o ile istnieja
		{
			if(r2[i] != -1)
				ma = max(ma, min(d[r2[i]] - d[i], d[i] - d[l1[i]]));

			if(l2[i] != -1)
				ma = max(ma, min(d[r1[i]] - d[i], d[i] - d[l2[i]]));
		}
	}

	oblicz_2_najblizsze();  // tablica a pozostala obrocona takze pamietam o tym i zaczynam od r1,r2
	
	for(int i=0;i<n;i++)
	{
		if(p1[i] == -1)
			r1[n - 1 - i] = -1;
		else
			r1[n - 1 - i] = n - 1 - p1[i];
		if(p2[i] == -1)
			r2[n - 1 - i] = -1;
		else
			r2[n - 1 - i] = n - 1 - p2[i];

		a[i] = s[i];   // obracam z powrotem tablice a
	}
	oblicz_2_najblizsze(); // teraz licze dla normalnej a (2 razy obroconej) czyli l1,l2
	for(int i=0;i<n;i++)
	{
		l1[i] = p1[i];
		l2[i] = p2[i];
	}

	pom = d[n - 1] - d[0];
	pom ++;
	mi = pom;  // ustawiam mi na wartosc nie do osiagniecia aby sprawdzic czy istnieje choc jedna trojka

	for(int i=0;i<n;i++)  // analogicznie do liczenia maxa
	{
		if(r1[i] == -1 || l1[i] == -1)  
			continue;
		else if(a[r1[i]] != a[l1[i]])   
			mi = min(mi, max(d[r1[i]] - d[i], d[i] - d[l1[i]]));
		else 
		{
			if(r2[i] != -1)
				mi = min(mi, max(d[r2[i]] - d[i], d[i] - d[l1[i]]));

			if(l2[i] != -1)
				mi = min(mi, max(d[r1[i]] - d[i], d[i] - d[l2[i]]));
		}
	}

	if(mi == pom) // bo wtedy nie istnieje zadna trojka bo mi jest bez zmian rowne pom
		mi = 0;

	printf("%ld %ld\n", mi, ma);
	
	return 0;
}
