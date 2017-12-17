package edu.handong.se.markdownconverter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Shin on 2017-12-16.
 */

public class Document implements MDElement {
    private String file;
    public List<Structure> struct;

    public Document(String file) {
        this.file = file;
        this.struct = new LinkedList<>();
    }

    public void accept(MDElementVisitor visitor) {
         visitor.visitDocument(this);
    }

}
