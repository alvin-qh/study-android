package alvin.adv.database.common.domain.models;

import java.time.LocalDate;

public interface IPerson {
    int getId();

    String getName();

    Gender getGender();

    LocalDate getBirthday();
}
