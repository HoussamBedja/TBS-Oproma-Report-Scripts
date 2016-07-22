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

id = "IMG_0206";

script = { org.jsoup.nodes.Element mainBody ->
    def message = null
    def matches = mainBody.select("textarea");

    if(matches.size() == 0){
        return null; //"No TEXTAREA elements were found.";
    }

    matches = mainBody.select("textarea[title = \"\"]");

    if(matches.size() > 0){
        return "A TEXTAREA with and empty TITLE attribute was found";
    }

    matches = mainBody.select("textarea[alt = \"\"]");

    if (matches.size() > 0) {
        return " A TEXTAREA with an empty ALT attribute was found.";
    }

    matches = mainBody.select("textarea[id]");

    if (matches.size() > 0) {
        for(org.jsoup.nodes.Element e : mathches){
            def id = e.attr("id")
            def labels = e.select("label[for = " + id + "][innerHTML ~= \\S]")

            if(labels.size() != 0){
                return "A TEXTAREA element with an empty LABEL was found.";
            } else {
                return "A TEXTAREA element with an implicit label was found.";
            }
        }
    } else {
        return "A TEXTAREA element with no associated label was found.";
    }

    return message;
}

blocking = false;
