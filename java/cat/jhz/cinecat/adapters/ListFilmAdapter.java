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

package cat.jhz.cinecat.adapters;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import cat.jhz.cinecat.MainActivity;
import cat.jhz.cinecat.R;
import cat.jhz.cinecat.config.Sources;
import cat.jhz.cinecat.dades.Film;
import cat.jhz.cinecat.vistes.InfoFilm;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;


/**
 * Created by jordi on 27/06/16.
 */
public class ListFilmAdapter extends RealmRecyclerViewAdapter<Film,ListFilmAdapter.ViewHolder> {
    private Context ctx;
    private ImageLoader imgloader = ImageLoader.getInstance();
    private Display mDisplay;
    private int width, height;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txvTitol, txvVersio;
        public ImageView imgCartell;
        public Film data;
        public ViewHolder(View v) {
            super(v);
            txvTitol = (TextView) v.findViewById(R.id.txvCardFilms);
            imgCartell = (ImageView) v.findViewById(R.id.img_card_view_films);
            txvVersio = (TextView) v.findViewById(R.id.txvCardFilmVersio);
        }
    }

    public ListFilmAdapter(@Nullable OrderedRealmCollection<Film> data, boolean autoUpdate, Context c) {
        super(data, autoUpdate);
        setHasStableIds(true);
        ctx = c;

        ImageLoaderConfiguration imgconfig = new ImageLoaderConfiguration.Builder(c)
                .memoryCacheExtraOptions(480,800)
                .threadPoolSize(6)
                .build();
        if(!imgloader.isInited()) imgloader.init(imgconfig);
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        mDisplay = wm.getDefaultDisplay();
        Point size = new Point();
        mDisplay.getSize(size);
        height = size.y;
        width = size.x;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.card_view_films, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int p = position;
        final Film item = getItem(position);
        holder.data = item;
        holder.txvTitol.setText(item.getTitol());
        holder.txvVersio.setText(item.getVersio());
        Typeface type = Typeface.createFromAsset(ctx.getAssets(),"fonts/Anna-Roman.otf");
        holder.txvTitol.setTypeface(type);

        String url_img = Sources.URL_IMGS + item.getCartell();
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .build();
        imgloader.displayImage(url_img, holder.imgCartell, defaultOptions);
       // holder.imgCartell.setMaxWidth(width/2);
       // holder.imgCartell.setMaxHeight(height/2);
        holder.imgCartell.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //Toast.makeText(ctx,item.getTitol(),Toast.LENGTH_SHORT).show();
                Fragment fragment = null;
                Class fragmentClass = InfoFilm.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                if (ctx == null)
                    return;
                if (ctx instanceof MainActivity) {
                    MainActivity feeds = (MainActivity) ctx;
                    feeds.setFilm_selected(item);
                    feeds.switchFragment(fragment);
                }

            }
        });

    }

}
