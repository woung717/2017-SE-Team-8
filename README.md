# Markdown - HTML Converter
This program is intended to convert markdown text to html text. Refer following instructions for more details.

## Getting Started

### Compile, Build, Test, Coverage Report, Jar

```
$ ant [compile|build|test|cov-report|build-jar]
```

### Usage
```
$ java -jar MarkdownConverter.jar [-h|--help] -i <input_file(s)> [-o <output_file(s)>]
```

#### Input and Output Files

| Flag | Description |
| --- | --- |
| -h, --help | Show help messages. |
| -i <input_file(s)> 	| Markdown file(s) to be converted to HTML file. |
| -o <output file(s)> 	| HTML file(s) to be converted from Markdown file. | 

### Example
```
$ java -jar MarkdownConverter.jar -i a.md b.md -o a.html b.html
```
