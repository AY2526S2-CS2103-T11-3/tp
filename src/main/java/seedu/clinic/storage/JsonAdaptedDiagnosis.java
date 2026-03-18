package seedu.clinic.storage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.clinic.commons.exceptions.IllegalValueException;
import seedu.clinic.model.person.Address;
import seedu.clinic.model.person.Diagnosis;
import seedu.clinic.model.person.Doctor;
import seedu.clinic.model.person.Email;
import seedu.clinic.model.person.Name;
import seedu.clinic.model.person.Phone;
import seedu.clinic.model.person.Prescription;
import seedu.clinic.model.tag.Tag;

/**
 * Jackson-friendly version of {@link `Diagnosis`}.
 */
class JsonAdaptedDiagnosis {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Diagnosis's %s field is missing!";

    private final int patientId;
    private final String description;
    private final String visitDate;
    private final String diagnosedBy;
    private final List<String> symptoms = new ArrayList<>();
    private final List<JsonAdaptedPrescription> prescriptions = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedDiagnosis} with the given diagnosis details.
     */
    @JsonCreator
    public JsonAdaptedDiagnosis(@JsonProperty("patientId") int patientId,
                                @JsonProperty("description") String description,
                                @JsonProperty("visitDate") String visitDate,
                                @JsonProperty("diagnosedBy") String diagnosedBy,
                                @JsonProperty("symptoms") List<String> symptoms,
                                @JsonProperty("prescriptions") List<JsonAdaptedPrescription> prescriptions) {

        this.patientId = patientId;
        this.description = description;
        this.visitDate = visitDate;
        this.diagnosedBy = diagnosedBy;

        if (symptoms != null) {
            this.symptoms.addAll(symptoms);
        }
        if (prescriptions != null) {
            this.prescriptions.addAll(prescriptions);
        }
    }

    /**
     * Converts a given {@code Diagnosis} into this class for Jackson use.
     */
    public JsonAdaptedDiagnosis(Diagnosis source) {
        patientId = source.getPatientId();
        description = source.getDescription();
        visitDate = source.getVisitDate().toString();
        diagnosedBy = source.getDiagnosedBy().getName().toString();

        symptoms.addAll(source.getSymptoms());

        prescriptions.addAll(source.getPrescriptions().stream()
                .map(JsonAdaptedPrescription::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted diagnosis object into the model's {@code Diagnosis} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Diagnosis toModelType() throws IllegalValueException {
        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "description"));
        }

        if (visitDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "visitDate"));
        }
        final LocalDate modelVisitDate = LocalDate.parse(visitDate);

        if (diagnosedBy == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "diagnosedBy"));
        }
        /**
         * TODO: Retrieve doctor from list
         * Currently creates new Doctor with diagnosedBy as name
         */
        final Doctor modelDoctor = new Doctor(new Name(diagnosedBy), new Phone("91234567"),
                new Email("test1@gmail.com"), new Address("123 Clementi Rd"), new HashSet<Tag>());

        final List<Prescription> modelPrescriptions = new ArrayList<>();
        for (JsonAdaptedPrescription p : prescriptions) {
            modelPrescriptions.add(p.toModelType());
        }

        Diagnosis d = new Diagnosis(patientId, description, modelVisitDate, modelDoctor);
        symptoms.forEach(d::addSymptom);
        modelPrescriptions.forEach(d::addPrescription);

        return d;
    }
}
