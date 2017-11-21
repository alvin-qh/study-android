package alvin.base.service.remote.tasks;

import java.util.Random;

import alvin.base.service.remote.aidls.models.Job;
import alvin.base.service.remote.aidls.models.JobResponse;

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
