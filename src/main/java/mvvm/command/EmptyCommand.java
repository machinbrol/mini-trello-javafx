package mvvm.command;

public class EmptyCommand extends Command {
    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }

    @Override
    boolean isRestorable() {
        return false;
    }

    @Override
    public String toString() {
        return "";
    }
}
