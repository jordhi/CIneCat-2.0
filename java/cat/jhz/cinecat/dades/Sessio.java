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
import io.realm.annotations.Index;

public class Sessio extends RealmObject {
	private String sesid;
	@Index private String idfilm;
	@Index private String cineid;
	@Index private String cicleid;
	@Index private String sesdata;
	private String titol, cinenom, localitat, comarca, ver;
	private String RES = "--";
	
	public Sessio(String idfilm, String sesid, String cineid, String titol,
			String sesdata, String cinenom, String localitat, String comarca, String cicleid,
			String ver) {
		this.idfilm = idfilm;
		this.sesid = sesid;
		this.cineid = cineid;
		this.titol = titol;
		this.sesdata = sesdata;
		this.cinenom = cinenom;
		this.localitat = localitat;
		this.comarca = comarca;
		this.cicleid = cicleid;
		this.ver = ver;
	}
	
	public Sessio() {
		this.idfilm = RES;
		this.sesid = RES;
		this.cineid = RES;
		this.titol = RES;
		this.sesdata = RES;
		this.cinenom = RES;
		this.localitat = RES;
		this.comarca = RES;
		this.cicleid = RES;
		this.ver = RES;
	}

	public String getCinenom() {
		return cinenom;
	}

	public void setCinenom(String cinenom) {
		this.cinenom = cinenom;
	}

	public String getIdfilm() {
		return idfilm;
	}

	public void setIdfilm(String idfilm) {
		this.idfilm = idfilm;
	}

	public String getSesid() {
		return sesid;
	}

	public void setSesid(String sesid) {
		this.sesid = sesid;
	}

	public String getCineid() {
		return cineid;
	}

	public void setCineid(String cineid) {
		this.cineid = cineid;
	}

	public String getTitol() {
		return titol;
	}

	public void setTitol(String titol) {
		this.titol = titol;
	}

	public String getSesdata() {
		return sesdata;
	}

	public void setSesdata(String sesdata) {
		this.sesdata = sesdata;
	}

	public String getLocalitat() {
		return localitat;
	}

	public void setLocalitat(String localitat) {
		this.localitat = localitat;
	}

	public String getComarca() {
		return comarca;
	}

	public void setComarca(String comarca) {
		this.comarca = comarca;
	}

	public String getCicleid() {
		return cicleid;
	}

	public void setCicleid(String cicleid) {
		this.cicleid = cicleid;
	}

	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}
	
	

}
