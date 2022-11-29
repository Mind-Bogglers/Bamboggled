import com.bamboggled.model.dice.BoardLetterGeneratorBig;
import com.bamboggled.model.dice.BoardLetterGeneratorSmall;
import com.bamboggled.model.dice.Die;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiceTest {

    @Test
    public void testBoardLetterGeneratorSmallRandomGenerateString() {
        BoardLetterGeneratorSmall smallGenerator = new BoardLetterGeneratorSmall();
        for (int i = 0; i < 100; i++) {
            assertEquals(smallGenerator.generateString().length(), 16);
        }
    }

    @Test
    public void testBoardLetterGeneratorBigRandomGenerateString() {
        BoardLetterGeneratorBig bigGenerator = new BoardLetterGeneratorBig();
        for (int i = 0; i < 100; i++) {
            assertEquals(bigGenerator.generateString().length(), 25);
        }
    }

    @Test
    public void testDieRoll() {
        Die die = new Die("ABCDEF");
        Character roll;
        for (int i = 0; i < 100; i++) {
            roll = (Character) die.roll();
            assert ("ABCDEF".contains(roll.toString()));
        }

    }

}

