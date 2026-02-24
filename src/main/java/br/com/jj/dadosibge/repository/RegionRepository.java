package br.com.jj.dadosibge.repository;

import br.com.jj.dadosibge.model.Region;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RegionRepository extends MongoRepository<Region, String> {

    @Query("{id:?0}")
    Optional<Region> findItemById(Integer id);

    @Query("{shortName:'?0'}")
    Optional<Region> findItemByShortName(String shortName);

}
