//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public final class Task3Crypto {
    private Task3Crypto() {
    }

    public static void encryptFile(Path in, Path out, char key) throws IOException {
        try (
                InputStream fis = Files.newInputStream(in);
                OutputStream fos = Files.newOutputStream(out);
                ShiftFilterOutputStream enc = new ShiftFilterOutputStream(fos, key);
        ) {
            fis.transferTo(enc);
        }

    }

    public static void decryptFile(Path in, Path out, char key) throws IOException {
        try (
                InputStream fis = Files.newInputStream(in);
                ShiftFilterInputStream dec = new ShiftFilterInputStream(fis, key);
                OutputStream fos = Files.newOutputStream(out);
        ) {
            dec.transferTo(fos);
        }

    }

    public static class ShiftFilterOutputStream extends FilterOutputStream {
        private final int key;

        public ShiftFilterOutputStream(OutputStream out, char keyChar) {
            super(out);
            this.key = keyChar & 255;
        }

        public void write(int b) throws IOException {
            int enc = (b & 255) + this.key & 255;
            this.out.write(enc);
        }

        public void write(byte[] b, int off, int len) throws IOException {
            for(int i = off; i < off + len; ++i) {
                int enc = (b[i] & 255) + this.key & 255;
                b[i] = (byte)enc;
            }

            this.out.write(b, off, len);
        }
    }

    public static class ShiftFilterInputStream extends FilterInputStream {
        private final int key;

        public ShiftFilterInputStream(InputStream in, char keyChar) {
            super(in);
            this.key = keyChar & 255;
        }

        public int read() throws IOException {
            int r = this.in.read();
            return r < 0 ? r : (r & 255) - this.key & 255;
        }

        public int read(byte[] b, int off, int len) throws IOException {
            int n = this.in.read(b, off, len);
            if (n <= 0) {
                return n;
            } else {
                for(int i = off; i < off + n; ++i) {
                    int dec = (b[i] & 255) - this.key & 255;
                    b[i] = (byte)dec;
                }

                return n;
            }
        }
    }
}
