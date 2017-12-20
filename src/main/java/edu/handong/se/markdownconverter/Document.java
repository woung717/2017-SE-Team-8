package edu.handong.se.markdownconverter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Shin on 2017-12-16.
 */

public class Document implements MDElement {
    private String file;
    private String outFile;
    public List<Structure> structures;

    public Document(String file, String outFile) {
        this.file = file;
        this.outFile = outFile;
        this.structures = new LinkedList<>();
    }

    public void accept(MDElementVisitor visitor) {
         visitor.visitDocument(this);
    }

    public List<Structure> getStructures() {
        return this.structures;
    }

    public String getFile() {
        return file;
    }

    public String getOutFile() {
        return outFile;
    }
}
