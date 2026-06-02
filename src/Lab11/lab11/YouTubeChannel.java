package Lab11.lab11;

import java.util.ArrayList;
import java.util.List;

public class YouTubeChannel implements Subject {
    private String channelName;
    // Lista stochează interfețe Observer conform indicațiilor din laborator
    private List<Observer> observers = new ArrayList<>();

    public YouTubeChannel(String channelName) {
        this.channelName = channelName;
    }

    @Override
    public void addObserver(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void uploadVideo(String title) {
        System.out.println("{" + channelName + "} uploaded a new video: " + title);
        // Notificăm observatorii în metoda uploadVideo
        notifyObservers("New video on " + channelName + ": " + title);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}