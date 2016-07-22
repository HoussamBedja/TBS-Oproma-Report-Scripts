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

id = "7.3.1";

script = { 	org.jsoup.nodes.Element mainBody -> //1.1.4
            def message = null;
            def matches = mainBody.select("marquee");
			
			if (matches.size() > 0){
				message = "MARQUEE element found, not used in migration."
			}
			
			return message;
			
}

blocking = false;
