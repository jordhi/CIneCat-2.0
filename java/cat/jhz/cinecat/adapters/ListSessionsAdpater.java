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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import cat.jhz.cinecat.R;
import cat.jhz.cinecat.dades.Sessio;
import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

public class ListSessionsAdpater extends RealmBaseAdapter<Sessio> implements ListAdapter {
	private Context context;
	private int fragment;
	
	
	public ListSessionsAdpater(@Nullable OrderedRealmCollection<Sessio> data, Context c, int f) {
		super(data);
		this.context = c;
		fragment = f;
	}

	class ViewHolder {
		public TextView txvDataSes;
		public TextView txvTitol;

	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder holder = null;
		View v = view;

		if(v == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.list_item_sessiocine, null);
			
			holder = new ViewHolder();
			holder.txvDataSes= (TextView) v.findViewById(R.id.txvSCDataSessio);
			holder.txvTitol= (TextView) v.findViewById(R.id.txvSCNomPeli);
			v.setTag(holder);
			
		}else {
			holder = (ViewHolder) v.getTag();
		}
		Sessio item = adapterData.get(position);
		holder.txvDataSes.setText(item.getSesdata());
        if(fragment == 0) holder.txvTitol.setText(item.getCinenom());
		else holder.txvTitol.setText(item.getTitol());

		return v;
	}

}
