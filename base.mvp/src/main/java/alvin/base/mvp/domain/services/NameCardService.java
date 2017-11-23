package alvin.base.mvp.domain.services;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import alvin.base.mvp.domain.models.NameCard;
import alvin.base.mvp.domain.repositories.NameCardRepository;
import alvin.lib.common.dbflow.repositories.TransactionManager;

@Singleton
public class NameCardService {

    private final NameCardRepository nameCardRepository;
    private final TransactionManager transactionManager;

    @Inject
    public NameCardService(@NonNull NameCardRepository nameCardRepository,
                           @NonNull TransactionManager transactionManager) {
        this.nameCardRepository = nameCardRepository;
        this.transactionManager = transactionManager;
    }

    public List<NameCard> loadAll() {
        return nameCardRepository.findALl();
    }
}
