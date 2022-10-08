package br.com.jj.dadosibge.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("imports")
public class ImportIBGE {

    @Id
    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dt;

    private Integer active;

}
