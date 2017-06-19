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
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.util.Xml;

import io.realm.Realm;

public class CicleParseXML {
	// We don't use namespaces
    private static final String ns = null;
    Context mcontext;
 
    public List<Cicle> parse(InputStream in, Context c) throws XmlPullParserException, IOException {
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
    
    private List<Cicle> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<Cicle> entries = new ArrayList<Cicle>();

        parser.require(XmlPullParser.START_TAG, ns, "dataroot");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("CICLE")) {
                entries.add(readEntry(parser));
            } else {
                skip(parser);
            }
        }  
        return entries;
    }
    
 // Parses the contents of an Film. If it encounters a ID, NOM, ADREÇA, LOCALITAT, or COMARCA tag, hands them off
 // to their respective "read" methods for processing. Otherwise, skips the tag.
    private Cicle readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
    	parser.require(XmlPullParser.START_TAG, ns, "CICLE");
    	String id = null;
    	String Nom = null;
    	String Info = null;
    	String imgCicle = null;
   	
    	while (parser.next() != XmlPullParser.END_TAG) {
    		if (parser.getEventType() != XmlPullParser.START_TAG) {
    			continue;
    		}
    		String name = parser.getName();

    		switch(name) {
    			case "CICLEID": id = readCicle(parser,name);break;
    			case "CICLENOM": Nom = readCicle(parser,name);break;
    			case "CICLEINFO": Info = readCicle(parser,name);break;
    			case "IMGCICLE": imgCicle = readCicle(parser,name);break;
    			default: skip(parser);
    		}
    	}
    	Cicle cicle =new Cicle(id, Nom, Info, imgCicle);

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(cicle);
        realm.commitTransaction();

    	
    	return cicle;
    }

    //Processes title tags in the feed.
    private String readCicle(XmlPullParser parser, String tag) throws IOException, XmlPullParserException {
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
