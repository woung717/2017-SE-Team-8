package edu.handong.se.markdownconverter;

/**
 * Created by Shin on 2017-12-19.
 */
public class Image extends Structure {
    private String alt;
    private String link;

    public Image(String alt, String link) {
        this.alt = alt;
        this.link = link;
    }
}
