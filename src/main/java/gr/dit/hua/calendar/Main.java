package gr.dit.hua.calendar;

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

                switch (args[0]){
                    case "day" :
                        calendar.displayDayEvents(args[1]);
                        break;

                    case "week" :
                        calendar.displayWeekEvents(args[1]);
                        break;

                    case "month" :
                        calendar.displayMonthEvents(args[1]);
                        break;

                    default:
                        System.out.println("Invalid input. Please try one of the following as the first argument if you want to view events:");
                        System.out.println("  - day");
                        System.out.println("  - week");
                        System.out.println("  - month");
                        System.out.println("  - pastday");
                        System.out.println("  - pastweek");
                        System.out.println("  - pastmonth");
                        System.out.println("  - todo");
                        System.out.println("  - due");
                        break;
                }


            }
        }

    }
}
