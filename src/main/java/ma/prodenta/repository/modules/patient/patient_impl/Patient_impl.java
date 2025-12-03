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
        return id;
    }
    @Override
    public Patient findByEmail(String email) {
        return null;
    }

    @Override
    public Optional<Patient> findByTelephone(String telephone) {
        return Optional.empty();
    }

    @Override
    public List<Patient> searchByNomPrenom(String keyword) {
        return List.of();
    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public long count() {
        return 0;
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
    public Patient findById(Long aLong) throws Exception {
        return null;
    }

    @Override
    public boolean create(Patient objet) throws SQLException {
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
            System.out.println("l'id du sexe est "+id_sexe);
            if (objet.getAssurance().name().equals("CNOPS")){
                id_assurance=1;
            }
            else{
                id_assurance=2;
            }
            System.out.println("l'id du assurance est "+id_assurance);
            PreparedStatement prp=con.prepareStatement(requete);
            prp.setString(1,objet.getNom());
            prp.setDate(2,java.sql.Date.valueOf(objet.getDateNaissance()));
            prp.setString(3,objet.getAdresse());
            prp.setString(4,objet.getTelephone());
            prp.setInt(5,id_sexe);
            prp.setInt(6,id_assurance);
            prp.setString(7,objet.getPrenom());
            prp.setString(8,objet.getEmail());
            int nombre_lignes=prp.executeUpdate();
            return  (nombre_lignes>0);
        }
        catch(SQLException ex){
            System.err.println(ex.getMessage());
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Patient objet) {

    }

    @Override
    public boolean delete(Patient objet) throws SQLException {
        return false;
    }

    @Override
    public boolean deleteById(Long aLong) throws SQLException {
        return false;
    }

    @Override
    public Optional<Antecedent> findByNom(String nom) {
        return Optional.empty();
    }
    public static void main(String[] args) {
        LocalDate date=LocalDate.of(2024,9,27);
        ArrayList<Antecedent> listeAntecedents=new ArrayList<>();
        Patient patient=new Patient("nascerallah",3,"hassan",date,"Beirut","hezbollah@gmail.com","0777181657",Sexe.Femme,Assurance.CNOPS,listeAntecedents);
        try{
            Patient_impl p=new Patient_impl();
           boolean flag = p.create(patient);
           if(flag==true){
               System.out.println("le patient a ete creer ");
           }
           else{
               System.out.println("le patient a ete supprimer ");
           }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}
