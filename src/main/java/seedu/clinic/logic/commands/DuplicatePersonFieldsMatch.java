package seedu.clinic.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.clinic.model.Model;
import seedu.clinic.model.person.Person;

/**
 * Represents duplicate contact-field matches for an add command.
 */
public class DuplicatePersonFieldsMatch<T extends Person> {
    private final T personToAdd;
    private final List<T> matchingPersons;
    private final boolean hasMatchingName;
    private final boolean hasMatchingPhone;
    private final boolean hasMatchingEmail;

    private DuplicatePersonFieldsMatch(T personToAdd, List<T> matchingPersons, boolean hasMatchingName,
            boolean hasMatchingPhone, boolean hasMatchingEmail) {
        this.personToAdd = personToAdd;
        this.matchingPersons = matchingPersons;
        this.hasMatchingName = hasMatchingName;
        this.hasMatchingPhone = hasMatchingPhone;
        this.hasMatchingEmail = hasMatchingEmail;
    }

    /**
     * Finds duplicate contact-field matches for {@code personToAdd} among existing persons of {@code personType}.
     */
    public static <T extends Person> DuplicatePersonFieldsMatch<T> find(Model model,
            T personToAdd, Class<T> personType) {
        requireNonNull(model);
        requireNonNull(personToAdd);
        requireNonNull(personType);

        List<T> matchingPersons = model.getClinicBook().getPersonList().stream()
                .filter(personType::isInstance)
                .map(personType::cast)
                .filter(existingPerson -> isMatchingAnyField(existingPerson, personToAdd))
                .collect(Collectors.toList());

        boolean hasMatchingName = matchingPersons.stream()
                .anyMatch(existingPerson -> existingPerson.getName().equals(personToAdd.getName()));
        boolean hasMatchingPhone = matchingPersons.stream()
                .anyMatch(existingPerson -> existingPerson.getPhone().equals(personToAdd.getPhone()));
        boolean hasMatchingEmail = matchingPersons.stream()
                .anyMatch(existingPerson -> existingPerson.getEmail().equals(personToAdd.getEmail()));

        return new DuplicatePersonFieldsMatch<>(personToAdd, matchingPersons,
                hasMatchingName, hasMatchingPhone, hasMatchingEmail);
    }

    public boolean hasExactDuplicate() {
        return matchingPersons.stream().anyMatch(this::matchesAllContactFields);
    }

    public boolean hasAnyMatch() {
        return !matchingPersons.isEmpty();
    }

    /**
     * Returns a predicate that keeps only the matching persons visible.
     */
    public Predicate<Person> asPredicate() {
        Set<Integer> matchingIds = matchingPersons.stream()
                .map(Person::getId)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        return person -> matchingIds.contains(person.getId());
    }

    public String getExactDuplicateMessage(String personLabel) {
        return String.format("Rejected: an existing %s already has the same name, phone number, and email address. "
                + "Matching %ss are shown below.", personLabel, personLabel);
    }

    public String getWarningMessage(String personLabel) {
        return String.format("Warning: existing %ss with the same %s were found. "
                + "Press Enter again to continue adding anyway OR key-in 'list' to get the original list."
                , personLabel, getMatchingFieldSummary());
    }

    private boolean matchesAllContactFields(T existingPerson) {
        return existingPerson.getName().equals(personToAdd.getName())
                && existingPerson.getPhone().equals(personToAdd.getPhone())
                && existingPerson.getEmail().equals(personToAdd.getEmail());
    }

    private String getMatchingFieldSummary() {
        List<String> fieldNames = new ArrayList<>();
        if (hasMatchingName) {
            fieldNames.add("name");
        }
        if (hasMatchingPhone) {
            fieldNames.add("phone number");
        }
        if (hasMatchingEmail) {
            fieldNames.add("email address");
        }

        if (fieldNames.size() == 1) {
            return fieldNames.get(0);
        }

        if (fieldNames.size() == 2) {
            return fieldNames.get(0) + " and " + fieldNames.get(1);
        }

        return fieldNames.get(0) + ", " + fieldNames.get(1) + ", and " + fieldNames.get(2);
    }

    private static boolean isMatchingAnyField(Person existingPerson, Person personToAdd) {
        return existingPerson.getName().equals(personToAdd.getName())
                || existingPerson.getPhone().equals(personToAdd.getPhone())
                || existingPerson.getEmail().equals(personToAdd.getEmail());
    }
}
