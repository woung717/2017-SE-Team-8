package edu.handong.se.markdownconverter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Shin on 2017-12-16.
 */

public class Document implements MDElement {
    private String file;
    private List<Structure> AST;

    public Document(String file) {
        this.file = file;
        this.AST = new LinkedList<>();
    }

    public void accept(MDElementVisitor visitor) {
         visitor.visitDocument(this);
    }

}
