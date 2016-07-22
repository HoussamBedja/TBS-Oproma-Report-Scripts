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

id = "";

// SCRIPT FOR IMG_0172 / 6.3.8

script = { org.jsoup.select.Elements doc ->
    def matches1 = doc.select("a");
        def message = null;
        if (matches1.size() > 0) {
          for (org.jsoup.nodes.Element element : matches){
			if (element.hasAttr("href")) {
				def hrefText = matches1.attr("abs:href");
				if (hrefText.matches("\\.pdf\$")) {
					def pdfVal = doc.select("download acrobat, download adobe, download pdf viewer");
					if (pdfVal == null) {
						message = "Text indicating where to download required special reader for PDF files is not present.";        
					} 
				}
				else if (hrefText.matches("\\.docx?\$")) {
					if (docVal == null) {
						message = "Text indicating where to download required special reader for Word (.doc) files is not present.";         
					} 
				}
				else if (hrefText.matches("\\.pptx?\$")) {
					def pptVal = doc.select("download powerpoint, download microsoft powerpoint");
					if (pptVal == null) { 
						message = "Text indicating where to download required special reader for Powerpoint (.ppt) files is not present.";
					}
				}
				else if (hrefText.matches("\\.xlsx?\$")) {
					def xlsVal = doc.select("download excel, download microsoft excel");
					 if (xlsVal == null) {
						message = "Text indicating where to download required special reader for Excel (.xls) files is not present.";
					}
				}
			}
		}
    }
	return message;
}

blocking = false;
