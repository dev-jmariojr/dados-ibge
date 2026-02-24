package br.com.jj.dadosibge.dto;

public record RegionParams(
        String shortName,
        Integer id
) {
    public boolean isEmpty() {
        return (shortName == null || shortName.isEmpty())
                && (id == null || id.equals(0));
    }
}
