package alvin.base.service.remote.services;

import java.util.Random;

import alvin.base.service.remote.models.Job;
import alvin.base.service.remote.models.JobResponse;

class Task {
    private final int minDelay;
    private final int maxDelay;

    private final Random random = new Random();

    Task(int minDelay, int maxDelay) {
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
