import java.util.Date;

public class PatientIdentity {
    private Identity identity;
    private Date dateOfBirth;

    public PatientIdentity(Identity identity, Date dateOfBirth) {
        this.identity = identity;
        this.dateOfBirth = dateOfBirth;
    }

    public boolean match(PatientIdentity other) {
        if (other == null) {
            return false;
        }

        return identity.match(other.identity)
                && dateOfBirth.compareTo(other.dateOfBirth) == 0;
    }

    public boolean isLessThan(PatientIdentity other) {
        if (identity.match(other.identity)) {
            return dateOfBirth.compareTo(other.dateOfBirth) < 0;
        }

        return identity.isLessThan(other.identity);
    }

    public String toString() {
        return identity.toString() + ", " + dateOfBirth.toString();
    }
}
