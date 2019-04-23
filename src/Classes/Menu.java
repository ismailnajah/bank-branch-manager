package Classes;

import java.io.*;
import java.util.*;
//cette class pour creer et manipuler les menus du programme 
public class Menu {
	public String[] options;
	
	Menu(Vector<String> options){
		this.options = new String[options.size()];
		for(int i=0;i<options.size();i++) {
			this.options[i] = options.get(i);
		}
	}
	
	Menu(String[] options){
		this.options = options;
	}
	
	public void show(boolean b) {
		if(b) {
			this.clear();
			this.header();
		}
		for(int i=0;i<options.length;i++)
			System.out.println(" "+(i+1)+"- "+options[i]);
	}
	
	public int getOption() throws IOException{
		Scanner in = new Scanner(System.in);
		int choice ;
		System.out.print(" Choisir une option : ");
		while(!in.hasNextInt()) {
			in.next();
			System.out.print(" #Erreur! Donner une valeur valide : ");
		}	
		choice = in.nextInt();
		while(choice < 1 || choice > options.length ){
			System.out.print(" #Erreur! choisir une option valide : ");
			while(!in.hasNextInt()) {
				in.next();
				System.out.print(" #Erreur! Donner une valeur valide : ");
			}
			choice = in.nextInt();
		}
		return choice;
	}
	
	public void header(){
		String s = "*  Programme gestion d'agence bancaire  *";
		System.out.print("\n\n       ");
		for(int i=0;i<s.length();i++)
			System.out.print("*");
		System.out.print("\n       "+s+"\n       ");
		for(int i=0;i<s.length();i++)
			System.out.print("*");
		System.out.println("\n");
	}
	
	//Method pour effacer le contenue d'ecran
	public void clear() {
		try {
			ProcessBuilder p=new ProcessBuilder("cmd", "/c", "cls");
			p.inheritIO().start().waitFor();
		}catch(IOException e) {
		}catch(InterruptedException e) {
		}
	}
}