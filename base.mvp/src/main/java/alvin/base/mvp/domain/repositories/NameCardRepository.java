package alvin.base.mvp.domain.repositories;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import alvin.base.mvp.domain.models.NameCard;
import alvin.lib.common.dbflow.repositories.BaseRepository;

@Singleton
public class NameCardRepository extends BaseRepository<NameCard> {

    @Inject
    public NameCardRepository() { }

    public List<NameCard> findALl() {
        return where().queryList();
    }
}
