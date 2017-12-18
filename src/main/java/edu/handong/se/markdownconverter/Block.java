package edu.handong.se.markdownconverter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Shin on 2017-12-16.
 */
public class Block extends Structure {
    private List<Text> texts;

    public Block() {
        this.texts = new LinkedList<>();
    }

    public void addText(Text text) {
        this.texts.add(text);
    }
}
