package br.com.jj.dadosibge.dto;

public record StateParams(
        String shortName,
        String shortNameRegion,
        Integer id
) {

    public boolean isEmpty() {
        return (shortName == null || shortName.isEmpty())
                && (shortNameRegion == null || shortNameRegion.isEmpty())
                && (id == null || id.equals(0));
    }
}
