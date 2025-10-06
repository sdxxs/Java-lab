import java.io.Serializable;

public abstract class Shape implements Drawable, Serializable {
    public String shapeColor;

    public Shape(String color){
        this.shapeColor = color;
    }

    abstract float calcArea();

    @Override
    public String toString(){
        return "Колір фігури: " + shapeColor;
    }

    public void draw(){
        System.out.println("Малюю фігуру " + this.getClass());
    }
}
