package edu.handong.se.markdownconverter;

import java.io.IOException;

/**
 * Created by Shin on 2017-12-16.
 */
public interface MDElementVisitor {
    public void visitDocument(Document doc);
    public void visitStructure(Structure struct) throws IOException;
}
