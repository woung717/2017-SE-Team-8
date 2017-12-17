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
    private List<String> contents;
    private int lines = 0;

    public Document(String file) {
        this.file = file;

        this.loadMarkdownFromFile(this.file);
    }

    @Override
    public void accept(MDElementVisitor visitor) {
        visitor.visitDocument(this);
    }

    public void loadMarkdownFromFile(String file) {
        this.contents = new LinkedList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while((line = br.readLine()) != null) {
                this.getContents().add(line);
            }
        } catch (IOException e) {
            System.out.println("File load error.");
        }

        this.lines = this.contents.size();
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public List<String> getContents() {
        return contents;
    }

    public int getLines() {
        return lines;
    }
}
