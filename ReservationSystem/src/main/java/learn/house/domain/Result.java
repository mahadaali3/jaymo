package learn.house.domain;

import learn.house.models.Reservation;

import java.util.ArrayList;
import java.util.List;

public class Result {
    private ArrayList<String> messages = new ArrayList<>();

    private Reservation reservation;
    public boolean isSuccess() {
        return messages.size() == 0;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public List<String> getMessages() {


        return new ArrayList<>(messages);
    }

    public void addMessage(String message){

        messages.add(message);
    }

}
