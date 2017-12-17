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
    }

    @Override
    public void visitStructure(Structure struct) {
    }
}
