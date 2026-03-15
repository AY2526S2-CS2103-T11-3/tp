package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s name or phone matches the supplied find criteria.
 */
public class PersonMatchesFindCriteriaPredicate implements Predicate<Person> {
    private final List<String> nameKeywords;
    private final Optional<Phone> phone;

    public PersonMatchesFindCriteriaPredicate(List<String> nameKeywords, Optional<Phone> phone) {
        requireNonNull(nameKeywords);
        requireNonNull(phone);
        this.nameKeywords = List.copyOf(nameKeywords);
        this.phone = phone;
    }

    @Override
    public boolean test(Person person) {
        boolean matchesName = !nameKeywords.isEmpty() && nameKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
        boolean matchesPhone = phone.map(person.getPhone()::equals).orElse(false);
        return matchesName || matchesPhone;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PersonMatchesFindCriteriaPredicate)) {
            return false;
        }

        PersonMatchesFindCriteriaPredicate otherPredicate = (PersonMatchesFindCriteriaPredicate) other;
        return nameKeywords.equals(otherPredicate.nameKeywords) && phone.equals(otherPredicate.phone);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("nameKeywords", nameKeywords)
                .add("phone", phone)
                .toString();
    }
}
