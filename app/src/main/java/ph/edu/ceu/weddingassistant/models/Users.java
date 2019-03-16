package ph.edu.ceu.weddingassistant.models;

public class Users {
    public String name;
    public String email;
    public String role;
    public String contactNumber;
    public String permit;

    public Users(){
    }

    public Users(String name,
                 String email,
                 String role,
                 String contactNumber,
                 String permit) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.contactNumber = contactNumber;
        this.permit = permit;
    }

}
