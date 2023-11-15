package gr.dit.hua.calendar;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import biweekly.property.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Calendar {

    private List<VEvent> events;

    private List<VEvent> sortedEvents;

    public Calendar(){}

    // this is the method that is called from the others to display each time the right events
    public void displayEvents(List<VEvent> events, String message){

        System.out.println("\n*** " + message + " ***\n");

        // get the important fields from each event and print them
        for (VEvent event : events) {

            Summary summary = event.getSummary();
            DateStart dtStart = event.getDateStart();
            DateEnd dtEnd = event.getDateEnd();
            Description description = event.getDescription();
            Status status = event.getStatus();

            System.out.println("--------------------");
            System.out.println("Title: " + summary.getValue());
            System.out.println("Start Date: " + dtStart.getValue());
            if (dtEnd!=null){
                System.out.println("End Date: " + dtEnd.getValue());
            }
            if (status!=null){
                System.out.println("Status: " + status.getValue());
            }
            System.out.println("Description: " + description.getValue());
            System.out.println("--------------------");
        }

    }

    // fetch the events from the current day
    public void displayDayEvents(String filePath) throws IOException {
        String content = readFile(filePath);
        ICalendar ical = Biweekly.parse(content).first();
        LocalDateTime dateTimeNow = LocalDateTime.now();
        events = new ArrayList<>();

        if (ical != null) {
            System.out.println("Events in the iCal file:");

            for (VEvent event : ical.getEvents()) {
                LocalDateTime eventStartDateTime = convertDateStartToLocalDateTime(event.getDateStart());

                if (eventStartDateTime.toLocalDate().isEqual(dateTimeNow.toLocalDate())) {
                    events.add(event);
                }
            }
            // call the method that sorts the eventlist
            sortedEvents = EventLists.sortByStartDate(events);

            displayEvents(sortedEvents, "Today's events");
        } else {
            System.out.println("Invalid or empty iCal file.");
        }
    }

    // view the events from the current day till the end of the week
    public void displayWeekEvents(String filePath) throws IOException {
        String content = readFile(filePath);
        ICalendar ical = Biweekly.parse(content).first();
        LocalDateTime dateTimeNow = LocalDateTime.now();
        events = new ArrayList<>();

        if (ical != null) {

            LocalDateTime startOfWeek = dateTimeNow.with(dateTimeNow.getDayOfWeek()).withHour(0).withMinute(0).withSecond(0).withNano(0);
            LocalDateTime endOfWeek = dateTimeNow.with(DayOfWeek.SUNDAY).plusDays(1).withHour(23).withMinute(59).withSecond(59);

            for (VEvent event : ical.getEvents()) {
                LocalDateTime eventStartDateTime = convertDateStartToLocalDateTime(event.getDateStart());

                if (eventStartDateTime.isAfter(startOfWeek) && eventStartDateTime.isBefore(endOfWeek) ) {
                    events.add(event);
                }
            }
            // call the method that sorts the eventlist
            sortedEvents = EventLists.sortByStartDate(events);

            displayEvents(sortedEvents, "Week's events");
        } else {
            System.out.println("Invalid or empty iCal file.");
        }

    }

    // view the events from the current month
    public void displayMonthEvents(String filePath) throws IOException {
        String content = readFile(filePath);
        ICalendar ical = Biweekly.parse(content).first();
        LocalDateTime dateTimeNow = LocalDateTime.now();
        events = new ArrayList<>();


        if (ical != null) {
            LocalDateTime startOfMonth = dateTimeNow.withDayOfMonth(dateTimeNow.getDayOfMonth()).withHour(0).withMinute(0).withSecond(0).withNano(0);
            LocalDateTime endOfMonth = dateTimeNow.withDayOfMonth(dateTimeNow.getMonth().length(dateTimeNow.toLocalDate().isLeapYear())).withHour(23).withMinute(59).withSecond(59);

            for (VEvent event : ical.getEvents()) {
                LocalDateTime eventStartDateTime = convertDateStartToLocalDateTime(event.getDateStart());

                if (eventStartDateTime.isAfter(startOfMonth) && eventStartDateTime.isBefore(endOfMonth) ) {
                    events.add(event);
                }
            }
            // call the method that sorts the eventlist
            sortedEvents = EventLists.sortByStartDate(events);

            displayEvents(sortedEvents, "Month's events");
        }else {
            System.out.println("Invalid or empty iCal file.");
        }
    }

    // display the events from the start of the day till now
    public void displayPastDay(String filePath) throws IOException {
        String content = readFile(filePath);
        ICalendar ical = Biweekly.parse(content).first();
        LocalDateTime dateTimeNow = LocalDateTime.now();
        events = new ArrayList<>();

        if (ical != null){
            LocalDateTime startOfDay = dateTimeNow.withDayOfMonth(dateTimeNow.getDayOfMonth()).withHour(0).withMinute(0).withSecond(0).withNano(0);

            for (VEvent event : ical.getEvents()) {
                LocalDateTime eventStartDateTime = convertDateStartToLocalDateTime(event.getDateStart());

                if (eventStartDateTime.isAfter(startOfDay) && eventStartDateTime.isBefore(dateTimeNow) ) {
                    events.add(event);
                }
            }
            // call the method that sorts the eventlist
            sortedEvents = EventLists.sortByStartDate(events);

            displayEvents(sortedEvents, "Pastday's events");
        }else {
            System.out.println("Invalid or empty iCal file.");
        }
    }

    // display the events from the start of the week till now
    public void displayPastWeek(String filePath) throws IOException {
        String content = readFile(filePath);
        ICalendar ical = Biweekly.parse(content).first();
        LocalDateTime dateTimeNow = LocalDateTime.now();
        events = new ArrayList<>();

        if (ical != null){
            LocalDateTime startOfWeek = dateTimeNow.with(DayOfWeek.MONDAY).withHour(0).withMinute(0).withSecond(0).withNano(0);

            for (VEvent event : ical.getEvents()) {
                LocalDateTime eventStartDateTime = convertDateStartToLocalDateTime(event.getDateStart());

                if (eventStartDateTime.isAfter(startOfWeek) && eventStartDateTime.isBefore(dateTimeNow) ) {
                    events.add(event);
                }
            }
            // call the method that sorts the eventlist
            sortedEvents = EventLists.sortByStartDate(events);

            displayEvents(sortedEvents, "Pastweek's events");
        }else {
            System.out.println("Invalid or empty iCal file.");
        }
    }

    // display the events from the start of the month till now
    public void displayPastMonth(String filePath) throws IOException {
        String content = readFile(filePath);
        ICalendar ical = Biweekly.parse(content).first();
        LocalDateTime dateTimeNow = LocalDateTime.now();
        events = new ArrayList<>();

        if (ical != null){
            LocalDateTime startOfMonth = dateTimeNow.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);

            for (VEvent event : ical.getEvents()) {
                LocalDateTime eventStartDateTime = convertDateStartToLocalDateTime(event.getDateStart());

                if (eventStartDateTime.isAfter(startOfMonth) && eventStartDateTime.isBefore(dateTimeNow) ) {
                    events.add(event);
                }
            }
            // call the method that sorts the eventlist
            sortedEvents = EventLists.sortByStartDate(events);

            displayEvents(sortedEvents, "Pastday's events");
        }else {
            System.out.println("Invalid or empty iCal file.");
        }
    }

    // display the events that are task which are not completed and the deadline has not expired
    public void displayToDoEvents(String filePath) throws IOException {
        String content = readFile(filePath);
        ICalendar ical = Biweekly.parse(content).first();
        LocalDateTime dateTimeNow = LocalDateTime.now();
        events = new ArrayList<>();

        if (ical != null){
            // for each event check if the status is not null to understand that it is a task and that it's value is "not completed"
            for (VEvent event : ical.getEvents()) {

                if (event.getStatus() != null && (event.getStatus().getValue().equalsIgnoreCase("not completed"))){

                    if(event.getDateEnd() != null){
                        LocalDateTime eventsEndDateTime = convertDateEndToLocalDateTime(event.getDateEnd());
                        // check if the event's dateTime is after the current
                        if (dateTimeNow.isBefore(eventsEndDateTime)){
                            events.add(event);
                        }
                    }
                }
            }
            // call the method that sorts the eventlist
            sortedEvents = EventLists.sortByStartDate(events);

            displayEvents(sortedEvents, "Todo event-tasks");
        }else {
            System.out.println("Invalid or empty iCal file.");
        }

    }

    // display the events that are "tasks" which are not completed and the deadline has expired
    public void displayDueEvents(String filePath) throws IOException {
        String content = readFile(filePath);
        ICalendar ical = Biweekly.parse(content).first();
        LocalDateTime dateTimeNow = LocalDateTime.now();
        events = new ArrayList<>();

        if (ical != null){
            // for each event check if the status is not null to understand that it is a task and that it's value is "not completed"
            for (VEvent event : ical.getEvents()) {

                if (event.getStatus() != null && (event.getStatus().getValue().equalsIgnoreCase("not completed"))){

                    if(event.getDateEnd() != null){
                        LocalDateTime eventsEndDateTime = convertDateEndToLocalDateTime(event.getDateEnd());
                        // check if the event's dateTime is after the current
                        if (dateTimeNow.isAfter(eventsEndDateTime)){
                            events.add(event);
                        }
                    }
                }
            }
            // call the method that sorts the eventlist
            sortedEvents = EventLists.sortByStartDate(events);

            displayEvents(sortedEvents, "Todo event-tasks");
        }else {
            System.out.println("Invalid or empty iCal file.");
        }

    }

    // method for create a new event and the file if it does not exist
    public void createEvent(String filePath) throws IOException {
        boolean notFinished = true;

        Scanner scanner = new Scanner(System.in);
        String answer;

        // create new file object
        File file = new File(filePath);

        // check if the file exists and if not create it
        if (!file.exists()) {
            Files.createFile(file.toPath());
            System.out.println("The file created: " + file);
        } else {
            System.out.println("The file exists: " + filePath);
        }

        // parse the ical file and if the calendar is null create one
        ICalendar ical = Biweekly.parse(file).first();
        if (ical == null) {
            ical = new ICalendar();
        }

        // procedure for the user to create new events from the terminal
        while (notFinished) {

            VEvent event = new VEvent();

            System.out.println("\nCreating a new Event ... ");

            // title of the event
            System.out.print("Enter a title for the event: ");
            event.setSummary(scanner.nextLine());

            // description of the event
            System.out.print("Enter a description for the event: ");
            event.setDescription(scanner.nextLine());

            // start date of the event
            System.out.print("Enter the start date and time of the event (yyyy-MM-dd HH:mm): ");
            boolean validStartDate = false;
            LocalDateTime startDateTime = null;

            while (!validStartDate) {
                try {
                    String startDateTimeStr = scanner.nextLine();
                    startDateTime = LocalDateTime.parse(startDateTimeStr, java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    validStartDate = true;
                } catch (DateTimeParseException e) {
                    System.err.print("Error parsing date and time. Please enter the correct format (yyyy-MM-dd HH:mm --> year-month-day hours:minutes): ");
                }
            }

            event.setDateStart(new DateStart(Date.from(startDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant())));

            // end date for the events that need it (tasks, appointments)
            System.out.print("Enter the end date and time of the event (optionally, press Enter to skip): ");
            String endDateTimeStr = scanner.nextLine();

            if (!endDateTimeStr.isEmpty()) {
                boolean validEndDate = false;
                LocalDateTime endDateTime = null;

                while (!validEndDate) {
                    try {
                        endDateTime = LocalDateTime.parse(endDateTimeStr, java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                        validEndDate = true;
                    } catch (DateTimeParseException e) {
                        System.err.print("Error parsing date and time. Please enter the correct format (yyyy-MM-dd HH:mm --> year-month-day hours:minutes): ");
                    }
                }

                event.setDateEnd(new DateEnd(Date.from(endDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant())));
            }

            // status for the events that need it (tasks)
            System.out.print("Enter the status of the event in case it is a task (C for Completed, N for Not Completed, or press Enter to skip): ");
            String status = scanner.nextLine().toUpperCase();

            boolean validStatus = false;

            while (!validStatus) {

                if (!status.equals("C") && !status.equals("N")) {
                    System.out.print("Please select one of the two options for the status of the event: 'C' / 'N' ");
                    status = scanner.nextLine().toUpperCase();
                } else {
                    validStatus = true;
                }
            }
            if ("C".equals(status)) {
                event.setStatus(Status.create("Completed"));
            } else {
                event.setStatus(Status.create("Not Completed"));
            }

            // add the new event to the iCalendar
            ical.addEvent(event);

            // write the iCalendar back to the file
            Biweekly.write(ical).go(file);

            System.out.println("\nSuccessfully created a new event!");

            System.out.print("\nDo you want to add more events? (y/n): ");
            answer = scanner.nextLine();

            if (answer.equalsIgnoreCase("n")) {
                notFinished = false;
            }
        }
    }

    // read the ical file
    private static String readFile(String filePath) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(filePath));
        return new String(encoded);
    }

    // change the format from DateStart to LocalDateTime
    static LocalDateTime convertDateStartToLocalDateTime(DateStart dateStart) {
        Date date = dateStart.getValue();
        return LocalDateTime.ofInstant(date.toInstant(), java.time.ZoneId.systemDefault());
    }

    // change the format from DateEnd to LocalDateTime
    static LocalDateTime convertDateEndToLocalDateTime(DateEnd dateEnd){
        Date date = dateEnd.getValue();
        return LocalDateTime.ofInstant(date.toInstant(), java.time.ZoneId.systemDefault());
    }
}
