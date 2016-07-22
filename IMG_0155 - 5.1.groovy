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

id = "IMG_0155 - 5.1";

// SCRIPT FOR IMG_0155 / 5.1

script = { org.jsoup.nodes.Element mainBody ->
        def matches1 = mainBody.select("table[border=0], table:not([border])");
        def message = null;
        if (matches1.size() > 0) {
			for (org.jsoup.nodes.Element element: matches1) {
				if (element.children.matches("th")) {
					message = "Table uses TH to markup headers. (Passed)";
				}
				else if (element.children.matches("input,select,textarea,img,table,button,td[string-length(text()) >= 100]")) {
					message = "Page contains only layout tables. (Passed)";
				} 
			}
		}
		def matches2 = mainBody.select("table[border > 0]");
		if (matches2.size() > 0) {
				for (org.jsoup.nodes.Element element: matches2) {
					if (element.children.matches("th")) {
						message = "Table uses TH to markup headers. (Passed)";
					}
					else {
						message = "Data table does not use TH to markup headers. (Failed)";
					}
				}
		}
        return message;
    }

blocking = false;
