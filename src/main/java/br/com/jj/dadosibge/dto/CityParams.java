package br.com.jj.dadosibge.dto;

public record CityParams(
        String state,
        String name,
        Integer id) {

    public boolean isEmpty() {
        return (state == null || state.isEmpty())
                && (name == null || name.isEmpty())
                && (id == null || id.equals(0));
    }
}
