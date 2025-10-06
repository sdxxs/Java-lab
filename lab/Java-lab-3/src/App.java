import java.util.List;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws Exception {
        List<Shape> ShapeList = new ArrayList<Shape>();

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


    }

}
