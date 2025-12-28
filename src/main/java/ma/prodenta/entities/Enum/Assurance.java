package ma.prodenta.entities.Enum;

public enum Assurance {

    CNOPS(1),
    CNSS(2),
    Aucune(4),
    RAMED(3);
    private int id;

    Assurance(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public Assurance get_Assurance_by_Libelle(String libelle)
    {
        for(Assurance assurance:Assurance.values()){
            if(assurance.name().equals(libelle)){
                return assurance;
            }
        }
        throw new IllegalArgumentException("le libelle de ce sexe n'existe pas ");
    }
}
