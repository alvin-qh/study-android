package alvin.base.service.remote;

import alvin.base.service.remote.IOnJobStatusChangeListener;
import alvin.base.service.remote.models.Job;

// This interface as a binder to connect callers and service
interface IRemoteBinder {

    // Add a instance of callback listener to service by key
    // 'in' means this argument should pass to service
    void addOnJobStatusChangeListener(String key, in IOnJobStatusChangeListener l);

    // Add a exist callback listener from service by key
    void removeOnJobStatusChangeListener(String key);

    // Add a new job to service to invork it
    // 'in' means this argument should pass to service
    void addNewJob(in Job job);
}
