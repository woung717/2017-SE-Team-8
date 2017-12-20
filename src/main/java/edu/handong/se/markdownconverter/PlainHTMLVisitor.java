package edu.handong.se.markdownconverter;

import javax.swing.text.html.HTML;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by Shin on 2017-12-20.
 */
public class PlainHTMLVisitor implements MDElementVisitor {
    private BufferedWriter bw;

    @Override
    public void visitDocument(Document doc) {
        try {
            this.bw = new BufferedWriter(new FileWriter(doc.getFile()));

            List<Structure> structures = doc.getStructures();

            for(Structure struct : structures) {
                struct.accept(this);
            }

            this.bw.close();
        } catch (IOException e) {
            System.out.print("File write error.");
        }
    }

    @Override
    public void visitStructure(Structure struct) throws IOException {
        if(struct instanceof Header) {
            Header header = (Header) struct;

            this.bw.write("<h" + Integer.toString(header.getLevel()) + ((header.getLevel() == 2) ? " style=\"font-size: 2em; border-bottom: 1px solid silver; paddding-bottom: 5px;\"": "") + ">");

            for(Text t : header.getTexts()) {
                writeText(t);
            }

            this.bw.write("</h" + Integer.toString(header.getLevel()) + ">" + "\n");
        } else if(struct instanceof Block) {
            Block block = (Block) struct;

            this.bw.write("<p>");

            for(Text t : block.getTexts()) {
                writeText(t);
            }

            this.bw.write("</p>" + "\n");
        } else if(struct instanceof ItemList) {
            ItemList itemList = (ItemList) struct;

            this.bw.write("<ul>" + "\n");
                for(Structure child: itemList.getChildren()) {
                    Item item = (Item) child;

                    
                }
            this.bw.write("</ul>" + "\n");
        }
    }

    private void writeText(Text t) throws IOException {
        if(t instanceof StyleText) {
            StyleText st = (StyleText) t;

            this.bw.write("<" + st.getType() + ">");
            this.bw.write(st.getValue());
            this.bw.write("</" + st.getType() + ">");
        } else if(t instanceof HTMLCode) {
            HTMLCode html = (HTMLCode) t;

            if(html.getType().equals("a")) {
                this.bw.write("<" + html.getType() + " href=\"" + html.getLink() + "\" title=\"" + html.getTitle() +"\">");
                this.bw.write(html.getValue());
                this.bw.write("</" + html.getType() + ">");
            } else if(html.getType().equals("img")) {
                this.bw.write("<" + html.getType() + "src=\"" + html.getLink() + "\" alt=\"" + html.getTitle() + "\">");
            }
        } else {
            this.bw.write(t.getValue());
        }
    }

}
