package Classes;

import java.io.Serializable;

public class ComptePayant extends Compte implements Serializable{
	public static float tauxOperation=5;

	public ComptePayant(float soldeInitial){
		super(soldeInitial);
	}

	public void retirer(float mt){
		if(mt <= solde-tauxOperation)
			solde -= mt + tauxOperation;
	}

	public void verser(float mt){
		solde += mt - tauxOperation;
	}

	public String toString(){
		return super.toString()+"\n Taux d'operation: "+tauxOperation;
	}
}