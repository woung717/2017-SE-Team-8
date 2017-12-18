package edu.handong.se.markdownconverter;

/**
 * Created by Shin on 2017-12-16.
 */
public class Text implements MDElement {
    private String value;

    public Text() {
        this.value = "";
    }

    public Text(String value) {
        this.value = value;
    }

    @Override
    public void accept(MDElementVisitor visitor) {
        ;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
