import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TranslatorApp {

    static class Translator {
        private final Map<String, String> dict = new HashMap<>();
        private int maxKeyWords = 1;

        // Регулярні вирази для перевірки мови
        private static final Pattern ENGLISH_PATTERN = Pattern.compile("^[a-zA-Z\\s']+$");
        private static final Pattern UKRAINIAN_PATTERN = Pattern.compile("^[а-щА-ЩЬьЮюЯяЇїІіЄєҐґ\\s'-]+$");

        // Метод додавання пари з перевіркою
        public void add(String en, String uk) {
            if (en == null || uk == null) return;
            en = en.trim();
            uk = uk.trim();

            // Перевіряємо, що слова належать своїй мові
            if (!ENGLISH_PATTERN.matcher(en).matches()) {
                System.out.println("⚠️ Помилка: англійська фраза повинна містити лише латинські літери!");
                return;
            }
            if (!UKRAINIAN_PATTERN.matcher(uk).matches()) {
                System.out.println("⚠️ Помилка: український переклад повинен містити лише українські літери!");
                return;
            }

            // Якщо все добре — додаємо
            en = en.toLowerCase(Locale.ROOT);
            dict.put(en, uk);
            int words = en.split("\\s+").length;
            if (words > maxKeyWords) maxKeyWords = words;
            System.out.println("✅ Додано: " + en + " → " + uk);
        }

        private static boolean isCapitalized(String w) {
            return w.length() > 0 && Character.isUpperCase(w.codePointAt(0));
        }

        private static String capitalizeFirst(String s) {
            if (s.isEmpty()) return s;
            int cp = s.codePointAt(0);
            int[] rest = s.codePoints().skip(1).toArray();
            return new String(new int[]{Character.toUpperCase(cp)}, 0, 1)
                    + new String(rest, 0, rest.length);
        }

        public String translatePhrase(String text) {
            if (text == null || text.isEmpty()) return text;

            Pattern word = Pattern.compile("\\b[\\p{Alpha}']+\\b");
            Matcher m = word.matcher(text);
            final List<int[]> spans = new ArrayList<>();
            final List<String> words = new ArrayList<>();
            while (m.find()) {
                spans.add(new int[]{m.start(), m.end()});
                words.add(m.group());
            }

            StringBuilder out = new StringBuilder(text.length());
            int lastOutPos = 0;
            boolean missingWord = false;

            for (int i = 0; i < words.size(); ) {
                int bestLen = 0;
                String bestTr = null;

                int maxLen = Math.min(maxKeyWords, words.size() - i);
                for (int len = maxLen; len >= 1; len--) {

                    boolean contiguous = true;
                    for (int k = i; k < i + len - 1; k++) {
                        int gapStart = spans.get(k)[1];
                        int gapEnd = spans.get(k + 1)[0];
                        String gap = text.substring(gapStart, gapEnd);
                        if (!gap.chars().allMatch(Character::isWhitespace)) {
                            contiguous = false; break;
                        }
                    }
                    if (!contiguous) continue;

                    StringBuilder key = new StringBuilder();
                    for (int k = 0; k < len; k++) {
                        if (k > 0) key.append(' ');
                        key.append(words.get(i + k).toLowerCase(Locale.ROOT));
                    }
                    String tr = dict.get(key.toString());
                    if (tr != null) {
                        bestLen = len;
                        bestTr = tr;
                        break;
                    }
                }

                if (bestLen > 0) {
                    int start = spans.get(i)[0];
                    int end = spans.get(i + bestLen - 1)[1];
                    out.append(text, lastOutPos, start);

                    String firstWord = words.get(i);
                    String finalTr = isCapitalized(firstWord) ? capitalizeFirst(bestTr) : bestTr;
                    out.append(finalTr);

                    lastOutPos = end;
                    i += bestLen;
                } else {
                    int start = spans.get(i)[0];
                    int end = spans.get(i)[1];
                    out.append(text, lastOutPos, start);

                    String w = words.get(i);
                    String tr = dict.get(w.toLowerCase(Locale.ROOT));
                    if (tr != null) {
                        out.append(isCapitalized(w) ? capitalizeFirst(tr) : tr);
                    } else {
                        out.append(w);
                        missingWord = true;
                    }

                    lastOutPos = end;
                    i++;
                }
            }
            out.append(text, lastOutPos, text.length());

            if (missingWord) {
                out.append("\n⚠️ Деякі слова не знайдено у довіднику.");
            }

            return out.toString();
        }

        public void fillDefaults() {
            add("hello", "привіт");
            add("world", "світ");
            add("how are you", "як ти");
            add("good morning", "доброго ранку");
            add("good evening", "доброго вечора");
            add("thank you", "дякую");
            add("please", "будь ласка");
            add("and", "і");
            add("you", "ти");
            add("i", "я");
        }

        public void printDict() {
            System.out.println("Словник (" + dict.size() + " записів):");
            dict.entrySet().stream().sorted(Map.Entry.comparingByKey())
                    .forEach(e -> System.out.println("  " + e.getKey() + " -> " + e.getValue()));
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Translator tr = new Translator();
        tr.fillDefaults();

        System.out.println("Простий перекладач EN→UK (HashMap) з підтримкою багатослівних фраз.");
        while (true) {
            System.out.println("""
                    
                    Меню:
                    1) Показати словник
                    2) Додати слово/фразу (англ → укр)
                    3) Додати кілька пар
                    4) Перекласти фразу
                    0) Вихід
                    Ваш вибір:""");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1" -> tr.printDict();
                case "2" -> {
                    System.out.print("Англійське слово/фраза: ");
                    String en = sc.nextLine();
                    System.out.print("Український переклад: ");
                    String uk = sc.nextLine();
                    tr.add(en, uk);
                }
                case "3" -> {
                    System.out.println("Введи пари через кому. Приклад: good morning=доброго ранку, thank you=дякую");
                    String line = sc.nextLine();
                    String[] pairs = line.split(",");
                    int ok = 0;
                    for (String p : pairs) {
                        String[] kv = p.split("=", 2);
                        if (kv.length == 2) {
                            tr.add(kv[0].trim(), kv[1].trim());
                            ok++;
                        }
                    }
                    System.out.println("✅ Спроба додати пар: " + ok);
                }
                case "4" -> {
                    System.out.println("Введи англійську фразу:");
                    String phrase = sc.nextLine();
                    String translated = tr.translatePhrase(phrase);
                    System.out.println("Переклад:\n" + translated);
                }
                case "0" -> {
                    System.out.println("Готово.");
                    return;
                }
                default -> System.out.println("Невірний вибір, спробуй ще.");
            }
        }
    }
}
