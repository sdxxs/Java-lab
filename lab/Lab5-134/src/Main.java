//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Scanner SC;

    public static void main(String[] args) {
        System.out.println("Лабораторна: файли, потоки, шифр, тести тегів");

        while(true) {
            System.out.println("\nМеню:\n  1) Рядок з максимальною кількістю слів у файлі\n  2) Шифрування файлу (FilterOutputStream)\n  3) Дешифрування файлу (FilterInputStream)\n  4) Частоти HTML-тегів з URL\n  5) Вихід\nВаш вибір:");
            String choice = SC.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        taskMaxWordsLine();
                        break;
                    case "2":
                        taskEncrypt();
                        break;
                    case "3":
                        taskDecrypt();
                        break;
                    case "4":
                        taskTags();
                        break;
                    case "5":
                        System.out.println("Готово.");
                        return;
                    default:
                        System.out.println("Невірний вибір, спробуй ще.");
                }
            } catch (Exception ex) {
                System.out.println("Помилка: " + ex.getMessage());
                ex.printStackTrace(System.out);
            }
        }
    }

    private static void taskMaxWordsLine() {
        System.out.print("Вкажіть шлях до текстового файлу (вхід): ");
        Path in = Paths.get(SC.nextLine().trim());
        if (!Files.isRegularFile(in, new LinkOption[0])) {
            System.out.println("Файл не знайдено.");
        } else {
            try {
                MaxLineResult res = Task1MaxWords.findMaxWordsLine(in);
                System.out.println("\nНайбільше слів: " + res.wordCount);
                System.out.println("Рядок № " + res.lineNumber + ":");
                System.out.println(res.lineText);
                System.out.print("\nЗберегти результат (серіалізація) у файл .bin? Вкажіть шлях або Enter щоб пропустити: ");
                String out = SC.nextLine().trim();
                if (!out.isEmpty()) {
                    FileService.saveObject(res, Paths.get(out));
                    System.out.println("Збережено: " + out);
                }
            } catch (IOException e) {
                System.out.println("Помилка читання файлу: " + e.getMessage());
            }

        }
    }

    private static void taskEncrypt() {
        System.out.print("Шлях вхідного файлу (plain): ");
        Path in = Paths.get(SC.nextLine().trim());
        System.out.print("Шлях вихідного файлу (encrypted): ");
        Path out = Paths.get(SC.nextLine().trim());
        char key = readKeyChar();

        try {
            Task3Crypto.encryptFile(in, out, key);
            System.out.println("Зафіксовано: " + String.valueOf(out.toAbsolutePath()));
        } catch (IOException e) {
            System.out.println("Помилка під час шифрування: " + e.getMessage());
        }

    }

    private static void taskDecrypt() {
        System.out.print("Шлях вхідного файлу (encrypted): ");
        Path in = Paths.get(SC.nextLine().trim());
        System.out.print("Шлях вихідного файлу (decrypted): ");
        Path out = Paths.get(SC.nextLine().trim());
        char key = readKeyChar();

        try {
            Task3Crypto.decryptFile(in, out, key);
            System.out.println("Розшифровано: " + String.valueOf(out.toAbsolutePath()));
        } catch (IOException e) {
            System.out.println("Помилка під час дешифрування: " + e.getMessage());
        }

    }

    private static void taskTags() {
        System.out.print("URL (Enter для прикладу): ");
        String url = SC.nextLine().trim();
        if (url.isEmpty()) {
            url = "https://smarthistory.org/knossos/?sidebar=europe-before-1000-b-c-e";
        }

        try {
            String html = Task4Tags.fetchHtml(url);
            Map<String, Integer> freq = Task4Tags.countTags(html);
            System.out.print("Зберегти результат (серіалізація) у файл .bin? Вкажіть шлях або Enter щоб пропустити: ");
            String save = SC.nextLine().trim();
            if (!save.isEmpty()) {
                FileService.saveObject(new Task4Tags.TagStats(url, freq), Paths.get(save));
                System.out.println("Збережено: " + save);
            }

            System.out.println("\n(a) Лексикографічно (tag ↑):");
            freq.keySet().stream().sorted()
                    .forEach(tag -> System.out.printf("%-15s %d%n", tag, freq.get(tag)));

            System.out.println("\n(b) За частотою (freq ↑), при рівності — lex:");
            freq.entrySet().stream()
                    .sorted(Comparator.<Map.Entry<String,Integer>,Integer>comparing(Map.Entry::getValue)
                            .thenComparing(Map.Entry::getKey))
                    .forEach(e -> System.out.printf("%-15s %d%n", e.getKey(), e.getValue()));

        } catch (IOException e) {
            System.out.println("Помилка завантаження/аналізу: " + e.getMessage());
        }

    }

    private static char readKeyChar() {
        while(true) {
            System.out.print("Введіть КЛЮЧ-символ (перший символ рядка використовується як ключ): ");
            String s = SC.nextLine();
            if (s != null && !s.isEmpty()) {
                return s.charAt(0);
            }

            System.out.println("Порожній ключ — спробуйте ще.");
        }
    }

    static {
        SC = new Scanner(System.in);
    }
}
