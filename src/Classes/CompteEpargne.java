package Classes;

import java.io.Serializable;

public class CompteEpargne extends Compte implements Serializable{

	private float taux;

	public CompteEpargne(float soldeInitial){
		super(soldeInitial);
	}

	public float getTaux(){
		return taux;
	}

	public void setTaux(float t){
		taux = t;
	}

	public void calculInterets(){
		solde *= (1+taux/100);
	}

	@Override
	public String getType() {
		return "Epargne";
	}

	public String toString(){
		return super.toString()+"\n Taux d'interet: "+taux+"%";
	}
}