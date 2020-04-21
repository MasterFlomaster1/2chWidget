package Base;

import com.vdurmont.emoji.EmojiManager;
import com.vdurmont.emoji.EmojiParser;

public class EmojiHandler {

    public static String resolveEmoji(String line) {
        if (EmojiManager.containsEmoji(line)) {
        }
        return line;
    }

}
