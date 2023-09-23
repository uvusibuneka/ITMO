package commandRealization.specialCommandRealization;

import common.LocalizationKeys;
import common.descriptions.CommandDescription;
import modules.InteractiveMode;

public class HelpCommandRealization extends SpecialCommandRealization {

    public HelpCommandRealization(InteractiveMode interactiveMode) {
        super(new CommandDescription("help", LocalizationKeys.HELP_COMMAND), interactiveMode);
    }

    @Override
    public void execution() {
        interactiveMode.printHelp();
    }
}
