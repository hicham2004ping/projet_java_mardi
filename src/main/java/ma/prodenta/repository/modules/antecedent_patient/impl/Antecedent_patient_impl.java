package ma.prodenta.repository.modules.antecedent_patient.impl;
import ma.prodenta.config.SessionFactory;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Patient;
import ma.prodenta.entities.Enum.Assurance;
import ma.prodenta.entities.Enum.NiveauRisque;
import ma.prodenta.entities.Enum.Sexe;
import ma.prodenta.repository.modules.antecedent_patient.api.Antecedent_patient;
import ma.prodenta.repository.modules.antecedent.impl.Antecedent_impl;
import ma.prodenta.repository.modules.patient.patient_impl.Patient_impl;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Antecedent_patient_impl implements Antecedent_patient {
    @Override
    public List<Antecedent> find_antecedent_by_patient(Patient patient) throws SQLException {
        List<Antecedent> antecedents = new ArrayList<>();
        int id_risuqe;
        String requete= """
                select * from antecedent a,patient_antecedent pa
                where pa.id_antecedent=a.idantecedent
                and pa.id_patient=?
                """;
        Antecedent antecedent = new Antecedent();
        try(Connection conn=SessionFactory.getInstance().getConnection();
        PreparedStatement pst=conn.prepareStatement(requete))
        {
        pst.setInt(1,patient.getId());
        ResultSet rs=pst.executeQuery();
        Antecedent_impl antecedent_impl=new Antecedent_impl();
        while(rs.next()){
            antecedent.setIdAntecedent(rs.getInt("id_antecedent"));
            antecedent.setNom(rs.getString("nom"));
            antecedent.setCategorie(rs.getString("categorie"));
            id_risuqe=rs.getInt("id_risuqe");
            NiveauRisque n=antecedent_impl.map_to_enum(id_risuqe);
            antecedent.setNiveauRisque(n);
            antecedents.add(antecedent);
            antecedent=new Antecedent();
    }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return antecedents;
    }

    public List<Patient> find_patients_by_antecedent(Antecedent antecedent) throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String requete = """
            select p.* from patient p
            join patient_antecedent pa on pa.id_patient = p.id
            where pa.id_antecedent = ?
        """;
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement pst = conn.prepareStatement(requete)) {
            pst.setInt(1, antecedent.getIdAntecedent());
            ResultSet rs = pst.executeQuery();
            Patient_impl patient_impl = new Patient_impl();

            while (rs.next()) {
                Patient patient = patient_impl.mapResultSetToPatient(rs);
                patients.add(patient);
            }

        } catch (SQLException e) {
            throw e;
        }

        return patients;
    }

    @Override
    public int nombre_antecedent_par_patient(Patient patient) throws SQLException {
        String requete = "select count(*) from patient_antecedent where id_patient = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement pst = conn.prepareStatement(requete)) {
            pst.setInt(1, patient.getId());
            ResultSet rs = pst.executeQuery();
            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            throw e;
        }
        return 0;
    }


    @Override
    public boolean supprimer_antecedent_par_patient(Patient patient) throws SQLException {
        String requete = "delete from patient_antecedent where id_patient = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement pst = conn.prepareStatement(requete)) {

            pst.setInt(1, patient.getId());
            int rows = pst.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            throw e;
        }

    }

    @Override
    public int get_last_id() {
        String requete= """
                select max(id) from patient_antecedent;
                """;
        int id=0;
        try(Connection conn=SessionFactory.getInstance().getConnection();
        PreparedStatement stmt=conn.prepareStatement(requete))
        {
            ResultSet rs=stmt.executeQuery();
            if(rs.next()) {
                id = rs.getInt(1);
            }
            return id+1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Patient> findAll() throws Exception {
        return List.of();
    }

    @Override
    public Patient findById(Integer integer) throws Exception {
        return null;
    }

    @Override
    public boolean create(Patient patient) throws SQLException, IOException {
        List<Antecedent> antecedents = patient.getAntecedents();
        if(antecedents==null || antecedents.isEmpty()){
            return false;
        }
        int cmp=0;
        int cmp1=0;
        int last_id = get_last_id();
        for (Antecedent antecedent : antecedents) {
            String requete= """
                insert into patient_antecedent values(?,?,?)
                """;
            try(Connection conn= SessionFactory.getInstance().getConnection();
                PreparedStatement stmt=conn.prepareStatement(requete)){
                stmt.setInt(1,last_id);
                last_id+=1;
                stmt.setInt(2,patient.getId());
                stmt.setInt(3,antecedent.getIdAntecedent());
                cmp+=stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            cmp1+=1;
        }
        return cmp1==cmp;
    }

    @Override
    public void update(Patient objet) {

    }

    @Override
    public boolean delete(Patient objet) throws SQLException {
        return false;
    }

    @Override
    public boolean deleteById(Integer integer) throws SQLException {
        return false;
    }

    @Override
    public Optional<Antecedent> findByNom(String nom) {
        return Optional.empty();
    }

     static void main() {
         Antecedent_patient_impl a = new Antecedent_patient_impl();
         Patient_impl p = new Patient_impl();
         Patient patient = new Patient();
         Antecedent_impl a1 = new Antecedent_impl();
         List<Antecedent> liste = new ArrayList<>();
         try {
             for (int i = 1; i <= 5; i++) {
                 liste.add(a1.findById(i));
             }
             patient.setId(p.get_last_id());
             patient.setNom("safiyeddine");
             patient.setPrenom("hachem");
             patient.setDateNaissance(LocalDate.of(2024, 10, 3));
             patient.setAdresse("Dahia");
             patient.setTelephone("0777181657");
             patient.setSexe(Sexe.Homme);
             patient.setEmail("hezbollah");
             patient.setAssurance(Assurance.CNSS);
             p.create(patient);
             patient.setAntecedents(liste);
             System.out.println("l'id du patient est " + patient.getId());
             boolean flag = a.create(patient);
             if (flag) {
                 System.out.println("valider");
             } else {
                 System.out.println("erreur");
             }
             System.out.println("le nom du patient c'est" + patient.getNom());
         } catch (Exception e) {
             System.out.println(e.getMessage());
         }
     }
}
