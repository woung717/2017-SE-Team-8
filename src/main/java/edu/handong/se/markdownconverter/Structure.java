package edu.handong.se.markdownconverter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Shin on 2017-12-16.
 */
public class Structure implements MDElement {
    private List<Text> texts;
    private List<Structure> children;

    public Structure() {
        this.texts = new LinkedList<>();
        this.children = new LinkedList<>();
    }

    @Override
    public void accept(MDElementVisitor visitor) {
        visitor.visitStructure(this);
    }

    public void addText(Text text) {
        this.texts.add(text);
    }

    public List<Text> getTexts() {
        return this.texts;
    }

    public void addChild(Structure struct) {
        this.children.add(struct);
    }

    public List<Structure> getChildren() {
        return this.children;
    }
}