package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a patient in the address book.
 */
public class Patient extends Person {

	private final NRIC nric;
	private final LocalDate dateOfBirth;
	private final String emergencyContact;
	private final List<Diagnosis> diagnoses = new ArrayList<>();

	/**
	 * Every field must be present and not null.
	 */
	public Patient(Name name, Phone phone, Email email, Address address, Set<Tag> tags,
			NRIC nric, LocalDate dateOfBirth, String emergencyContact) {
		super(name, phone, email, address, tags);
		requireAllNonNull(nric, dateOfBirth, emergencyContact);
		this.nric = nric;
		this.dateOfBirth = dateOfBirth;
		this.emergencyContact = emergencyContact;
	}

	/**
	 * Reuses an existing person as the shared identity and contact details for a patient.
	 */
	public Patient(Person person, NRIC nric, LocalDate dob, String emergencyContact) {
		this(person.getName(), person.getPhone(), person.getEmail(), person.getAddress(), person.getTags(),
				nric, dob, emergencyContact);
	}

	public NRIC getNric() {
		return nric;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public String getEmergencyContact() {
		return emergencyContact;
	}

	public void addDiagnosis(Diagnosis diagnosis) {
		requireAllNonNull(diagnosis);
		diagnoses.add(diagnosis);
	}

	public void removeDiagnosis(Diagnosis diagnosis) {
		requireAllNonNull(diagnosis);
		diagnoses.remove(diagnosis);
	}

	public List<Diagnosis> getDiagnoses() {
		return Collections.unmodifiableList(diagnoses);
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}

		if (!(other instanceof Patient)) {
			return false;
		}

		Patient otherPatient = (Patient) other;
		return super.equals(otherPatient)
				&& nric.equals(otherPatient.nric)
				&& dateOfBirth.equals(otherPatient.dateOfBirth)
				&& emergencyContact.equals(otherPatient.emergencyContact)
				&& diagnoses.equals(otherPatient.diagnoses);
	}

	@Override
	public int hashCode() {
		return java.util.Objects.hash(super.hashCode(), nric, dateOfBirth, emergencyContact, diagnoses);
	}
}
