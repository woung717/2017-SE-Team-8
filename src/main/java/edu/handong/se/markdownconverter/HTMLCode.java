package edu.handong.se.markdownconverter;

/**
 * Created by Shin on 2017-12-17.
 */
public class HTMLCode extends Text {
    private String id;
    private String link;
    private String optional;

    public HTMLCode(String id, String link, String optional) {
        this.id = id;
        this.link = link;
        this.optional = optional;
    }
}
