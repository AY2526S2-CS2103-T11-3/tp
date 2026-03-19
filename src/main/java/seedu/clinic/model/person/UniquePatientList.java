package seedu.clinic.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.clinic.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.clinic.model.person.exceptions.DuplicatePatientException;

/**
 * A list of patients that enforces uniqueness between its elements and does not allow nulls.
 * A patient is considered unique by NRIC.
 */
public class UniquePatientList implements Iterable<Patient> {

    private final ObservableList<Patient> internalList = FXCollections.observableArrayList();
    private final ObservableList<Patient> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent patient as the given argument.
     */
    public boolean contains(Patient toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(existing -> existing.getNric().equals(toCheck.getNric()));
    }

    /**
     * Adds a patient to the list.
     * The patient must not already exist in the list.
     */
    public void add(Patient toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePatientException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the contents of this list with {@code patients}.
     * {@code patients} must not contain duplicate patients by NRIC.
     */
    public void setPatients(List<Patient> patients) {
        requireAllNonNull(patients);
        if (!patientsAreUnique(patients)) {
            throw new DuplicatePatientException();
        }

        internalList.setAll(patients);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Patient> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Patient> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UniquePatientList)) {
            return false;
        }

        UniquePatientList otherUniquePatientList = (UniquePatientList) other;
        return internalList.equals(otherUniquePatientList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    private boolean patientsAreUnique(List<Patient> patients) {
        for (int i = 0; i < patients.size() - 1; i++) {
            for (int j = i + 1; j < patients.size(); j++) {
                if (patients.get(i).getNric().equals(patients.get(j).getNric())) {
                    return false;
                }
            }
        }
        return true;
    }
}
