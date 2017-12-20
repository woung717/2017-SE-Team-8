package edu.handong.se.markdownconverter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shin on 2017-12-17.
 */

public class MDParser {
    private Document doc;
    private List<Text> textBuffer;

    public MDParser(Document doc) {
        this.doc = doc;
        this.textBuffer = new ArrayList<>();
    }

    public Document parse() {
        try {
            String line;
            int listDepth = 0;
            boolean HTMLBlock = false;
            ItemList itemList = null;
            QuotedBlock quotedBlock = null;
            BufferedReader br = new BufferedReader(new FileReader(this.doc.getFile()));

            while((line = br.readLine()) != null) {
                line = line.replaceAll(".*&[^a].*]", "&amp;").replaceAll("\\\\<", "&lt;").replaceAll("\\\\>", "&gt;");

                if(line.matches(".*<pre><code>.*")) HTMLBlock = true;
                else if(line.matches(".*</code></pre>.*")) HTMLBlock = false;

                if (line.matches( "([^*]*[*]+[^*]+[*]+[^*]*)" )) {
                    String temp = "";
                    String boldType = "";
                    boolean starFlag = false;

                    for (int i = 0; i < line.length(); i++) {
                        if (line.charAt(i) == '*') {
                            if (!starFlag) {
                                boldType = (line.charAt(i + 1) == '*') ? "strong" : "em";

                                if (line.length() - 1 > i && line.charAt(i + 1) == '*') i++;
                                starFlag = true;

                                if (temp.length() > 0) {
                                    textBuffer.add(new PlainText(temp));
                                    temp = "";
                                }
                            } else {
                                if (line.length() - 1 > i && line.charAt(i + 1) == '*') i++;
                                starFlag = false;

                                if (temp.length() > 0) {
                                    textBuffer.add(new StyleText(temp, boldType));
                                    temp = "";
                                }
                            }
                        } else {
                            temp += line.charAt(i) ;
                        }
                    }

                    if (temp.length() != 0) textBuffer.add(new PlainText(temp + " "));
                } else if(line.matches(".*[`]+[^`]+[`]+.*")) {
                    String temp = "";
                    boolean codeFlag = false;

                    for(int i = 0; i < line.length(); i++) {
                        if(line.charAt(i) == '`') {
                            if(codeFlag == false) {
                                codeFlag = true;

                                if (temp.length() > 0) {
                                    textBuffer.add(new PlainText(temp));
                                    temp = "";
                                }
                            } else {
                                if(temp.length() == 0) continue;

                                codeFlag = false;

                                if (temp.length() > 0) {
                                    textBuffer.add(new StyleText(temp, "code"));
                                    temp = "";
                                }
                            }
                        } else {
                            temp += line.charAt(i) ;
                        }
                    }

                    if (temp.length() != 0) textBuffer.add(new PlainText(temp));
                } else if(line.matches("[^!]*\\[.+\\][\\[\\(].*")) {
                    String temp = "";

                    for(int i = 0; i < line.length(); i++) {
                        if(line.charAt(i) == '[') {
                            if (temp.length() > 0) {
                                textBuffer.add(new PlainText(temp));
                                temp = "";
                            }
                        } else if(line.charAt(i) == ']') {
                            String text = temp;

                            temp = "";

                            if(line.length() - 1 > i && (line.charAt(i + 1) == '[')) {
                                i += 2;

                                while(line.length() - 1 > i && line.charAt(i) != ']') {
                                    temp += line.charAt(i++);
                                }

                                if (temp.length() > 0) {
                                    textBuffer.add(new HTMLCode(text, "a", temp));
                                    temp = "";
                                }
                            }

                            if(line.length() - 1 > i && (line.charAt(i + 1) == '(')) {
                                i += 2;

                                while(line.length() - 1 > i && line.charAt(i) != ')') {
                                    temp += line.charAt(i++);
                                }

                                if (temp.length() > 0) {
                                    String[] meta = temp.split(" ");

                                    if(meta.length >= 2) {
                                        textBuffer.add(new HTMLCode(text, "a", meta[0], meta[1].substring(1, meta[1].length() - 1)));
                                    }

                                    temp = "";
                                }
                            }

                        } else {
                            temp += line.charAt(i);
                        }
                    }

                    if(temp.length() != 0) textBuffer.add(new PlainText(temp + " "));
                } else if(line.matches("\\[.+\\]:.+ [\\(\"].+[\\)\"]")) {
                    String[] meta = line.split(" ");

                    if(meta.length >= 3) {
                        String id = meta[0].replaceAll("[\\[\\]:]", "");
                        String link = meta[1];
                        String optional = meta[2].replaceAll("[\"\\(\\)]", "");

                        for(Structure struct : doc.getStructures()) {
                            HTMLCode code = findHTMLById(struct, id);

                            if(code != null) {
                                code.setLink(link);
                                code.setTitle(optional);
                                break;
                            }
                        }
                    }
                } else if(line.matches(".*!\\[.+\\]\\(.+\\).*")) {
                    String temp = "";
                    String alt = "";
                    String src = "";

                    for(int i = 0; i < line.length(); i++) {
                        if(line.length() - 1 > i + 1 && line.charAt(i) == '!' && line.charAt(i + 1) == '[') {
                            if(temp.length() > 0) {
                                textBuffer.add(new PlainText(temp));
                                temp = "";
                            }

                            i += 2;
                            while(line.charAt(i) != ']') {
                                alt += line.charAt(i++);
                            }

                            i += 2;
                            while(line.charAt(i) != ')') {
                                src += line.charAt(i++);
                            }

                            HTMLCode image = new HTMLCode();

                            image.setType("img");
                            image.setLink(src);
                            image.setTitle(alt);

                            textBuffer.add(image);
                            alt = src = "";
                        } else {
                            temp += line.charAt(i);
                        }
                    }
                } else if (line.matches( "[=]+" )) {  // setext type header level 1
                    Header header = new Header( "setext", 1 );

                    for (Text t : textBuffer) { header.addText( t ); }

                    doc.structures.add( header );
                    textBuffer.clear();
                } else if (line.matches( "[-]+" )) {   // setext type header level 2
                    Header header = new Header( "setext", 2 );

                    for (Text t : textBuffer) { header.addText( t ); }

                    doc.structures.add( header );
                    textBuffer.clear();
                } else if (line.matches( "^[#]+ .*" )) {   // atx type header
                    String[] s = line.split( " " );
                    Header header = new Header( "atx", s[0].length() );

                    header.addText( new PlainText( line.replace( "#", "" ).trim() ) );
                    doc.structures.add( header );
                    textBuffer.clear();
                } else if (line.matches( "[\\s]*" )) {
                    clearTextBuffer(doc, textBuffer, itemList, listDepth, quotedBlock);

                    if(itemList != null) itemList = null;
                    if(quotedBlock != null) quotedBlock = null;
                } else if (textBuffer.size() == 0 && line.replaceAll( " ", "" ).matches( "[*-]{3,}" )) {
                    doc.structures.add( new HorizontalRule() );
                } else if (line.matches("[\\s]*[\\*+-] .+")) {
                    if(itemList == null) {
                        itemList = new ItemList();
                    }

                    if(textBuffer.size() != 0) {
                        Item item = new Item(listDepth);

                        for(Text text : textBuffer) { item.addText(text); }

                        itemList.addItem(item);
                        textBuffer.clear();
                    }

                    int indent = getIndentLevel(line);

                    if((indent / 2) > listDepth) listDepth++;
                    else if((indent / 2) < listDepth) listDepth--;

                    textBuffer.add(new PlainText(line.replaceAll("[*+-] ", "").trim() + " "));
                } else if(line.matches("[\\s]*>.+")){
                    if(quotedBlock == null) {
                        clearTextBuffer(doc, textBuffer, itemList, listDepth, quotedBlock);

                        quotedBlock = new QuotedBlock();
                    }

                    textBuffer.add(new PlainText(line.replaceAll(">", "").trim()));
                } else {
                    if(quotedBlock != null) {
                        for(Text text : textBuffer) { quotedBlock.addText(text); }

                        textBuffer.clear();
                        doc.structures.add(quotedBlock);
                        quotedBlock = null;
                    }

                    if(HTMLBlock) line += "\n";
                    if(itemList != null) line = line.trim();
                    textBuffer.add(new PlainText(line + " "));
                }
            }
            clearTextBuffer(doc, textBuffer, itemList, listDepth, quotedBlock);
        } catch (IOException e) {
            System.out.println("File load error.");
        }

        return doc;
    }

