import java.util.*;
import java.text.*;


class JournalEntry {
    private String surname;
    private String name;
    private Date birthDate;
    private String phone;
    private String street;
    private int house;
    private int flat;

    public JournalEntry(String surname, String name, Date birthDate,
                        String phone, String street, int house, int flat) {
        this.surname = surname;
        this.name = name;
        this.birthDate = birthDate;
        this.phone = phone;
        this.street = street;
        this.house = house;
        this.flat = flat;
    }

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    public String toString() {
        return String.format("%s %s, Дата нар.: %s, Телефон: %s, Адреса: вул. %s,Буд. %d, кв. %d",
                surname, name, sdf.format(birthDate), phone, street, house, flat);
    }
}

// Перевірка правильності введення
class Validator {
    public static boolean isNameValid(String s) {
        return s.matches("[А-Яа-яA-Za-z'ійє\\-]+");
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
    public static boolean isValidDate(String s) {
        return parseDate(s) != null;
    }

    public static boolean isPhoneValid(String s) {
        return s.matches("\\+\\d{7,15}"); // тільки цифри, довжина 7-15
    }

    public static boolean isStreetValid(String s) {
        return s.matches("[А-Яа-яA-Za-z0-9'\\-\\.\\s]+");
    }

    public static boolean isPositiveNumber(String s) {
        try {
            int n = Integer.parseInt(s);
            return n >= 0 && n < 10000; //  обмеження
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
    // Основна програма
    public class Journal {
        private static Scanner sc = new Scanner(System.in);
        private static List<JournalEntry> journal = new ArrayList<>();

        public static void main(String[] args) {
            while (true) {
                System.out.println("\n===== ЖУРНАЛ КУРАТОРА =====");
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
            System.out.println("\n--- Додавання запису ---");

            String surname = inputUntilValid("Прізвище: ", Validator::isNameValid);
            String name = inputUntilValid("Ім'я: ", Validator::isNameValid);

            String dateStr = inputUntilValid("Дата народження (dd.MM.yyyy): ", Validator::isValidDate);
            Date birthDate = Validator.parseDate(dateStr);

            String phone = inputUntilValid("Телефон (7-15 цифр): ", Validator::isPhoneValid);
            String street = inputUntilValid("Вулиця: ", Validator::isStreetValid);
            int house = Integer.parseInt(inputUntilValid("Будинок: ", Validator::isPositiveNumber));
            int flat = Integer.parseInt(inputUntilValid("Квартира(0 якщо це будинок): ", Validator::isPositiveNumber));

            JournalEntry entry = new JournalEntry(surname, name, birthDate, phone, street, house, flat);
            journal.add(entry);
            System.out.println(" Запис успішно додано!");
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

        // Функція для циклічного введення при неправильному вводі
        private static String inputUntilValid(String prompt, java.util.function.Predicate<String> validator) {
            String input;
            do {
                System.out.print(prompt);
                input = sc.nextLine();
                if (!validator.test(input)) {
                    System.out.println("Невірний ввід, спробуйте ще раз.");
                }
            } while (!validator.test(input));
            return input;
        }
    }

