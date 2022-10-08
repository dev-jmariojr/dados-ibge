package br.com.jj.dadosibge.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public enum ImportIBGEActive {

    IMPORTANDO(0,"Importação em andamento"),
    IMPORTADO(1,"Importação finalizada"),
    ABORTADO(2, "Importação abortada");

    @Getter
    private Integer id;

    private String description;

}