    private HTMLCode findHTMLById(Structure struct, String id) {
        for(Text t : struct.getTexts()) {
            if(t instanceof HTMLCode && ((HTMLCode)t).getId().equals(id)) {
                return (HTMLCode)t;
            }
        }

        if(struct.getChildren().size() > 0) {
            for(Structure child : struct.getChildren()) {
                return findHTMLById(child, id);
            }
        }

        return null;
    }

    private void clearTextBuffer(Document doc, List<Text> textBuffer, ItemList itemList, int depth, QuotedBlock quotedBlock) {
        if(itemList != null) {
            if(textBuffer.size() != 0) {
                Item item = new Item(depth);

                for (Text text : textBuffer) { item.addText( text ); }

                itemList.addItem( item );
                textBuffer.clear();
            }
            doc.structures.add(itemList);
        }

        if(quotedBlock != null) {
            for(Text text : textBuffer) { quotedBlock.addText(text); }

            textBuffer.clear();
            doc.structures.add(quotedBlock);
        }

        if (textBuffer.size() != 0) {
            Block block = new Block();

            for (Text t : textBuffer) { block.addText(t); }

            doc.structures.add(block);
            textBuffer.clear();
        }
    }

    private int getIndentLevel(String line) {
        for(int i = 0; i < line.length(); i++)
            if(line.charAt(i) != ' ') return i;

        return 0;
    }

    public Document parse(Document doc) {
        this.doc = doc;

        return this.parse();
    }
}
