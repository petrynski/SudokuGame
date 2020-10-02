import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Timer class is used to count player's playtime.
 */
public class Timer implements Runnable {
    private LocalDateTime startTime = LocalDateTime.now();
    private LocalDateTime stopTime = LocalDateTime.now();
    private volatile boolean exit = false;
    /**
     * When run, it saves start time and when player solves sudoku, it saves finish time and shows victory window.
     */
    @Override
    public void run()
    {
        Validator valid = new Validator();
        startTimer();
        while (!valid.isSudokuSolved()) {
            if (exit)
                return;
        }
        stopTimer();
        Sudoku.finish();
    }

    private void startTimer() {
        startTime = LocalDateTime.now();
    }

    private void stopTimer() {
        stopTime = LocalDateTime.now();
    }

    long getTimeDiff(){
        Duration duration = Duration.between(startTime,LocalDateTime.now());
        return duration.getSeconds();
    }

    long getFinalTimeDiff(){
        Duration duration = Duration.between(startTime,stopTime);
        return duration.getSeconds();
    }

    void stop(){
        exit = true;
    }
}
