import java.util.*;

public class PerfectNumbers {

    // Функція для пошуку досконалих чисел
    public static List<Integer> findPerfectNumbers(int n) {
        List<Integer> perfects = new ArrayList<>();

        for (int i = 2; i <= n; i++) {
            int sum = 1; // 1 завжди дільник
            for (int d = 2; d <= i / 2; d++) {
                if (i % d == 0) {
                    sum += d;
                }
            }
            if (sum == i) {
                perfects.add(i);
            }
        }

        return perfects;
    }

    // Тестування
    public static void main(String[] args) {
         int n;
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Введіть число n: ");
            n = sc.nextInt();
        }

        List<Integer> result = findPerfectNumbers(n);
        System.out.println("Досконалі числа від 1 до " + n + ": " + result);
    }
}
