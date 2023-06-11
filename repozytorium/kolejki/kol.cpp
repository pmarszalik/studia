#include <bits/stdc++.h>

#include "kol.h"
using namespace std;

namespace {
/*
        CODE REVIEW:

        bede korzystal z listy dwustronnej ze scalaniem i odwracaniem
        bardzo podobnej do tej zaimplementowanej na wykladzie
        w tej liscie wskazniki next i prev sa nierozroznialne,
        czyli nie wiem czy prev istotnie jest "w prawo" a next "w prawo"
        dlatego w zadnej z funkcji tego nie zakladam
*/
typedef struct kolejka {
  interesant *head, *tail;
} kolejka;

vector<kolejka> kolejki;
int numerki;

kolejka pusta(kolejka &res) {
  res.head->next = NULL;
  res.head->prev = res.tail;
  res.tail->next = res.head;
  res.tail->prev = NULL;
  return res;
}

kolejka stworz_kolejke() {
  kolejka res;
  res.head = (interesant *)malloc(sizeof(interesant));
  res.tail = (interesant *)malloc(sizeof(interesant));
  res = pusta(res);
  return res;
}

bool czypusta(kolejka &h) {
  return (h.head->prev == h.tail || h.head->next == h.tail);
}

/*
        ta funkcja skleja elementy a i d,
        ale element a skleja od strony b, a element d skleja od strony c
        co to znaczy?
        ze od tego momentu a wskazuje na d tym wskaznikiem, ktory wczesniej
        wskazywal na b i analogicznie z d
*/
void link(interesant *a, interesant *b, interesant *c, interesant *d) {
  if (a->prev == b)
    a->prev = d;
  else
    a->next = d;
  if (d->prev == c)
    d->prev = a;
  else
    d->next = a;
}

/*
        funkcja oczywiscie dodaje element i na koniec kolejki ko
        warunek ktory sprawdzam to upewnienie sie:
        w ktora strone od ko.tail sa kolejne elementy listy
*/
void dodaj(kolejka &ko, interesant *i) {
  i->prev = ko.tail;
  i->next = ko.tail;
  if (!ko.tail->prev)
    link(i, ko.tail, ko.tail, ko.tail->next);
  else
    link(i, ko.tail, ko.tail, ko.tail->prev);
  ko.tail->next = i;
  ko.tail->prev = NULL;
}

/*
        zwyczajnie usuwa element z listy (kolejki) w ktorej byl
*/
void usun(interesant *i) { link(i->prev, i, i, i->next); }

/*
        sprawia ze kolejka a jest pusta, a w kolejce b po ostatnim elemencie
        podpiete sa kolejne elementy a
*/
void sklej(kolejka &a, kolejka &b) {
  if (czypusta(a)) return;
  interesant *i, *j;
  if (!a.head->prev)
    i = a.head->next;
  else
    i = a.head->prev;
  if (!b.tail->prev)
    j = b.tail->next;
  else
    j = b.tail->prev;
  link(j, b.tail, a.head, i);
  if (!a.tail->prev)
    i = a.tail->next;
  else
    i = a.tail->prev;
  b.tail->prev = NULL;
  b.tail->next = NULL;
  link(b.tail, NULL, a.tail, i);
  a = pusta(a);
}

/*
        sprawdza "z ktorej strony" elementu i1 jest element i2
        przez to rozumiem ze funkcja zwraca true jest idac z i1 do i2
        najpierw robie ruch -> next, a false gdy najpierw robie ruch -> prev
        to jednoznacznie mowi mi jak sie dostac do i2 z i1 i robie to w funkcji
        usun_pomiedzy

        zmiennymi k1 i k2 chodze po lisce w "lewo" i "prawo" i1 i "szukam" i2
        zmienne l1 i l2 trzymaja mi miejsce z ktorego sie dostalem do k1 i k2
        dzieki nim wiem ze idac po liscie nigdy sie nie cofne
*/
bool ktora_strona(interesant *i1, interesant *i2) {
  interesant *k1, *k2, *l1, *l2;
  k1 = i1->next;
  k2 = i1->prev;
  l1 = l2 = i1;

  while (k1 != i2 && k2 != i2) {
    if (k1) {
      if (k1->next == l1) {
        l1 = k1;
        k1 = k1->prev;
      } else {
        l1 = k1;
        k1 = k1->next;
      }
    }
    if (k2) {
      if (k2->next == l2) {
        l2 = k2;
        k2 = k2->prev;
      } else {
        l2 = k2;
        k2 = k2->next;
      }
    }
  }

  if (k1 == i2) return true;
  return false;
}

/*
        przechodze po liscie od i1 do i2 tak samo jak w funkcji ktora_strona
        idac kolejno usuwam elementy z listy i wpisuje je do wektora
*/
void usun_pomiedzy(interesant *i1, interesant *i0, interesant *i2,
                   vector<interesant *> &v) {
  v.push_back(i0);
  while (i1 != i2) {
    v.push_back(i1);
    if (i1->next == i0) {
      usun(i0);
      i0 = i1;
      i1 = i1->prev;
    } else {
      usun(i0);
      i0 = i1;
      i1 = i1->next;
    }
  }
  v.push_back(i2);
  usun(i0);
  usun(i2);
}

/*
        wpisuje wszystkie elementy pomiedzy i0 a i2 wylacznie do wektora
        o ile i1 jest drugim elementem na sciezce pomiedzy i0 a i2
*/
void wypisz_pomiedzy(interesant *i1, interesant *i0, interesant *i2,
                     vector<interesant *> &v) {
  while (i1 != i2) {
    v.push_back(i1);
    if (i1->prev == i0) {
      i0 = i1;
      i1 = i1->next;
    } else {
      i0 = i1;
      i1 = i1->prev;
    }
  }
}

/*
        wypisuje wszystkie elementy w kolejce ko w kolejnosci obsluzenia
        sposob chodzenia po liscie jest taki jak w dwoch funkcjach wyzej
*/
void wypisz_wszystkie(kolejka &ko, vector<interesant *> &v) {
  interesant *i;
  if (!ko.head->next)
    i = ko.head->prev;
  else
    i = ko.head->next;
  wypisz_pomiedzy(i, ko.head, ko.tail, v);
}
}  // namespace

