package edu.handong.se.markdownconverter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Shin on 2017-12-17.
 */

public class MDParser {
    private String file;
    private List<Text> textBuffer;

    public MDParser(String file) {
        this.file = file;
        this.textBuffer = new ArrayList<>();
    }

    public Document parse() {
        Document doc = new Document(this.file);

        try {
            String line;
            int listDepth = 0;
            ItemList itemList = null;
            QuotedBlock quotedBlock = null;
            BufferedReader br = new BufferedReader(new FileReader(this.file));

            while((line = br.readLine()) != null) {
                line = line.replaceAll("\\\\<", "&lt;").replaceAll("\\\\>", "&gt;");

                if (line.matches( "(([^*]*[*]{1}[^*]+[*]{1}[^*]*)|([^*]*[*]{2}[^*]+[*]{2}[^*]*))" )) {
                    String temp = "";
                    String boldType = "";
                    boolean starFlag = false;

                    for (int i = 0; i < line.length(); i++) {
                        if (line.charAt(i) == '*') {
                            if (!starFlag) {
                                boldType = (line.charAt(i + 1) == '*') ? "strong" : "emphasize";

                                if (line.length() - 1 > i && line.charAt( i + 1 ) == '*') i++;
                                starFlag = true;

                                if (temp.length() > 0) textBuffer.add( new Text( temp ) );
                                temp = "";
                            } else {
                                if (line.length() - 1 > i && line.charAt( i + 1 ) == '*') i++;
                                starFlag = false;

                                if (temp.length() > 0) textBuffer.add( new StyleText( temp, boldType ) );
                                temp = "";
                            }
                        } else {
                            temp += line.charAt( i );
                        }
                    }

                    if (temp.length() != 0) textBuffer.add( new PlainText( temp ) );
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
                } else if (line.replaceAll( " ", "" ).matches( "[*-]{3,}" )) {
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

                    textBuffer.add(new PlainText(line.replaceAll("[*+-] ", "").trim()));
                } else if(line.matches(">.+")){
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

                    textBuffer.add(new PlainText(line));
                }
            }
            clearTextBuffer(doc, textBuffer, itemList, listDepth, quotedBlock);
        } catch (IOException e) {
            System.out.println("File load error.");
        }

        return doc;
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

            for (Text t : textBuffer) { block.addText( t ); }

            doc.structures.add( block );
            textBuffer.clear();
        }
    }

    private int getIndentLevel(String line) {
        for(int i = 0; i < line.length(); i++)
            if(line.charAt(i) != ' ') return i;

        return 0;
    }

    public Document parse(String file) {
        this.file = file;

        return this.parse();
    }
}
