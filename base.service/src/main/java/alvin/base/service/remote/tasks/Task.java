package alvin.adv.service.remote.tasks;

import java.util.Random;

import alvin.adv.service.remote.models.Job;
import alvin.adv.service.remote.models.JobResponse;

public class Task {
    private final int minDelay;
    private final int maxDelay;

    private final Random random = new Random();

    public Task(int minDelay, int maxDelay) {
        this.minDelay = minDelay;
        this.maxDelay = maxDelay;
    }

    public JobResponse runJob(Job job) {
        long timeSpend = System.currentTimeMillis();

        try {
            Thread.sleep(random.nextInt(maxDelay - minDelay) + minDelay);
        } catch (InterruptedException ignore) {
        }

        timeSpend = System.currentTimeMillis() - timeSpend;

        return new JobResponse(job.getName(), timeSpend);
    }
}
