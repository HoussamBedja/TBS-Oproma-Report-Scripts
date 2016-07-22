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

id = "IMG_0207";

script = { org.jsoup.nodes.Element mainBody ->
    def matches = mainBody.select("a");
    def titles = null;

    if(matches.size() == 0){
        return null; "No Anchor elements found on the page."
    }

    matches = mainBody.select("a[href][title]");

    if(matches.size() > 0){
        titles = matches.select("[title ~= \"\"");
        if(titles.size() > 0){
            return "text element contains an empty TITLE attribute and link text that may not clearly identify the tag"
        }

    } else {
        return "text element contains link text that may not clearly identify the target of each link.";
    }

    return null;
}

blocking = false;
