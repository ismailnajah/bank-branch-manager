package Classes;

import java.io.*;
import java.util.ArrayList;

public class Agence implements Serializable {
    public String filePath = "";
	public ArrayList<Compte> lesComptes;
	public ArrayList<Client> lesClients;
	private int code;
	private String nomAgence;

	public Agence(int _code){
		code = _code;
		lesClients = new ArrayList();
		lesComptes = new ArrayList();
	}

    public Agence() {
        code = -1;
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

    public static Agence loadAgence(String path) {
		Agence agence = null;
		try {
            FileInputStream file = new FileInputStream(path);
			ObjectInputStream obj = new ObjectInputStream(file);
			agence = (Agence)obj.readObject();
            agence.setFilePath(path);
			obj.close();
			file.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return agence;
	}

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String toString() {
        return " Code d'Agence: " + code + "\n Nom d'Agence: " + nomAgence + "\n Nombre des Clients: " + lesClients.size() + "\n";
    }

    public void save(String path) {
		try {
            filePath = path;
            FileOutputStream file = new FileOutputStream(path);
			ObjectOutputStream obj = new ObjectOutputStream(file);
			obj.writeObject(this);
			obj.close();
			file.close();

		} catch (Exception e){
			e.printStackTrace();
		}

	}

	public void removeClient(Client client) {
		lesClients.remove(client);
		lesComptes.remove(client.getCompte());
	}

    public void setCode(String text) {
        if (!text.isEmpty())
            code = Integer.parseInt(text);
    }
}