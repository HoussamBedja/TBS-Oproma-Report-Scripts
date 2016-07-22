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

id = "1.1.4";

script = { org.jsoup.nodes.Element mainBody ->
    def message = null;
    def matches = mainBody.select("applet");

    if (matches.size() > 0) {
        message = "Page contains an applet element. Applet elements will not be migrated.";
    }

    return message;
}

blocking = false;
