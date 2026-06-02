package Lab11.lab11;

public class ObserverPatternDemo {
    public static void main(String[] args) {
        YouTubeChannel channel = new YouTubeChannel("Tech Explained");

        MediaInterested media1 = new MediaInterested("CNN");
        MediaInterested media2 = new MediaInterested("FoxNews");

        // Înainte de primul apel, înregistrăm ambii observatori (vor fi 2)
        channel.addObserver(media1);
        channel.addObserver(media2);

        System.out.println("--- Primul Upload (2 observatori) ---");
        channel.uploadVideo("Observer Pattern in Java");

        // Eliminăm un observator înainte de al doilea apel (va rămâne doar 1)
        channel.removeObserver(media2);

        System.out.println("\n--- Al doilea Upload (1 observator) ---");
        channel.uploadVideo("Singleton Pattern in Java");
    }
}