package gr.dit.hua.calendar;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import biweekly.property.DateEnd;
import biweekly.property.DateStart;
import biweekly.property.Description;
import biweekly.property.Summary;
import biweekly.util.ICalDate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Calendar {

    private List<VEvent> events;

    private String[] args;

    public Calendar(){}

    public List<VEvent> getEvents() {
        return events;
    }

    public void setEvents(List<VEvent> events) {
        this.events = events;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public void displayDayEvents(String filePath) throws IOException {
        String content = readFile(filePath);
        ICalendar ical = Biweekly.parse(content).first();
        LocalDateTime dateTimeNow = LocalDateTime.now();

        if (ical != null) {
            System.out.println("Events in the iCal file:");

            for (VEvent event : ical.getEvents()) {
                LocalDateTime eventStartDateTime = convertDateStartToLocalDateTime(event.getDateStart());

                if (eventStartDateTime.toLocalDate().isEqual(dateTimeNow.toLocalDate())) {
                    Summary summary = event.getSummary();
                    DateStart dtStart = event.getDateStart();
                    DateEnd dtEnd = event.getDateEnd();
                    Description description = event.getDescription();

                    System.out.println("--------------------");
                    System.out.println("Title: " + (summary != null ? summary.getValue() : ""));
                    System.out.println("Start Date: " + (dtStart != null ? dtStart.getValue() : ""));
                    System.out.println("End Date: " + (dtEnd != null ? dtEnd.getValue() : ""));
                    System.out.println("Description: " + (description != null ? description.getValue() : ""));
                    System.out.println("--------------------");
                }
            }
        } else {
            System.out.println("Invalid or empty iCal file.");
        }
    }
    // read the ical file
    private static String readFile(String filePath) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(filePath));
        return new String(encoded);
    }

    // change the format from DateStart to LocalDateTime
    private static LocalDateTime convertDateStartToLocalDateTime(DateStart dateStart) {
        Date date = dateStart.getValue();
        return LocalDateTime.ofInstant(date.toInstant(), java.time.ZoneId.systemDefault());
    }
}
