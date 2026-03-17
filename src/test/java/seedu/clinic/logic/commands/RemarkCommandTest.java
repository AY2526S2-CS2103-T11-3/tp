package seedu.clinic.logic.commands;

import static seedu.clinic.logic.commands.CommandTestUtil.assertCommandFailure;

import org.junit.jupiter.api.Test;

import seedu.clinic.model.Model;
import seedu.clinic.model.ModelManager;

public class RemarkCommandTest {

    private final Model model = new ModelManager();

    @Test
    public void execute() {
        assertCommandFailure(new RemarkCommand(), model, RemarkCommand.MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
