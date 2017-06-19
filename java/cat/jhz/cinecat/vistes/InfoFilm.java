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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

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

public class InfoFilm extends Fragment {
	List<Sessio> llista_sessions = new ArrayList<>();
	Context ctx;
	private Button txvSinopsi;
	private String strSinopsi;
	private RecyclerView mRecyclerView;
	private TextView txvTitol,txvAny,txvDireccio,txvVersio;
	private ListView lsvSessions;
    private ImageView imgCartell;
	private Film film;
    private List<Sessio> lstSessions;
    private ImageLoader imgloader = ImageLoader.getInstance();
    private Realm realm;


	public InfoFilm () {
       realm =  Realm.getDefaultInstance();
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.infofilms, parent, false);
		//mRecyclerView = (RecyclerView) v.findViewById(R.id.lstFilms);
		txvTitol = (TextView) v.findViewById(R.id.txvInfoFilmTitol);
        txvAny = (TextView) v.findViewById(R.id.txvInfoFilmsAny);
        txvDireccio = (TextView) v.findViewById(R.id.txvInfoFilmsDireccio);
        txvVersio = (TextView) v.findViewById(R.id.txvInfoFilmsVersio);
        lsvSessions = (ListView) v.findViewById(R.id.lsvInfoFilmsCinemes);
        imgCartell = (ImageView) v.findViewById(R.id.imgInfoFilms);

        ctx = (MainActivity) this.getActivity();
        ImageLoaderConfiguration imgconfig = new ImageLoaderConfiguration.Builder(ctx)
                .memoryCacheExtraOptions(480,800)
                .build();
        if(!imgloader.isInited()) imgloader.init(imgconfig);

		MainActivity feeds = (MainActivity) this.getActivity();
        film = feeds.getFilm_selected();
		feeds.setActionBarTitle(film.getTitol());
        feeds.setItemMenuNav(0);
		film = feeds.getFilm_selected();
		txvTitol.setText(film.getTitol());
        Typeface type = Typeface.createFromAsset(ctx.getAssets(),"fonts/Anna-Roman.otf");
        txvTitol.setTypeface(type);

        txvVersio.setText(film.getVersio());
        txvAny.setText(film.getAny());
        txvDireccio.setText(film.getDireccio());

        String url_img = "http://gencat.cat/llengua/cinema/" + film.getCartell();
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .build();
        imgloader.displayImage(url_img, imgCartell, defaultOptions);

        // carregar la llista al adptar

        RealmResults<Sessio> s = realm.where(Sessio.class).findAll();
        ListSessionsAdpater adapter = new ListSessionsAdpater(realm.where(Sessio.class).equalTo("idfilm",film.getId()).findAll(), getContext(),0);
        lsvSessions.setAdapter(adapter);

        lsvSessions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Sessio ses = (Sessio) parent.getItemAtPosition(position);
                String cineId = ses.getCineid();
                Cinema cine;
                // Cerquem el cinema corresponent
                RealmResults<Cinema> cinemes = realm.where(Cinema.class).equalTo("Id",cineId).findAll();
                cine = cinemes.first();
                //Toast.makeText(ctx,cine.getNom(),Toast.LENGTH_SHORT).show();

                Fragment fragment = null;
                Class fragmentClass = InfoCine.class;
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
                    feeds.setCine_selected(cine);
                    feeds.switchFragment(fragment);
                }
            }
        });

		return v;
	}


}
