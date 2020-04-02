package tatracker.logic.parser.session;

import static tatracker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tatracker.logic.parser.Prefixes.DATE;
import static tatracker.logic.parser.Prefixes.MODULE;
import static tatracker.logic.parser.Prefixes.SESSION_TYPE;

import tatracker.logic.commands.session.FilterSessionCommand;
import tatracker.logic.parser.ArgumentMultimap;
import tatracker.logic.parser.ArgumentTokenizer;
import tatracker.logic.parser.Parser;
import tatracker.logic.parser.ParserUtil;
import tatracker.logic.parser.exceptions.ParseException;
import tatracker.model.session.SessionPredicate;


/**
 * Parses input arguments and creates a new FilterSessionCommand object
 */
public class FilterSessionCommandParser implements Parser<FilterSessionCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterSessionCommand
     * and returns a FilterSessionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterSessionCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, DATE,
                MODULE, SESSION_TYPE);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            FilterSessionCommand.DETAILS.getUsage()));
        }

        SessionPredicate predicate = new SessionPredicate();

        if (argMultimap.getValue(DATE).isPresent()) {
            predicate.setDate(ParserUtil.parseDate(argMultimap.getValue(DATE).get()));
        }

        if (argMultimap.getValue(MODULE).isPresent()) {
            predicate.setModuleCode(argMultimap.getValue(MODULE).map(String::trim).get());
        }

        if (argMultimap.getValue(SESSION_TYPE).isPresent()) {
            predicate.setSessionType(ParserUtil.parseSessionType(argMultimap.getValue(SESSION_TYPE).get()));
        }

        if (!predicate.isAnyFieldEdited()) {
            throw new ParseException(FilterSessionCommand.DETAILS.getUsage()); // TODO: Change this message
        }

        return new FilterSessionCommand(predicate);
    }
}

