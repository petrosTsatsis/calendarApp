package gr.dit.hua.calendar;

import biweekly.component.VEvent;
import biweekly.component.VTodo;

import java.time.LocalDateTime;
import java.util.List;

import static gr.dit.hua.calendar.Calendar.convertDateStartToLocalDateTime;
import static gr.dit.hua.calendar.Calendar.convertDateDueToLocalDateTime;

public class EventLists {

    public EventLists() {
    }

    // method that sorts the events based on which has the closer Start/Due date to the current
    public static <T> List<T> sortByDate(List<T> events) {
        events.sort((event1, event2) -> {
            LocalDateTime dateTime1 = getDateTime(event1);
            LocalDateTime dateTime2 = getDateTime(event2);

            // compare events based on dates
            return dateTime1.compareTo(dateTime2);
        });

        return events;
    }

    // method to extract start/due date from either VEvent or VTodo
    private static <T> LocalDateTime getDateTime(T event) {
        if (event instanceof VEvent) {
            return convertDateStartToLocalDateTime(((VEvent) event).getDateStart());
        } else if (event instanceof VTodo) {
            return convertDateDueToLocalDateTime(((VTodo) event).getDateDue());
        } else {
            throw new IllegalArgumentException("Unsupported event type");
        }
    }

}