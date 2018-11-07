package source;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BinaryOutputFileStream {
    FileOutputStream outputStream;
    int buffor;
    int n = 0;

    public BinaryOutputFileStream(String fileName) throws FileNotFoundException {
        outputStream = new FileOutputStream(fileName);
    }

    public void Write(boolean x) {
        buffor<<=1;
        if(x)
            buffor |= 1;

        n++;
        if(n == 8)
            clearBuffor();
    }

    public void Write(byte x) {
        if (n==0) {
            byte[] bytes = {x};
            try {
                outputStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            for (int i = 0; i < 8; i++) {
                boolean bit = ((x >>> (8 - i - 1)) & 1) == 1;
                Write(bit);
            }
        }
    }

    private void clearBuffor() {
        if(n == 0)
            return;
        buffor <<=(8-n);
        try {
            outputStream.write(buffor);
        } catch (IOException e) {
            e.printStackTrace();
        }
        n = 0;
        buffor = 0;
    }
}
