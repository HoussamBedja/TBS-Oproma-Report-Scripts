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

id = "IMG_0160 - 5.5";

// SCRIPT FOR IMG_0160 / 5.5

script = { org.jsoup.nodes.Element mainBody ->
        def matches1 = mainBody.select("table[th]");
        def message = null;
		if (matches1.size() > 0) {
			for (org.jsoup.nodes.Element element: matches1) {
				if (element.hasAttr("summary")) {
					message = "Table has Summary. (Passed)";
				}
				else if (element.children.matches("caption")) {
					message = "Table has caption. (Passed)";
				}
				else if (element.hasAttr("title")) {
					def titleVal = element.attr("title");
					if (!titleVal.matches("^\\s*\$")) {
						message = "Table uses a title. (Failed)";
					}
				}
			}
        }
        return message;
    }

blocking = false;
