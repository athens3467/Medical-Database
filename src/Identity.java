public class Identity {
    private String firstName;
    private String lastName;

    public Identity(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Identity(String firstName, String lastName, boolean lastNameFirst) {
        if (lastNameFirst) {
            this.firstName = lastName;
            this.lastName = firstName;
        } else {
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }

    public String fullname() {
        return lastName + ", " + firstName;
    }

    public boolean match(Identity other) {
        if (other == null) {
            return false;
        }

        return firstName.toLowerCase().equals(other.firstName.toLowerCase())
                && lastName.toLowerCase().equals(other.lastName.toLowerCase());
    }

    public boolean isLessThan(Identity other) {
        int lastComparison = lastName.toLowerCase().compareTo(other.lastName.toLowerCase());

        if (lastComparison < 0) {
            return true;
        }

        if (lastComparison > 0) {
            return false;
        }

        int firstComparison = firstName.toLowerCase().compareTo(other.firstName.toLowerCase());

        if (firstComparison < 0) {
            return true;
        }

        return false;
    }

    public String toString() {
        return fullname();
    }
}
