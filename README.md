# calendarApp

This project is implemented using Java 17 and Maven. In your target folder you need the jar file in order to 
run the app. So we run : 
```bash 
mvn clean install
``` 
to create the jar file of the project. Also we use the
the following maven dependencies : 
- ical4j library which is used to read and write ical files
- biweekly library which is used to write/parse the ical file.

# run the app

To run the app you have 2 main functionalities : 

1) View the events of calendar base on : day-week-month-pastday-pastweek-pastmonth-due-todo
   For example let's assume the .ics file is named as mycal.ics, go to your project directory and run :
   ```bash
   java -jar target/CalendarApp-1.0-SNAPSHOT.jar day mycal.ics
   ```
   with this command you need to have the .ics file
   inside the project folder otherwise you need to provide the absolute path to find the file
   ```bash
   java -jar target/CalendarApp-1.0-SNAPSHOT.jar day "your-absolute-path-to-the-.ics"
   ```
   and so you can see the events of the current day. Instead of the word day you can use one of the words day, week, month
   ,pastday, pastweek, pastmonth, due, todo to view more events.

3) Create a new Calendar and add events to it or if the Calendar already exists update it with the new events. To do so go to your
   project directory (again for this example the file's name is mycal.ics don't forget to use your own file name instead) and run :
   ```bash
   java -jar target/CalendarApp-1.0-SNAPSHOT.jar day mycal.ics
   ```
   now you provide only the name of the .ics file if it is inside your project folder otherwise again you need to use the
   absolute path. No need for other arguments, if the file does not exist the app will create it and add the events you create
   otherwise it will use the existed file.
   
   
