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
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import cat.jhz.cinecat.MainActivity;
import cat.jhz.cinecat.R;
import cat.jhz.cinecat.config.Sources;
import cat.jhz.cinecat.dades.Cicle;
import cat.jhz.cinecat.vistes.fragment_films;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by jordi on 25/06/16.
 */

public class ListCicleAdapter extends RealmRecyclerViewAdapter<Cicle,ListCicleAdapter.ViewHolder> {
    private Context ctx;
    private ImageLoader imgloader = ImageLoader.getInstance();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txvNomCicle;
        public ImageView imgCartell;
        public Cicle data;
        public ViewHolder(View v) {
            super(v);
            txvNomCicle = (TextView) v.findViewById(R.id.txvCardCicles);
            imgCartell = (ImageView) v.findViewById(R.id.img_card_view_ciclesCartell);

        }
    }


    public ListCicleAdapter(@Nullable OrderedRealmCollection<Cicle> data, boolean autoUpdate, Context c) {
        super(data, autoUpdate);
        setHasStableIds(true);
        ctx = c;
        ImageLoaderConfiguration imgconfig = new ImageLoaderConfiguration.Builder(c)
                .memoryCacheExtraOptions(480,800)
                .threadPoolSize(4)
                .build();
        if(!imgloader.isInited()) imgloader.init(imgconfig);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.card_view_cicles, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int p = position;
        final Cicle item = getItem(p);


        // Títol del cicle
        holder.txvNomCicle.setText(item.getNom());
       /* Typeface type = Typeface.createFromAsset(ctx.getAssets(),"fonts/panpizza.ttf");
        holder.txvNomCicle.setTypeface(type);
        holder.txvNomCicle.setTextSize(30);*/


        String url_img = Sources.URL_IMGS + item.getImg();
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .build();
        imgloader.displayImage(url_img, holder.imgCartell, defaultOptions);
        holder.imgCartell.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //Toast.makeText(ctx,item.getNom(),Toast.LENGTH_SHORT).show();
                Fragment fragment = null;
                Class fragmentClass = fragment_films.class;
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
                    feeds.setCicle_selected(item);
                    feeds.switchFragment(fragment);
                }
            }
        });
    }

}
