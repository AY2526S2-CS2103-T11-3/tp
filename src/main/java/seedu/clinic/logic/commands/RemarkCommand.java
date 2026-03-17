package seedu.clinic.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.clinic.model.Model;

/**
 * Changes the remark of an existing person in the clinic book.
 */
public class RemarkCommand extends Command {

    public static final String COMMAND_WORD = "remark";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult("Hello from remark");
    }
}
