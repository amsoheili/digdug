package ir.ac.kntu.GameObject;

public class Timer implements Runnable{
    private int second=0;
    private int minute=0;
    private int hour=0;

    @Override
    public void run() {
        if (second == 60){
            if (minute == 60){
                hour++;
                minute = 0;
                second = 0;
            }else {
                minute++;
                second = 0;
            }
        }else {
            second++;
        }
    }

    public int getSecond() {
        return second;
    }

    public int getMinute() {
        return minute;
    }

    public int getHour() {
        return hour;
    }

    @Override
    public String toString(){
        return "Hour: " + getHour() +
                "-Minute: " + getMinute() +
                "-Second: " + getSecond();
    }
}
