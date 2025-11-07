//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public final class Task1MaxWords {
    public static MaxLineResult findMaxWordsLine(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        int bestIdx = -1;
        int bestCount = -1;

        for(int i = 0; i < lines.size(); ++i) {
            int wc = countWords((String)lines.get(i));
            if (wc > bestCount) {
                bestCount = wc;
                bestIdx = i;
            }
        }

        String bestLine = bestIdx >= 0 ? (String)lines.get(bestIdx) : "";
        return new MaxLineResult(bestIdx + 1, bestCount, bestLine, path.toString());
    }

    private static int countWords(String s) {
        if (s == null) {
            return 0;
        } else {
            String trimmed = s.trim();
            return trimmed.isEmpty() ? 0 : trimmed.split("\\s+").length;
        }
    }
}
