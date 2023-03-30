# Terminal client development environment

## Getting started
1. Navigate to the directory containing `src/` of the terminal client
```bash
cd EstateAgencySystemTerminal
```
2. Compile and run using maven
```bash
mvn compile exec:java -Dexec.mainClass="stacs.estate.cs5031p3code.TerminalClientMain"
```
3. To run unit tests
```bash
mvn clean
mvn test
```