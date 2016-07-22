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

id = "9.5.1";

script = { 	org.jsoup.nodes.Element mainBody -> //9.5.1
            def message = null;
            def matches = mainBody.select("a, area, button, input, label, legend, textarea");

            if (matches.size() > 0){
                for (org.jsoup.nodes.Element element : matches){

                    def accessoryattr = element.attr("accessory");

                    if (accessoryattr.size() > 0){
                        if (accessoryattrtext.val().matches("^\\s*\$")){
                            message = id + ": Invalid keyboard shortcut key defined.";
                            break;
                        }else if (accessoryattr.val().length() > 3){
                            message = id + ": Too many characters defined for the keyboard access-key.";
                            break;
                        }
                    }else {
                        message = id + ": There are no keyboard shortcut keys to any of the controls in this page.";
						break;
                    }
                }
            }

            return message; 
}

blocking = false;
