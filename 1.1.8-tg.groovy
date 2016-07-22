/*******************************************************************************
 *  Site issue rule -- Identifies errors in a Web pages that must be addressed
 *  as part of the technical migration.
 *------------------------------------------------------------------------------
 *  Fields:
 *      - id: String. A unique identifier for the rule. The file name for the
 *        rule should be the value of id with a .groovy extension.
 *      - rule: Closure. Takes as input the main body of a Web page. Returns an
 *        error message as a String if the error checked for by the rule was
 *        detected or null otherwise.
 *      - blocking: Boolean. Default is false. Set to true if the error
 *        identified by the rule is a blocker for migration.
 ******************************************************************************/

id = "1.1.8";

script = { org.jsoup.nodes.Element mainBody ->
    def message = null;
    def matches = mainBody.select("embed");

    if (matches.size() > 0) {
        for (org.jsoup.nodes.Element element : matches) {
            def childNoembeds = element.select("noembed");
            def previousElement = element.previousElementSibling().tagName();
            def nextElement = element.nextElementSibling().tagName();

            if (childNoembeds.size() <= 0 && previousElement != "noembed" && nextElement != "noembed") {
                message = "Page contains one or more embed element without a child or adjacent noembed element.";

                break;
            }
        }
    }

    return message;
}

blocking = false;
