import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WordClock {

    private List<Integer> time;
    private StringBuilder wordTime;
    private boolean til;

    //no argument constructor will pull current computer time with setTime() function
    public WordClock() {
        this.time = setTime();
        this.wordTime = new StringBuilder();
        setTo12Hour();
    }

    public WordClock(int hh, int mm) {
        this.time = new ArrayList<Integer>();
        this.wordTime = new StringBuilder();
        if(hh < 24 && hh > 0 && mm >= 0 && mm < 60) {
            this.time.add(hh);
            this.time.add(mm);
        } else {
            throw new IllegalArgumentException("Invalid time provided. Time must be between 00:00 and 23:59");
        }
        setTo12Hour();
    }

    public List<Integer> getTime() {
        return this.time;
    }

    public String getWordTime() {
        this.findWordMinutes();
        this.findWordHour();
        return this.wordTime.toString();
    }

    public void printWordTime() {
        System.out.println(getWordTime());
    }

    private List<Integer> setTime() {
        String t = LocalTime.now().toString();
        return Stream.of(t.split(":"))
                .map(String::new)
                .filter(n -> n.length() < 3)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private void setTo12Hour () {
        if(this.time.get(0) > 12) {
            this.time.set(0, this.time.get(0) - 12);
        }
    }

    //The word list is simplified and stops at "half", so if the minutes are greater than 30 we go into a "til" phrase.
    private int isTilOrPast() {
        if(this.time.get(1) > 30) {
            return 0;
        }
        if(this.time.get(1) == 0) {
            return 2;
        }
        return 1;
    }

    private void findWordMinutes() {
        int minutes = this.time.get(1);
        int c = isTilOrPast(); // 0 is "til", 1 is "past"
        switch(c) {
            case 0:
                wordTime.append(Words.minutes[60 - minutes] + " " + Words.directions[0]);
                this.til = true;
                break;
            case 1:
                wordTime.append(Words.minutes[minutes] + " " + Words.directions[1]);
                break;
            case 2: //This is the case for 00 minutes, so it will not add any minutes or "past"/"til" to the string. It will only be the hour.
                break;
        }
    }

    private void findWordHour() {
        int hour = this.time.get(0);
        if(til) { // If minutes are past 30, we switch to a "xx til hour + 1" phrasing
            hour++;
            if(hour == 13) { //special case for when hour was 12 before, so we wrap around on a 12hr clock.
                hour = 1;
            }
        }
        wordTime.append(" " + Words.hours[hour]);
        if(hour != 0 && hour != 12) {
            wordTime.append(" " + Words.oclock);
        }
    }
}
