package ma.prodenta.entities.Enum;

import java.util.ArrayList;
import java.util.List;

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
    public Assurance getAssurance(int id) {
        for (Assurance assurance : Assurance.values()) {
            if (assurance.getId() == id) {
                return assurance;
            }
        }
        return null;
    }

    public static Assurance getBy_id(int id) {
        for (Assurance assurance : Assurance.values()) {
            if (assurance.getId() == id) {
                return assurance;
            }
        }
        return null;
    }

    public static Assurance  get_Assurance_by_Libelle(String libelle)
    {
        for(Assurance assurance:Assurance.values()){
            if(assurance.name().equals(libelle)){
                return assurance;
            }
        }
        throw new IllegalArgumentException("le libelle de ce sexe n'existe pas ");
    }

    public static List<Assurance> find_all(){
        List<Assurance>assurances=new ArrayList<>();
        for(Assurance assurance:Assurance.values()){
            assurances.add(assurance);
        }
        return assurances;
    }

    public static void main(){
        Assurance a=Assurance.get_Assurance_by_Libelle("CNOPS");
        System.out.println("l'd c'est "+a.getId());
    }
}
