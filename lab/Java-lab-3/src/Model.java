import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.Collections;

public class Model {
    List<Shape> ShapeList;
    public Model(List<Shape> ShapeList){
        this.ShapeList = ShapeList;
    }

    String PrintAllObjects(){
        StringBuffer stringBuffer = new StringBuffer();
        for (Shape shape : ShapeList) {
            stringBuffer.append(shape.toString() + " з площею: " + shape.calcArea() + "\n");
        }
        return stringBuffer.toString();
    }

    String SumOfAreas(){
        float Sum = 0;
        for (Shape shape : ShapeList) {
            Sum += shape.calcArea();
        }
        return Float.toString(Sum);
    }

    String CompareByArea(){
        Collections.sort(ShapeList, AreaComparator);
        return PrintAllObjects();
    }

    String CompareByColor(){
        Collections.sort(ShapeList, ColorComparator);
        return PrintAllObjects();
    }

    String SumOfClass(Class ChosenClass){
        float Sum = 0;
        for (Shape shape : ShapeList) {
            if (shape.getClass() == ChosenClass) {
                Sum += shape.calcArea();
            }
        }
        return Float.toString(Sum);
    }

    private static Comparator<Shape> AreaComparator = new Comparator<Shape>() {
        public int compare(Shape i, Shape j){
            if (i.calcArea() > j.calcArea()) {
                return 1;
            } else {
                return -1;
            }
        }
    };

    private static Comparator<Shape> ColorComparator = new Comparator<Shape>() {
        public int compare(Shape i, Shape j){
            return i.shapeColor.compareTo(j.shapeColor);
        }
    };

    void saveToFile(String path) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(ShapeList); // Shape implements Serializable — все ок
        }
    }

    @SuppressWarnings("unchecked")
    void loadFromFile(String path) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            Object obj = ois.readObject();
            ShapeList.clear();
            ShapeList.addAll((List<Shape>) obj);
        }
    }

    List<Shape> getShapes() {
        return Collections.unmodifiableList(ShapeList);
    }

}
