package br.com.jj.dadosibge.repository;

import br.com.jj.dadosibge.model.Region;
import br.com.jj.dadosibge.model.State;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StateRepository extends MongoRepository<State, String> {
    @Query("{id:?0}")
    Optional<State> findItemById(Integer id);

    @Query("{shortName:'?0'}")
    Optional<State> findItemByShortName(String shortName);

    @Query("{shortNameRegion:'?0'}")
    List<State> findItemsByShortNameRegion(String shortNameRegion);

}
