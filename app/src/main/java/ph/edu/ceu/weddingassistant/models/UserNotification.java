package ph.edu.ceu.weddingassistant.models;

public class UserNotification {
    String childId;
    String clientId;
    String serviceProvider;
    String status;
    String eventTitle;
    String eventDate;
    String eventLocation;

    public UserNotification(String childId, String clientId, String serviceProvider, String status, String eventTitle, String eventDate, String eventLocation) {
        this.childId = childId;
        this.clientId = clientId;
        this.serviceProvider = serviceProvider;
        this.status = status;
        this.eventTitle = eventTitle;
        this.eventDate = eventDate;
        this.eventLocation = eventLocation;
    }

    public String getChildId() {
        return childId;
    }

    public String getClientId() {
        return clientId;
    }

    public String getServiceProvider() {
        return serviceProvider;
    }

    public String getStatus() {
        return status;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getEventLocation() {
        return eventLocation;
    }
}
