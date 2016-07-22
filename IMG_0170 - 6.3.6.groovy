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

id = "";

// SCRIPT FOR IMG_0170 / 6.3.6

script = { org.jsoup.nodes.Element mainBody ->
	def matches1 = mainBody.select("applet");
    def message = null;
    if (matches1.size() > 0) {
	for (org.jsoup.nodes.Element element: matches1) {
		if (element.content.matches("\\S")) {
			if (element.hasAttr("alt")) {
				def altVal = element.attr("alt");
				if (altVal.matches("\\S")) {
					message = "All APPLET elements have content and a valid ALT attribute. (Passed)";
                }
            }
			else {
				message = "APPLET element does not have an ALT attribute. (Failed)";
			}
        }
        else {
			message = "APPLET element does not contain content. (Failed)";
        }
    }
    return message;
}

blocking = false;
