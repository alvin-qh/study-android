package alvin.base.service.messenger.services;

import android.os.Bundle;
import android.os.Message;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class Messages {
    public static final int WHAT_RUN_JOB = 1;
    public static final int WHAT_JOB_STARTED = 10;
    public static final int WHAT_JOB_FINISHED = 11;

    private Messages() {
    }

    private static String nowAsString() {
        return LocalDateTime.now(ZoneOffset.UTC).toString();
    }

    public static Message makeJobMessage(String jobName) {
        Bundle bundle = new Bundle();
        bundle.putString("name", jobName);
        return Message.obtain(null, WHAT_RUN_JOB, bundle);
    }

    public static Message makeJobStartedAckMessage(String jobName) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("job-info", new JobInfo(jobName, nowAsString()));
        return Message.obtain(null, WHAT_JOB_STARTED, bundle);
    }

    public static Message makeJobCompleteAckMessage(String jobName) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("job-info", new JobInfo(jobName, nowAsString()));
        return Message.obtain(null, WHAT_JOB_FINISHED, bundle);
    }
}
