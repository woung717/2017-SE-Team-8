package edu.handong.se.markdownconverter;

import java.io.IOException;

/**
 * Created by Shin on 2017-12-16.
 */
public interface MDElement {
    public void accept(MDElementVisitor visitor) throws IOException;
}
