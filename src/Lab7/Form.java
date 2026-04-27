package Lab7;

public class Form {
    private String color;
    private static int counter;

    public Form() {
        color = "white"; counter++;
    }

    public Form(String color) {
        this.color = color; counter++;
    }

    public static int getInstanceCount() {
        return counter;
    }

    public float getArea() {
        return 0;
    }

    public String toString() {
        return "This form has the color " + color;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Form) {
            Form f = (Form) obj;
            if (this.color == null && f.color == null) {
                return true;
            } else if (this.color != null && f.color != null && this.color.compareTo(f.color) == 0) {
                return true;
            }
        }
        return false;
    }
}
