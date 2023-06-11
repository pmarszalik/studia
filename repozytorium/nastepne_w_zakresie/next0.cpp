#include "next.h"

#include <bits/stdc++.h>
using namespace std;

typedef struct node {
  int val;
  node *left, *right;
} node;

/*
po kolei:
przenumerowany wektor x, wektor x posortowany bez powtorzen
dlugosc y i z;
struktura persistent (wektor ze wskaznikami do n drzew), stworzone wskazniki na node (aby potem zwolnic)
*/
vector<int> y, z;
int n, m;
vector<node *> tree, stworzone;

void stworz_z(const vector<int> &x) {
  for (int i = 0; i < int(x.size()); i++) z.push_back(x[i]);

  sort(z.begin(), z.end());
  z.erase(unique(z.begin(), z.end()), z.end());
}

void stworz_y(vector<int> &x) {
  for (int i = 0; i < int(x.size()); i++)
    x[i] = int(lower_bound(z.begin(), z.end(), x[i]) - z.begin());
}

void stworz_puste(node *w, int a, int b) {
  if (a == b) {
    w->left = w->right = NULL;
    w->val = INT_MAX;
    return;
  }

  int mid = (a + b) / 2;
  node *wl, *wr;
  wl = (node *)malloc(sizeof(node));
  wr = (node *)malloc(sizeof(node));
  stworzone.push_back(wl);
  stworzone.push_back(wr);
  stworz_puste(wl, a, mid);
  stworz_puste(wr, mid + 1, b);
  w->left = wl;
  w->right = wr;
  w->val = INT_MAX;
}

/*
dodaje kolejny element do struktury (tworzac "nowe" drzewo)
paramentry a i b to zakres jaki trzyma ten wierzcholek drzewa przedzialowego
*/
void dodaj(node *w, node *v, int a, int b, int co, int gdzie) {
  if (a == b && b == gdzie) {
    v->val = co;
    v->right = NULL;
    v->left = NULL;
    return;
  }
  int mid = (a + b) / 2;
  node *p = (node *)malloc(sizeof(node));
  stworzone.push_back(p);
  if (a <= gdzie && gdzie <= mid) {
    v->left = p;
    v->right = w->right;
    dodaj(w->left, p, a, mid, co, gdzie);
  } else {
    v->right = p;
    v->left = w->left;
    dodaj(w->right, p, mid + 1, b, co, gdzie);
  }
  v->val = co;
}

/*
zwraca minimum na przedziale [c, d] w drzewie
w konsekwencji zwraca najwczesniejsze wystapienie liczby z tego przedzialu w danym drzewie
paramentry a i b to zakres jaki trzyma ten wierzcholek drzewa przedzialowego
parametry c i d to przedzial ktory chce podzielic na przedzialy bazowe w drzewie,
czyli przedzial w ktorym chce znalezc nastepna liczbe (tytulowy range)
*/
int nastepny(node *w, int a, int b, int c, int d) {
  if (d < a || b < c) return INT_MAX;
  if (c <= a && b <= d) return w->val;
  int mid = (a + b) / 2;
  return min(nastepny(w->left, a, mid, c, d),
             nastepny(w->right, mid + 1, b, c, d));
}

void init(const vector<int> &x) {
  for (int i = 0; i < int(x.size()); i++) y.push_back(x[i]);
  stworz_z(x);
  stworz_y(y);
  n = int(x.size());
  m = int(z.size());
  tree.resize(n + 1);

  node *p = (node *)malloc(sizeof(node));
  stworzone.push_back(p);
  stworz_puste(p, 0, m - 1);
  tree[n] = p;

  for (int i = n - 1; i >= 0; i--) {
    node *r = (node *)malloc(sizeof(node));
    stworzone.push_back(r);
    tree[i] = r;
    dodaj(tree[i + 1], r, 0, m - 1, i, y[i]);
  }
}

int nextInRange(int i, int a, int b) {
  a = int(lower_bound(z.begin(), z.end(), a) - z.begin());
  b = int(upper_bound(z.begin(), z.end(), b) - z.begin());
  // cout << a << " " << b << "\n";
  if (a == m || b == 0) return -1;
  b--;
  int c = nastepny(tree[i], 0, m - 1, a, b);
  if (c == INT_MAX) c = -1;
  return c;
}

void done() {
  for (int i = 0; i < int(stworzone.size()); i++) free(stworzone[i]);
  y.clear();
  z.clear();
  stworzone.clear();
}