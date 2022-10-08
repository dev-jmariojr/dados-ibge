package br.com.jj.dadosibge.service;

import br.com.jj.dadosibge.model.City;
import br.com.jj.dadosibge.model.ImportIBGE;
import br.com.jj.dadosibge.model.Region;
import br.com.jj.dadosibge.model.State;

import java.util.List;

public interface ImportService {

    public Boolean existsActive();

    public Boolean newImport();

    public Boolean abortImport(String value);

    public List<ImportIBGE> allImport();

    public ImportIBGE findById(String value);


    public List<Region> regionList();

    public List<State> stateList(Region value);

    public List<City> cityList(State value);

}
