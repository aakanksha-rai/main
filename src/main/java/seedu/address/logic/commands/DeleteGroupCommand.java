package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.module.Module;

/**
 * Deletes a group identified using it's group code.
 */
public class DeleteGroupCommand extends Command {

    public static final String COMMAND_WORD = "group";
    public static final String DELETE_MODEL = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + DELETE_MODEL
            + ": Deletes the  group identified by the group code.\n"
            + "Parameters: " + PREFIX_MODULE + " MODULE_CODE " + PREFIX_GROUP + " GROUP_CODE\n"
            + "Example: " + COMMAND_WORD + " " + DELETE_MODEL + PREFIX_MODULE + "CS2013T" + PREFIX_GROUP + "G03";

    public static final String MESSAGE_DELETE_GROUP_SUCCESS = "Deleted Group: %1$s";
    public static final String MESSAGE_INVALID_GROUP_CODE = "There is no group with the given group code.";
    public static final String MESSAGE_INVALID_MODULE_CODE = "There is no module with the given module code.";

    private final Group group;
    private final String moduleCode;

    public DeleteGroupCommand(Group group, String moduleCode) {
        this.group = group;
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Module moduleToDeleteFrom = model.getModule(moduleCode);

        if (moduleToDeleteFrom == null) {
            throw new CommandException(MESSAGE_INVALID_MODULE_CODE);
        }

        Module module = model.getModule(moduleCode);

        if (!model.hasModule(module)) {
            throw new CommandException(MESSAGE_INVALID_MODULE_CODE);
        }

        if (!module.hasGroup(group)) {
            throw new CommandException(MESSAGE_INVALID_GROUP_CODE);
        }

        Group deletedGroup = moduleToDeleteFrom.getGroup(group.getIdentifier());
        moduleToDeleteFrom.deleteGroup(deletedGroup);
        return new CommandResult(String.format(MESSAGE_DELETE_GROUP_SUCCESS, deletedGroup));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteGroupCommand // instanceof handles nulls
                && group.equals(((DeleteGroupCommand) other).group)); // state check
    }
}
