package tatracker.logic.parser.student;

import static tatracker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tatracker.logic.parser.CliSyntax.PREFIX_GROUP;
import static tatracker.logic.parser.CliSyntax.PREFIX_MODULE;

import java.util.stream.Stream;

import tatracker.logic.commands.student.FilterStudentViewCommand;
import tatracker.logic.parser.ArgumentMultimap;
import tatracker.logic.parser.ArgumentTokenizer;
import tatracker.logic.parser.Parser;
import tatracker.logic.parser.ParserUtil;
import tatracker.logic.parser.Prefix;
import tatracker.logic.parser.exceptions.ParseException;

/**
 * Parse input arguments and create a new FilterStudentViewCommand object
 */
public class FilterStudentViewCommandParser implements Parser<FilterStudentViewCommand> {


    /**
     * Parses the given {@code String} of arguments in the context of the FilterSessionCommand
     * and returns a FilterSessionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterStudentViewCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MODULE,
                PREFIX_GROUP);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                    FilterStudentViewCommand.MESSAGE_USAGE));
        }

        String moduleCode = "";
        String groupCode = "";

        if (argMultimap.getValue(PREFIX_MODULE).isPresent()) {
            moduleCode = (ParserUtil.parseValue(argMultimap.getValue(PREFIX_MODULE).get())).toUpperCase();
        }

        if (argMultimap.getValue(PREFIX_GROUP).isPresent()) {
            groupCode = (ParserUtil.parseValue(argMultimap.getValue(PREFIX_GROUP).get())).toUpperCase();
        }

        return new FilterStudentViewCommand(moduleCode, groupCode);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}