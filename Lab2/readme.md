# Komunikator z szyfrowaniem
Implementacja serwera pochodzi z https://github.com/lukaszsus/encryptedChat

zalozenia zadania w pliku zadanie.txt

- Protokol REG oraz LOG haslo hashowane md5
- Protokol TEXT szyfrowany:
	 - szyfr vigenere- klucz to zlaczenie naprzemiennie liter z loginu osoby wysylajacej oraz loginu osoby do ktorej wyslano wiadomosc, np "marek"+"zbigniew"->"mzabriegkniew". Wszystkie znaki w kluczu nie bedace literami zostaja zamienione na 'a'. 
	 - XOR- szyfrowanie wedlug klucza opisanego wyzej bez zmieniania znakow nie bedacych literami
	 - Transpozycja macierzy
	  > wejscie: abcd123456789
	  > a b c d
	  > 1 2 3 4
	  > 5 6 7 8
	  > 9
	  > wynik szyfrowania odczytujemy pionowo 
	  > wyjscie: a159b26c37d48