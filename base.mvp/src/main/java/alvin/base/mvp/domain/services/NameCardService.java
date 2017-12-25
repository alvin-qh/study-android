package alvin.base.mvp.domain.services;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Singleton;

import alvin.base.mvp.domain.models.NameCard;
import alvin.base.mvp.domain.repositories.NameCardRepository;
import alvin.lib.common.dbflow.repositories.TransactionManager;

@Singleton
public class NameCardService {

    private final NameCardRepository nameCardRepository;
    private final TransactionManager txManager;

    @Inject
    public NameCardService(@NonNull NameCardRepository nameCardRepository,
                           @NonNull TransactionManager txManager) {
        this.nameCardRepository = nameCardRepository;
        this.txManager = txManager;
    }

    public List<NameCard> loadAll() {
        return nameCardRepository.findALl();
    }

    public NameCard findById(int nameCardId) {
        Optional<NameCard> mayNameCard = nameCardRepository.findById(nameCardId);
        if (!mayNameCard.isPresent()) {
            throw new IllegalArgumentException("Invalid nameCardId " + nameCardId);
        }
        return mayNameCard.get();
    }
}
