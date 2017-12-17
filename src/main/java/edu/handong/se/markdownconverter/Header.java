package edu.handong.se.markdownconverter;

/**
 * Created by Shin on 2017-12-16.
 */
public class Header extends Structure {
    private String type;
    private int level;

    public Header(String setext, int i) {
        this.type = type;
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
