package source;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        menu();
    }

    private static void menu(){
        Scanner scanner = new Scanner(System.in);
        boolean end = false;
        do {
            System.out.println("1. Szyfrowanie cezara małych i dużych liter z negacją bitową");
            System.out.println("2. Kompresja pliku input.txt");
            System.out.println("3. Generowanie pliku input.txt");
            switch (scanner.next()) {
                case "1":
                    caesarMenu();
                    break;
                case "2":
                    compresionMenu();
                    break;
                case "3":
                    generationMenu();
                    break;
                default:
                    end = true;
            }
        } while (!end);
    }

    private static void generationMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj znaki zbiór znaków:");
        String text = scanner.nextLine();
        System.out.println("Podaj wielkosc pliku w kb");
        int size = scanner.nextInt();
        try {
            FileOutputStream fileWriter = new FileOutputStream("input.txt");
            int i = 0;
            Random rand = new Random();
            while (i<=size*1024) {
                fileWriter.write(text.charAt(rand.nextInt(text.length())));
                i++;
            }
            fileWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void compresionMenu() {
        DictionaryCompression dictionaryCompression = new DictionaryCompression();
        dictionaryCompression.Compression("input.txt", "encoded.txt");
        dictionaryCompression.Decode("encoded.txt", "decoded.txt");
    }

    private static void caesarMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Wprowadź tekst do zaszyfrowania:");
        String text = scanner.nextLine();
        System.out.println("Podaj klucz:");
        int key = scanner.nextInt();
        CaesarCipherWithBitNegation caesarCipherWithBitNegation = new CaesarCipherWithBitNegation();
        String encryptedText = caesarCipherWithBitNegation.Encrypt(text, key);
        System.out.println(encryptedText);
        System.out.println("Po deszyfracji:");
        System.out.println(caesarCipherWithBitNegation.Decode(encryptedText, key));
    }
}
