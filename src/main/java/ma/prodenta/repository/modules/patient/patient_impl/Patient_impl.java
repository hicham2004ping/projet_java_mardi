package ma.prodenta.repository.modules.patient.patient_impl;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Patient;
import ma.prodenta.entities.Enum.Assurance;
import ma.prodenta.repository.common.Connextion_db;
import ma.prodenta.repository.common.CrudRepository;
import ma.prodenta.repository.modules.patient.api.PatientDao;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import ma.prodenta.entities.Enum.Sexe;
class Patient_impl implements PatientDao  {
    public static  Connextion_db connetion_base;
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
    public List<Patient> findAll() throws Exception {
        return List.of();
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
                    (nom,datenaissance,adresse,telephone,idsexe,idassurance)
                    values
                    (?,?,?,?,?,?)
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
    //on peut le transformer en main en editons le nom vers main
    public static void test(String[] args){
        List<Antecedent> list=null;
        Patient p=new Patient(2,"karaki",LocalDate.of(2024,9,20),"beirut","021831241",Sexe.Homme, Assurance.CNOPS,list);
        try{
            Patient_impl pat=new Patient_impl();
            boolean flag=pat.create(p);
            if(flag){
                System.out.println("l'utilisateur a ete bien creer ");
            }
            else{
                System.out.println("l'utilisateur n'a pas ete bien creer");
            }
        }
        catch(Exception ex){
            System.err.println(ex.getMessage());
        }
    }
}