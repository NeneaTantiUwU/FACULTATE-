package Lab12.Laborator12_starter.lab12_starter.ro.ulbs.proiectaresoftware.lab12;

public class Restaurant {

    private static final Restaurant restaurant;
    private  long usedWater; //ml

    static {
        restaurant = new Restaurant();
    }

    private Restaurant() {
        this.usedWater = 0;
    }

    public static Restaurant getRestaurant() {
        return restaurant;
    }

    public synchronized void washHands() {
        for(int i = 0; i < 1000; i++) {
            this.usedWater++;
        }
    }
    public synchronized void washDishes() {
        for(int i = 0; i < 1000; i++) {
                this.usedWater++;
        }
    }

    public synchronized long getUsedWater() {
        return usedWater;
    }
}
