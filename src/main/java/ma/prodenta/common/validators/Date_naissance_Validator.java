package ma.prodenta.common.validators;
import java.time.LocalDate;

public class Date_naissance_Validator {
    private LocalDate date_ajourdhui;
    public Date_naissance_Validator(LocalDate date_naissance){
        date_ajourdhui=LocalDate.now();
        if(date_naissance==null){
            throw new IllegalArgumentException("il faut entrer une date");
        }
        else if(date_ajourdhui.isAfter(date_naissance)){
            throw new IllegalArgumentException("il faut entrer une date valide ");
        }
    }
}
