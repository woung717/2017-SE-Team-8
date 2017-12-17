package edu.handong.se.markdownconverter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Shin on 2017-12-16.
 */
public class Structure implements MDElement {
    private String type;
    private List<Text> text;
    private List<Structure> children;

    public Structure() {
        this.children = new LinkedList<>();
    }

    @Override
    public void accept(MDElementVisitor visitor) {

    }

    public String getType() {
        return this.type;
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
}