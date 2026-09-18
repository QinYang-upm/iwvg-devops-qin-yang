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
    private boolean active;

    public User() {
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
}