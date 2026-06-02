package Lab11.lab11;

public class MediaInterested implements  Observer{
    private String name;

    public MediaInterested(String name) {
        this.name = name;
    }

    // Implementarea metodei din interfață
    @Override
    public void update(String message) {
        // Redirecționăm notificarea către logica ta internă
        doSomeLogic(message);
    }

    public void doSomeLogic(String message) {
        System.out.println("[" + name + "] received update: " + message);
    }
}