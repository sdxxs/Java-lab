public class Triangle extends Shape{
    float side1, side2, side3;

    public Triangle(String color,float Side1, float Side2, float Side3){
        super(color);
        this.side1 = Side1;
        this.side2 = Side2;
        this.side3 = Side3;
    }

    float calcArea(){
        float s = (side1 + side2 + side3)/2;
        float area = (float)Math.sqrt(s*(s-side1)*(s-side2)*(s-side3));
        return area;
    }

    @Override
    public String toString(){
        return "Фігура: Трикутник; колір: " + shapeColor + "; Довжини сторін:" + side1 + ", " + side2 + ", " + side3;
    }
}
