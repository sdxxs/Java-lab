public class Rectangle extends Shape{
    float width, height;

    public Rectangle(String color,float width, float height){
        super(color);
        this.width = width;
        this.height = height;
    }

    float calcArea(){
        return width*height;
    }

    @Override
    public String toString(){
        return "Фігура: Прямокутник; колір: " + shapeColor + "; Ширина: " + width + "; Висота: " + height;
    }
}
