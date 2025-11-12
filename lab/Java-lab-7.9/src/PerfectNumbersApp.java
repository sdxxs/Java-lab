import java.util.*;
import java.util.stream.*;

// Task #9: Знайти всі досконалі числа в діапазоні [1..n]. */
public final class PerfectNumbersApp {

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Введіть додатне ціле n (1..100_000_000): ");
            String raw = sc.nextLine();

            int n = InputValidator.parsePositiveInt(raw);
            InputValidator.validateRange(n, 1, 100_000_000);

            PerfectNumberService service = new PerfectNumberService();
            PerfectNumbersResult res = service.findInRange(n);

            System.out.println("Досконалі числа від 1 до " + res.n() + ": " + Arrays.toString(res.numbers()));
            System.out.println("Кількість: " + res.numbers().length + ", час: " + res.elapsedMillis() + " мс");
        } catch (UserInputException e) {
            // Помилки користувацького вводу (не валідне число, вихід за межі тощо)
            System.err.println("Помилка вводу: " + e.getMessage());
        } catch (ArithmeticException e) {
            // Малоймовірне переповнення/арифметична помилка
            System.err.println("Арифметична помилка: " + e.getMessage());
        } catch (Exception e) {
            // Непередбачені помилки
            System.err.println("Непередбачена помилка: " + e.getMessage());
        }
    }


     // n верхня межа пошуку (включно)
     // numbers відсортований масив знайдених досконалих чисел
     // elapsedMillis час виконання, мс

    public record PerfectNumbersResult(int n, int[] numbers, long elapsedMillis) { }

    public static final class InputValidator {
        private InputValidator() {}


        public static int parsePositiveInt(String s) {
            Objects.requireNonNull(s, "рядок не може бути null");
            s = s.trim();
            if (s.isEmpty()) throw new UserInputException("Порожній ввід. Очікується додатне ціле число.");
            if (!s.matches("[+]?\\d+")) throw new UserInputException("Некоректний формат числа: \"" + s + "\".");

            try {
                long v = Long.parseLong(s);
                if (v < 1L || v > Integer.MAX_VALUE)
                    throw new UserInputException("Число має бути в межах 1.." + Integer.MAX_VALUE + ".");
                return (int) v;
            } catch (NumberFormatException ex) {
                throw new UserInputException("Занадто велике число або некоректний ввід.");
            }
        }

        //Перевіряє, що значення у межах [min..max].

        public static void validateRange(int value, int min, int max) {
            if (value < min || value > max) {
                throw new UserInputException("Значення " + value + " виходить за допустимий діапазон [" + min + ".." + max + "].");
            }
        }
    }


    public static class UserInputException extends RuntimeException {
        public UserInputException(String message) { super(message); }
    }


    //Усередині застосовується оптимізований підрахунок суми власних дільників через корінь.

    public static final class PerfectNumberService {


        public PerfectNumbersResult findInRange(int n) {
            long t0 = System.nanoTime();

            if (n < 2) return new PerfectNumbersResult(n, new int[0], elapsedMillis(t0));

            // Евристика: для великих меж — паралелимо.
            IntStream base = IntStream.rangeClosed(2, n);
            IntStream stream = (n >= 200_000) ? base.parallel() : base.sequential();

            int[] perfects = stream
                    .filter(this::isPerfect)      // lambda-предикат досконалості
                    .sorted()                      // гарантуємо порядок для паралельного випадку
                    .toArray();

            return new PerfectNumbersResult(n, perfects, elapsedMillis(t0));
        }

        //Перевірка досконалості числа: сума власних дільників == n.

        private boolean isPerfect(int n) {
            if (n <= 1) return false;

            // 1 — завжди власний дільник (для n>1). Додаємо решту через стрім.
            long sum = 1L + IntStream
                    .rangeClosed(2, (int) Math.sqrt(n))
                    .filter(d -> n % d == 0)
                    .mapToLong(d -> {
                        int q = n / d;
                        // Якщо d == q (d — корінь), додаємо один раз; інакше додаємо обидві пари.
                        return (d == q) ? d : (long) d + q;
                    })
                    .sum();

            // Захист від переповнення — хоч тут і long, але перевіримо верхню межу.
            if (sum > Integer.MAX_VALUE && sum != n) {
                throw new ArithmeticException("Проміжна сума дільників перевищила Integer.MAX_VALUE для n=" + n);
            }
            return sum == n;
        }

        private static long elapsedMillis(long t0) {
            return (System.nanoTime() - t0) / 1_000_000L;
        }
    }
}
