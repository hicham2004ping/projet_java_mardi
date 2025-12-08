package ma.prodenta.repository.test_repository;
import ma.prodenta.config.SessionFactory;
import ma.prodenta.entities.En.RDV;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class test_RDV {

    public static void Testrdv() {
        Date d = new Date(104, 8, 9); // 9 septembre 2004
        RDV p1=new RDV();
        int i=1;
        Time t = Time.valueOf("14:30:45");
        p1.setIdRDV(i);
        p1.setIddossier(1);
        p1.setDateRDV(d);
        p1.setMotif("trois dent casse");
        p1.setHeure(t);
        p1.setNoteMedecin("trois dent casse");

}
}
