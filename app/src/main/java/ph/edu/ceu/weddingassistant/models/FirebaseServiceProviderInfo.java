package ph.edu.ceu.weddingassistant.models;

public class FirebaseServiceProviderInfo {
    public String f_service_name;
    public String f_service_email;
    public String f_contact;
    public String f_permit;
    public String f_category;
    public Double f_cost;

    FirebaseServiceProviderInfo(){
    }

    public FirebaseServiceProviderInfo(String f_service_name, String f_service_email, String f_contact, String f_permit, String f_category, Double f_cost) {
        this.f_service_name = f_service_name;
        this.f_service_email = f_service_email;
        this.f_contact = f_contact;
        this.f_permit = f_permit;
        this.f_category = f_category;
        this.f_cost = f_cost;
    }
}
