package Base;

import org.apache.commons.text.StringEscapeUtils;

public class HtmlStringHandler {

    public String handleString(String line) {
        return StringEscapeUtils.unescapeHtml4(line);
    }

}
