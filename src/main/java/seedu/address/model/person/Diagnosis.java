package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a diagnosis attached to a patient.
 * Guarantees: immutable; is valid as declared in {@link #isValidDiagnosis(String)}.
 */
public class Diagnosis {

    public static final String MESSAGE_CONSTRAINTS =
            "Diagnosis descriptions should not be blank";
    public static final String VALIDATION_REGEX = "[^\\s].*";

    private final String value;
    private final LocalDate visitDate;
    private final Doctor diagnosedBy;
    private final List<String> symptoms = new ArrayList<>();
    private final List<Prescription> prescriptions = new ArrayList<>();

    /**
     * Constructs a {@code Diagnosis}.
     *
     * @param diagnosis A valid diagnosis description.
     * @param diagnosedBy Doctor who gave this diagnosis.
     */
    public Diagnosis(String diagnosis, Doctor diagnosedBy) {
        requireAllNonNull(diagnosis, diagnosedBy);
        checkArgument(isValidDiagnosis(diagnosis), MESSAGE_CONSTRAINTS);
        value = diagnosis;
        visitDate = null;
        this.diagnosedBy = diagnosedBy;
    }

    /**
     * Constructs a {@code Diagnosis} with a visit date.
     *
     * @param diagnosis A valid diagnosis description.
     * @param visitDate Date of the visit associated with the diagnosis.
     * @param diagnosedBy Doctor who gave this diagnosis.
     */
    public Diagnosis(String diagnosis, LocalDate visitDate, Doctor diagnosedBy) {
        requireAllNonNull(diagnosis, visitDate, diagnosedBy);
        checkArgument(isValidDiagnosis(diagnosis), MESSAGE_CONSTRAINTS);
        value = diagnosis;
        this.visitDate = visitDate;
        this.diagnosedBy = diagnosedBy;
    }

    /**
     * Returns true if a given string is a valid diagnosis description.
     */
    public static boolean isValidDiagnosis(String test) {
        requireAllNonNull(test);
        return test.matches(VALIDATION_REGEX);
    }

    public LocalDate getVisitDate() {
        return visitDate;
    }

    public Doctor getDiagnosedBy() {
        return diagnosedBy;
    }

    public void addSymptom(String symptom) {
        requireAllNonNull(symptom);
        checkArgument(isValidDiagnosis(symptom), MESSAGE_CONSTRAINTS);
        symptoms.add(symptom);
    }

    public void removeSymptom(String symptom) {
        requireAllNonNull(symptom);
        symptoms.remove(symptom);
    }

    public List<String> getSymptoms() {
        return Collections.unmodifiableList(symptoms);
    }

    public void addPrescription(Prescription prescription) {
        requireAllNonNull(prescription);
        prescriptions.add(prescription);
    }

    public void removePrescription(Prescription prescription) {
        requireAllNonNull(prescription);
        prescriptions.remove(prescription);
    }

    public List<Prescription> getPrescriptions() {
        return Collections.unmodifiableList(prescriptions);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("value", value)
                .add("visitDate", visitDate)
            .add("diagnosedBy", diagnosedBy)
                .add("symptoms", symptoms)
                .add("prescriptions", prescriptions)
                .toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Diagnosis)) {
            return false;
        }

        Diagnosis otherDiagnosis = (Diagnosis) other;
        return value.equals(otherDiagnosis.value)
                && java.util.Objects.equals(visitDate, otherDiagnosis.visitDate)
                && diagnosedBy.equals(otherDiagnosis.diagnosedBy)
                && symptoms.equals(otherDiagnosis.symptoms)
                && prescriptions.equals(otherDiagnosis.prescriptions);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(value, visitDate, diagnosedBy, symptoms, prescriptions);
    }
}