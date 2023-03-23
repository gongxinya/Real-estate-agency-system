# Production environment

# Running the back-end server
1. Keep the port "8080" open and available
2. Download and unzip the submitted file
2. Open the file in terminal
3. Run the server
```bash
java -jar cs5031p2code-0.0.1.war
```

# Running the front-end UI
1. Navigate to the 'TravelPlannerSystemUI'
```bash
cd TravelPlannerSystemUI
```
2. Install, build, and run
```bash
npm install
npm run build
npm run preview
```
3. Open the url displayed in the terminal in a browser

# Testing the back-end server
1. Navigate into 'TravelPlannerSystem'
```bash
cd TravelPlannerSystem
```
2. Open and execute
```bash
mvn clean
mvn install
```
3. Start test
```bash
mvn test
```