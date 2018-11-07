### zad1

szyfr cezara- osobno ma³e i du¿e litery, potem negacja bitowa wszystkich znaków.

### zad2

kompresja i dekompresja s³ownikowa obs³uguj¹ca du¿e pliki<br /> 
tekst do kompresji powinien znajdowaæ siê w pliku input.txt<br />
tekst po kompresji bêdzie w pliku encoded.txt<br />
tekst po dekompresji bêdzie w pliku decoded.txt<br />
przyk³ad:<br />
wejœcie "ABAABC"

d- znaków<br />
s³ownik: {A, B, C}<br />
potrzeba n (dwóch) bitów aby opisaæ elementy s³ownika:<br />
-	A- 00<br />
-	B- 01<br />
-	C- 10<br />

do zapisania kolejnych liter w tekœcie bêdziemy potrzebowali 2*d=12 bitów<br />
reszta okreœla ile bitów zosta³o nie wykorzystanych<br />
0<=reszta<8, mo¿na zapisaæ t¹ wartoœæ na 3 bitach
r=8-(3+d*n)%8

wyjœcie: <br />
char(wielkosc slownika), char(A), char(B), char(C), 3bit(r), tekst kodowany s³ownikiem, r-bitów uzupe³niaj¹cych

