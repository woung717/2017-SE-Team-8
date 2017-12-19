package edu.handong.se.markdownconverter;

/**
 * Created by Shin on 2017-12-17.
 */
public class HTMLCode extends Text {
    private String id;
    private String link;
    private String optional;

    public HTMLCode(String text, String link, String optional) {
        super(text);

        this.link = link;
        this.optional = optional;
    }

    public HTMLCode(String text, String id) {
        super(text);

        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setOptional(String optional) {
        this.optional = optional;
    }
}
