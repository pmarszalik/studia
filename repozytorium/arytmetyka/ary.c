#include <stdio.h>
#include <math.h>
#include "ary.h"
/*
PEER REVIEW: Marcin Piekutowski
*/

bool iszero(double x)
{
	return (fabs(x) < 1e-10);
}

bool in_wartosc(wartosc w, double x)
{
	if(isnan(w.l)) //bo to przedzial (nan,nan), czyli niezdefiniowane
		return false;

	if(w.inv)  // gdy to antyprzedzial
	{	
		if(iszero(w.l - x) || iszero(w.r - x)) //  jak x jest na brzegu
			return true;
		else if(w.l < x && x < w.r) // jak jest w srodku
			return false;

		return true;
	}

	if(w.l < x && x < w.r) // x w srodku
		return true;
	else if(iszero(w.l - x) || iszero(w.r - x)) //   x na brzegu
		return true;

	return false;
}

bool czy_nan(wartosc w)
{
	if(isnan(w.l))
		return true;
	else if(w.l > w.r)
		return true;

	return false;
}

double min_wartosc(wartosc w)
{
	if(czy_nan(w))
		return NAN;
	else if(w.inv)
		return -HUGE_VAL; 

	return w.l;
}

double max_wartosc(wartosc w)
{
	if(czy_nan(w))
		return NAN;
	else if(w.inv)
		return HUGE_VAL;

	return w.r;
}

double sr_wartosc(wartosc w)
{
	if(czy_nan(w))
		return NAN;
	else if(w.inv) //  bo to -inf + inf
		return NAN;
	else if(isinf(w.l) && isinf(w.r))   // bo to inf - inf
		return NAN;
	else if(isinf(w.r))
		return HUGE_VAL;
	else if(isinf(w.l))
		return -HUGE_VAL;

	return (w.r + w.l) / 2;
}

wartosc plus(wartosc w, wartosc v)
{
	if(!w.inv && !v.inv)
		return wartosc_od_do(w.l + v.l, w.r + v.r);
	else if(!w.inv)
	{
		if(w.r + v.l > w.l + v.r || iszero(w.r + v.l - w.l - v.r))
			return wartosc_od_do(-HUGE_VAL, HUGE_VAL);
		else
		{
			v.l += w.r;
			v.r += w.l;
			return v;
		}
	}
	else if(!v.inv)   //zamieniam miejscami zeby antyprzedzial byl drugi
		return plus(v, w);
	
	return wartosc_od_do(-HUGE_VAL, HUGE_VAL); // bo widac ze sumujac dowolnie duze liczby z jednego antyprzedzialu	
}													// i dowolnie male z drugiego moge otzymac co chce

wartosc minus(wartosc w, wartosc v)
{
	v.l = -v.l;
	v.r = -v.r;
	double x = v.r;
	v.r = v.l;
	v.l = x;
	return plus(w, v);  // bo istotnie (a,b) - (c,d) = (a,b) + (-d,-c)
}

wartosc przedzial_razy_przedzial(wartosc w, wartosc v)
{						// gdy oba sa przedzialami (zwyklymi) [a,b] i [c,d]
	double le,ri,b[3];	// to zauwazam ze "ograniczeniami" beda ktores z liczb ac,ad,bc,bd
	le = w.l * v.l;
	ri = le;
	b[0] = w.l * v.r;
	b[1] = w.r * v.l;
	b[2] = w.r * v.r;
	
	for(int i = 0;i < 3;i++)
	{
		if(b[i] < le)
			le = b[i];

		if(ri < b[i])
			ri = b[i];
	}

	return wartosc_od_do(le,ri);
}

wartosc przedzial_razy_antyprzedzial(wartosc w, wartosc v)
{					// gdy mam przedzial * antyprzedzial to widze ze znaczenie ma pare warunkow:
	if(in_wartosc(w, 0)) 				//jesli w przedziale jest 0 to moge antyprzedzial "dowolnie zmniejszyc", wiec cale R
		return wartosc_od_do(-HUGE_VAL, HUGE_VAL);
	else if(!in_wartosc(v, 0))  // czy antyprzedzial ma 0 bo jesli ma to ma znaczenie w ktora strone chce zkalowac ograniczenia
	{
		if(0 < w.l)
		{
			v.l = v.l * w.l;
			v.r = v.r * w.l;
			return v;
		}
		else
		{
			double x = v.l;
			v.l = v.r * w.r;
			v.r = x * w.r;
			return v;
		}
	}
	else
	{
		if(v.l * w.l < v.l * w.r)
			v.l *= w.r;
		else
			v.l *= w.l;

		if(v.r * w.l < v.r * w.r)   
			v.r *= w.l;
		else
			v.r *= w.r;

		if(v.l > v.r || iszero(v.l - v.r))
		{
			if(0 < w.l)
				return wartosc_od_do(-HUGE_VAL, HUGE_VAL);   // bo to znaczy ze ograniczenia na siebie "naszly" wiec mam cale R
			else
			{
				double x = v.l;			// a to znaczy ze skalowalem przez ujemne wartosci wiec sie obrocilo
				v.l = v.r;
				v.r = x;
				return v;
			}
		}
		
		return v;
	}
}

