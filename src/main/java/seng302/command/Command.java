package seng302.command;

import seng302.Environment;

/**
 * Interface for a command.
 *
 * @author adg62
 */
public interface Command {
    void execute(Environment env);
}
