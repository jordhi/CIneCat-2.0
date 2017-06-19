/*
Copyright (C) <2017>  <Jordi Hernandez>
Twitter: @jordikarate
Web: http://www.jordihernandez.cat/cinecat

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package cat.jhz.cinecat.vistes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import cat.jhz.cinecat.R;
import cat.jhz.cinecat.adapters.ListCinemaAdapter;
import cat.jhz.cinecat.dades.Cinema;
import io.realm.Realm;
import io.realm.RealmResults;


public class fragment_cines extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Cinema> lstCinemes;
    private List<String> llista_comarques;
    private Spinner spnComarca;
    private List<Cinema> llista_cinemes_filtre = new ArrayList<Cinema>();

    private Realm realm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cines, container, false);
        realm = Realm.getDefaultInstance();
        mRecyclerView = (RecyclerView) v.findViewById(R.id.lstCinemes);
        spnComarca = (Spinner) v.findViewById(R.id.spnComarca);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        setupRecyclerView();
        crearFiltreComarques();

        return v;
    }

    private void setupRecyclerView() {

        mAdapter = new ListCinemaAdapter(realm.where(Cinema.class).findAll(),true, getContext());
        mRecyclerView.setAdapter(mAdapter);

    }

    private void crearFiltreComarques() {
        RealmResults<Cinema> cinemes= realm.where(Cinema.class).distinct("Comarca");
        llista_comarques = new ArrayList<String>();
        for (Cinema c : cinemes) {
            if (c.getComarca() != null) llista_comarques.add(c.getComarca());
        }

        //fillin spinner Comarques

        final ArrayAdapter<String> adapter_comarques = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, llista_comarques);
        adapter_comarques.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnComarca.setAdapter(adapter_comarques);


        //get Cinema selected and filter for Comarca
        spnComarca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View view, int position,
                                       long id) {
                RealmResults<Cinema> c = realm.where(Cinema.class).
                        equalTo("Comarca",llista_comarques.get(position)).
                        findAll();
                mAdapter = new ListCinemaAdapter(c,true, getContext());
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {

            }

        });

    }

    protected List<Cinema> getCinemesComarca(String comarca) {
        ListIterator<Cinema> it = lstCinemes.listIterator();
        List<Cinema> filtre = new ArrayList<>();
        //Recórrer Cinemes i afegir a la llista els que coincideixen amb la comarca demanada
        while(it.hasNext()) {
            Cinema cine = (Cinema)it.next();
            if(cine.getComarca().equals(comarca))
                filtre.add(cine);

        }
        return filtre;
    }
}
