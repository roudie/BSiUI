package source;

public class CaesarCipherWithBitNegation {
    private final int firstSmallLetter = 'a';
    private final int lastSmallLetter = 'z';
    private final int firstBigLetter = 'A';
    private final int lastBigLetter = 'Z';

    public String Encrypt(String text, int key) {
        StringBuilder stringBuilder = new StringBuilder();
        if(key>26)
            key = key%26;
        if(key<0)
            key = key%26+26;
        for (int i=0; i<text.length();i++) {
            char letter = text.charAt(i);
            if(lastSmallLetter >=letter && letter>= firstSmallLetter) {
                stringBuilder.append((char)(255-(firstSmallLetter + (letter+key - firstSmallLetter)%26)));
            } else if (lastBigLetter >=letter && letter>= firstBigLetter){
                stringBuilder.append((char)(255-(firstBigLetter + (letter+key - firstBigLetter)%26)));
            } else {
                stringBuilder.append((char)(255-letter));
            }
        }
        return stringBuilder.toString();
    }

    public String Decode(String text, int key) {
        StringBuilder stringBuilder = new StringBuilder();
        key=-key;
        if(key>26)
            key = key%26;
        if(key<0)
            key = key%26+26;
        for (int i=0; i<text.length();i++) {
            char letter = (char) (255-text.charAt(i));

            if(lastSmallLetter >=letter && letter>= firstSmallLetter) {
                stringBuilder.append((char)((firstSmallLetter + (letter+key - firstSmallLetter)%26)));
            } else if (lastBigLetter >=letter && letter>= firstBigLetter){
                stringBuilder.append((char)((firstBigLetter + (letter+key - firstBigLetter)%26)));
            } else {
                stringBuilder.append(letter);
            }
        }
        return stringBuilder.toString();
    }
}
