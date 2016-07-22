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

id = "IMG_0121-122 - 1.1.1";

// SCRIPT FOR IMG_0121/IMG_0122 / 1.1.1

script = { org.jsoup.nodes.Element mainBody ->
	def matches = mainBody.select("img");
	def message = null;

	if(matches.size() < 0){
		return message;
	}

	for (org.jsoup.nodes.Element element: matches) {
		def src = element.attr("src");
		def hasAlt = element.hasAttr("alt");

		if (hasAlt) {
			if (src.contains("%DecorativeImageList%") || src.equals("%DecorativeImageList%")) {
				message = id + ": Decorative IMG element contains no ALT attribute.";
			}
		} else if(!hasAlt){
			def alt = element.attr("alt");
			if (alt.matches("^\\s*\$")) {
				if ( (element.select("a").size()) == 0) {
					message = id + ": Non-decorative IMG element contains empty ALT attribute";
				}
			}
		} else if(!element.hasAttr("longdesc")){
			message = id + ": IMG element contains no ALT attribute.";
		}
	}

	return message;
}

blocking = false;
