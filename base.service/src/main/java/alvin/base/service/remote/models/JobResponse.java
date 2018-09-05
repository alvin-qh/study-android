package alvin.base.service.remote.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * The response of each job, include job name and time use of this job.
 * <p>
 * This class implements from {@link Parcelable} interface, means it can be serialized or
 * deserialize and transfer between callers and service.
 * <p>
 * In '/src/main/aidl/alvin/base/service/remote/aidls/models/JobResponse.aidl' file, this class is
 * declared as AIDL interface
 *
 * @see android.os.Parcelable
 */
public class JobResponse implements Parcelable {
    private final String name;
    private final long timeSpend;

    protected JobResponse(Parcel in) {
        name = in.readString();
        timeSpend = in.readLong();
    }

    public JobResponse(String name, long timeSpend) {
        this.name = name;
        this.timeSpend = timeSpend;
    }

    public String getName() {
        return name;
    }

    public long getTimeSpend() {
        return timeSpend;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Serialize the instance, write every data into {@link Parcel} argument in order.
     * Make sure the write order as same as read order in {@link JobResponse#JobResponse(Parcel)} method
     * <p>
     * todo: Figure out how to use the argument flags
     *
     * @param parcel the instance to write data into it
     */
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(name);
        parcel.writeLong(timeSpend);
    }

    /**
     * A creator to create instance of {@link JobResponse} class.
     */
    public static final Creator<JobResponse> CREATOR = new Creator<JobResponse>() {

        /**
         * Use {@link JobResponse#JobResponse(Parcel)} to deserialize from parcel to instance.
         */
        @Override
        public JobResponse createFromParcel(Parcel in) {
            return new JobResponse(in);
        }

        /**
         * How to create an array of {@link JobResponse} instance
         */
        @Override
        public JobResponse[] newArray(int size) {
            return new JobResponse[size];
        }
    };
}
