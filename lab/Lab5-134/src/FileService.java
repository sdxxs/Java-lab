//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;

public final class FileService {
    private FileService() {
    }

    public static void saveObject(Serializable obj, Path out) throws IOException {
        Files.createDirectories(out.getParent() != null ? out.getParent() : out.toAbsolutePath().getParent());

        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(out))) {
            oos.writeObject(obj);
        }

    }

    public static <T> T loadObject(Path in) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(in))) {
            return (T)ois.readObject();
        }
    }
}
