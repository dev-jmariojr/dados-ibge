package br.com.jj.dadosibge.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("regions")
public class Region {

    @Id
    @JsonAlias("code")
    private String code;

    @JsonAlias("id")
    private Integer id;

    @JsonAlias("sigla")
    private String shortName;

    @JsonAlias("nome")
    private String name;

    private Integer countStates;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dt;
}
