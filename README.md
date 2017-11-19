# Markdown - HTML Converter
This program is intended to convert markdown text to html text. Refer following instructions for more details.

## Getting Started

### Usage
```
$ MarkdownConverter [option] -i <input_file(s)> [-o <output_file(s)>]
```
#### Option  

| Flag | Description |
| --- | --- |
| -t, --type=plain\|stylish\|slide	 | Generate HTML files as given style (default: plain) 	|

#### Input and Output Files

| Flag | Description |
| --- | --- |
| -i <input_file(s)> 	| Markdown file(s) to be converted to HTML file. |
| -o <output file(s)> 	| HTML file(s) to be converted from Markdown file. | 

## Example
```
$ MarkdownConverter -t=stylish -i a.md b.md -o a.html b.html
```