void otwarcie_urzedu(int m) {
  numerki = 0;
  kolejki.resize(m);
  for (int i = 0; i < m; i++) kolejki[i] = stworz_kolejke();
}

interesant *nowy_interesant(int k) {
  interesant *i = (interesant *)malloc(sizeof(interesant));
  i->val = numerki;
  numerki++;
  dodaj(kolejki[k], i);
  return i;
}

int numerek(interesant *i) { return i->val; }

interesant *obsluz(int k) {
  if (czypusta(kolejki[k])) return NULL;
  interesant *i;
  if (!kolejki[k].head->next)
    i = kolejki[k].head->prev;
  else
    i = kolejki[k].head->next;
  usun(i);
  return i;
}

void zmiana_okienka(interesant *i, int k) {
  usun(i);
  dodaj(kolejki[k], i);
}

void zamkniecie_okienka(int k1, int k2) { sklej(kolejki[k1], kolejki[k2]); }

std::vector<interesant *> fast_track(interesant *i1, interesant *i2) {
  std::vector<interesant *> f;
  if (i1 == i2) {
    f.push_back(i1);
    usun(i1);
    return f;
  }
  if (ktora_strona(i1, i2))
    usun_pomiedzy(i1->next, i1, i2, f);
  else
    usun_pomiedzy(i1->prev, i1, i2, f);
  return f;
}

void naczelnik(int k) {
  interesant *i = kolejki[k].head;
  kolejki[k].head = kolejki[k].tail;
  kolejki[k].tail = i;
}

std::vector<interesant *> zamkniecie_urzedu() {
  vector<interesant *> f;

  for (long unsigned int i = 0; i < kolejki.size(); i++) {
    if (!czypusta(kolejki[i])) wypisz_wszystkie(kolejki[i], f);
    free(kolejki[i].head);
    free(kolejki[i].tail);
  }

  return f;
}