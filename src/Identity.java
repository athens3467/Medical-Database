public class Identity {
    private String firstName;
    private String lastName;

    public Identity(String firstName, String lastName) {
        if (firstName == null || lastName == null ||
                firstName.trim().isEmpty() ||
                lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid name.");
        }

        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
    }

    public Identity(String firstName, String lastName,
                    boolean lastNameFirst) {
        this(lastNameFirst ? lastName : firstName,
                lastNameFirst ? firstName : lastName);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String fullname() {
        return lastName + ", " + firstName;
    }

    public boolean match(Identity other) {
        if (other == null) {
            return false;
        }

        return firstName.equalsIgnoreCase(other.firstName) &&
                lastName.equalsIgnoreCase(other.lastName);
    }

    public boolean isLessThan(Identity other) {
        if (other == null) {
            return false;
        }

        int comparison =
                lastName.compareToIgnoreCase(other.lastName);

        if (comparison < 0) {
            return true;
        }

        if (comparison > 0) {
            return false;
        }

        return firstName.compareToIgnoreCase(other.firstName) < 0;
    }

    public String toString() {
        return fullname();
    }
}
