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

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cat.jhz.cinecat.MainActivity;
import cat.jhz.cinecat.R;

public class fragment_Infocinecat extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_infocinecat, container, false);
        SharedPreferences prefs = getActivity().getSharedPreferences(getString(R.string.file_prefs), 0);
        TextView txvAct = (TextView) v.findViewById(R.id.txvInfoDataActualitzacio);
        String d = prefs.getString(getString(R.string.uptaded_data),"no actualitzat");
        txvAct.setText(d);

        MainActivity feed = (MainActivity) getActivity();
        //feed.setBottomInfo(ContextCompat.getColor(getContext(),R.color.primary_dark),null);

        return v;

    }
}
