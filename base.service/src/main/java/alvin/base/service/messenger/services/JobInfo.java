package alvin.base.service.messenger.services;

import android.os.Parcel;
import android.os.Parcelable;

public class JobInfo implements Parcelable {
    private String name;
    private String timestamp;

    JobInfo(String name, String timestamp) {
        this.name = name;
        this.timestamp = timestamp;
    }

    private JobInfo(Parcel in) {
        name = in.readString();
        timestamp = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(timestamp);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<JobInfo> CREATOR = new Creator<JobInfo>() {
        @Override
        public JobInfo createFromParcel(Parcel in) {
            return new JobInfo(in);
        }

        @Override
        public JobInfo[] newArray(int size) {
            return new JobInfo[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
