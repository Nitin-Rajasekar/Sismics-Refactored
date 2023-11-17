import java.util.ArrayList;
import java.util.List;

public class PassengerPlane {

    public String model;
    public int passengerCapacity;
    public int crewCapacity;
    private List<PlaneObserver> observers = new ArrayList <> ();

    public PassengerPlane(String model) {
        this.model = model;
        switch (model) {
            case "A380":
                passengerCapacity = 500;
                crewCapacity = 42;
                break;
            case "A350":
                passengerCapacity = 320;
                crewCapacity = 40;
                break;
            case "Embraer 190":
                passengerCapacity = 25;
                crewCapacity = 5;
                break;
            case "Antonov AN2":
                passengerCapacity = 15;
                crewCapacity = 3;
                break;
            default:
                throw new IllegalArgumentException(String.format("Model type '%s' is not recognized", model));
        }
    }

    public void addObserver(PlaneObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(PlaneObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (PlaneObserver observer : observers) {
            observer.update(this);
        }
    }

    public void setPassengerCapacity(int passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
        notifyObservers();
    }

    public void setCrewCapacity(int crewCapacity) {
        this.crewCapacity = crewCapacity;
        notifyObservers();
    }

}

interface PlaneObserver {
    void update(PassengerPlane plane);
}