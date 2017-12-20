package edu.handong.se.markdownconverter;

/**
 * Created by Shin on 2017-12-17.
 */
public class StyleText extends Text {
    private String type;

    public StyleText(String value, String type) {
        super(value);

        this.type = type;
    }

    public String getType() {
        return type;
    }
}
