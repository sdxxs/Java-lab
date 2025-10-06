public class Circle extends Shape{
    float radius;

    public Circle(String color, float radius){
        super(color);
        this.radius = radius;
    }

    float calcArea(){
        return (float)(Math.PI * Math.pow(radius, 2));
    }

    @Override
    public String toString(){
        return "Фігура: Коло; колір: " + shapeColor + "; Радіус: " + radius;
    }
}
