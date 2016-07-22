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

id = "IMG_0205";

script = { org.jsoup.nodes.Element mainBody ->
    def message = null;
    def matches = mainBody.select("select");

    if(matches.size() == 0){
        return null; // "No SELECT elements were found.";
    }

    matches = mainBody.select("select[title = \"\"]");

    if(matches.size() > 0 ){
        return "A element with and empty TITLE attribute was found";
    }

    matches = mainBody.select("select[alt = \"\"]");

    if (matches.size() > 0) {
        return " A element with an empty ALT attribute was found.";
    }

    matches = mainBody.select("select[id]");

    if (matches.size() > 0) {
        for(org.jsoup.nodes.Element e : mathches){
            def id = e.attr("id");
            def labels = e.select("label[for = " + id + "][innerHTML ~= \\S");

            if(labels.size() != 0){
                return "A SELECT element with an empty LABEL was found.";
            } else {
                return "A SELECT element with an implicit label was found.";
            }
        }
    } else {
        return "A SELECT element with no associated label was found.";
    }

    return message;
}

blocking = false;
