package edu.handong.se.markdownconverter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            BufferedReader br = new BufferedReader(new FileReader(this.file));

            while((line = br.readLine()) != null) {
                if(line.matches("(([^*]*[*]{1}[^*]+[*]{1}[^*]*)|([^*]*[*]{2}[^*]+[*]{2}[^*]*))")) { // matches aa*aa*aa or aa**aa**aa
                    String temp = "";
                    String boldType = "";
                    boolean starFlag = false;

                    for(int i = 0; i < line.length(); i++) {
                        if(line.charAt(i) == '*') {
                            if(!starFlag) {
                                boldType = (line.charAt(i + 1) == '*') ? "strong" : "emphasize";
                                if(line.charAt(i + 1) == '*') i++;

                                starFlag = true;

                                if(temp.length() > 0) textBuffer.add(new Text(temp));
                                temp = "";
                            } else {
                                if(line.charAt(i + 1) == '*') i++;

                                starFlag = false;

                                if(temp.length() > 0) textBuffer.add(new StyleText(temp, boldType));
                                temp = "";
                            }
                        } else {
                            temp += line.charAt(i);
                        }
                    }
                } else if(line.matches("[=]+")) {  // setext type header level 1
                    Header header = new Header("setext", 1);

                    for(Text t : textBuffer) { header.addText(t); }
                    doc.struct.add(header);
                } else if(line.matches("[-]+")) {   // setext type header level 2
                    Header header = new Header("setext", 2);

                    for(Text t : textBuffer) { header.addText(t); }
                    doc.struct.add(header);
                } else if(line.matches("^[#]+ .*")) {   // atx type header
                    String[] s = line.split(" ");
                    Header header = new Header("atx", s[0].length());

                    header.addText(new PlainText(line.replace("#", "").trim()));
                    doc.struct.add(header);
                } else if(line.matches("\\s+")) {

                } else {
                }
            }
        } catch (IOException e) {
            System.out.println("File load error.");
        }

        return doc;
    }

    public Document parse(String file) {
        this.file = file;

        return this.parse();
    }
}
