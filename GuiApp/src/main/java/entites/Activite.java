package entites;

import java.util.Date;

public class Activite {
    private String nomAnimateur;
    private String titre;
    private String periode;
    private String numeroLocal;
    private Date dateDebut;
    private Date dateFin;
    private String occurence;
    private String dureeEnHeure;
    private String clienteleVisee;
    

    public Activite(String nomAnimateur, String titre, String periode, String numeroLocal, Date dateDebut, Date dateFin, String occurence, String dureeEnHeure, String clienteleVisee) {
        this.nomAnimateur = nomAnimateur;
        this.titre = titre;
        this.periode = periode;
        this.numeroLocal = numeroLocal;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.occurence = occurence;
        this.dureeEnHeure = dureeEnHeure;
        this.clienteleVisee = clienteleVisee;
    }

    public String getNomAnimateur() {
        return nomAnimateur;
    }

    public void setNomAnimateur(String nomAnimateur) {
        this.nomAnimateur = nomAnimateur;
    }

    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    public String getNumeroLocal() {
        return numeroLocal;
    }

    public void setNumeroLocal(String numeroLocal) {
        this.numeroLocal = numeroLocal;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getOccurence() {
        return occurence;
    }

    public void setOccurence(String occurence) {
        this.occurence = occurence;
    }

    public String getDureeEnHeure() {
        return dureeEnHeure;
    }

    public void setDureeEnHeure(String dureeEnHeure) {
        this.dureeEnHeure = dureeEnHeure;
    }

    public String getClienteleVisee() {
        return clienteleVisee;
    }

    public void setClienteleVisee(String clienteleVisee) {
        this.clienteleVisee = clienteleVisee;
    }


    public String getTitre() {
        return this.titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

}
