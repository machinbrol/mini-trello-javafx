package mvvm.command;

import model.*;

public class CreateCardCommand extends Command {
    private final BoardFacade boardFacade;
    private final Column column;
    private Card card;
    private Memento<Card> memento;

    public CreateCardCommand(Column column, BoardFacade boardFacade) {
        this.boardFacade = boardFacade;
        this.column = column;
    }

    @Override
    public void execute() {
        card = boardFacade.addCard(column);
        memento = card.save(MemType.ADD);
    }

    @Override
    void undo() {
        card.restore(memento);
    }

    @Override
    boolean isRestorable() {
        return card.isRestorable(memento);
    }


    @Override
    public String toString() {
        return "Ajout d'une carte à la " + column;
    }
}
