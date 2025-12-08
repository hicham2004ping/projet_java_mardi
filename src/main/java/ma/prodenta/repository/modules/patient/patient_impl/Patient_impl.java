package ma.prodenta.repository.modules.patient.patient_impl;
import com.mysql.cj.protocol.Resultset;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Patient;
import ma.prodenta.entities.Enum.Assurance;
import ma.prodenta.repository.common.Connextion_db;
import ma.prodenta.repository.common.CrudRepository;
import ma.prodenta.repository.modules.patient.api.PatientDao;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ma.prodenta.entities.Enum.Sexe;
import ma.prodenta.repository.modules.antecedent_patient.impl.Antecedent_patient_impl;
import javax.xml.transform.Result;
import ma.prodenta.config.SessionFactory;

@Data
@NoArgsConstructor
public class Patient_impl implements PatientDao {

    public static Connextion_db connetion_base;

    public int get_last_id() throws IOException, SQLException {
        int id = 0;

        try (
                Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement pst = conn.prepareStatement("select max(idpatient) from patient");
                ResultSet rs = pst.executeQuery()
        ) {
            if (rs.next()) {
                id = rs.getInt(1);
            }
        }

        return id + 1;
    }


    @Override
    public Patient findByEmail(String email) {

        String sql = "SELECT * FROM patient WHERE email=?";

        try (
                Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)
        ) {
            pst.setString(1, email);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return mapResultSetToPatient(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Optional<Patient> findByTelephone(String telephone) {
        return Optional.empty();
    }


    @Override
    public List<Patient> searchByNomPrenom(String keyword) {

        List<Patient> patients = new ArrayList<>();
        String sql = """
            SELECT * FROM patient
            WHERE nom LIKE ? OR prenom LIKE ?
        """;

        try (
                Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)
        ) {

            pst.setString(1, "%" + keyword + "%");
            pst.setString(2, "%" + keyword + "%");

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                patients.add(mapResultSetToPatient(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return patients;
    }


    @Override
    public boolean existsById(Long id) {

        String sql = "SELECT 1 FROM patient WHERE idpatient=?";

        try (
                Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)
        ) {

            pst.setLong(1, id);
            return pst.executeQuery().next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public long count() {

        String sql = "SELECT COUNT(*) FROM patient";

        try (
                Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery()
        ) {

            if (rs.next()) {
                return rs.getLong(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }


    @Override
    public Patient mapResultSetToPatient(ResultSet rs) throws SQLException {

        Patient patient = new Patient();
        Antecedent_patient_impl antecedent = new Antecedent_patient_impl();
        List<Antecedent> liste = new ArrayList<>();

        patient.setId(rs.getInt("idpatient"));

        Date dateNaissanceSql = rs.getDate("datenaissance");
        if (dateNaissanceSql != null) {
            patient.setDateNaissance(dateNaissanceSql.toLocalDate());
        }

        patient.setNom(rs.getString("nom"));
        patient.setPrenom(rs.getString("prenom"));
        patient.setAdresse(rs.getString("adresse"));
        patient.setTelephone(rs.getString("telephone"));
        patient.setEmail(rs.getString("email"));

        patient.setSexe(rs.getInt("idsexe") == 1 ? Sexe.Homme : Sexe.Femme);

        switch (rs.getInt("idassurance")) {
            case 1 -> patient.setAssurance(Assurance.CNOPS);
            case 2 -> patient.setAssurance(Assurance.CNSS);
            case 3 -> patient.setAssurance(Assurance.RAMED);
            default -> patient.setAssurance(Assurance.Aucune);
        }

        try {
            liste = antecedent.find_antecedent_by_patient(patient);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        patient.setAntecedents(liste);

        return patient;
    }


    @Override
    public List<Patient> findPage(int limit, int offset) {
        return List.of();
    }

    @Override
    public void addAntecedentToPatient(Long patientId, Long antecedentId) {}

    @Override
    public void removeAntecedentFromPatient(Long patientId, Long antecedentId) {}

    @Override
    public void removeAllAntecedentsFromPatient(Long patientId) {}

    @Override
    public List<Antecedent> getAntecedentsOfPatient(Long patientId) {
        return List.of();
    }

    @Override
    public List<Patient> getPatientsByAntecedent(Long antecedentId) {
        return List.of();
    }

    @Override
    public List<Patient> findAll() throws Exception {

        List<Patient> patients = new ArrayList<>();

        String requete = "SELECT * FROM patient";

        try (
                Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(requete);
                ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {
                patients.add(mapResultSetToPatient(rs));
            }

        }

        return patients;
    }


    @Override
    public Patient findById(Integer id) throws Exception {

        String sql = "SELECT * FROM patient WHERE idpatient=?";

        try (
                Connection conn = SessionFactory.getInstance().getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)
        ) {

            pst.setInt(1, id);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return mapResultSetToPatient(rs);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public boolean create(Patient objet) throws SQLException {
        List<Antecedent> liste = objet.getAntecedents();
        Antecedent_patient_impl antecedent_patient_impl = new Antecedent_patient_impl();
        String requete = """
            insert into patient
            (nom,datenaissance,adresse,telephone,idsexe,idassurance,prenom,email)
            values (?,?,?,?,?,?,?,?)
        """;

        try (
                Connection con = SessionFactory.getInstance().getConnection();
                PreparedStatement prp =
                        con.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS)
        ) {

            int id_sexe = objet.getSexe() == Sexe.Homme ? 1 : 2;

            int id_assurance = switch (objet.getAssurance()) {
                case CNOPS -> 1;
                case CNSS -> 2;
                case RAMED -> 3;
                default -> 4;
            };

            prp.setString(1, objet.getNom());
            prp.setDate(2, Date.valueOf(objet.getDateNaissance()));
            prp.setString(3, objet.getAdresse());
            prp.setString(4, objet.getTelephone());
            prp.setInt(5, id_sexe);
            prp.setInt(6, id_assurance);
            prp.setString(7, objet.getPrenom());
            prp.setString(8, objet.getEmail());

            int nombre_lignes = prp.executeUpdate();

            if (nombre_lignes == 0) return false;

            ResultSet rs = prp.getGeneratedKeys();

            if (rs.next()) {
                objet.setId(rs.getInt(1));
            }

            if (liste != null && !liste.isEmpty()) {
                return antecedent_patient_impl.create(objet);
            }
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Patient objet) throws SQLException {

        Antecedent_patient_impl antecedent_patient_impl = new Antecedent_patient_impl();

        List<Antecedent> newList = objet.getAntecedents();
        List<Antecedent> oldList = antecedent_patient_impl.find_antecedent_by_patient(objet);

        for (Antecedent a : oldList) {
            if (!newList.contains(a)) {
                antecedent_patient_impl.supprimer_antecedent_patient(objet, a);
            }
        }

        for (Antecedent a : newList) {
            if (!oldList.contains(a)) {
                antecedent_patient_impl.ajouter_antecedent_patient(objet, a);
            }
        }

        String sql = """
            UPDATE patient
            SET nom=?,prenom=?,datenaissance=?,adresse=?,telephone=?,email=?,
                idsexe=?,idassurance=?
            WHERE idpatient=?
        """;

        try (
                Connection con = SessionFactory.getInstance().getConnection();
                PreparedStatement pst = con.prepareStatement(sql)
        ) {

            pst.setString(1, objet.getNom());
            pst.setString(2, objet.getPrenom());
            pst.setDate(3, Date.valueOf(objet.getDateNaissance()));
            pst.setString(4, objet.getAdresse());
            pst.setString(5, objet.getTelephone());
            pst.setString(6, objet.getEmail());
            pst.setInt(7, objet.getSexe() == Sexe.Homme ? 1 : 2);

            int assuranceId = switch (objet.getAssurance()) {
                case CNOPS -> 1;
                case CNSS -> 2;
                case RAMED -> 3;
                default -> 4;
            };

            pst.setInt(8, assuranceId);
            pst.setInt(9, objet.getId());

            pst.executeUpdate();
        }
    }

    @Override
    public boolean delete(Patient objet) throws SQLException, IOException {
        return deleteById(objet.getId());
    }

    @Override
    public boolean deleteById(Integer objet) throws SQLException, IOException {

        String sql = "DELETE FROM patient WHERE idpatient=?";

        try (
                Connection con = SessionFactory.getInstance().getConnection();
                PreparedStatement pst = con.prepareStatement(sql)
        ) {

            pst.setInt(1, objet);
            return pst.executeUpdate() > 0;
        }
    }

    @Override
    public Optional<Antecedent> findByNom(String nom) {
        return Optional.empty();
    }
}
