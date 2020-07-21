import com.guseyn.broken_xml.ParsedXML;
import com.guseyn.broken_xml.XmlDocument;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class SymbolChecksTest {
    @Test
    void testJustOpeningBracket() {
        final ParsedXML xml = new ParsedXML("<");
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 0);
    }

    @Test
    void testJustClosingBracket() {
        final ParsedXML xml = new ParsedXML(">");
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 0);
    }

    @Test
    void testJustQuestionMark() {
        final ParsedXML xml = new ParsedXML("?");
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 0);
    }

    @Test
    void testJustBackSlash() {
        final ParsedXML xml = new ParsedXML("/");
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 0);
    }

    @Test
    void testJustDash() {
        final ParsedXML xml = new ParsedXML("-");
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 0);
    }

    @Test
    void testClosingCommentStuff() {
        final ParsedXML xml = new ParsedXML("-->");
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 0);
    }

    @Test
    void testCommentInComment() {
        final ParsedXML xml = new ParsedXML("<!-- comment <!-- comment -->");
        XmlDocument document = xml.document();
        assertEquals(document.comments().size(), 1);
        assertEquals(document.comments().get(0).text(), " comment <!-- comment ");
    }

    @Test
    void testTwoDashes() {
        final ParsedXML xml = new ParsedXML("--");
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 0);
    }

    @Test
    void testExclamationMarkWithTwoDashes() {
        final ParsedXML xml = new ParsedXML("!--");
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 0);
    }

    @Test
    void testRandomWithTwoDashes() {
        final ParsedXML xml = new ParsedXML("/--");
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 0);
    }

    @Test
    void testOpeningCommentWithRandomAndTwoDashes() {
        final ParsedXML xml = new ParsedXML("<%--");
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 0);
    }

    @Test
    void testOpeningCommentWithBackSlashAndTwoDashes() {
        final ParsedXML xml = new ParsedXML("</--");
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 0);
    }

    @Test
    void testQuestionMarkWithClosingBracket() {
        final ParsedXML xml = new ParsedXML("?>");
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 0);
    }

    @Test
    void testQuestionMarkWithRandomSymbol() {
        final ParsedXML xml = new ParsedXML("?|");
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 0);
    }

    @Test
    void testQuestionMarkWithOpeningBracket() {
        final ParsedXML xml = new ParsedXML("<?");
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 0);
    }

    @Test
    void testJustX() {
        final ParsedXML xml = new ParsedXML("x");
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 0);
    }

    @Test
    void testJustM() {
        final ParsedXML xml = new ParsedXML("m");
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 0);
    }

    @Test
    void testJustL() {
        final ParsedXML xml = new ParsedXML("l");
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 0);
    }

    @Test
    void testJustXM() {
        final ParsedXML xml = new ParsedXML("xm");
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 0);
    }

    @Test
    void testJustXL() {
        final ParsedXML xml = new ParsedXML("xl");
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 0);
    }

    @Test
    void testJustXRandom() {
        final ParsedXML xml = new ParsedXML("xrr");
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 0);
    }

    @Test
    void testJustMRandom() {
        final ParsedXML xml = new ParsedXML("rmr");
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 0);
    }

    @Test
    void testJustLRandom() {
        final ParsedXML xml = new ParsedXML("rrl");
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 0);
    }

    @Test
    void testOpenBracketQuestionMarkRandomML() {
        final ParsedXML xml = new ParsedXML("<?rml");
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 0);
    }

    @Test
    void testOpenBracketQuestionMarkRandomMRandom() {
        final ParsedXML xml = new ParsedXML("<?rmr");
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 0);
    }

    @Test
    void testXML() {
        final ParsedXML xml = new ParsedXML("xml");
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 0);
    }

    @Test
    void testXMLWithBrackets() {
        final ParsedXML xml = new ParsedXML("<?xml?>");
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 0);
    }

    @Test
    void testXMLWithOpeningBracket() {
        final ParsedXML xml = new ParsedXML("<xml");
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 0);
    }

    @Test
    void testXMLWithClosingBracket() {
        final ParsedXML xml = new ParsedXML("xml>");
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 0);
    }

    @Test
    void testXMLWithBracketsAndSpaces() {
        final ParsedXML xml = new ParsedXML("< ? xml ? >");
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 0);
    }

    @Test
    void testNoXMLWithBracketsAndSpaces() {
        final ParsedXML xml = new ParsedXML("<?noxml?>");
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 0);
    }

    @Test
    void testXMLWithQuestionMarkAndRandomSymbol() {
        final ParsedXML xml = new ParsedXML("/?xml");
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 0);
    }

    @Test
    void testOpeningElementInOpeningElement() {
        final ParsedXML xml = new ParsedXML("<elm<elm>");
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 1);
        assertEquals(document.roots().get(0).name(), "elm<elm");
    }

    @Test
    void testClosingElementInClosingElement() {
        final ParsedXML xml = new ParsedXML("<elm></</elm>");
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 1);
        assertEquals(document.roots().get(0).name(), "elm");
        assertEquals(document.roots().get(0).texts().get(0).value(), "");
    }

    @Test
    void testElementTextFollowedBySpace() {
        final ParsedXML xml = new ParsedXML("<elm>text < /elm>");
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 1);
        assertEquals(document.roots().get(0).name(), "elm");
        assertEquals(document.roots().get(0).texts().get(0).value(), "text < /elm>");
    }

    @Test
    void testOnCurrentElementIsNull() {
        final ParsedXML xml = new ParsedXML("</elm>");
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 0);
    }

    @Test
    void testAttributeValueWithoutAttributeName() {
        final ParsedXML xml = new ParsedXML("<elm='value'>");
        XmlDocument document = xml.document();
        assertEquals(document.roots().get(0).name(), "elmvalue");
    }

    @Test
    void testCommentLikeStuff() {
        final ParsedXML xml = new ParsedXML("!!--");
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 0);
    }
}
