package edu.handong.se.markdownconverter;

/**
 * Created by Shin on 2017-12-16.
 */
public class Item extends Structure {
    private int depth;

    public Item(int depth) {
        this.depth = depth;
    }

    public int getDepth() {
        return depth;
    }
}
