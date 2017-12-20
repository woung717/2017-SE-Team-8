package edu.handong.se.markdownconverter;

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
            this.bw = new BufferedWriter(new FileWriter(doc.getOutFile()));

            List<Structure> structures = doc.getStructures();

            this.bw.write("<html>" + "\n");

            this.bw.write("<head>" + "\n");
            this.bw.write("<style>" + "\n");
            this.bw.write(this.css);
            this.bw.write("</style>" + "\n");
            this.bw.write("</head>" + "\n");

            this.bw.write("<body>"  + "\n");

            for(Structure struct : structures) {
                struct.accept(this);
            }

            this.bw.write("</body>"  + "\n");

            this.bw.write("</html>");

            this.bw.close();
        } catch (IOException e) {
            System.out.print("File write error.");
        }
    }

    @Override
    public void visitStructure(Structure struct) throws IOException {
        if(struct instanceof Header) {
            Header header = (Header) struct;

            this.bw.write("<h" + Integer.toString(header.getLevel()) + ">");

            for(Text t : header.getTexts()) {
                writeText(t);
            }

            this.bw.write("</h" + Integer.toString(header.getLevel()) + ">" + "\n");
        }  else if(struct instanceof QuotedBlock) {
            QuotedBlock quotedBlock = (QuotedBlock) struct;

            this.bw.write("<blockquote>");
            this.bw.write("<p>");

            for(Text t : quotedBlock.getTexts()) {
                writeText(t);
            }

            this.bw.write("</p>");
            this.bw.write("</blockquote>" + "\n");
        } else if(struct instanceof Block && (struct.getTexts().size() != 0)) {
            Block block = (Block) struct;

            this.bw.write("<p>");

            for(Text t : block.getTexts()) {
                writeText(t);
            }

            this.bw.write("</p>" + "\n");
        } else if(struct instanceof ItemList) {
            int currentDepth = 0;
            ItemList itemList = (ItemList) struct;

            this.bw.write("<ul>" + "\n");

            for(Structure child: itemList.getChildren()) {
                Item item = (Item) child;

                if(item.getDepth() > currentDepth) {
                    currentDepth = item.getDepth();
                    this.bw.write("<ul>" + "\n");
                } else if(item.getDepth() < currentDepth) {
                    currentDepth = item.getDepth();
                    this.bw.write("</ul>" + "\n");
                }

                this.bw.write("<li>");

                for(Text t : item.getTexts()) {
                    writeText(t);
                }

                this.bw.write("</li>" + "\n");
            }

            for(int i = 0; i < currentDepth; i++)
                this.bw.write("</ul>" + "\n");

            this.bw.write("</ul>" + "\n");
        } else if(struct instanceof HorizontalRule) {
            this.bw.write("<hr />" + "\n");
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
                this.bw.write("<" + html.getType() + " src=\"" + html.getLink() + "\" alt=\"" + html.getTitle() + "\">");
            }
        } else {
            this.bw.write(t.getValue());
        }
    }

    private final String css ="body {\n" +
            "  font-family: Helvetica, arial, sans-serif;\n" +
            "  font-size: 14px;\n" +
            "  line-height: 1.6;\n" +
            "  padding-top: 10px;\n" +
            "  padding-bottom: 10px;\n" +
            "  background-color: white;\n" +
            "  padding: 30px; }\n" +
            "\n" +
            "body > *:first-child {\n" +
            "  margin-top: 0 !important; }\n" +
            "body > *:last-child {\n" +
            "  margin-bottom: 0 !important; }\n" +
            "\n" +
            "a {\n" +
            "  color: #4183C4; }\n" +
            "a.absent {\n" +
            "  color: #cc0000; }\n" +
            "a.anchor {\n" +
            "  display: block;\n" +
            "  padding-left: 30px;\n" +
            "  margin-left: -30px;\n" +
            "  cursor: pointer;\n" +
            "  position: absolute;\n" +
            "  top: 0;\n" +
            "  left: 0;\n" +
            "  bottom: 0; }\n" +
            "\n" +
            "h1, h2, h3, h4, h5, h6 {\n" +
            "  margin: 20px 0 10px;\n" +
            "  padding: 0;\n" +
            "  font-weight: bold;\n" +
            "  -webkit-font-smoothing: antialiased;\n" +
            "  cursor: text;\n" +
            "  position: relative; }\n" +
            "\n" +
            "h1 tt, h1 code {\n" +
            "  font-size: inherit; }\n" +
            "\n" +
            "h2 tt, h2 code {\n" +
            "  font-size: inherit; }\n" +
            "\n" +
            "h3 tt, h3 code {\n" +
            "  font-size: inherit; }\n" +
            "\n" +
            "h4 tt, h4 code {\n" +
            "  font-size: inherit; }\n" +
            "\n" +
            "h5 tt, h5 code {\n" +
            "  font-size: inherit; }\n" +
            "\n" +
            "h6 tt, h6 code {\n" +
            "  font-size: inherit; }\n" +
            "\n" +
            "h1 {\n" +
            "  font-size: 28px;\n" +
            "  color: black; }\n" +
            "\n" +
            "h2 {\n" +
            "  font-size: 24px;\n" +
            "  border-bottom: 1px solid #cccccc;\n" +
            "  color: black; }\n" +
            "\n" +
            "h3 {\n" +
            "  font-size: 18px; }\n" +
            "\n" +
            "h4 {\n" +
            "  font-size: 16px; }\n" +
            "\n" +
            "h5 {\n" +
            "  font-size: 14px; }\n" +
            "\n" +
            "h6 {\n" +
            "  color: #777777;\n" +
            "  font-size: 14px; }\n" +
            "\n" +
            "p, blockquote, ul, ol, dl, li, table, pre {\n" +
            "  margin: 15px 0; }\n" +
            "\n" +
            "body > h2:first-child {\n" +
            "  margin-top: 0;\n" +
            "  padding-top: 0; }\n" +
            "body > h1:first-child {\n" +
            "  margin-top: 0;\n" +
            "  padding-top: 0; }\n" +
            "  body > h1:first-child + h2 {\n" +
            "    margin-top: 0;\n" +
            "    padding-top: 0; }\n" +
            "body > h3:first-child, body > h4:first-child, body > h5:first-child, body > h6:first-child {\n" +
            "  margin-top: 0;\n" +
            "  padding-top: 0; }\n" +
            "\n" +
            "a:first-child h1, a:first-child h2, a:first-child h3, a:first-child h4, a:first-child h5, a:first-child h6 {\n" +
            "  margin-top: 0;\n" +
            "  padding-top: 0; }\n" +
            "\n" +
            "h1 p, h2 p, h3 p, h4 p, h5 p, h6 p {\n" +
            "  margin-top: 0; }\n" +
            "\n" +
            "li p.first {\n" +
            "  display: inline-block; }\n" +
            "\n" +
            "ul, ol {\n" +
            "  padding-left: 30px; }\n" +
            "\n" +
            "ul :first-child, ol :first-child {\n" +
            "  margin-top: 0; }\n" +
            "\n" +
            "ul :last-child, ol :last-child {\n" +
            "  margin-bottom: 0; }\n" +
            "\n" +
            "dl {\n" +
            "  padding: 0; }\n" +
            "  dl dt {\n" +
            "    font-size: 14px;\n" +
            "    font-weight: bold;\n" +
            "    font-style: italic;\n" +
            "    padding: 0;\n" +
            "    margin: 15px 0 5px; }\n" +
            "    dl dt:first-child {\n" +
            "      padding: 0; }\n" +
            "    dl dt > :first-child {\n" +
            "      margin-top: 0; }\n" +
            "    dl dt > :last-child {\n" +
            "      margin-bottom: 0; }\n" +
            "  dl dd {\n" +
            "    margin: 0 0 15px;\n" +
            "    padding: 0 15px; }\n" +
            "    dl dd > :first-child {\n" +
            "      margin-top: 0; }\n" +
            "    dl dd > :last-child {\n" +
            "      margin-bottom: 0; }\n" +
            "\n" +
            "blockquote {\n" +
            "  border-left: 4px solid #dddddd;\n" +
            "  padding: 0 15px;\n" +
            "  color: #777777; }\n" +
            "  blockquote > :first-child {\n" +
            "    margin-top: 0; }\n" +
            "  blockquote > :last-child {\n" +
            "    margin-bottom: 0; }\n" +
            "\n" +
            "table {\n" +
            "  padding: 0; }\n" +
            "  table tr {\n" +
            "    border-top: 1px solid #cccccc;\n" +
            "    background-color: white;\n" +
            "    margin: 0;\n" +
            "    padding: 0; }\n" +
            "    table tr:nth-child(2n) {\n" +
            "      background-color: #f8f8f8; }\n" +
            "    table tr th {\n" +
            "      font-weight: bold;\n" +
            "      border: 1px solid #cccccc;\n" +
            "      text-align: left;\n" +
            "      margin: 0;\n" +
            "      padding: 6px 13px; }\n" +
            "    table tr td {\n" +
            "      border: 1px solid #cccccc;\n" +
            "      text-align: left;\n" +
            "      margin: 0;\n" +
            "      padding: 6px 13px; }\n" +
            "    table tr th :first-child, table tr td :first-child {\n" +
            "      margin-top: 0; }\n" +
            "    table tr th :last-child, table tr td :last-child {\n" +
            "      margin-bottom: 0; }\n" +
            "\n" +
            "img {\n" +
            "  max-width: 100%; }\n" +
            "\n" +
            "span.frame {\n" +
            "  display: block;\n" +
            "  overflow: hidden; }\n" +
            "  span.frame > span {\n" +
            "    border: 1px solid #dddddd;\n" +
            "    display: block;\n" +
            "    float: left;\n" +
            "    overflow: hidden;\n" +
            "    margin: 13px 0 0;\n" +
            "    padding: 7px;\n" +
            "    width: auto; }\n" +
            "  span.frame span img {\n" +
            "    display: block;\n" +
            "    float: left; }\n" +
            "  span.frame span span {\n" +
            "    clear: both;\n" +
            "    color: #333333;\n" +
            "    display: block;\n" +
            "    padding: 5px 0 0; }\n" +
            "span.align-center {\n" +
            "  display: block;\n" +
            "  overflow: hidden;\n" +
            "  clear: both; }\n" +
            "  span.align-center > span {\n" +
            "    display: block;\n" +
            "    overflow: hidden;\n" +
            "    margin: 13px auto 0;\n" +
            "    text-align: center; }\n" +
            "  span.align-center span img {\n" +
            "    margin: 0 auto;\n" +
            "    text-align: center; }\n" +
            "span.align-right {\n" +
            "  display: block;\n" +
            "  overflow: hidden;\n" +
            "  clear: both; }\n" +
            "  span.align-right > span {\n" +
            "    display: block;\n" +
            "    overflow: hidden;\n" +
            "    margin: 13px 0 0;\n" +
            "    text-align: right; }\n" +
            "  span.align-right span img {\n" +
            "    margin: 0;\n" +
            "    text-align: right; }\n" +
            "span.float-left {\n" +
            "  display: block;\n" +
            "  margin-right: 13px;\n" +
            "  overflow: hidden;\n" +
            "  float: left; }\n" +
            "  span.float-left span {\n" +
            "    margin: 13px 0 0; }\n" +
            "span.float-right {\n" +
            "  display: block;\n" +
            "  margin-left: 13px;\n" +
            "  overflow: hidden;\n" +
            "  float: right; }\n" +
            "  span.float-right > span {\n" +
            "    display: block;\n" +
            "    overflow: hidden;\n" +
            "    margin: 13px auto 0;\n" +
            "    text-align: right; }\n" +
            "\n" +
            "code, tt {\n" +
            "  margin: 0 2px;\n" +
            "  padding: 0 5px;\n" +
            "  white-space: nowrap;\n" +
            "  border: 1px solid #eaeaea;\n" +
            "  background-color: #f8f8f8;\n" +
            "  border-radius: 3px; }\n" +
            "\n" +
            "pre code {\n" +
            "  margin: 0;\n" +
            "  padding: 0;\n" +
            "  white-space: pre;\n" +
            "  border: none;\n" +
            "  background: transparent; }\n" +
            "\n" +
            ".highlight pre {\n" +
            "  background-color: #f8f8f8;\n" +
            "  border: 1px solid #cccccc;\n" +
            "  font-size: 13px;\n" +
            "  line-height: 19px;\n" +
            "  overflow: auto;\n" +
            "  padding: 6px 10px;\n" +
            "  border-radius: 3px; }\n" +
            "\n" +
            "pre {\n" +
            "  background-color: #f8f8f8;\n" +
            "  border: 1px solid #cccccc;\n" +
            "  font-size: 13px;\n" +
            "  line-height: 19px;\n" +
            "  overflow: auto;\n" +
            "  padding: 6px 10px;\n" +
            "  border-radius: 3px; }\n" +
            "  pre code, pre tt {\n" +
            "    background-color: transparent;\n" +
            "    border: none; }" + "\n";
}
