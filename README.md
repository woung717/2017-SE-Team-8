
# Markdown - HTML Converter
This program is intended to convert markdown text to html text. Detailed instructions are given in the Usage below.

## Geting Started

### Usage
* No Argument  
If you do not include any arguments, the program will display a help message.

* Using Argumet  
__*% MarkdownConverter [option] -i <input_file(s)> [-o <output_file(s)>]*__
  * option  :  (-t, --type)=(plain|=stylish|=slide)  -  Generate HTML files as given style (default: plain)  
  * -i <input_file(s)>  -  a (/ list of) input Markdown file(s) to be converted to HTML file.
  * -o <output file(s)>  -  a (/ list of) output HTML file(s) to be converted from Markdown file.  

### Exceptions
  1. Invalid input  
  **If user inputs invalid input, the program shows error message**  
  1. Empty List of Input file  
  **If list of Input file is empty, the program shows "I/O error" message**  
  1. Inconsistency between number of input files and number of output files   
  **If the number of input files differs from the number of output files, the program shows "I/O error" message**
