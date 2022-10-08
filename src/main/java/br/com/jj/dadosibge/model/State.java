package br.com.jj.dadosibge.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("states")
public class State {

    @Id
    @JsonAlias("code")
    private String code;

    @JsonAlias("id")
    private Integer id;

    @JsonAlias("sigla")
    private String shortName;

    @JsonAlias("nome")
    private String name;

    private String shortNameRegion;

    private Integer countCities;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dt;

    @DBRef
    @JsonIgnore
    private Region region;

}
