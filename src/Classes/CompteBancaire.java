package Classes;

import java.io.Serializable;

public class CompteBancaire extends Compte implements Serializable{
	public CompteBancaire(float soldeInitial){
		super(soldeInitial);
		decouvert = 200;
	}

	public CompteBancaire(){
		super();
		decouvert = 200;
	}
	
	public void setDecouvert(float d){
		decouvert = d;
	}

	@Override
	public String getType() {
		return "Compte Bancaire";
	}

	public String toString() {
		return super.toString()+"\n Decouvert: "+decouvert;
	}
}