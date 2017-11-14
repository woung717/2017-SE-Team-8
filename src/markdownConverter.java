import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class markdownConverter {

	public static void main(String[] args) {

		boolean mainLoop = true;
		while(mainLoop) {
			
			if(args.length < 1) {
				System.out.println("");
				System.out.println("If you want to read help message, please input \"-help\"");
				System.out.println("");
				System.out.println("MD-to-HTML Converter ('Q' or 'q' to quit)");
				System.out.println("=============================");
				String newargs = (new Scanner(System.in)).nextLine();
				args = newargs.split(" ");
			}

			switch(args[0]) {

			case "-c":
			case "--convert":
				if(args.length < 3) {
					args[0] = "";
					continue;
				}
				else if (args.length == 3) {
					baseConversion(args);
					System.out.println("");
					System.out.println("Plain Conversion Complete.");
					break;
				}
				
				switch(args[3]) {

				case "--stylish":
					baseConversion(args);
					System.out.println("");
					System.out.println("Stylish Conversion Complete.");
					break;
					
				case "--slide":
					baseConversion(args);
					System.out.println("");
					System.out.println("Slide Conversion Complete.");
					break;
					
				case "--plain":
				default:
					baseConversion(args);
					System.out.println("");
					System.out.println("Plain Conversion Complete.");
					break;
				}
			break;

			case "-h":
			case "--help":
				System.out.println("");
				System.out.println("Input Form: [first] [second] [third] [fourth] / ex) -c README.md README.html -plain ");
				System.out.println("In the first input, you can choose option \"-h(-help)\" or \"-c(-convert)\". ");
				System.out.println("If you input \"-c\" in first input, specify an input MD file name in second input. ");
				System.out.println("And specify the name of generated HTML file in third input. ");
				System.out.println("You can specify a conversion option in fourth input as one of the following three: \r\n" + 
						"\"-plain (default)\", \"-stylish\", \"-slide\"");
				break;
				
			case "q":
			case "Q":
				mainLoop = false;
				break;
				
			default:
				System.out.println("");
				System.out.println("Wrong Input!");
				System.out.println("If you want to read help message, please input \"-help\"");
				break;
			}
			
			if (args[0].equals("Q") || args[0].equals("q")) { 
				System.out.println("Bye!");
				
			}
			else {
				System.out.println("");
				System.out.println("MD-to-HTML Converter ('Q' or 'q' to quit)");
				System.out.println("=============================");
				String newargs = (new Scanner(System.in)).nextLine();
				args = newargs.split(" ");
			}
		}
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
