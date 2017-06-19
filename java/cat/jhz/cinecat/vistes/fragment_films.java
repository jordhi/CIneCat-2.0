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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cat.jhz.cinecat.MainActivity;
import cat.jhz.cinecat.R;
import cat.jhz.cinecat.adapters.ListFilmAdapter;
import cat.jhz.cinecat.dades.Cicle;
import cat.jhz.cinecat.dades.Film;
import cat.jhz.cinecat.dades.Sessio;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by jordi on 15/06/16.
 */
public class fragment_films extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private GridLayoutManager mGrid;

    private List<Film> lstFilms;
    private Cicle cicle;

    private Realm realm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_films, parent, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.lstFilms);

        //mRecyclerView.setHasFixedSize(true);
       // mLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        mGrid = new GridLayoutManager(getContext(),2);
        mRecyclerView.setLayoutManager(mGrid);

        MainActivity feed = (MainActivity) getActivity();
        feed.setActionBarTitle("Pel·lícules");
        feed.setItemMenuNav(0);
        cicle = feed.getCicle_selected();

        realm = Realm.getDefaultInstance();
        if(cicle == null) {
            //No selected from Cicle get all Films
            mAdapter = new ListFilmAdapter(realm.where(Film.class).findAll(), true, getContext());
        } else {
            List<String> ids = new ArrayList<>();
            //Search all Sessio for this Cicle
            RealmResults<Sessio> ses = realm.where(Sessio.class).equalTo("cicleid",cicle.getId()).findAll();
            Iterator<Sessio> it = ses.iterator();
            //Create array of idfilm from these sessions
            while(it.hasNext()) ids.add(it.next().getIdfilm());

            //Search films from array of id's
            if(ids.size()>0)
                mAdapter = new ListFilmAdapter(realm.where(Film.class).in("id",ids.toArray(new String[0])).findAll(), true, getContext());
            else
                mAdapter = new ListFilmAdapter(null, true, getContext());
        }

        mRecyclerView.setAdapter(mAdapter);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
