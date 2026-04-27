package Lab7;

public class Circle extends Form{
    private float radius;

    public Circle() {
        super();
    }

    public Circle (float radius, String color) {
        super(color);
        this.radius = radius;
    }

    public float getArea() {
        return (float)(Math.PI * radius * radius);
    }

    public String toString() {
        return "Circle: "+super.toString()+"; radius="+radius;
    }

}
