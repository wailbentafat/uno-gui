package Logic;

import java.util.ArrayList;

public class ActionCard extends Card {
    public enum Actions {
        SKIP,REVERSE,DRAW_TWO,WILD,DRAW_FOUR_WILD; // The Values that the action attribute can hold
        static boolean isCompatible(Actions action,Colors color) { //  a function to check if a color and the action is compatible example:A Red Color Card cant have drawFourWild action because the drawFourWild is only four Black Cards so isCompatible(DRAW_FOUR_WILD,RED) will return false
            if (color == Colors.BLACK) { // checking if the color is black
                return action == Actions.DRAW_FOUR_WILD || action == Actions.WILD; // checking if the action is either drawFourWild or Wild
            } else {
                // else if the card color not black then check if the action is A SKIP OR REVERSE OR DRAW_TWO cant be WILD OR WILD_DRAW_FOUR
                return action == Actions.SKIP || action == Actions.REVERSE || action == Actions.DRAW_TWO;
            }
        }
    }
    private final Actions action;
    public ActionCard(Actions action,Colors color) {
        super(color);
        if (Actions.isCompatible(action,color)) { // checking if Color and the Action passed to the constructor is compatible
            this.action = action; // if they are compatible initialize the value of action attribute with the passed action value
        } else {
            throw new IllegalArgumentException("Please Enter A Compatible Action And Color"); // else throw an error
        }
    }
    public Actions getAction() {
        return this.action;
    }
    @Override
    public boolean isPlayable(Card lastPlayedCard) {
        if (lastPlayedCard instanceof ActionCard) { // checking if the lastPlayedCard is An action card to check if they have the same action
            return ((ActionCard) lastPlayedCard).getAction() == this.action || super.isPlayable(lastPlayedCard);
        }
        // else if the lastPlayedCard not an actionCard it means we can check if they have the same action so we need to check if they have the same color using the superClass isPlayable Function
        return super.isPlayable(lastPlayedCard);
    }
}

