package alvin.adv.dagger.common.domain.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.time.LocalDateTime;

import alvin.adv.dagger.common.domain.db.MessageDatabase;
import alvin.lib.common.dbflow.converts.LocalDateTimeConvert;
import alvin.lib.mvp.contracts.IModel;

@Table(name = "message", database = MessageDatabase.class)
public class Message extends BaseModel implements IModel {

    @PrimaryKey(autoincrement = true)
    private int id;

    @Column
    private String message;

    @Column(typeConverter = LocalDateTimeConvert.class)
    private LocalDateTime timestamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
