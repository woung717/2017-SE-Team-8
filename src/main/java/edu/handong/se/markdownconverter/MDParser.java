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
    private String file;
    private List<String> textBuffer;

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
                if(line.matches("[=]+")) {
                    Header header = new Header("setext", 1);

                    header.addChild(new PlainText(textBuffer.remove(textBuffer.size() - 1)));
                    doc.struct.add(header);
                } else if(line.matches("[-]+")) {
                    Header header = new Header("setext", 2);

                    header.addChild(new Text(textBuffer.remove(textBuffer.size() - 1)));
                    doc.struct.add(header);
                } else {
                    textBuffer.add(line);
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
