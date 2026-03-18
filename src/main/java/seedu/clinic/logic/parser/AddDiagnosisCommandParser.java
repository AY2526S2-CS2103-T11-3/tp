package seedu.clinic.logic.parser;

import static seedu.clinic.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_DISPENSED_BY;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_DOSAGE;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_FREQ;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_MEDICATION;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_PRESCRIBED_BY;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_VISIT_DATE;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_DIAGNOSED_BY;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_SYMPTOM;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import seedu.clinic.commons.core.index.Index;
import seedu.clinic.logic.commands.AddDiagnosisCommand;
import seedu.clinic.logic.parser.exceptions.ParseException;
import seedu.clinic.model.person.Address;
import seedu.clinic.model.person.Diagnosis;
import seedu.clinic.model.person.Doctor;
import seedu.clinic.model.person.Email;
import seedu.clinic.model.person.Name;
import seedu.clinic.model.person.Phone;
import seedu.clinic.model.person.Prescription;
import seedu.clinic.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddDiagnosisCommand object
 */
public class AddDiagnosisCommandParser implements Parser<AddDiagnosisCommand> {

    public AddDiagnosisCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_ID, PREFIX_DESC, PREFIX_VISIT_DATE,
                        PREFIX_DIAGNOSED_BY, PREFIX_SYMPTOM, PREFIX_MEDICATION,
                        PREFIX_DOSAGE, PREFIX_FREQ, PREFIX_DISPENSED_BY);

        if (!arePrefixesPresent(argMultimap,
                PREFIX_ID, PREFIX_DESC, PREFIX_VISIT_DATE, PREFIX_DIAGNOSED_BY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT,
                    AddDiagnosisCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getAllValues(PREFIX_SYMPTOM).isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT,
                    AddDiagnosisCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_ID, PREFIX_DESC, PREFIX_VISIT_DATE, PREFIX_DIAGNOSED_BY);

        Index id = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_ID).get());
        String description = argMultimap.getValue(PREFIX_DESC).get();
        LocalDate visitDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_VISIT_DATE).get());
        String diagnosedBy = argMultimap.getValue(PREFIX_DIAGNOSED_BY).get();
        List<String> symptoms = argMultimap.getAllValues(PREFIX_SYMPTOM);

        List<String> medNames = argMultimap.getAllValues(PREFIX_MEDICATION);
        List<String> dosages = argMultimap.getAllValues(PREFIX_DOSAGE);
        List<String> frequencies = argMultimap.getAllValues(PREFIX_FREQ);
        List<String> dispensedByList = argMultimap.getAllValues(PREFIX_DISPENSED_BY);

        int prescriptionCount = medNames.size();

        if (prescriptionCount == 0) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT,
                    AddDiagnosisCommand.MESSAGE_USAGE));
        } else if (prescriptionCount > 0) {
            if (IntStream.of(dosages.size(), frequencies.size(), dispensedByList.size())
                    .anyMatch(size -> size != prescriptionCount)) {
                throw new ParseException(String.format(
                        MESSAGE_INVALID_COMMAND_FORMAT,
                        AddDiagnosisCommand.MESSAGE_USAGE));
            }
        }

        List<Prescription> prescriptions = new ArrayList<>();
        for (int i = 0; i < prescriptionCount; i++) {
            prescriptions.add(ParserUtil.parsePrescription(
                    medNames.get(i),
                    dosages.get(i),
                    frequencies.get(i),
                    dispensedByList.get(i)
            ));
        }

        /**
         * TODO: Retrieve doctor from list
         * Currently creates new Doctor with diagnosedBy as name
         */
        Doctor doctorObj = new Doctor(new Name(diagnosedBy), new Phone("91234567"),
                new Email("test1@gmail.com"), new Address("123 Clementi Rd"), new HashSet<Tag>());
        Diagnosis diagnosis = new Diagnosis(id.getOneBased(), description, visitDate, doctorObj);
        symptoms.forEach(diagnosis::addSymptom);
        prescriptions.forEach(diagnosis::addPrescription);

        return new AddDiagnosisCommand(id, diagnosis);
    }

    /**
     * Returns true if all prefixes contain values.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes)
                .allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
