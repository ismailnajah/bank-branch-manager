package Classes;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;

public class Agence implements Serializable {

	public Client clientCourant;
	public Compte compteCourant;
	public ArrayList<Compte> lesComptes;
	public ArrayList<Client> lesClients;
	private int code;
	private String nomAgence;

	public Agence(int _code){
		code = _code;
		lesClients = new ArrayList();
		lesComptes = new ArrayList();
	}

	public void addClient(Client c){
		lesClients.add(c);
		lesComptes.add(c.getCompte());
	}

	public void addCompte(Compte cpte){
		lesComptes.add(cpte);
		lesClients.add(cpte.getProprietaire());
	}

	public Compte getCompte(int code){
		for(int i=0 ; i < lesComptes.size() ; i++ ){
			if(lesComptes.get(i).getCode() == code)
				return lesComptes.get(i);
		}
		return null;
	}
	
	public Client getClient(String CIN) {
		for(Client c : lesClients) {
			if(c.getCin().toUpperCase().equals(CIN.toUpperCase()))
				return c;
		}
		return null;
	}

	public String getNom(){
		return nomAgence;
	}
	public int getCode() {
		return code;
	}
	public void setNom(String nom){
		nomAgence = nom;
	}
	

	public String toString(){
		return " Code d'Agence: "+code+"\n Nom d'Agence: "+nomAgence+"\n Nombre des Clients: "+lesClients.size()+"\n";
	}

	public static Agence loadAgence(String name) {
		Agence agence = null;
		try {
			FileInputStream file = new FileInputStream("Agences\\"+name);
			ObjectInputStream obj = new ObjectInputStream(file);
			agence = (Agence)obj.readObject();
			obj.close();
			file.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return agence;
	}

	public static ArrayList<String> getStoredAgences(String folderName) {
		ArrayList<String> list = new ArrayList<>();
		File folder = new File(folderName);
		if(folder.exists() && folder.isDirectory()) {
			for (File fileEntry : folder.listFiles()) {
					list.add(fileEntry.getName());
			}
		}
		return list;
	}

	public void save() {
		try {
			File folder = new File("Agences");
			if(!(folder.exists() && folder.isDirectory())) {
				folder.mkdir();
			}

			FileOutputStream file = new FileOutputStream("Agences\\"+this.getNom()+"-"+this.getCode());
			ObjectOutputStream obj = new ObjectOutputStream(file);
			obj.writeObject(this);
			obj.close();
			file.close();

		} catch (Exception e){
			e.printStackTrace();
		}

	}

	public void removeClient(Client client) {
		System.out.println(client.toString());
		lesClients.remove(client);
		lesComptes.remove(client.getCompte());
	}
}