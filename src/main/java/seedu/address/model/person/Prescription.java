package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a prescription attached to a diagnosis.
 * Guarantees: immutable; all fields are non-null and non-blank.
 */
public class Prescription {

    public static final String MESSAGE_CONSTRAINTS =
            "Prescription fields should not be blank";
    private static final String VALIDATION_REGEX = "[^\\s].*";

    private final String medicationName;
    private final String dosage;
    private final String frequency;
    private final Pharmacist dispensedBy;

    /**
     * Constructs a {@code Prescription}.
     */
    public Prescription(String medicationName, String dosage, String frequency, Pharmacist dispensedBy) {
        requireAllNonNull(medicationName, dosage, frequency, dispensedBy);
        checkArgument(isValidField(medicationName), MESSAGE_CONSTRAINTS);
        checkArgument(isValidField(dosage), MESSAGE_CONSTRAINTS);
        checkArgument(isValidField(frequency), MESSAGE_CONSTRAINTS);
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.frequency = frequency;
        this.dispensedBy = dispensedBy;
    }

    public static boolean isValidField(String value) {
        requireAllNonNull(value);
        return value.matches(VALIDATION_REGEX);
    }

    public String getMedicationName() {
        return medicationName;
    }

    public String getDosage() {
        return dosage;
    }

    public String getFrequency() {
        return frequency;
    }

    public Pharmacist getDispensedBy() {
        return dispensedBy;
    }

    @Override
    public String toString() {
        return medicationName + " (" + dosage + ", " + frequency + ", dispensedBy="
            + dispensedBy.getName() + ")";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Prescription)) {
            return false;
        }

        Prescription otherPrescription = (Prescription) other;
        return medicationName.equals(otherPrescription.medicationName)
                && dosage.equals(otherPrescription.dosage)
            && frequency.equals(otherPrescription.frequency)
            && dispensedBy.equals(otherPrescription.dispensedBy);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(medicationName, dosage, frequency, dispensedBy);
    }
}