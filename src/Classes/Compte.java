package Classes;

import java.io.Serializable;

public abstract class Compte implements Serializable{
	public static int nbComptes=0;
	private int code;
	protected Client proprietaire;
	protected float solde;
	protected float decouvert;
	

	Compte(){
		code = ++nbComptes;
		solde = 0;
		decouvert = 0;
	}

	Compte(float soldeInitial){
		code = ++nbComptes;
		solde = soldeInitial;
		decouvert = 0;
		proprietaire = null;
	}
	public Client getProprietaire(){
		return proprietaire;
	}
	public int getCode(){
		return code;
	}
	public float getDecouvert(){
		return decouvert;
	}
	
	public float getSolde(){
		return solde;
	}
	
	
	public void setDecouvert(float d){}
	public void calculInterets(){}
	public void setTaux(float t){}

	public void retirer(float mt){
		if(mt<=(solde+decouvert))
			solde -= mt;
	}

	public void verser(float mt){
		solde += mt;
	}
	public void setProprietaire(Client c){
		proprietaire = c;
	}
	public void affectProprietaire(Client c) {
		if(c!=null) {
			if(c.getCompte() != null) {
				c.getCompte().setProprietaire(null);
			}
			c.setCompte(this);
			this.setProprietaire(c);
		}
	}
	
	
	
	
	public String toString(){
		return "\n code du Compte : "+code+"\n Solde: "+solde+" Dh";
	}

}