wartosc antyprzedzial_razy_antyprzedzial(wartosc w, wartosc v)
{			// latwo widac ze iloczyn dwoch antyprzedzialow daje -inf,inf gdy w jednym jest zero
	if(in_wartosc(w, 0) || in_wartosc(v, 0))	
		return wartosc_od_do(-HUGE_VAL, HUGE_VAL);

	double le,ri;
	le = w.l * v.r;

	if(le < w.r * v.l)			///jesli nie to po prostu trzeba madrze przeskalowac
		le = w.r * v.l;

	ri = w.l * v.l;

	if(w.r * v.r < ri)
		ri = w.r * v.r;

	w.l = le;
	w.r = ri;
	return w;
}

wartosc razy(wartosc w, wartosc v)
{
	if(iszero(w.l) && iszero(w.r))      	//bo 0 razy cokolwiek to 0
		return w;
	else if(iszero(v.l) && iszero(v.r))     
		return v;
	else if(!w.inv && !v.inv)   
		return przedzial_razy_przedzial(w, v);	
	else if(!w.inv) 
		return przedzial_razy_antyprzedzial(w, v);
	else if(!v.inv) 
		return przedzial_razy_antyprzedzial(v, w);
	else   
		return antyprzedzial_razy_antyprzedzial(w, v);
}

wartosc odwrotnosc_przedzialu(wartosc v)
{
	if(iszero(v.l))
	{
		v.l = 1 / v.r;
		v.r = HUGE_VAL;    //odwrotnosc przedzialu z zerem z lewej
	}
	else if(iszero(v.r))
	{ 
		v.r = 1 / v.l;	
		v.l = -HUGE_VAL;   //odwrotnosc przedzialu z zerem z prawej
	}
	else if(in_wartosc(v,0))
	{
		v.inv = 1;
		v.l = 1 / v.l; 			// odwrotnosc przedzialu z zerem w srodku
		v.r = 1 / v.r;
	}
	else
	{
		v.l = 1 / v.l;
		v.r = 1 / v.r;   //  odwrotnosc przedzialu bez zera
		double x = v.l;
		v.l = v.r;
		v.r = x;
	}

	return v;
}

wartosc odwrotnosc_antyprzedzialu(wartosc v)
{
	if(!in_wartosc(v, 0))   //odwrotnosc antyprzedzialu bez 0
	{
		v.l = 1 / v.l;
		v.r = 1 / v.r;
		v.inv = 0;
	}
	else if(iszero(v.r))
	{
		v.l = 1 / v.l;			// odwrotnosc antyprzedzialu z zerem z prawej
		v.r = HUGE_VAL;
		v.inv = 0;
	}
	else if(iszero(v.l))
	{
		v.r = 1 / v.r;   // odwrotnosc antyprzedzialu z zerem z lewej
		v.l = -HUGE_VAL;
		v.inv = 0;
	}
	else
	{
		v.l = 1 / v.l;
		v.r = 1 / v.r;  // odwrotnosc antyprzedzialu z zerem gdzies w srodku
		double x = v.l;
		v.l = v.r;
		v.r = x;
	}

	return v;
}


wartosc podzielic(wartosc w, wartosc v)
{
	if(iszero(v.l) && iszero(v.r))
		return	wartosc_od_do(-NAN, NAN);   // cos / 0 to nan
	else if(iszero(w.l) && iszero(w.r))
		return w;        //  zero przez cokolwiek niezerowego to 0
	else if(!v.inv) // zauwazam ze dzielenie to mnozenie przez odwrotnosc
	    v = odwrotnosc_przedzialu(v);
	else   //tutaj licze odwrotnosci antyprzedzialow
		v = odwrotnosc_antyprzedzialu(v);

	return razy(w, v);   //no i mnoze przez te odwrotnosci
}

wartosc wartosc_dokladnosc(double x, double p)
{
	struct wartosc w;
	w.l = x * (1 - 0.01 * p);   //sprowadzam procenty do ulamkow
	w.r = x * (1 + 0.01 * p);

	if(w.l > w.r)   //tak sie moze zdazyc dla ujemnych
	{
		double y = w.r;
		w.r = w.l;
		w.l = y; 
	}

	w.inv = 0;
	return w;
}

wartosc wartosc_od_do(double x, double y)
{
	struct wartosc w;
	w.l = x;
	w.r = y;
	w.inv = 0;
	return w;
}

wartosc wartosc_dokladna(double x)
{
	return wartosc_od_do(x,x);
}