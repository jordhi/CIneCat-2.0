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

public class Film extends RealmObject {
	@PrimaryKey private String id;
	private String prioritat, titol, situacio, any, cartell, original, direccio, interprets,
					sinopsi, versio, qualificacio, trailer, web, estrena;
	private String RES = "--";

	public Film(String id, String prioritat, String titol, String situacio,
			String any, String cartell, String original, String direccio,
			String interprets, String sinopsi, String versio,
			String qualificacio, String trailer, String web, String estrena) {
		
		this.id = id;
		this.prioritat = prioritat;
		this.titol = titol;
		this.situacio = situacio;
		this.any = any;
		this.cartell = cartell;
		this.original = original;
		this.direccio = direccio;
		this.interprets = interprets;
		this.sinopsi = sinopsi;
		this.versio = versio;
		this.qualificacio = qualificacio;
		this.trailer = trailer;
		this.web = web;
		this.estrena = estrena;
	}

	public Film() {
		this.id = RES;
		this.prioritat = RES;
		this.titol = RES;
		this.situacio = RES;
		this.any = RES;
		this.cartell = RES;
		this.original = RES;
		this.direccio = RES;
		this.interprets = RES;
		this.sinopsi = RES;
		this.versio = RES;
		this.qualificacio = RES;
		this.trailer = RES;
		this.web = RES;
		this.estrena = RES;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPrioritat() {
		return prioritat;
	}

	public void setPrioritat(String prioritat) {
		this.prioritat = prioritat;
	}

	public String getTitol() {
		return titol;
	}

	public void setTitol(String titol) {
		this.titol = titol;
	}

	public String getSituacio() {
		return situacio;
	}

	public void setSituacio(String situacio) {
		this.situacio = situacio;
	}

	public String getAny() {
		return any;
	}

	public void setAny(String any) {
		this.any = any;
	}

	public String getCartell() {
		return cartell;
	}

	public void setCartell(String cartell) {
		this.cartell = cartell;
	}

	public String getOriginal() {
		return original;
	}

	public void setOriginal(String original) {
		this.original = original;
	}

	public String getDireccio() {
		return direccio;
	}

	public void setDireccio(String direccio) {
		this.direccio = direccio;
	}

	public String getInterprets() {
		return interprets;
	}

	public void setInterprets(String interprets) {
		this.interprets = interprets;
	}

	public String getSinopsi() {
		return sinopsi;
	}

	public void setSinopsi(String sinopsi) {
		this.sinopsi = sinopsi;
	}

	public String getVersio() {
		return versio;
	}

	public void setVersio(String versio) {
		this.versio = versio;
	}

	public String getQualificacio() {
		return qualificacio;
	}

	public void setQualificacio(String qualificacio) {
		this.qualificacio = qualificacio;
	}

	public String getTrailer() {
		return trailer;
	}

	public void setTrailer(String trailer) {
		this.trailer = trailer;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public String getEstrena() {
		return estrena;
	}

	public void setEstrena(String estrena) {
		this.estrena = estrena;
	}

	@Override
	public String toString() {
		return getId() + "-" + getTitol() + "-" + getVersio();
	}
}
