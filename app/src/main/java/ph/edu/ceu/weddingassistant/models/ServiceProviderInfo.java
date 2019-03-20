package ph.edu.ceu.weddingassistant.models;

public class ServiceProviderInfo {
    public String service_name;
    public String service_email;
    public String contact;
    public String permit;
    public String category;
    public Double cost;
    public int thumbnail;

    public ServiceProviderInfo(){
    }

    public ServiceProviderInfo(String service_name, String service_email, String contact, String permit, String category, Double cost, int thumbnail) {
        this.service_name = service_name;
        this.service_email = service_email;
        this.contact = contact;
        this.permit = permit;
        this.category = category;
        this.cost = cost;
        this.thumbnail = thumbnail;
    }

    public String getService_name() {
        return service_name;
    }

    public String getService_email() {
        return service_email;
    }

    public String getContact() {
        return contact;
    }

    public String getPermit() {
        return permit;
    }

    public String getCategory() {
        return category;
    }

    public Double getCost() {
        return cost;
    }

    public int getThumbnail() {
        return thumbnail;
    }
}
