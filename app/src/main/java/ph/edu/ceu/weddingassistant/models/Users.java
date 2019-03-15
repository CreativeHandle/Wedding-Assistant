package ph.edu.ceu.weddingassistant.models;

public class Users {
    public String email;
    public String name;
    public String role;
    public String contactNumber;

    public Users(){
    }

    public Users(String email,
                 String name,
                 String role,
                 String contactNumber) {
        this.email = email;
        this.name = name;
        this.role = role;
        this.contactNumber = contactNumber;
    }

}
