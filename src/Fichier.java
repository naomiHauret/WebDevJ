public class Fichier {
	
	int statut;
	int present;
	String nom;
	String contenu;
	String chemin;
	
	public Fichier(int statut, String nom, String contenu, String chemin, int present){
		this.statut= statut; //0 pour fichier créé, 1 pour fichier ouvert
		this.nom= nom;
		this.contenu= contenu; 
		this.chemin=chemin;
		this.present= present; //0 pour oui, 1 pour non
	}
	
	public int getStatut(){
		return this.statut;
	}
	
	public int getPresent(){
		return this.present;
	}
	
	public String getChemin(){
		return this.chemin;
	}
	
	public void setChemin(String ceChemin){
		this.chemin= ceChemin;
	}
	
	public String getNom(){
		return this.nom;
	}
	
	public String getContenu(){
		return this.contenu;
	}
	
	
	public void setStatut(int unStatut){
		this.statut= unStatut;
	}
	
	public void setPresent(int unePresence){
		this.present= unePresence;
	}
	
	public void setNom(String unNom){
		this.nom= unNom;
	}
	
	public void setContenu(String unContenu){
		this.contenu= unContenu;
	}
	
	
	public boolean estOuvert(Fichier unFichier){
		int leStatut= unFichier.getStatut();
		boolean resu= false;
		
		if(leStatut==1){
			resu=true;
		}
		
		return resu;
	}
}
