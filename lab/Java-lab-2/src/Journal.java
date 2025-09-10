import java.util.*;
import java.text.*;

// Сутність "Запис в журналі куратора"
class JournalCurator {
    private  String surname;
    private String name;
    private Date birthDate;
    private String phone;
    private String street;
    private int house;
    private int flat;

    public JournalCurator(String surname, String name, Date birthDate,
                        String phone, String street, int house, int flat) {
        this.surname = surname;
        this.name = name;
        this.birthDate = birthDate;
        this.phone = phone;
        this.street = street;
        this.house = house;
        this.flat = flat;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return String.format("%s %s, Дата нар.: %s, Телефон: %s, Адреса: вул. %s, %d-%d",
                surname, name, sdf.format(birthDate), phone, street, house, flat);
    }
}

// Утиліти для перевірки правильності введення
class Validator {
    public static boolean isNameValid(String s) {
        return s.matches("[А-Яа-яA-Za-z]+");
    }

    public static Date parseDate(String s) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            sdf.setLenient(false);
            return sdf.parse(s);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean isPhoneValid(String s) {
        return s.matches("\\d{10,12}"); // тільки цифри, довжина 7-15
    }

    public static boolean isStreetValid(String s) {
        return s.matches("[А-Яа-яA-Za-z\\s]+");
    }

    public static boolean isPositiveNumber(String s) {
        return s.matches("\\d+");
    }
}

// Основний застосунок
public class Journal {
    private static Scanner sc = new Scanner(System.in);
    private static List<JournalCurator> journal = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n ЖУРНАЛ КУРАТОРА ");
            System.out.println("1. Додати запис");
            System.out.println("2. Переглянути записи");
            System.out.println("3. Вихід");
            System.out.print("Виберіть пункт: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1" -> addEntry();
                case "2" -> showEntries();
                case "3" -> {
                    System.out.println("Програму завершено.");
                    return;
                }
                default -> System.out.println("Невірний вибір! Спробуйте ще раз.");
            }
        }
    }

    // Додавання запису з перевіркою
    private static void addEntry() {
        System.out.println("\n Додавання запису...");

        String surname = inputUntilValid("Прізвище: ", Validator::isNameValid);
        String name = inputUntilValid("Ім'я: ", Validator::isNameValid);

        Date birthDate = null;
        while (birthDate == null) {
            System.out.print("Дата народження (dd.MM.yyyy): ");
            birthDate = Validator.parseDate(sc.nextLine());
            if (birthDate == null) System.out.println("❌ Неправильний формат дати!");
        }

        String phone = inputUntilValid("Телефон (10-12 цифр): ", Validator::isPhoneValid);
        String street = inputUntilValid("Вулиця: ", Validator::isStreetValid);
        int house = Integer.parseInt(inputUntilValid("Будинок: ", Validator::isPositiveNumber));
        int flat = Integer.parseInt(inputUntilValid("Квартира: ", Validator::isPositiveNumber));

        JournalCurator entry = new JournalCurator(surname, name, birthDate, phone, street, house, flat);
        journal.add(entry);
        System.out.println("✅ Запис успішно додано!");
    }

    // Виведення всіх записів
    private static void showEntries() {
        System.out.println("\n--- Записи в журналі ---");
        if (journal.isEmpty()) {
            System.out.println("Журнал порожній.");
        } else {
            for (int i = 0; i < journal.size(); i++) {
                System.out.println((i + 1) + ". " + journal.get(i));
            }
        }
    }

    // Загальна функція для циклічного введення до коректного значення
    private static String inputUntilValid(String prompt, java.util.function.Predicate<String> validator) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (validator.test(input)) return input;
            System.out.println("❌ Невірний формат! Спробуйте ще раз.");
        }
    }
}
