package edu.handong.se.markdownconverter;

/**
 * Created by Shin on 2017-12-16.
 */
public interface MDElement {
    public void accept(MDElementVisitor visitor);
}
