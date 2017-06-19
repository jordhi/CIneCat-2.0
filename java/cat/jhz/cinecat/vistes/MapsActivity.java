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
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import cat.jhz.cinecat.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String nomCinema, addrCine, poblacioCine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        nomCinema = getIntent().getExtras().getString("cineNom");
        addrCine = getIntent().getExtras().getString("addrCine");
        poblacioCine = getIntent().getExtras().getString("PoblacioCine");
        //Log.d("MAPS",nomCinema + "/" + addrCine);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng address = getLocationFromAddress(this,addrCine);
        if(address!=null){
            mMap.addMarker(new MarkerOptions().position(address).title(nomCinema));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(address));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(address,12.0f));
        }
    }

    public LatLng getLocationFromAddress(Context context, String strAddress)
    {
        Geocoder coder = new Geocoder(context);
        List<Address> address = null;
        LatLng p1 = null;
        Address location = null;
        try {
            if(Geocoder.isPresent()) {
                address = coder.getFromLocationName(strAddress, 1);
                if(address != null && address.size()>0) {
                    location = address.get(0);
                    location.getLatitude();
                    location.getLongitude();
                } else CompletePlace();
            }
            p1 = new LatLng(location.getLatitude(), location.getLongitude());
        }catch (Exception e) {
            e.printStackTrace();
        }
        return p1;
    }

    private void CompletePlace() {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                            .build(this);
            //intent.putExtra("name",nomCinema);
            startActivityForResult(intent, 1);
        } catch (GooglePlayServicesRepairableException e1) {
            e1.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e1) {
            e1.printStackTrace();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                String toastMsg = String.format("Place: %s", place.getName());
                mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(nomCinema));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(),15.0f));
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }


}
