package alvin.adv.mvp.domain.repositories;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Singleton;

import alvin.adv.mvp.domain.models.NameCard;
import alvin.adv.mvp.domain.models.NameCard_Table;
import alvin.lib.common.collect.Collections2;
import alvin.lib.common.dbflow.repositories.BaseRepository;

@Singleton
public class NameCardRepository extends BaseRepository<NameCard> {

    @Inject
    public NameCardRepository() { }

    public List<NameCard> findALl() {
        return where().queryList();
    }

    public Optional<NameCard> findById(int id) {
        final List<NameCard> nameCards = where(NameCard_Table.id.eq(id)).queryList();
        return Collections2.first(nameCards);
    }
}
