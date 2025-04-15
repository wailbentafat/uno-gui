package Logic;
// the value card class is the class used to instantiate a card that hold a value from 0 to 9
public class ValueCard extends Card {
    private final int value;
    public ValueCard(int value,Colors color) {
        super(color);
        if (value < 0 || value > 9) { // checking if the passed value is not between 0 and 9 in this case throw and error
            throw new IllegalArgumentException("Please Enter A Valid Value");
        }
        this.value = value;
    }
    public int getValue() { // getter function to get the value of the value attribute because the value attribute is private
        return this.value;
    }
    @Override
    public boolean isPlayable(Card lastPlayedCard) {
        if (lastPlayedCard instanceof ValueCard) { // checking the passed card is an instance of a value card to check if they have the same value or color
            return ((ValueCard) lastPlayedCard).getValue() == this.value || super.isPlayable(lastPlayedCard);
        }
        // if the lastPlayedCard is not a valueCard than check if they have the same color using the superClass isPlayable methode which check if the lastPlayedCard and this Card have the same color
        return super.isPlayable(lastPlayedCard);
    }

}
