package edu.handong.se.markdownconverter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Shin on 2017-12-17.
 */
public class HTMLVisitor implements MDElementVisitor {
    private int currentLine = 0;
    public List<Structure> ASTs;

    public HTMLVisitor() {
        this.ASTs = new LinkedList<>();
    }

    @Override
    public void visitDocument(Document doc) {
        for(int i = 0; i < nLine; i++) {
            String line;

            if(line.matches("[=]+")) {
                Header header = new Header("setext", 1);

                header.addChild(new Text(textBuffer.remove(textBuffer.size() - 1)));
                root.addChild(header);
            } else if(line.matches("[-]+")) {
                Header header = new Header("setext", 2);

                header.addChild(new Text(textBuffer.remove(textBuffer.size() - 1)));
                root.addChild(header);
            } else {
                textBuffer.add(line);
            }
        }

        ASTs.add(root);
    }

    @Override
    public void visitStructure(Structure struct) {
    }
}
