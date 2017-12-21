package test;

import org.junit.Test;
import static org.junit.Assert.*;

import edu.handong.se.markdownconverter.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import java.util.List;


public class MDParserTest
{

	@Test
	public void textTest1() {
		Text t = new Text();
		String testText = "Hello";
		t.setValue(testText);
		String getTestText = t.getValue();
		assertEquals(testText, getTestText);
	}

	@Test
	public void textTest2() {
		Text text2 = new Text();
		assertEquals("", text2.getValue());
	}

	@Test
	public void textTest3() {
		Text text3 = new Text("Hi");
		assertEquals("Hi", text3.getValue());
	}

	@Test
	public void styleTextTest() {
		StyleText st = new StyleText("Hello", "Italic");
		assertEquals("Hello", st.getValue());
	}

	@Test
	public void plainTextTest() {
		PlainText pt = new PlainText("Hello");
		assertEquals("Hello", pt.getValue());
	}

	@Test
	public void headerTest() {
		Header header = new Header("ext", 2);
		assertEquals("ext", header.getType());
		assertEquals(2, header.getLevel());
	}

	@Test
	public void HTMLTest() {
		HTMLCode html1 = new HTMLCode("How", "Are", "You", "?");
		HTMLCode html2 = new HTMLCode("I'm", "Fine", "Thank you");
		HTMLCode html3 = new HTMLCode();
		html3.setLink("And");
		html3.setTitle("You");
		html3.setType("?");
		assertEquals("Thank you", html2.getId());
		assertEquals("Are", html1.getType());
		assertEquals("http://You", html1.getLink());
		assertEquals("?", html1.getTitle());
	}

	@Test
	public void structureTest() {
		Structure str1 = new Structure();
		Structure str2 = new Structure();
		Text t1 = new Text("AAA");
		Text t2 = new Text("BBB");
		str1.addText(t1);
		str2.addText(t2);
		str1.addChild(str2);
		List<Text> childT = str1.getTexts();
		List<Structure> childS = str1.getChildren();
		List<Text> childST = childS.get(0).getTexts();
		assertEquals(t1.getValue(), childT.get(0).getValue());
		assertEquals(t2.getValue(), childST.get(0).getValue());
	}

	@Test
	public void printTest() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));
	    MarkdownConverter MC = new MarkdownConverter();
	    MC.printHelpMessage();
	    String expectedOutput = "Usage: java -jar MarkdownConverter.jar [-h|--help] -i <input_file(s)> [-o <output_file(s)>]\r\n Convert markdown format files to HTML files corresponding <input_file(s)> to <output_file(s)>.\r\n";
	    assertEquals(expectedOutput, outContent.toString());
	}

	@Test
	public void inputTest() {
		MarkdownConverter MC = new MarkdownConverter();
		String inputArgs1[] = {"--input", "a.md", "b.md", "--output", "a.html", "b.html"};
		MC.main(inputArgs1);

		String inputArgs2[] = {"-h"};
		MC.main(inputArgs2);

		String inputArgs3[] = {"-t"};
		MC.main(inputArgs3);

		String inputArgs4[] = {"--input", "a.md", "--output", "a.html", "b.html"};
		MC.main(inputArgs4);

		String inputArgs5[] = {"--output", "a.html", "b.html"};
		MC.main(inputArgs5);

		String inputArgs6[] = {""};
		MC.main(inputArgs6);

		String inputArgs7[] = {"--input", "a.md", "b.md"};
		MC.main(inputArgs7);

		String inputArgs8[] = {"-o", "a.html", "b.html"};
		MC.main(inputArgs8);

		String inputArgs9[] = {"--i", "a.md", "b.md"};
		MC.main(inputArgs9);

		String inputArgs10[] = {"--help"};
		MC.main(inputArgs10);

		String[] InputArgs11 = new String[0];
		MC.main(InputArgs11);

		String inputArgs12[] = {"-i"};
		MC.main(inputArgs12);
	}

	@Test
	public void fileTest() {
		String fileName1 = "sample/doc1.md";
		Document doc1 = new Document(fileName1, "doc1.html");
		MDParser MD1 = new MDParser(doc1);
		MD1.parse();
		MD1.parse(doc1);

		String fileName2 = "sample/doc2.md";
		Document doc2 = new Document(fileName2, "doc2.html");
		MDParser MD2 = new MDParser(doc2);
		MD2.parse();

		String fileName3 = "sample/doc3.md";
		Document doc3 = new Document(fileName3, "doc3.html");
		MDParser MD3 = new MDParser(doc3);
		MD3.parse();

		String fileName4 = "sample/doc4.md";
		Document doc4 = new Document(fileName4, "doc4.html");
		MDParser MD4 = new MDParser(doc4);
		MD4.parse();

		String fileName5 = "sample/sampledoc.md";
		Document doc5 = new Document(fileName5, "sampledoc.html");
		MDParser MD5 = new MDParser(doc5);
		MD5.parse();

		String fileName6 = "sample/missingsink.md";
		Document doc6 = new Document(fileName6, "missingsink.html");
		MDParser MD6 = new MDParser(doc6);
		MD6.parse();
	}


	@Test
	public void plainVisitorTest() {
		PlainHTMLVisitor pv = new PlainHTMLVisitor();
		Document doc1 = new Document("sample/sampledoc.md", "sampledoc.html");

		MDParser parser = new MDParser(doc1);

		parser.parse().accept(pv);
	}

	@Test
	public void getIndentLevelTest() {
		MDParser parser = new MDParser(null);

		assertEquals(0, parser.getIndentLevel(""));
	}

	@Test
	public void acceptTest() {
		Text text = new Text();

		text.accept(null);
	}

	@Test
	public void IOExceptionTest() {
		Document doc = new Document("", "");
		PlainHTMLVisitor visitor = new PlainHTMLVisitor();

		visitor.visitDocument(doc);

		MDParser parser = new MDParser(doc);

		parser.parse();
	}


	public static void main(String [] args) {
		MDParserTest md = new MDParserTest();
	}

}