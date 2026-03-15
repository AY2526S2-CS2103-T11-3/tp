package seedu.clinic.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.getErrorMessageForDuplicatePrefixes;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.PersonMatchesFindCriteriaPredicate;
import seedu.address.model.person.Phone;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingPrefixes_throwsParseException() {
        assertParseFailure(parser, "Alice Bob",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validNameArgs_returnsFindCommand() {
        FindCommand expectedFindCommand = new FindCommand(
                new PersonMatchesFindCriteriaPredicate(Arrays.asList("Alice", "Bob"), Optional.empty()));
        assertParseSuccess(parser, " n/Alice Bob", expectedFindCommand);
        assertParseSuccess(parser, " \n n/Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_validPhoneArgs_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new PersonMatchesFindCriteriaPredicate(Collections.emptyList(),
                        Optional.of(new Phone("98765432"))));
        assertParseSuccess(parser, " p/98765432", expectedFindCommand);
    }

    @Test
    public void parse_validNameAndPhoneArgs_returnsFindCommand() {
        FindCommand expectedFindCommand = new FindCommand(new PersonMatchesFindCriteriaPredicate(
                Arrays.asList("Alice", "Bob"), Optional.of(new Phone("98765432"))));
        assertParseSuccess(parser, " n/Alice Bob p/98765432", expectedFindCommand);
    }

    @Test
    public void parse_emptyNameValue_throwsParseException() {
        assertParseFailure(parser, " n/   ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPhoneValue_throwsParseException() {
        assertParseFailure(parser, " p/abc", Phone.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_duplicatePrefixes_throwsParseException() {
        assertParseFailure(parser, " n/Alice n/Bob", getErrorMessageForDuplicatePrefixes(PREFIX_NAME));
        assertParseFailure(parser, " p/12345678 p/98765432", getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));
    }
}
