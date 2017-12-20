package edu.handong.se.markdownconverter;

/**
 * Created by Shin on 2017-12-17.
 */
public class HTMLCode extends Text {
    private String id;
    private String link;
    private String title;
    private String type;

    public HTMLCode() {
        super();
    }

    public HTMLCode(String text, String type, String link, String title) {
        super(text);

        this.type = type;
        this.link = link;
        this.title = title;
    }

    public HTMLCode(String text, String type, String id) {
        super(text);

        this.type = type;
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }
}
