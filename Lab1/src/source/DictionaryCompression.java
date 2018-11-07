package source;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DictionaryCompression {
    private HashMap<Character, Integer> dictionary;
    private long textLenght = 0;
    private int bitAmountPerChar = 0;
    private int rest = 0;
    private static final int buffSize = 512;


    public void Decode(String inputFile, String outputFile){
        //dictionary = null;
        HashMap<String, Character> dictionary = new HashMap<String, Character>();
        FileInputStream fileReader = null;
        FileOutputStream fileWriter = null;
        rest = 8;
        try {
            fileReader = new FileInputStream(inputFile);
            fileWriter = new FileOutputStream(outputFile);
            int size=fileReader.read();
            bitAmountPerChar = (int)Math.ceil(Math.log(size)/Math.log(2));
            String bitsPerChar ="";
            for (int i = 0; i<bitAmountPerChar;i++){
                bitsPerChar+="0";
            }
            int buffor;
            for(int i = 0; i<size; i++) {
                buffor=fileReader.read();
                String buffString = Integer.toBinaryString(i);
                buffString=bitsPerChar.substring(buffString.length()) + buffString;
                dictionary.put(buffString, (char)buffor);
            }

            System.out.println("Słownik");
            for (Map.Entry<String, Character> pair : dictionary.entrySet())
            {
                System.out.println(pair.getKey() +" "+ pair.getValue());
            }

            String bufforString = "";

            while ((buffor=fileReader.read())!=-1) {
                String x = Integer.toBinaryString((char)buffor);
                bufforString += "00000000".substring(x.length())+x;
                if(bufforString.length()>=200) {
                    bufforString = cleanOutputDecompr(bufforString, fileReader, fileWriter, dictionary);
                }
            }

            cleanOutputDecompr(bufforString, fileReader, fileWriter, dictionary);
            fileWriter.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String cleanOutputDecompr(String bufforString, FileInputStream fileReader, FileOutputStream fileWriter, HashMap<String, Character> dictionary) throws IOException {

        if(fileReader.available()>=1)
        {
            if(rest==8) {
                rest = Integer.parseInt(bufforString.substring(0, 3), 2);
                bufforString = bufforString.substring(3);
            }
            while (bufforString.length()>=bitAmountPerChar) {
                String x = bufforString.substring(0, bitAmountPerChar);
                bufforString=bufforString.substring(bitAmountPerChar);
                char y = dictionary.get(x);
                fileWriter.write(y);
            }
            return bufforString;
        }
        else {
            if(rest==8) {
                rest = Integer.parseInt(bufforString.substring(0, 3), 2);
                bufforString = bufforString.substring(3);
            }
            bufforString = bufforString.substring(0, bufforString.length()-rest);
            while (bufforString.length()>=bitAmountPerChar) {
                String x = bufforString.substring(0, bitAmountPerChar);
                bufforString=bufforString.substring(bitAmountPerChar);
                char y = dictionary.get(x);
                fileWriter.write(y);
            }
            return bufforString;
        }
    }

    public void Compression(String inputFile, String outputFile) {
        dictionary = null;
        textLenght = 0;
        bitAmountPerChar = 0;
        rest = 0;
        try {
            makeDictionary(inputFile);

            System.out.println("Długość tekstu: " + textLenght);
            System.out.println("Reszta: " + rest);
            System.out.println("Wielkość słownika: " + dictionary.size());
            System.out.println("Bity na znak: "+ bitAmountPerChar);
//
            System.out.println("Słownik");
            for (HashMap.Entry<Character, Integer> pair : dictionary.entrySet())
            {
                System.out.println(pair.getKey() +" "+ pair.getValue());
            }
            CompresionToFile(inputFile, outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void CompresionToFile(String inputFile, String outputFile) throws IOException {
        // wiekosc slownika
        String outputBuffor = Integer.toBinaryString(dictionary.size());
        outputBuffor="00000000".substring(outputBuffor.length()) + outputBuffor;

        // slownik
        for(int i = 0; i < dictionary.size(); i++) {
            char x = (char) getKeyFromValue(dictionary, i);
            String y = Integer.toBinaryString(x);
            y = "00000000".substring(y.length()) + y;
            outputBuffor+=y;
        }

        // reszta
        String restString= Integer.toBinaryString(rest);
        restString="000".substring(restString.length()) + restString;
        outputBuffor+=restString;

        String bitsPerChar ="";
        for (int i = 0; i<bitAmountPerChar;i++){
            bitsPerChar+="0";
        }

        FileReader fileReader = null;
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(outputFile);
            fileReader = new FileReader(inputFile);
            int buffor;

            while ((buffor=fileReader.read())!=-1) {
                String buffString = Integer.toBinaryString(dictionary.get((char)buffor));
                buffString=bitsPerChar.substring(buffString.length()) + buffString;
                outputBuffor+=(buffString);
                if(outputBuffor.length()>200) {
                    outputBuffor = cleanOutputCompression(stream, outputBuffor);
                }
            }
            for(int i = 0; i<rest;i++) {
                outputBuffor+="1";
            }
            cleanOutputCompression(stream, outputBuffor);

        } finally {
            if (fileReader != null) {
                fileReader.close();
            }
            if (stream != null) {
                stream.close();
            }
        }
    }

    public static Object getKeyFromValue(Map hm, Object value) {
        for (Object o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }

    private String cleanOutputCompression(FileOutputStream stream, String outputBuffor) throws IOException {
        while (outputBuffor.length()>=8) {
            String x = outputBuffor.substring(0, 8);
            char z = (char)Integer.parseInt(x, 2);
            byte y = (byte)z;
            stream.write(y);
            outputBuffor = outputBuffor.substring(8);
        }
        return outputBuffor;
    }

    private void makeDictionary(String inputFile) throws IOException {
        int i = 0;
        FileReader fileReader = null;
        dictionary = new HashMap<>();

            fileReader = new FileReader(inputFile);
            int buffor;
            while ((buffor=fileReader.read())!=-1) {
                textLenght++;
                if(!dictionary.containsKey((char)buffor)) {
                    dictionary.put((char) buffor, i++);
                }
            }
            bitAmountPerChar = (int)Math.ceil(Math.log(dictionary.size())/Math.log(2));
            rest = (int) ((8 - (3 + textLenght * (long)bitAmountPerChar) % 8) % 8);


        if (fileReader != null) {
            fileReader.close();
        }
    }
}
