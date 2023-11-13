package gr.dit.hua.calendar;

import biweekly.component.VEvent;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        Calendar calendar = new Calendar();
        // check length of the given arguments
        if(args.length > 2) {
            System.out.println("\nPlease do not select more than two arguments.");
        }else {
            // if the arguments are two go on with the options
            if (args.length == 2 && args[1].endsWith(".ics")){
                // if the first argument is "day", call the method to display the events of the current day
                if(args[0].matches("day")){
                    calendar.displayDayEvents(args[1]);
                }

            }
        }

    }
}
