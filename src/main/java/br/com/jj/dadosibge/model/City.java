package br.com.jj.dadosibge.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("cities")
public class City {

    @Id
    @JsonAlias("code")
    private String code;

    @JsonAlias("id")
    private Integer id;

    @JsonAlias("nome")
    private String name;

    private String shortNameState;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dt;

    @DBRef
    @JsonIgnore
    private State state;
}
