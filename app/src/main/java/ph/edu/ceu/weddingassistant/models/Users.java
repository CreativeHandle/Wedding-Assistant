package ph.edu.ceu.weddingassistant.models;

public class Users {
    String firstName;
    String lastName;
    String role;

    public Users(){
    }

    public Users(String firstName,
                 String lastName,
                 String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

}
