package br.com.jj.dadosibge.repository;

import br.com.jj.dadosibge.model.City;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends MongoRepository<City, String> {

    @Query("{id:?0}")
    Optional<City> findItemById(Integer id);
    @Query("{name:{$regex:?0}}")
    List<City> findItemByName(String name);
    @Query("{shortNameState:'?0'}")
    List<City> findItemsByState(String shortNameState);
}
