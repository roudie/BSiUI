zad1
szyfr cezara- osobno ma³e i du¿e litery, potem negacja bitowa wszystkich znaków.
zad2
kompresja i dekompresja s³ownikowa obs³uguj¹ca du¿e pliki 
tekst do kompresji powinien znajdowaæ siê w pliku input.txt
tekst po kompresji bêdzie w pliku encoded.txt
tekst po dekompresji bêdzie w pliku decoded.txt
przyk³ad:
wejœcie "ABAABC"

d- znaków
s³ownik: {A, B, C}
potrzeba n (dwóch) bitów aby opisaæ elementy s³ownika:
	A- 00
	B- 01
	C- 10

do zapisania kolejnych liter w tekœcie bêdziemy potrzebowali 2*d=12 bitów
reszta okreœla ile bitów zosta³o nie wykorzystanych
0<=reszta<8, mo¿na zapisaæ t¹ wartoœæ na 3 bitach
r=8-(3+d*n)%8

wyjœcie 
char(wielkosc slownika), char(A), char(B), char(C), 3bit(r), tekst kodowany s³ownikiem, r-bitów uzupe³niaj¹cych

