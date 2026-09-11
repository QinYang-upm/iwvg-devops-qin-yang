package es.upm.miw.devops.code;

/**
 * Represents a user in the application.
 */
public class User {

    private String id;
    private String firstName;
    private String familyName;
    private String email;

    // Required for object serialization/deserialization
    public User() {
    }

    public User(String id, String firstName, String familyName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.familyName = familyName;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getEmail() {
        return email;
    }
}