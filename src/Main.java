import app.WordClock;

public class Main {

    public static void main(String[] args) {
        if(args.length == 0) {
            WordClock wc = new WordClock();
            wc.printWordTime();
        }
        if(args.length == 2) {
            int hour;
            int minute;
            try {
                hour = Integer.parseInt(args[0]);
                minute = Integer.parseInt(args[1]);
                WordClock wc = new WordClock(hour, minute);
                wc.printWordTime();
            } catch (NumberFormatException e) {
                System.out.println("Arguments must be typed as valid 24 hour clock numbers");
            }
        }
    }

}
