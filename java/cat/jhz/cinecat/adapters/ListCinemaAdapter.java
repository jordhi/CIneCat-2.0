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
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cat.jhz.cinecat.MainActivity;
import cat.jhz.cinecat.R;
import cat.jhz.cinecat.dades.Cinema;
import cat.jhz.cinecat.vistes.InfoCine;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by jordi on 25/06/16.
 */

public class ListCinemaAdapter extends RealmRecyclerViewAdapter<Cinema,ListCinemaAdapter.ViewHolder> {
    private Context ctx;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txvNomCinema, txvPoblacio, txvInicial;
        public CardView card;
        public Cinema data;

        public ViewHolder(View v) {
            super(v);
            txvNomCinema = (TextView) v.findViewById(R.id.txvCardCinemes);
            txvPoblacio = (TextView) v.findViewById(R.id.txvCardCinemesPoble);
            txvInicial = (TextView) v.findViewById(R.id.txvCardInicialCinema);
            card = (CardView) v.findViewById(R.id.card_view_cinemes);
        }
    }

    public ListCinemaAdapter(@Nullable OrderedRealmCollection<Cinema> data, boolean autoUpdate, Context c) {
        super(data, autoUpdate);
        setHasStableIds(true);
        ctx = c;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.card_view_cinemes, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int p = position;
        final Cinema item = getItem(p);
        holder.data = item;
        holder.txvNomCinema.setText(item.getNom());
        holder.txvPoblacio.setText(item.getLocalitat());
        holder.txvInicial.setText(String.valueOf(item.getLocalitat().charAt(0)));

        Typeface type = Typeface.createFromAsset(ctx.getAssets(),"fonts/panpizza.ttf");
        holder.txvNomCinema.setTypeface(type);
        holder.txvNomCinema.setTextSize(30);

        int[] colors = ctx.getResources().getIntArray(R.array.colors);
        int rnd = (int) (Math.random() * colors.length -1);
        holder.txvInicial.setBackgroundColor(colors[rnd]);

        holder.card.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //Toast.makeText(ctx,item.getNom(),Toast.LENGTH_SHORT).show();
                Fragment fragment = null;
                Class fragmentClass = InfoCine.class;
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
                    feeds.setCine_selected(item);
                    feeds.switchFragment(fragment);
                }
            }
        });

    }
}
