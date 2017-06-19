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

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Cicle extends RealmObject {

	@PrimaryKey private String id;
	private String nom, info, img;
	private String RES = "--";

	public Cicle(String id, String nom, String info, String img) {
		this.id = id;
		this.nom = nom;
		this.info = info;
		this.img = img;
	}

	public Cicle() {
		this.id = RES;
		this.nom = RES;
		this.info = RES;
		this.img = RES;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getRES() {
		return RES;
	}


}
