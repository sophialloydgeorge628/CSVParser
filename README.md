# Extensible CSV Parser 
A modified CSV parser to accomdate different data sources and ways to parse a CSV file into various data types. 

**Sophia Lloyd George**
slloydge 

**Total estimated time to complete**: 11 hours 

**link to repo**: https://github.com/sophialloydgeorge628/CSVParser

**Citations:**

OpenAI. (2024). GPT-4. (Mar 13 version). [Large language model]. https://chat.openai.com/chat/

Motivation: I used GPT to make recommendations about error handling and to give advice about chaining constructors, as I expected that I would need to allow for passing in both a reader object and a path to file 

Anonymous. (2019, August 26). *How do I call one constructor from another in Java?* Stack Overflow. https://stackoverflow.com/questions/285177/how-do-i-call-one-constructor-from-another-in-java

Motivation: I referred to Stack Overflow to determine how I could go about constructor chaining, which became important since I wanted to allow for passing in both a reader object and a path to a file. 

**Design Choices** 
Starting with the stencil code, I modified the CSV parser to accomodate different data sources and to convert parsed content into a wider array of data types to allow for more flexibility.

 In Parser.java, I made the fields for the Parser class private, added a field called "Creator from Row<T>", which took a generic parameter T describing what the parser should return for each row, integrated constructor chaining so that the first constructor (taking a file path and a CreatorFromRow<T> object, calls the second one, which takes a reader object (where the source could vary, such as files or strings) and a CreatorFromRow<T? object, and added getParsedContent (a getter that returns a list of the parsedContent, which will comprise all rows of the CSV converted into objects of the specified type T). 

I did not modify the existing parse code; as it stands, the BufferedReader wraps the Reader to ensure methodical reading of the lines from the CSV, which is then split into an array of strings based on a specific RegEx (includes splitting along commas that are inside quotes); for each row, the parsed content is transformed into a specific generic type T through the creator.create() method. 

I also added a few new classes (Skip Header Creator, Trivial Creator, Float Creator), as well as a new interface, CreatorFromRow. Each class is intended to accomdate different parsing circumstances: trivial creator keeps the list of parsed content as list of strings; Float Creator converts the parsed content into a list of floats; and SkipHeaderCreator skips the header in generating parsed content. The interface CreatorFromRow is implemented by all of the aforementioned classes and contains the create method. 

For testing, I tested to ensure general functionality for uniform CSV files (assuming nothing out of the ordinary in terms of formatting), tested to ensure sizing of the malformed CSV matched my expectations, tested IOException for trivialcreator, tsted to see if parsing was successful given two sequential commas, tested normal behavior on inputs that exhibit different functionality (e.g., CSV data with and without column headers), testing for size of bird/habitat data, testing to ensure that the right contents were in the CSV file, testing CSV data in different reader types (e.g. String Reader and File Reader), testing factory failure exception, testing float creator, and testing skip header. 

Errors/Bugs: N/A 


