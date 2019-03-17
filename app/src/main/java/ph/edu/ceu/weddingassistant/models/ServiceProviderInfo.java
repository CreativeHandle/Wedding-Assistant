package ph.edu.ceu.weddingassistant.models;

public class ServiceProviderInfo {
    public String person_name;
    public String permit;
    public String category;
    public Double cost;

    public ServiceProviderInfo(){
    }

    public ServiceProviderInfo(String person_name, String permit, String category, Double cost) {
        this.person_name = person_name;
        this.permit = permit;
        this.category = category;
        this.cost = cost;
    }

    public String getPerson_name() {
        return person_name;
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
}
