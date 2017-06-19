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

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import cat.jhz.cinecat.MainActivity;
import cat.jhz.cinecat.R;
import cat.jhz.cinecat.dades.Film;

public class fragment_mesInfo extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        TextView txvSinopsi;
        TextView txvInterprets;

        View v = inflater.inflate(R.layout.activity_fragment_mesinfo, parent, false);
        txvSinopsi = (TextView) v.findViewById(R.id.txvMesInfoSinopsi);
        txvInterprets = (TextView) v.findViewById(R.id.txvMesInfoInterprets);

        Film film;

        MainActivity feeds = (MainActivity) this.getActivity();
        film = feeds.getFilm_selected();
        txvSinopsi.setText(film.getSinopsi());
        txvInterprets.setText(film.getInterprets());

        return v;
    }
}
