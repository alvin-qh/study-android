package alvin.base.service.remote.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

/**
 * An work job, include job name and arguments.
 * <p>
 * This class implements from {@link Parcelable} interface, means it can be serialized or
 * deserialize and transfer between callers and service.
 * <p>
 * In '/src/main/aidl/alvin/base/service/remote/aidls/models/Job.aidl' file, this class is
 * declared as AIDL interface
 *
 * @see android.os.Parcelable
 */
public class Job implements Parcelable {
    private final String name;
    private final Map<String, Object> arguments;

    /**
     * This constructor use to deserialize data to an instance.
     *
     * @param in An instance of {@link Parcel}, read data from it in order
     */
    protected Job(Parcel in) {
        name = in.readString();
        arguments = new HashMap<>();

        // Read map from Parcel instance
        in.readMap(arguments, HashMap.class.getClassLoader());
    }

    public Job(String name, Map<String, Object> arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public String getName() {
        return name;
    }

    public Map<String, Object> getArguments() {
        return arguments;
    }

    /**
     * todo: Figure out how to use this method.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Serialize the instance, write every data into {@link Parcel} argument in order.
     * Make sure the write order as same as read order in {@link Job#Job(Parcel)} method
     * <p>
     * todo: Figure out how to use the argument flags
     *
     * @param parcel the instance to write data into it
     */
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(name);
        parcel.writeMap(arguments);
    }

    /**
     * A creator to create instance of {@link Job} class.
     */
    public static final Creator<Job> CREATOR = new Creator<Job>() {

        /**
         * Use {@link Job#Job(Parcel)} to deserialize from parcel to instance.
         */
        @Override
        public Job createFromParcel(Parcel in) {
            return new Job(in);
        }

        /**
         * How to create an array of {@link Job} instance.
         */
        @Override
        public Job[] newArray(int size) {
            return new Job[size];
        }
    };
}
