package ph.edu.ceu.weddingassistant.models;

public class Users {
    String name;
    String email;
    String role;
    String contactNumber;
    String permit;

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

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getPermit() {
        return permit;
    }
}
