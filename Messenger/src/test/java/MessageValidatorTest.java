import org.example.Entity.MessageEntity;
import org.example.MessageValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MessageValidatorTest {
    @Test
    void validMessageShouldPass() {
        MessageEntity msg = new MessageEntity();
        msg.setReceiver("test");
        msg.setMessage("hello");

        boolean result = MessageValidator.getInstance().isValid(msg);

        assertTrue(result);
    }

    @Test
    void emptyMessageShouldFail() {
        MessageEntity msg = new MessageEntity();
        msg.setReceiver("");
        msg.setMessage("");

        boolean result = MessageValidator.getInstance().isValid(msg);

        assertFalse(result);
    }
}
