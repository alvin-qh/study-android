package alvin.base.database.dbflow.presenters;

import android.support.annotation.NonNull;

import java.util.Collections;

import javax.inject.Inject;

import alvin.base.database.common.domain.models.Gender;
import alvin.base.database.common.domain.models.IPerson;
import alvin.base.database.dbflow.domain.models.Person;
import alvin.base.database.dbflow.domain.repositories.PersonRepository;
import alvin.lib.common.dbflow.repositories.TransactionManager;
import alvin.lib.mvp.contracts.adapters.PresenterAdapter;

import static alvin.base.database.dbflow.DBFlowContracts.Presenter;
import static alvin.base.database.dbflow.DBFlowContracts.View;

public class DBFlowPresenter extends PresenterAdapter<View> implements Presenter {

    private final PersonRepository personRepository;
    private final TransactionManager transactionManager;

    @Inject
    public DBFlowPresenter(@NonNull View view,
                           PersonRepository personRepository,
                           TransactionManager transactionManager) {
        super(view);
        this.transactionManager = transactionManager;
        this.personRepository = personRepository;
    }

    @Override
    public void savePerson(@NonNull IPerson person) {
        transactionManager.executeAsync(db -> mapPerson(person).save(db))
                .success(tx -> with(view -> view.onPersonCreate(person)))
                .build()
                .execute();
    }

    @Override
    public void getPersons(@NonNull Gender gender) {
        personRepository.findByGender(gender)
                .queryListResultCallback((tx, persons) ->
                        with(view -> view.onPersonGot(Collections.unmodifiableList(persons))))
                .execute();
    }

    @Override
    public void updatePerson(@NonNull IPerson person) {
        personRepository.findById(person.getId())
                .querySingleResultCallback((tx1, p) -> {
                    if (p != null) {
                        p.setName(person.getName());
                        p.setBirthday(p.getBirthday());
                        p.setGender(p.getGender());
                        transactionManager.executeAsync(db -> p.save())
                                .success(tx2 -> with(view -> view.onPersonUpdate(person)))
                                .build()
                                .execute();
                    }
                }).execute();
    }

    @Override
    public void deletePerson(@NonNull IPerson person) {
        personRepository.findById(person.getId())
                .querySingleResultCallback((tx1, p) -> {
                    if (p != null) {
                        transactionManager.executeAsync(db -> p.delete())
                                .success(tx2 -> with(view -> view.onPersonDelete(person)))
                                .build()
                                .execute();
                    }
                }).execute();
    }

    private Person mapPerson(IPerson person) {
        if (person instanceof Person) {
            return (Person) person;
        }
        return new Person(person.getName(), person.getGender(), person.getBirthday());
    }
}
