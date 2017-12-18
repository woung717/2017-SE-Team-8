package edu.handong.se.markdownconverter;

/**
 * Created by Shin on 2017-12-16.
 */
public class ItemList extends Structure {
    public ItemList() {
    }

    public void addItem(Item item) {
        this.addChild(item);
    }
}
