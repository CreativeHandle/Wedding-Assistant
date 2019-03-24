package ph.edu.ceu.weddingassistant.models;

public class UserEvents {
    String clientId;
    String eventTitle;
    String eventDate;
    String serviceProvider;

    public UserEvents(){
    }

    public UserEvents(String clientId, String eventTitle, String eventDate, String serviceProvider) {
        this.clientId = clientId;
        this.eventTitle = eventTitle;
        this.eventDate = eventDate;
        this.serviceProvider = serviceProvider;
    }

    public String getClientId() {
        return clientId;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getServiceProvider() {
        return serviceProvider;
    }
}
