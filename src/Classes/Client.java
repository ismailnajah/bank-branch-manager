package Classes;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;
import java.sql.Date;

public class Client implements Serializable{
	public static int nbClient;
	private int matricule;
	private String nom;
	private String prenom;
	private String cin;
	private Date date_naissance;
	private Compte compte;

	public Client(String nom,String prenom,String cin,Date date_naissance){
		this.matricule = ++nbClient;
		this.nom = nom;
		this.prenom = prenom;
		this.cin = cin;
		this.date_naissance = date_naissance;
	}

	public void affecterCompte(Compte c) {
		if(c!=null) {
			if(c.getProprietaire()!=null) {
				c.getProprietaire().setCompte(null);
			}
			c.setProprietaire(this);
			this.setCompte(c);
		}
	}

	public int getMatricule() {
		return matricule;
	}

	public void setMatricule(int matricule) {
		this.matricule = matricule;
	}

	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public Compte getCompte() {
		return compte;
	}

	public void setCompte(Compte compte) {
		this.compte = compte;
	}

	public Date getDate_naissance() {
		return date_naissance;
	}

	public void setDate_naissance(Date date_naissance) {
		this.date_naissance = date_naissance;
	}

	public String toString(){
		return " Le Client "+getNom()+"\n date naissance : "+date_naissance.toString()
				+"\n CIN : "+getCin()+"\n Matricule : "+getMatricule() +compte.toString();
	}


}
