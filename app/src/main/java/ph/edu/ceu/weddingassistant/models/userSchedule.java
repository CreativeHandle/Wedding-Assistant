package ph.edu.ceu.weddingassistant.models;

import java.util.List;

public class userSchedule {
    String occupiedDay;

    public userSchedule(){}

    public userSchedule(String occupiedDay) {
        this.occupiedDay = occupiedDay;
    }

    public String getOccupiedDay() {
        return occupiedDay;
    }
}
