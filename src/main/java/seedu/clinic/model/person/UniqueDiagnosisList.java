package seedu.clinic.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.clinic.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.clinic.model.person.exceptions.DuplicateDiagnosisException;
import seedu.clinic.model.person.exceptions.DiagnosisNotFoundException;
import seedu.clinic.model.person.Diagnosis;

/**
 * A list of diagnoses that enforces uniqueness between its elements and does not allow nulls.
 * A diagnosis is considered unique by comparing using {@code Diagnosis#isSameDiagnosis(Diagnosis)}.
 * As such, adding and updating of diagnoses uses isSameDiagnosis for equality.
 * However, removal of a diagnosis uses Diagnosis#equals(Object) to remove the exact object.
 *
 * Supports a minimal set of list operations.
 *
 * @see Diagnosis#isSameDiagnosis(Diagnosis)
 */
public class UniqueDiagnosisList implements Iterable<Diagnosis> {

    private final ObservableList<Diagnosis> internalList = FXCollections.observableArrayList();
    private final ObservableList<Diagnosis> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent diagnosis as the given argument.
     */
    public boolean contains(Diagnosis toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameDiagnosis);
    }

    /**
     * Adds a diagnosis to the list.
     * The diagnosis must not already exist in the list.
     */
    public void add(Diagnosis toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateDiagnosisException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the diagnosis {@code target} in the list with {@code editedDiagnosis}.
     * {@code target} must exist in the list.
     * The identity of {@code editedDiagnosis} must not be the same as another existing diagnosis in the list.
     */
    public void setDiagnosis(Diagnosis target, Diagnosis editedDiagnosis) {
        requireAllNonNull(target, editedDiagnosis);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new DiagnosisNotFoundException();
        }

        if (!target.isSameDiagnosis(editedDiagnosis) && contains(editedDiagnosis)) {
            throw new DuplicateDiagnosisException();
        }

        internalList.set(index, editedDiagnosis);
    }

    /**
     * Removes the equivalent diagnosis from the list.
     * The diagnosis must exist in the list.
     */
    public void remove(Diagnosis toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new DiagnosisNotFoundException();
        }
    }

    public void setDiagnoses(UniqueDiagnosisList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code diagnoses}.
     * {@code diagnoses} must not contain duplicate diagnoses.
     */
    public void setDiagnoses(List<Diagnosis> diagnoses) {
        requireAllNonNull(diagnoses);
        if (!diagnosesAreUnique(diagnoses)) {
            throw new DuplicateDiagnosisException();
        }

        internalList.setAll(diagnoses);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Diagnosis> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    public Stream<Diagnosis> stream() {
        return internalList.stream();
    }

    @Override
    public Iterator<Diagnosis> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UniqueDiagnosisList)) {
            return false;
        }
        UniqueDiagnosisList otherList = (UniqueDiagnosisList) other;
        return internalList.equals(otherList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code diagnoses} contains only unique diagnoses.
     */
    private boolean diagnosesAreUnique(List<Diagnosis> diagnoses) {
        for (int i = 0; i < diagnoses.size() - 1; i++) {
            for (int j = i + 1; j < diagnoses.size(); j++) {
                if (diagnoses.get(i).isSameDiagnosis(diagnoses.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
