package ph.edu.ceu.weddingassistant.models;

public class Users {
    String email;
    String firstName;
    String lastName;
    String role;

    public Users(){
    }

    public Users(String email,
                 String firstName,
                 String lastName,
                 String role) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

}
