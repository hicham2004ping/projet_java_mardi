package ma.prodenta.repository.modules.antecedent_patient.impl;
import ma.prodenta.config.SessionFactory;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Patient;
import ma.prodenta.entities.Enum.NiveauRisque;
import ma.prodenta.repository.modules.antecedent_patient.api.Antecedent_patient;
import ma.prodenta.repository.modules.antecedent.impl.Antecedent_impl;
import ma.prodenta.repository.modules.patient.patient_impl.Patient_impl;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            id_risuqe=rs.getInt("idRisque");
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
            join patient_antecedent pa on pa.id_patient = p.idPatient
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
            System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
        }
        return false;
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
        List<Patient> patients = new ArrayList<>();

        String requete = """
        SELECT DISTINCT p.*
        FROM patient p
        JOIN patient_antecedent pa ON p.id = pa.id_patient;
    """;

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement pst = conn.prepareStatement(requete)) {

            ResultSet rs = pst.executeQuery();
            Patient_impl patientImpl = new Patient_impl();

            while (rs.next()) {
                Patient p = patientImpl.mapResultSetToPatient(rs);

                p.setAntecedents(find_antecedent_by_patient(p));

                patients.add(p);
            }
        }

        return patients;

    }

    @Override
    public Patient findById(Integer integer) throws Exception {
        String requete = "SELECT * FROM patient WHERE id = ?";

        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement pst = conn.prepareStatement(requete)) {

            pst.setInt(1, integer);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Patient_impl patientImpl = new Patient_impl();
                Patient patient = patientImpl.mapResultSetToPatient(rs);

                patient.setAntecedents(find_antecedent_by_patient(patient));

                return patient;
            }
            return null;
        }
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
    public boolean modifier(Patient objet){
        boolean flag = false;

        try{
            flag=supprimer_antecedent_par_patient(objet);
            if(flag){
                flag=this.create(objet);
                return true;
            }
            return false;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(Patient objet) throws SQLException {
        boolean flag = false;
         flag= deleteById(objet.getId());
        return flag;
    }

    @Override
    public boolean deleteById(Integer id) throws SQLException {
        boolean flag=false;
        String requete = "DELETE FROM patient_antecedent WHERE id_patient = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement pst = conn.prepareStatement(requete)) {
            pst.setInt(1, id);
            flag= pst.executeUpdate() > 0;
            return flag;
        }
    }

    @Override
    public Optional<Antecedent> findByNom(String nom)  {
        return Optional.empty();
    }

    @Override
    public boolean ajouter_antecedent_patient(Patient patient,Antecedent antecedent)throws SQLException {
        String requete= """
                insert into patient_antecedent values(?,?,?)
                """;
        int id=0;
        id=get_last_id();
        try(Connection conn= SessionFactory.getInstance().getConnection();
        PreparedStatement pst=conn.prepareStatement(requete))
        {
            pst.setInt(1,id);
            pst.setInt(2,patient.getId());
            pst.setInt(3,antecedent.getIdAntecedent());
            return pst.executeUpdate() > 0;
        }
    }
    @Override
    public boolean supprimer_antecedent_patient(Patient p,Antecedent antecedent)throws SQLException {
        String requete= """
                delete from patient_antecedent where id_patient=? and id_antecedent=?
                """;
        int id=0;
        id=get_last_id();
        try(Connection conn= SessionFactory.getInstance().getConnection();
            PreparedStatement pst=conn.prepareStatement(requete))
        {
            pst.setInt(1,p.getId());
            pst.setInt(2,antecedent.getIdAntecedent());
            return pst.executeUpdate() > 0;
        }
    }
}
