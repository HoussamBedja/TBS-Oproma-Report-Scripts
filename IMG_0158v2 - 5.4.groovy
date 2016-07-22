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

id = "IMG_0158 - 5.4";

// SCRIPT FOR IMG_0158 / 5.4

script = { org.jsoup.nodes.Element mainBody ->
        def matches1 = mainBody.select("table[border=0], table:not([border])");
		if (matches1.size() > 0) {
			for (org.jsoup.nodes.Element element: matches1) {
				def matches2 = element.select("tr > th");
				def message = null;
				if (matches2.size() > 0) {
					def elementChildren = element.select("input,select,textarea,img,table,button,td[string-length(text())>=100]"); // what is string-length(text())???
					if (elementChildren.size() > 0)
						message = "Apparent layout table uses TH elements. (Failed)";
				}
			}
		}
        return message;
    }

blocking = false;
