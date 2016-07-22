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

id = "1.1.2";

script = { org.jsoup.nodes.Element mainBody ->
    def message = null;
    def matches = mainBody.select("input").not("[type='hidden']")
                                          .not("[type='submit']")
                                          .not("[type='reset']")
                                          .not("[type='button']")
                                          .not("[type='image']");
    // NOTE: There should be a separate rule to ensure input[type='image'] has
    // the appropriate alt attribute.

    if (matches.size() > 0) {
        for (org.jsoup.nodes.Element element : matches) {
            def idAttr = element.attr("id");
            def labels = null;
            def titleAttr = element.attr("title");

            if (idAttr.length() > 0) {
                labels = mainBody.select("label[for='" + idAttr + "']");
            }

            if ((labels == null || labels.size() == 0) && (titleAttr.length() <= 0)) {
                message = "Page contains one or more input elements with neither a label or title attribute.";

                break;
            }
        }
    }

    return message;
}

blocking = false;
