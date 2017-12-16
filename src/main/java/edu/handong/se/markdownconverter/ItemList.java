package edu.handong.se.markdownconverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shin on 2017-12-16.
 */
public class ItemList extends Structure {
    private List<Item> items;
    private String shape;
    private int depth;

    public ItemList(String shape, int depth) {
        this.items = new ArrayList<>();
        this.shape = shape;
        this.depth = depth;
    }
}
