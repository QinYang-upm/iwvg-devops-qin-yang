package es.upm.miw.devops.code;

/**
 * Represents a user in the application.
 */
public class User {

    private String id;
    private String firstName;
    private String familyName;
    private String email;
    private String identity;
    private String address;
    private String city;
    private String province;
    private String postalCode;
    private String role;
    private boolean active;

    public User() {
    }

    // Backward-compatible constructor
    public User(
            String id,
            String firstName,
            String familyName,
            String email,
            String identity,
            String address,
            String city,
            String province,
            String postalCode,
            boolean active
    ) {
        this(
                id,
                firstName,
                familyName,
                email,
                identity,
                address,
                city,
                province,
                postalCode,
                "USER",
                active
        );
    }

    public User(
            String id,
            String firstName,
            String familyName,
            String email,
            String identity,
            String address,
            String city,
            String province,
            String postalCode,
            String role,
            boolean active
    ) {
        this.id = id;
        this.firstName = firstName;
        this.familyName = familyName;
        this.email = email;
        this.identity = identity;
        this.address = address;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
        this.role = role;
        this.active = active;
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

    public String getIdentity() {
        return identity;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getRole() {
        return role;
    }

    public boolean isBillable() {
        return hasContent(firstName)
                && hasContent(familyName)
                && hasContent(email)
                && hasContent(identity)
                && hasContent(address)
                && hasContent(city)
                && hasContent(province)
                && hasContent(postalCode);
    }

    private boolean hasContent(String value) {
        return value != null && !value.isBlank();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(role);
    }

    public void updateFrom(User user) {
        this.firstName = user.firstName;
        this.familyName = user.familyName;
        this.email = user.email;
        this.identity = user.identity;
        this.address = user.address;
        this.city = user.city;
        this.province = user.province;
        this.postalCode = user.postalCode;
        this.active = user.active;
    }
}