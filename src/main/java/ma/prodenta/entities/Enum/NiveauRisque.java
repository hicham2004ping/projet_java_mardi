package ma.prodenta.entities.Enum;
public enum NiveauRisque {
    Faible(1),
    Modéré(2),
    Trèsdangereux(3) ,
    Dangereux(4);

    private int id;
    NiveauRisque(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public static NiveauRisque getByLibelle(String libelle) {
        for(NiveauRisque niveauRisque : values()){
            if(niveauRisque.name().equals(libelle)){
                return niveauRisque;
            }
        }
        return null;
    }
    public static void main(){
        NiveauRisque nr=NiveauRisque.getByLibelle("Faible");
        System.out.println("le niveau de risque c'est "+nr.getId());
    }
}
