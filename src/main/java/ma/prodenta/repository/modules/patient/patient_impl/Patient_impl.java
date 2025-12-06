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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ma.prodenta.entities.Enum.Sexe;
import ma.prodenta.repository.modules.antecedent_patient.impl.Antecedent_patient_impl;
import javax.xml.transform.Result;

@Data @NoArgsConstructor
public class Patient_impl implements PatientDao  {
    public static  Connextion_db connetion_base;
    public int get_last_id() throws IOException, SQLException {
        int id=0;
        try(Connection conn=DriverManager.getConnection(new Connextion_db().getUrl(),new Connextion_db().getUsername(),new Connextion_db().getPassword())){
            PreparedStatement pst=conn.prepareStatement("select max(idpatient) from patient");
            ResultSet rs=pst.executeQuery();
            if(rs.next()){
                id=rs.getInt(1);
            }
        }
        return id+1;
    }
    @Override
    public Patient findByEmail(String email) {
        try (Connection conn = DriverManager.getConnection(
                new Connextion_db().getUrl(),
                new Connextion_db().getUsername(),
                new Connextion_db().getPassword())) {

            String sql = "SELECT * FROM patient WHERE email = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
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

        try (Connection conn = DriverManager.getConnection(
                new Connextion_db().getUrl(),
                new Connextion_db().getUsername(),
                new Connextion_db().getPassword())) {

            String sql = """
                SELECT * FROM patient
                WHERE nom LIKE ? OR prenom LIKE ?
                """;

            PreparedStatement pst = conn.prepareStatement(sql);
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
        try (Connection conn = DriverManager.getConnection(
                new Connextion_db().getUrl(),
                new Connextion_db().getUsername(),
                new Connextion_db().getPassword())) {

            String sql = "SELECT 1 FROM patient WHERE idpatient=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setLong(1, id);
            return pst.executeQuery().next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public long count() {
        try (Connection conn = DriverManager.getConnection(
                new Connextion_db().getUrl(),
                new Connextion_db().getUsername(),
                new Connextion_db().getPassword())) {

            String sql = "SELECT COUNT(*) FROM patient";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

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
        Antecedent_patient_impl antecedent=new Antecedent_patient_impl();
        List<Antecedent> liste=new ArrayList<>();
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
        int id_sexe = rs.getInt("idsexe");
        if (id_sexe == 1) {
            patient.setSexe(Sexe.Homme);
        } else {
            patient.setSexe(Sexe.Femme);
        }

        int id_assurance = rs.getInt("idassurance");
        switch (id_assurance) {
            case 1 -> patient.setAssurance(Assurance.CNOPS);
            case 2 -> patient.setAssurance(Assurance.CNSS);
            case 3 -> patient.setAssurance(Assurance.RAMED);
            default -> patient.setAssurance(Assurance.Aucune);
        }
        try{
            liste=antecedent.find_antecedent_by_patient(patient);
        }
        catch(Exception e){
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
    public void addAntecedentToPatient(Long patientId, Long antecedentId) {

    }

    @Override
    public void removeAntecedentFromPatient(Long patientId, Long antecedentId) {

    }

    @Override
    public void removeAllAntecedentsFromPatient(Long patientId) {
    }

    @Override
    public List<Antecedent> getAntecedentsOfPatient(Long patientId) {
        return List.of();
    }

    @Override
    public List<Patient> getPatientsByAntecedent(Long antecedentId) {
        return List.of();
    }

    @Override
    public List<Patient> findAll() throws Exception,IOException, SQLException {
        List<Patient> patients=new ArrayList<>();
        Patient patient=new Patient();
        try(Connection conn=DriverManager.getConnection(new Connextion_db().getUrl(),new Connextion_db().getUsername(),new Connextion_db().getPassword())){
            String requete= """
                    select * from patient
                    """;
            PreparedStatement stmt=conn.prepareStatement(requete);
            ResultSet rs=stmt.executeQuery();
            while(rs.next()){
                patient.setId(rs.getInt("idpatient"));
                patient.setDateNaissance(rs.getDate("datenaissance").toLocalDate());
                patient.setAdresse(rs.getString("adresse"));
                patient.setTelephone(rs.getString("telephone"));
                patient.setNom(rs.getString("nom"));
                int id_sexe=rs.getInt("idsexe");
                int id_assurance=rs.getInt("idassurance");
                if (id_sexe==1){
                    patient.setSexe(Sexe.Homme);
                }
                else{
                    patient.setSexe(Sexe.Femme);
                }
                if (id_assurance==1){
                    patient.setAssurance(Assurance.CNOPS);
                }
                else if (id_assurance==2){
                    patient.setAssurance(Assurance.CNSS);
                }
                else if (id_assurance==3){
                    patient.setAssurance(Assurance.RAMED);
                }
                else{
                    patient.setAssurance(Assurance.Aucune);
                }
                patients.add(patient);
                patient=new Patient();
            }
            return patients;
        }
    }

    @Override
    public Patient findById(Integer id) throws Exception {
        try (Connection conn = DriverManager.getConnection(
                new Connextion_db().getUrl(),
                new Connextion_db().getUsername(),
                new Connextion_db().getPassword())) {

            String sql = "SELECT * FROM patient WHERE idpatient=?";
            PreparedStatement pst = conn.prepareStatement(sql);
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
        List<Antecedent> liste=objet.getAntecedents();
        Antecedent_patient_impl antecedent_patient_impl=new Antecedent_patient_impl();
        boolean flag;
        try(Connection con=DriverManager.getConnection(new Connextion_db().getUrl(), new Connextion_db().getUsername(),new Connextion_db().getPassword())){
            String requete= """
                    insert into patient
                    (nom,datenaissance,adresse,telephone,idsexe,idassurance,prenom,email)
                    values
                    (?,?,?,?,?,?,?,?)
                    """;
            int id_sexe;
            int id_assurance;
            if (objet.getSexe().name().equals("Homme")){
                id_sexe=1;
            }
            else{
                id_sexe=2;
            }
            if (objet.getAssurance().name().equals("CNOPS")){
                id_assurance=1;
            }
            else{
                id_assurance=2;
            }
            PreparedStatement prp=con.prepareStatement(requete,Statement.RETURN_GENERATED_KEYS);
            prp.setString(1,objet.getNom());
            prp.setDate(2,java.sql.Date.valueOf(objet.getDateNaissance()));
            prp.setString(3,objet.getAdresse());
            prp.setString(4,objet.getTelephone());
            prp.setInt(5,id_sexe);
            prp.setInt(6,id_assurance);
            prp.setString(7,objet.getPrenom());
            prp.setString(8,objet.getEmail());
            int nombre_lignes=prp.executeUpdate();
            ResultSet rs=prp.getGeneratedKeys();
            if(nombre_lignes==0){return false;}
            if(rs.next()){
                objet.setId(rs.getInt(1));
            }
            System.out.println("lid du patient c'est "+objet.getId());
            if (liste!=null && !liste.isEmpty()){
                flag=antecedent_patient_impl.create(objet);
                return (nombre_lignes>0 && flag);
            }
            else{
                return  (nombre_lignes>0);
            }
        }
        catch(SQLException ex){
            System.err.println(ex.getMessage());
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Patient objet) throws SQLException {
        Antecedent_patient_impl antecedent_patient_impl=new Antecedent_patient_impl();
        List<Antecedent> liste=objet.getAntecedents();
        List<Antecedent> liste1=antecedent_patient_impl.find_antecedent_by_patient(objet);

        for(Antecedent antecedent:liste1){
            if(!liste.contains(antecedent)){
              boolean flag = antecedent_patient_impl.supprimer_antecedent_patient(objet,antecedent);
            }
        }

        for(Antecedent antecedent:liste){
            if(!liste1.contains(antecedent)){
             boolean flag =   antecedent_patient_impl.ajouter_antecedent_patient(objet,antecedent);
            }
        }
        try (Connection con = DriverManager.getConnection(
                new Connextion_db().getUrl(),
                new Connextion_db().getUsername(),
                new Connextion_db().getPassword())) {

            String sql = """
                UPDATE patient
                SET nom=?,prenom=?,datenaissance=?,adresse=?,telephone=?,email=?,idsexe=?,idassurance=?
                WHERE idpatient=?
                """;
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, objet.getNom());
            pst.setString(2, objet.getPrenom());
            pst.setDate(3, java.sql.Date.valueOf(objet.getDateNaissance()));
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean delete(Patient objet) throws SQLException, IOException {
        return deleteById(objet.getId());
    }

    @Override
    public boolean deleteById(Integer objet) throws SQLException, IOException {
        try (Connection con = DriverManager.getConnection(
                new Connextion_db().getUrl(),
                new Connextion_db().getUsername(),
                new Connextion_db().getPassword())) {
            String sql = "DELETE FROM patient WHERE idpatient=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, objet);
            return pst.executeUpdate() > 0;
        }
    }

    @Override
    public Optional<Antecedent> findByNom(String nom) {
        return Optional.empty();
    }
}
