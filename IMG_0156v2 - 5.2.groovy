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


id = "IMG_0156 - 5.2";

// SCRIPT FOR IMG_0156 / 5.2

script = { org.jsoup.nodes.Element mainBody ->
        def matches1 = mainBody.select("table:not([table])"); // ???
        def message = null;
        if (matches1.size() >  0) {
			for (org.jsoup.nodes.Element element: matches1) {
			def elementChildren = element.select("thead,tfoot,tbody,col,colgroup,td[axis], td[scope], td[headers]");
				if (elementChildren.size() <= 0) {
					def thChildren = element.select("th[colspan][rowspan]");
					if (thChildren.size() > 0) {
						for (org.jsoup.nodes.Element thChild: thChildren) {
							def colBorder = Integer.parseInt(element.attr("colspan")); 
							def rowBorder = Integer.parseInt(element.attr("rowspan"));
							if (colBorder > 1 || rowBorder > 1) {
								message = "Table has headers spanning multiple columns or rows, but no additional markup to clarify table structure. (Failed)";
							}
						}
					}
				}
			}
		}
        return message;
    }

blocking = false;
