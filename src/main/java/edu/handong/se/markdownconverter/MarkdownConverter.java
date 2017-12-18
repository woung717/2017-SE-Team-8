package edu.handong.se.markdownconverter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MarkdownConverter {

    public static void main(String[] args) {
        List<String> inputFiles = new ArrayList<>();
        List<String> outputFiles = new ArrayList<>();
        List<Document> docs = new LinkedList<>();

        if(args.length < 1) {
            printHelpMessage();
        } else {
            boolean inputFlag = false, outputFlag = false;
            boolean helpFlag = false;

            for(String arg : args) {
                if(arg.startsWith("-")) {
                    if(arg.equals("-h") || arg.equals("--h")) {
                        helpFlag = true;
                        break;
                    } else if(arg.equals("-i") || arg.equals("--input")) {
                        outputFlag = false;
                        inputFlag = true;
                        continue;
                    } else if(arg.equals("-o") || arg.equals("--output")) {
                        inputFlag = false;
                        outputFlag = true;
                        continue;
                    } else {
                        System.out.println("Invalid input flag: " + arg);
                        helpFlag = true;
                        break;
                    }
                }

                if(inputFlag) {
                    inputFiles.add(arg);
                } else if(outputFlag) {
                    outputFiles.add(arg);
                } else {
                    System.out.println("Invalid input: " + arg);
                    helpFlag = true;
                    break;
                }
            }

            if(helpFlag) {
                printHelpMessage();
            } else {
                if ((inputFiles.size() == 0) ||
                        ((outputFiles.size() != 0) && (inputFiles.size() != outputFiles.size()))) {
                    System.out.println( "I/O error: <input_file(s)> are not given or number of input files and output files are not matched." );
                    printHelpMessage();
                } else {
                    if(outputFiles.size() == 0) {
                        for (String input : inputFiles) {
                            outputFiles.add( input + ".html" );
                        }
                    }

                    for(int i = 0; i < inputFiles.size(); i++) {
                        MDParser parser = new MDParser(inputFiles.get(i));

                        docs.add(parser.parse());
                    }
                }
            }
        }
    }

    public static void printHelpMessage() {
        System.out.println("Usage: MarkdownConverter -i <input_file(s)> [-o <output_file(s)>]");
        System.out.println(" Convert markdown format files to HTML files corresponding <input_file(s)> to <output_file(s)>.");
    }

    public static void baseConversion(String[] args) {
        try {
            if (args[1] != null && args[2] != null) {
                File mdFile = new File(args[1]);
                File htmlFile = new File(args[2]);

                BufferedReader fRead = new BufferedReader(new FileReader(mdFile));
                BufferedWriter fWrite = new BufferedWriter(new FileWriter(htmlFile));

                String HTMLStart = "<!DOCTYPE html> \r\n" + "<html> \r\n" +
                        "<head> \r\n" + "<meta charset=\"EUC-KR\"> \r\n" + "<title>"
                        + args[2] + "</title> \r\n" + "</head> \r\n" + "<body> \r\n";
                String HTMLEnd = "</body> \r\n" + "</html>";
                String line = null;

                fWrite.write(HTMLStart);
                while((line = fRead.readLine()) != null) {
                    fWrite.write(line);
                    fWrite.write("\r\n");
                }

                fWrite.write(HTMLEnd);
                fWrite.close();
                fRead.close();
            }
            else {
                System.out.println("Input Error!");
            }
        } catch (Exception e) {
            System.out.println("File Error!");
        }
    }
}
