package seedu.clinic.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.clinic.logic.commands.exceptions.CommandException;
import seedu.clinic.model.Model;
import seedu.clinic.model.person.Person;

/**
 * Base add command with reusable duplicate contact-field warning logic.
 */
public abstract class AddPersonWithDuplicateWarningCommand<T extends Person>
        extends Command implements ConfirmableCommand {
    private final T personToAdd;
    private boolean isConfirmed;

    protected AddPersonWithDuplicateWarningCommand(T personToAdd) {
        requireNonNull(personToAdd);
        this.personToAdd = personToAdd;
    }

    protected abstract Class<T> getPersonType();

    protected abstract String getPersonLabel();

    protected abstract String getSuccessMessage();

    /**
     * Hook for subclasses to reject duplicates outside contact-field checks.
     */
    protected void validateAdditionalConstraints(Model model) throws CommandException {
        requireNonNull(model);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        validateAdditionalConstraints(model);

        DuplicatePersonFieldsMatch<T> duplicateMatch =
                DuplicatePersonFieldsMatch.find(model, personToAdd, getPersonType());

        if (duplicateMatch.hasExactDuplicate()) {
            model.updateFilteredPersonList(duplicateMatch.asPredicate());
            throw new CommandException(duplicateMatch.getExactDuplicateMessage(getPersonLabel()));
        }

        if (duplicateMatch.hasAnyMatch() && !isConfirmed) {
            model.updateFilteredPersonList(duplicateMatch.asPredicate());
            return new CommandResult(duplicateMatch.getWarningMessage(getPersonLabel()),
                    false, false, true);
        }

        model.addPerson(personToAdd);
        return new CommandResult(String.format(getSuccessMessage(), personToAdd));
    }

    @Override
    public void confirm() {
        isConfirmed = true;
    }
}
