# Sprint 1: Extensible CSV Parser 
a modified CSV parser to accomdate different data sources and ways to parse a CSV file into different data types. 


slloydge 


total estimated time to complete: 11 hours 


link to repo: https://github.com/sophialloydgeorge628/CSVParser

Citations: 

I used GPT to make recommendations about error handling and to give advice about chaining constructors, as I expected that I would need to allow for passing in both a reader object and a path to file 

Reference: OpenAI. (2024). GPT-4. (Mar 13 version). [Large language model]. https://chat.openai.com/chat/


Stack Overflow: Constructor Chaining in Java
Reference: <a href="https://stackoverflow.com/questions/285177/how-do-i-call-one-constructor-from-another-in-java">...</a>

Errors/Bugs: N/A 
- `src/main/java/Parser/Parser.java`: starter for `Parser` object and `parse(file)`; read-in from given CSV file and parses each row into `List<String>`
- `src/test/java/edu/brown/cs/student/ParserTest.java`: sample tests for testing parser parsing functionality and exception 
- `/data`: directory containing sample CSV data files 

   # CSVParser
