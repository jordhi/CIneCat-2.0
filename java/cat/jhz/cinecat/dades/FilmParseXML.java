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

package cat.jhz.cinecat.dades;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.util.Xml;

import io.realm.Realm;

public class FilmParseXML {
	// We don't use namespaces
    private static final String ns = null;
    Context mcontext;
 
    public ArrayList<Film> parse(InputStream in, Context c) throws XmlPullParserException, IOException {
    	mcontext = c;
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }
    
    private ArrayList<Film> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        ArrayList<Film> entries = new ArrayList<Film>();

        parser.require(XmlPullParser.START_TAG, ns, "dataroot");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("FILM")) {
                entries.add(readEntry(parser));
            } else {
                skip(parser);
            }
        }  
        return entries;
    }
    
 // Parses the contents of an Film. If it encounters a ID, NOM, ADREÇA, LOCALITAT, or COMARCA tag, hands them off
 // to their respective "read" methods for processing. Otherwise, skips the tag.
    private Film readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {

    	parser.require(XmlPullParser.START_TAG, ns, "FILM");
    	String id = null;
    	String prioritat = null;
    	String titol = null;
    	String situacio = null;
    	String any = null;
    	String cartell = null;
    	String original = null;
    	String direccio = null;
    	String interprets = null;
    	String sinopsi = null;
    	String versio = null;
    	String qualificacio = null;
    	String trailer = null;
    	String web = null;
    	String estrena = null;
    	
   	
    	while (parser.next() != XmlPullParser.END_TAG) {
    		if (parser.getEventType() != XmlPullParser.START_TAG) {
    			continue;
    		}
    		String name = parser.getName();

    		switch(name) {
    			case "IDFILM": id = readFilm(parser,name);break;
    			case "PRIORITAT":prioritat = readFilm(parser,name);break;
    			case "TITOL": titol = readFilm(parser,name);break;
    			case "SITUACIO": situacio = readFilm(parser,name);break;
    			case "ANY": any = readFilm(parser,name);break;
    			case "CARTELL": cartell = readFilm(parser,name);break;
    			case "ORIGINAL": original = readFilm(parser,name);break;
    			case "DIRECCIO": direccio = readFilm(parser,name);break;
    			case "INTERPRETS": interprets = readFilm(parser,name);break;
    			case "SINOPSI": sinopsi = readFilm(parser,name);break;
    			case "VERSIO": versio = readFilm(parser,name);break;
    			case "QUALIFICACIO": qualificacio = readFilm(parser,name);break;
    			case "TRAILER": trailer = readFilm(parser,name);break;
    			case "WEB": web = readFilm(parser,name);break;
    			case "ESTRENA": estrena = readFilm(parser,name);break;
    			default: skip(parser);
    		}
    	}
    	Film film =new Film(id, prioritat, titol, situacio, any, cartell, original, 
				direccio, interprets, sinopsi, versio, qualificacio, trailer, web, estrena);

		Realm realm = Realm.getDefaultInstance();
		realm.beginTransaction();
		realm.copyToRealmOrUpdate(film);
		realm.commitTransaction();

    	return film;
    }

    //Processes title tags in the feed.
    private String readFilm(XmlPullParser parser, String tag) throws IOException, XmlPullParserException {
    	parser.require(XmlPullParser.START_TAG, ns, tag);
    	String item = readText(parser);
    	parser.require(XmlPullParser.END_TAG, ns, tag);
    	return item;
    }

   
 
    //For the tags title and summary, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
    	String result = "";
    	if (parser.next() == XmlPullParser.TEXT) {
    		result = parser.getText();
    		parser.nextTag();
    	}
    	return result;
    }

   
	
    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
            case XmlPullParser.END_TAG:
                depth--;
                break;
            case XmlPullParser.START_TAG:
                depth++;
                break;
            }
        }
     }
}
