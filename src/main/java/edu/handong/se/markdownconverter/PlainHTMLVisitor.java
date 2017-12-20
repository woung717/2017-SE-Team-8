package edu.handong.se.markdownconverter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by Shin on 2017-12-20.
 */
public class PlainHTMLVisitor implements MDElementVisitor {
    private BufferedWriter bw;

    @Override
    public void visitDocument(Document doc) {
        try {
            this.bw = new BufferedWriter(new FileWriter(doc.getFile()));

            List<Structure> structures = doc.getStructures();

            for(Structure struct : structures) {
                struct.accept(this);
            }

            this.bw.close();
        } catch (IOException e) {
            System.out.print("File write error.");
        }
    }

    @Override
    public void visitStructure(Structure struct) {

    }

}
