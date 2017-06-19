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

package cat.jhz.cinecat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import cat.jhz.cinecat.config.Sources;
import cat.jhz.cinecat.dades.CicleParseXML;
import cat.jhz.cinecat.dades.CinemaParseXML;
import cat.jhz.cinecat.dades.FilmParseXML;
import cat.jhz.cinecat.dades.SessioParseXML;
import io.realm.Realm;

public class SplashCinemaCat extends AppCompatActivity {

    private ConnectivityManager cm;
    private SharedPreferences prefs;
    private String updated;
    private int carrega=0;

    private String TAG;
    private ProgressBar mProgresBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_cinema_cat);
        TAG = getClass().getSimpleName();

        prefs = getSharedPreferences(getString(R.string.file_prefs),MODE_PRIVATE);
        String DEFAULT_DATA = "2011-08-31T00:00:00";
        updated = prefs.getString(getString(R.string.uptaded_data), DEFAULT_DATA);

        mProgresBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgresBar.setMax(8);
        mProgresBar.setProgress(0);

        cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        new getDataTask(this).execute(Sources.URL_CINEMES);
    }

    //Asiynchrone connection to download data
    private class getDataTask extends AsyncTask <String, Integer, Boolean> {
        boolean connexio;
        public getDataTask(Context c) {
        }

        @Override
        protected void onPostExecute(Boolean data) {
            //start app
            mProgresBar.setVisibility(ProgressBar.GONE);
            iniciarApp(data);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mProgresBar.setProgress(carrega);

        }


        @Override
        protected void onPreExecute() {
            // check internet connection
            NetworkInfo activieNetwork = cm.getActiveNetworkInfo();
            connexio = activieNetwork != null && activieNetwork.isConnectedOrConnecting();
            mProgresBar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(String... params) {
            Boolean updated = null;
            if(connexio) {
                //Read date updated
                try {
                   updated = readDatefromNetwork(params[0]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                //TODO avisar que no hi ha connexió
            }

            return updated;
        }
    }

    private Boolean saveUpdatedPrefs(String data) {
        boolean act = false;
        // Update data in prefs
        SharedPreferences.Editor editor = prefs.edit();
        if(!data.equals(updated)) {
            editor.putString(getString(R.string.uptaded_data),data);
            editor.commit();
            act = false;
        }
        else act=true;
        return act;
    }

    private void iniciarApp(boolean act) {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra(getString(R.string.uptaded_data),act);
        startActivity(i);
        finish();
    }

    private Boolean readDatefromNetwork(String url) throws IOException {
        String updated = null;
        Boolean act;
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        InputStream stream = downloadUrl(url);
        BufferedReader in = new BufferedReader(new InputStreamReader(stream));
        String line = in.readLine();
        if (line != null) {
            line = in.readLine();
            if (line != null) {
                updated = line.substring(line.indexOf("generated")+11,line.indexOf("generated") + 30);
            }
        }
        carrega++;
        mProgresBar.setProgress(carrega);
        act = saveUpdatedPrefs(updated);
        if(!act) { //if no updated
            //Delete old data
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            realm.deleteAll();
            realm.commitTransaction();
            // Download and parse data
            loadXmlFromNetwork(Sources.URL_CINEMES);
            loadXmlFromNetwork(Sources.URL_FILMS);
            loadXmlFromNetwork(Sources.URL_CICLES);
            loadXmlFromNetwork(Sources.URL_SESSIONS);
        }
        return act;

    }

    private InputStream downloadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();
        return conn.getInputStream();
    }

    private void loadXmlFromNetwork(String urlString) throws IOException {
        InputStream stream = null;
        // Instantiate the parser
        try {
            stream = downloadUrl(urlString);
            carrega++;
            mProgresBar.setProgress(carrega);
            switch(urlString) {
                case Sources.URL_CINEMES:
                    CinemaParseXML cinemaParseXML = new CinemaParseXML();
                    cinemaParseXML.parse(stream, this);
                    break;
                case Sources.URL_FILMS:
                    FilmParseXML filmParseXML = new FilmParseXML();
                    filmParseXML.parse(stream, this);
                    break;
                case Sources.URL_CICLES:
                    CicleParseXML cicleParseXML = new CicleParseXML();
                    cicleParseXML.parse(stream, this);
                    break;
                case Sources.URL_SESSIONS:
                    SessioParseXML sessioParseXML = new SessioParseXML();
                    sessioParseXML.parse(stream, this);
                    break;
            }
            carrega++;
            mProgresBar.setProgress(carrega);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                stream.close();
            }
        }


    }
}
