package Lab7;

public class Square  extends Form{
    private float side;

    public Square() {
        super();
    }

    public Square (float side, String color) {
        super(color);
        this.side = side;
    }

    public float getArea() {
        return side * side;
    }

    public String toString() {
        return "Square: "+super.toString()+"; side="+side;
    }

}
