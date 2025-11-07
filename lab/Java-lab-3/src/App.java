import java.util.List;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        List<Shape> ShapeList = new ArrayList<>();

        Shape rect1 = new Rectangle("червоний", 5, 4);
        ShapeList.add(rect1);
        Shape rect2 = new Rectangle("синій", 10, 8);
        ShapeList.add(rect2);
        Shape rect3 = new Rectangle("коричневий", 7.5f, 6);
        ShapeList.add(rect3);
        Shape tri1 = new Triangle("золотий", 4, 7, 6);
        ShapeList.add(tri1);
        Shape tri2 = new Triangle("срібний", 6.6f, 6, 6);
        ShapeList.add(tri2);
        Shape tri3 = new Triangle("попелястий", 10, 11, 16);
        ShapeList.add(tri3);
        Shape circle1 = new Circle("сірий", 3);
        ShapeList.add(circle1);
        Shape circle2 = new Circle("прозорий", 4);
        ShapeList.add(circle2);
        Shape circle3 = new Circle("оранжевий", 2);
        ShapeList.add(circle3);
        Shape circle4 = new Circle("вишневий", 7);
        ShapeList.add(circle4);
        View view = new View();
        Model model = new Model(ShapeList);
        Controller cont = new Controller(view, model);
        cont.PrintAllObjects();
        cont.SumOfAreas();
        cont.SumOfClass(Circle.class);
        cont.CompareByArea();
        cont.CompareByColor();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\nМеню:");
            System.out.println("1) Показати всі об'єкти");
            System.out.println("2) Сума площ усіх фігур");
            System.out.println("3) Сума площ фігур типу Circle");
            System.out.println("4) Сортувати за площею");
            System.out.println("5) Сортувати за кольором");
            System.out.println("6) Зберегти набір у файл");
            System.out.println("7) Завантажити набір з файлу");
            System.out.println("0) Вихід");
            System.out.print("Ваш вибір: ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1" -> cont.PrintAllObjects();
                case "2" -> cont.SumOfAreas();
                case "3" -> cont.SumOfClass(Circle.class); // або Triangle.class / Rectangle.class
                case "4" -> cont.CompareByArea();
                case "5" -> cont.CompareByColor();
                case "6" -> {
                    System.out.print("Шлях до файлу для збереження (наприклад, data.bin): ");
                    String path = sc.nextLine().trim();
                    cont.Save(path);
                }
                case "7" -> {
                    System.out.print("Шлях до файлу для завантаження (наприклад, data.bin): ");
                    String path = sc.nextLine().trim();
                    cont.Load(path);
                }
                case "0" -> {
                    System.out.println("Готово.");
                    return;
                }
                default -> System.out.println("Невірний вибір, спробуйте ще.");
            }
        }
    }
}
