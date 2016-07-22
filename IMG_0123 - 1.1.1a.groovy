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

id = "IMG_0123 - 1.1.1a";

// SCRIPT FOR IMG_0123 / 1.1.1a

script = { org.jsoup.nodes.Element mainBody ->
        def matches1 = mainBody.select("img");
        def message = null;
		if (matches1.size() > 0) {
			for (org.jsoup.nodes.Element element: matches1) {
				if (element.hasAttr("abs:src")) {
					def srcVal = element.attr("abs:src");
					if (srcVal.contains("%DecorativeImageList%") || srcVal.equals("%DecorativeImageList%")) {
						if (element.hasAttr("alt")) {
							message = "All IMG elements have valid ALT attributes. (Passed)";
						}
						else {
							message = "Decorative IMG element contains no ALT attribute. (Failed)";
						}
					}
					else if (element.hasAttr("alt")) {
						def altVal = element.attr("alt");
						if (altVal.matches("^\\s*\$")) {
							if (!element.contains("a")) {
								message = "Non-decorative IMG element contains empty ALT attribute. (Failed)";
							}
						}
						else if (altVal.matches("\\w")) {
							if (!altVal.length() > 100) {
								message = "All IMG elements have valid ALT attributes. (Passed)";
							}
						}
					}
					else if (!element.hasAttr("longdesc")) {
						message = "IMG element contains no ALT attribute. (Failed)";
					}
				}
			}
		}
        return message;
	}

blocking = false;
