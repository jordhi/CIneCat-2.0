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
import io.realm.annotations.PrimaryKey;

public class Cinema extends RealmObject {
	@PrimaryKey
	private String Id;
	private String Nom, Adreca;
	@Index private String Provincia;
	@Index private String Comarca;
	@Index private String Localitat;


	public Cinema(String id, String nom, String adreca, String localitat, String comarca, String Provincia) {
		Id = id;
		Nom = nom;
		Adreca = adreca;
		Localitat = localitat;
		Comarca = comarca;
	}



	public Cinema() {
		Id = null;
		Nom = null;
		Adreca = null;
		Localitat = null;
		Comarca = null;
		Provincia = null;

	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getNom() {
		return Nom;
	}

	public void setNom(String nom) {
		Nom = nom;
	}

	public String getAdreca() {
		return Adreca;
	}

	public void setAdreca(String adreca) {
		Adreca = adreca;
	}

	public String getLocalitat() {
		return Localitat;
	}

	public void setLocalitat(String localitat) {
		Localitat = localitat;
	}

	public String getComarca() {
		return Comarca;
	}

	public void setComarca(String comarca) {
		Comarca = comarca;
	}

	public void setProvincia(String provincia) {
		Provincia = provincia;
	}

	public String getProvincia() {

		return Provincia;
	}

	@Override
	public String toString() {
		return "Cinema{" +
				"Nom='" + Nom + '\'' +
				", Localitat='" + Localitat + '\'' +
				", Comarca='" + Comarca + '\'' +
				'}';
	}
}