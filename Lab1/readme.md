zad1
szyfr cezara- osobno ma�e i du�e litery, potem negacja bitowa wszystkich znak�w.
zad2
kompresja i dekompresja s�ownikowa obs�uguj�ca du�e pliki 
tekst do kompresji powinien znajdowa� si� w pliku input.txt
tekst po kompresji b�dzie w pliku encoded.txt
tekst po dekompresji b�dzie w pliku decoded.txt
przyk�ad:
wej�cie "ABAABC"

d- znak�w
s�ownik: {A, B, C}
potrzeba n (dw�ch) bit�w aby opisa� elementy s�ownika:
	A- 00
	B- 01
	C- 10

do zapisania kolejnych liter w tek�cie b�dziemy potrzebowali 2*d=12 bit�w
reszta okre�la ile bit�w zosta�o nie wykorzystanych
0<=reszta<8, mo�na zapisa� t� warto�� na 3 bitach
r=8-(3+d*n)%8

wyj�cie 
char(wielkosc slownika), char(A), char(B), char(C), 3bit(r), tekst kodowany s�ownikiem, r-bit�w uzupe�niaj�cych

