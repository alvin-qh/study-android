package alvin.base.database.sqlite.presenters;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import alvin.base.database.common.domain.models.Gender;
import alvin.base.database.common.domain.models.IPerson;
import alvin.base.database.sqlite.domain.repositories.PersonRepository;
import alvin.lib.common.rx.RxDecorator;
import alvin.lib.common.rx.RxType;
import alvin.lib.mvp.contracts.adapters.PresenterAdapter;
import io.reactivex.Completable;
import io.reactivex.Single;

import static alvin.base.database.sqlite.SQLiteContracts.Presenter;
import static alvin.base.database.sqlite.SQLiteContracts.View;

public class SQLitePresenter extends PresenterAdapter<View> implements Presenter {

    private final RxDecorator.Builder rxDecoratorBuilder;
    private final PersonRepository personRepository;

    @Inject
    SQLitePresenter(View view,
                           @RxType.IO RxDecorator.Builder rxDecoratorBuilder,
                           PersonRepository personRepository) {
        super(view);
        this.rxDecoratorBuilder = rxDecoratorBuilder;
        this.personRepository = personRepository;
    }

    @SuppressLint("CheckResult")
    @Override
    public void savePerson(@NonNull IPerson person) {
        final RxDecorator decorator = rxDecoratorBuilder.build();
        decorator.<List<IPerson>>de(
                Completable.create(emitter -> {
                    personRepository.create(person);
                    emitter.onComplete();
                })
        ).subscribe(() -> with(view -> view.onPersonCreate(person)));
    }

    @SuppressLint("CheckResult")
    @Override
    public void getPersons(@NonNull Gender gender) {
        final RxDecorator decorator = rxDecoratorBuilder.build();
        decorator.<List<IPerson>>de(
                Single.create(emitter -> emitter.onSuccess(personRepository.findByGender(gender)))
        ).subscribe(persons -> with(view -> view.onPersonGot(persons)));
    }

    @SuppressLint("CheckResult")
    @Override
    public void updatePerson(@NonNull IPerson person) {
        final RxDecorator decorator = rxDecoratorBuilder.build();
        decorator.<List<IPerson>>de(
                Completable.create(emitter -> {
                    personRepository.update(person);
                    emitter.onComplete();
                })
        ).subscribe(() -> with(view -> view.onPersonUpdate(person)));
    }

    @SuppressLint("CheckResult")
    @Override
    public void deletePerson(@NonNull IPerson person) {
        final RxDecorator decorator = rxDecoratorBuilder.build();
        decorator.<List<IPerson>>de(
                Completable.create(emitter -> {
                    personRepository.delete(person.getId());
                    emitter.onComplete();
                })
        ).subscribe(() -> with(view -> view.onPersonDelete(person)));
    }
}
