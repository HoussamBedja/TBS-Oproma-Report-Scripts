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

id = "H84";

script = { 	org.jsoup.nodes.Element mainBody -> 
            def message = null;
            def matches = mainBody.select("select");
			
			if (matches.size() > 0){
				for(org.jsoup.nodes.Element element : matches){
					def onchangeAttr = element.attr("onchange");
					if(onchangeAttr.size() > 0 && [onchangeAttr*=submit]){
						message = "FAILED: Use a separate button to allow user to confirm action."
						break;
					}
				}
			}
			
			return message;
}

blocking = false;
