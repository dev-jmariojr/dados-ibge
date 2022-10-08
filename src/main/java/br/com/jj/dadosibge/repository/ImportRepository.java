package br.com.jj.dadosibge.repository;

import br.com.jj.dadosibge.model.ImportIBGE;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ImportRepository extends MongoRepository<ImportIBGE, String> {

    @Query("{active:?0}")
    ImportIBGE findActive(Integer active);

}
