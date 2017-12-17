package edu.handong.se.markdownconverter;

/**
 * Created by Shin on 2017-12-16.
 */
public class Text extends Structure {
    private String contents;

    public Text() {
        this.contents = "";
    }

    public Text(String contents) {
        this.contents = contents;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
