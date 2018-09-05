package alvin.base.service.intent.tasks;

import java.util.Random;

public class Task {
    private final int minDelay;
    private final int maxDelay;
    private final Random random = new Random();

    public Task(int minDelay, int maxDelay) {
        this.minDelay = minDelay;
        this.maxDelay = maxDelay;
    }

    public long work() {
        long time = System.currentTimeMillis();
        try {
            Thread.sleep(random.nextInt(maxDelay - minDelay) + minDelay);
        } catch (InterruptedException ignore) {
        }
        return System.currentTimeMillis() - time;
    }
}
