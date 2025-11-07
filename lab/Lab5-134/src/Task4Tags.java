//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Task4Tags {

    public static String fetchHtml(String url) throws IOException {
        StringBuilder sb = new StringBuilder(32768);

        String line;
        try (BufferedReader br = new BufferedReader(new InputStreamReader((new URL(url)).openStream(), StandardCharsets.UTF_8))) {
            while((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }
        }
        return sb.toString();
    }

    public static Map<String, Integer> countTags(String html) {
        Map<String, Integer> freq = new HashMap();
        String noComments = html.replaceAll("(?s)<!--.*?-->", "");
        Pattern p = Pattern.compile("<\\s*([a-zA-Z0-9]+)(\\s[^>]*)?>");
        Matcher m = p.matcher(noComments);

        while(m.find()) {
            String tag = m.group(1).toLowerCase(Locale.ROOT);
            if (!tag.startsWith("/")) {
                freq.merge(tag, 1, Integer::sum);
            }
        }
        return freq;
    }

    public static class TagStats implements Serializable {
        public final String url;
        public final Map<String, Integer> frequencies;

        public TagStats(String url, Map<String, Integer> frequencies) {
            this.url = url;
            this.frequencies = frequencies;
        }
    }
}
