### zad1

szyfr cezara- osobno ma�e i du�e litery, potem negacja bitowa wszystkich znak�w.

### zad2

kompresja i dekompresja s�ownikowa obs�uguj�ca du�e pliki<br /> 
tekst do kompresji powinien znajdowa� si� w pliku input.txt<br />
tekst po kompresji b�dzie w pliku encoded.txt<br />
tekst po dekompresji b�dzie w pliku decoded.txt<br />
przyk�ad:<br />
wej�cie "ABAABC"

d- znak�w<br />
s�ownik: {A, B, C}<br />
potrzeba n (dw�ch) bit�w aby opisa� elementy s�ownika:<br />
-	A- 00<br />
-	B- 01<br />
-	C- 10<br />

do zapisania kolejnych liter w tek�cie b�dziemy potrzebowali 2*d=12 bit�w<br />
reszta okre�la ile bit�w zosta�o nie wykorzystanych<br />
0<=reszta<8, mo�na zapisa� t� warto�� na 3 bitach
r=8-(3+d*n)%8

wyj�cie: <br />
char(wielkosc slownika), char(A), char(B), char(C), 3bit(r), tekst kodowany s�ownikiem, r-bit�w uzupe�niaj�cych

