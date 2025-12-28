package ma.prodenta.entities.Enum;

public enum Sexe {
    Homme(1) ,
    Femme(2);
    private  int id;
    Sexe(int id) {
        this.id=id;
    }
    public int getId() {
        return id;
    }
    public static Sexe get_sexeby_id(int idd){
        for(Sexe s:Sexe.values()){
            if(s.getId()==idd)
                return s;
        }
        throw new IllegalArgumentException("Le sexe n'existe pas");
    }
    public static Sexe get_sexeby_libelle(String libelle){
        for(Sexe s:Sexe.values()){
            if(s.name().equals(libelle))
                return s;
        }
        throw new IllegalArgumentException("Le sexe n'existe pas");
    }
}
