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

    public Structure getChild(int index) {
        return this.children.get(index);
    }

    public int getChildrenSize() {
        return this.children.size();
    }

    public void addChild(Structure struct) {
        this.children.add(struct);
    }

    public void addText(Text text) {
        this.texts.add(text);
    }
}