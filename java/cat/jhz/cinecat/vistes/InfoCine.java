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

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cat.jhz.cinecat.MainActivity;
import cat.jhz.cinecat.R;
import cat.jhz.cinecat.adapters.ListSessionsAdpater;
import cat.jhz.cinecat.dades.Cinema;
import cat.jhz.cinecat.dades.Film;
import cat.jhz.cinecat.dades.Sessio;
import io.realm.Realm;
import io.realm.RealmResults;

public class InfoCine extends Fragment {
	List<Sessio> llista_sessions = new ArrayList<>();
	Context ctx;
	ImageLoader imgloader;
	private Button txvSinopsi;
	private String strSinopsi;
	private RecyclerView mRecyclerView;
	private Film film;
	private Cinema cine;
	private TextView txvNomCinema,txvPoblacio,txvAdreca;
    private List<Sessio> lstSessions;
    private ListView lsvFilms;

    private Realm realm;
	public InfoCine() {
        realm = Realm.getDefaultInstance();
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.infocines, parent, false);
		//mRecyclerView = (RecyclerView) v.findViewById(R.id.lstFilms);
		txvNomCinema = (TextView) v.findViewById(R.id.txvInfoCineNom);
		txvPoblacio = (TextView) v.findViewById(R.id.txvInfoCinePoblacio);
		txvAdreca = (TextView) v.findViewById(R.id.txvInfoCineAdreca);
		lsvFilms = (ListView) v.findViewById(R.id.lsvInfoCineSessions);

		MainActivity feeds = (MainActivity) this.getActivity();
		cine = feeds.getCine_selected();
        feeds.setActionBarTitle(cine.getNom());
        feeds.setItemMenuNav(1);
		txvNomCinema.setText(cine.getNom());
        Typeface type = Typeface.createFromAsset(getActivity().getAssets(),"fonts/panpizza.ttf");
        txvNomCinema.setTypeface(type);
        txvNomCinema.setTextSize(30);

		txvAdreca.setText(cine.getAdreca());
		txvPoblacio.setText(cine.getLocalitat());

        ctx = this.getActivity();

		// carregar la llista al adptar
        ListSessionsAdpater adapter = new ListSessionsAdpater(realm.where(Sessio.class).equalTo("cineid",cine.getId()).findAll(), getContext(),1);
        lsvFilms.setAdapter(adapter);

		lsvFilms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Sessio ses = (Sessio) parent.getItemAtPosition(position);
                // Agafer el idFilm de la sessio i cercar la pel�l�cula a la base de dades
                // per assignarla a film_selected i obrir el fragment d'InfoFilm
                String idFilm = ses.getIdfilm();
                Film film;

                RealmResults<Film> films = realm.where(Film.class).equalTo("id",idFilm).findAll();
                film = films.first();
                //Toast.makeText(ctx,film.getTitol(),Toast.LENGTH_SHORT).show();

                Fragment fragment = null;
                Class fragmentClass = InfoFilm.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                if (ctx == null)
                    return;
                if (ctx instanceof MainActivity) {
                    MainActivity feeds = (MainActivity) ctx;
                    feeds.setFilm_selected(film);
                    feeds.switchFragment(fragment);
                }


			}
		});

		return v;
	}


}
