package ma.prodenta.repository.modules.dossierMedical.implementation;
import ma.prodenta.config.Application_contexte;
import ma.prodenta.config.SessionFactory;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.DossierMedical;
import ma.prodenta.entities.En.Ordonnance;
import ma.prodenta.entities.En.Patient;
import ma.prodenta.mvc.dto.dossiermedical.Dossier_Medical_vu_generale_DTO;
import ma.prodenta.repository.modules.dossierMedical.api.DossierMedicalRepository;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Dossier_medical_impl implements DossierMedicalRepository {

    @Override
    public DossierMedical find_patient(Patient patient) throws SQLException {
        String requete= """
                select * from dossiermedical where idPatient=?
                """;
        DossierMedical ds=new DossierMedical();
        try(Connection conn= SessionFactory.getInstance().getConnection();
            PreparedStatement ps=conn.prepareStatement(requete);)
        {
            ps.setInt(1, patient.getId());
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                ds.setDateCreation(rs.getDate("dateCreation").toLocalDate());
                ds.setIdPatient(rs.getInt("idPatient"));
                ds.setIdMedecin(rs.getInt("idMedecin"));
                ds.setIdDossier(rs.getInt("idDossier"));
            }
            return ds;
        }
    }

    @Override
    public List<Ordonnance> find_ordonances(DossierMedical dossier) throws SQLException {
        String sql= """
                select o.idOrd,o.dateOrd,o.id_conultation
                from ordonnance o, dossiermedical d
                where o.idDossier=d.idDossier
                and
                o.idDossier=?
                """;
        List<Ordonnance> list=new ArrayList<>();
        Ordonnance ordonnance=new Ordonnance();
        try(Connection conn= SessionFactory.getInstance().getConnection();
        PreparedStatement ps=conn.prepareStatement(sql);)
        {
            ps.setInt(1, dossier.getIdDossier());
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
                ordonnance.setDateOrd(rs.getDate("dateOrd").toLocalDate());
                ordonnance.setIdDossier(dossier.getIdDossier());
                ordonnance.setIdOrd((long)rs.getInt("o.idOrd"));
                ordonnance.setIdConsultation(rs.getInt("id_conultation"));
                list.add(ordonnance);
                ordonnance=new Ordonnance();
            }
            return list;
        }
    }

    @Override
    public List<DossierMedical> findAll() throws Exception {
        List<DossierMedical> list_objets = new ArrayList<>();
        String requete= """
                select * from dossiermedical
                """;
        try(Connection conn=SessionFactory.getInstance().getConnection();
            PreparedStatement ps=conn.prepareStatement(requete);)
        {
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                DossierMedical objet=new DossierMedical();
                objet.setIdDossier(rs.getInt("idDossier"));
                objet.setIdPatient(rs.getInt("idPatient"));
                objet.setIdPatient(rs.getInt("idPatient"));
                objet.setDateCreation(rs.getDate("dateCreation").toLocalDate());
                list_objets.add(objet);
                objet=new DossierMedical();
            }
            return list_objets;
        }
    }

    @Override
    public DossierMedical findById(Integer integer) throws Exception {
        String requete= """
                select * from dossiermedical where idDossier=?
                """;
        DossierMedical ds=new DossierMedical();
        try(Connection conn= SessionFactory.getInstance().getConnection();
            PreparedStatement ps=conn.prepareStatement(requete);)
        {
            ps.setInt(1, integer);
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                ds.setDateCreation(rs.getDate("dateCreation").toLocalDate());
                ds.setIdPatient(rs.getInt("idPatient"));
                ds.setIdMedecin(rs.getInt("idMedecin"));
                ds.setIdDossier(rs.getInt("idDossier"));
            }
            return ds;
        }

    }
    @Override
    public boolean create(DossierMedical objet) throws SQLException, IOException {
        String requete= """
                insert into dossiermedical (dateCreation,idPatient,idMedecin) values (?,?,?)
                """;
        try(Connection conn= SessionFactory.getInstance().getConnection();
            PreparedStatement ps=conn.prepareStatement(requete,Statement.RETURN_GENERATED_KEYS))
        {
            ps.setDate(1, java.sql.Date.valueOf(objet.getDateCreation()));
            ps.setInt(2, objet.getIdPatient());
            ps.setInt(3, objet.getIdMedecin());
            boolean flag= ps.executeUpdate()>0;
            if(flag){
                ResultSet rs=ps.getGeneratedKeys();
                if(rs.next()){
                    objet.setIdDossier(rs.getInt(1));
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public void update(DossierMedical objet) throws SQLException, IOException, Exception {
    }

    @Override
    public boolean delete(DossierMedical objet) throws SQLException, Exception {
        return deleteById(objet.getIdDossier());
    }

    @Override
    public boolean deleteById(Integer integer) throws SQLException, Exception {
        String requete= """
                delete from  dossiermedical where idDossier=?
        """;
        try(Connection conn= SessionFactory.getInstance().getConnection();
        PreparedStatement ps=conn.prepareStatement(requete);){
            ps.setInt(1, integer);
            return ps.executeUpdate()>0;
        }
    }

    @Override
    public Optional<Antecedent> findByNom(String nom) {
        return Optional.empty();
    }

    public int get_last_id(){
        String requete= """
                select max(idDossier) from  dossiermedical
                """;
        int id=0;
        try(Connection conn=SessionFactory.getInstance().getConnection();
        PreparedStatement ps=conn.prepareStatement(requete);)
        {
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                id=rs.getInt(1);
            }
            return id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public int total_consultations(Patient patient) throws SQLException, IOException {
        String requete= """
                select count(*) from consultation where idDossier=?
                """;
       DossierMedical dossierMedical=find_patient(patient);
       int n = 0;
       try(Connection conn= SessionFactory.getInstance().getConnection();
       PreparedStatement ps=conn.prepareStatement(requete))
       {
        ps.setInt(1, dossierMedical.getIdDossier());
        ResultSet rs=ps.executeQuery();
        if(rs.next()){
            n = rs.getInt(1);
        }
        return n;
       }
    }

    @Override
    public int total_ordonances(Patient patient) throws SQLException, IOException {
        String requete= """
                select count(*) from ordonnance where idDossier=?
                """;
        DossierMedical dossierMedical=find_patient(patient);
        int n = 0;
        try(Connection conn= SessionFactory.getInstance().getConnection();
            PreparedStatement ps=conn.prepareStatement(requete))
        {
            ps.setInt(1, dossierMedical.getIdDossier());
            ResultSet rs=ps.executeQuery();

            if(rs.next()){
                n = rs.getInt(1);
            }
            return n;
        }
    }

    @Override
    public int total_dossier_existe() throws SQLException, IOException {
        String requete= """
                select count(*) from dossiermedical;
                """;
        int n=0;
        try(Connection conn=SessionFactory.getInstance().getConnection();
        PreparedStatement ps=conn.prepareStatement(requete))
        {
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                n=rs.getInt(1);
            }
            return n;
        }
    }

    @Override
    public int total_rendez_vous(Patient patient) throws SQLException, IOException {
        String requete= """
                select count(*) from rdv where id_dossier=?
                """;
        DossierMedical dossierMedical=find_patient(patient);
        int n = 0;
        try(Connection conn= SessionFactory.getInstance().getConnection();
        PreparedStatement ps=conn.prepareStatement(requete))
        {
            ps.setInt(1, dossierMedical.getIdDossier());
            ResultSet resultSet=ps.executeQuery();
            if(resultSet.next()){
                n=resultSet.getInt(1);
            }
            return n;
        }
    }

    @Override
    public boolean supprimer_dossier_medical_patient(Patient patient) throws SQLException {
        String requete= """
                delete from  dossiermedical where idPatient=?
                """;
        try(Connection conn=SessionFactory.getInstance().getConnection();
        PreparedStatement ps=conn.prepareStatement(requete))
        {
            ps.setInt(1,patient.getId());
            return ps.executeUpdate()>0;
        }
    }

    @Override
    public int total_certificat_patient(Patient patient) throws SQLException, IOException {
        String requete= """
                select count(*) from certificat where idDossier=?
                """;
        DossierMedical dossier=find_patient(patient);
        int n = 0;
        try(Connection conn=SessionFactory.getInstance().getConnection();
        PreparedStatement ps=conn.prepareStatement(requete)){
            ps.setInt(1, dossier.getIdDossier());
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                n=rs.getInt(1);
            }
            return n;
        }
    }

    @Override
    public List<Dossier_Medical_vu_generale_DTO> find_all_view() throws SQLException {
        List<Dossier_Medical_vu_generale_DTO>list=new ArrayList<>();
        Dossier_Medical_vu_generale_DTO dossier=new Dossier_Medical_vu_generale_DTO();
        String requete = """
                SELECT
                    d.idDossier,
                    d.dateCreation,
                    p.idPatient,
                    p.nom,
                    p.prenom,
                    COUNT(DISTINCT o.idOrd) AS total_ordonnance,
                    COUNT(DISTINCT c.idConsult) AS total_consultation
                FROM patient p
                JOIN dossiermedical d ON p.idPatient = d.idPatient
                LEFT JOIN ordonnance o ON d.idDossier = o.idDossier
                LEFT JOIN consultation c ON d.idDossier = c.idDossier
                GROUP BY
                    d.idDossier,
                    d.dateCreation,
                    p.idPatient,
                    p.nom,
                    p.prenom;
                
                
                """;
        try(Connection conn= SessionFactory.getInstance().getConnection();
        PreparedStatement ps=conn.prepareStatement(requete);)
        {
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                dossier.setIdDossier(rs.getInt("idDossier"));
                dossier.setPatient_nom(rs.getString("nom"));
                dossier.setPatient_prenom(rs.getString("prenom"));
                dossier.setDate_creation(rs.getDate("dateCreation").toLocalDate());
                dossier.setTotal_ordonnance(rs.getInt("total_ordonnance"));
                dossier.setTotal_conusltations(rs.getInt("total_consultation"));
                list.add(dossier);
                dossier=new Dossier_Medical_vu_generale_DTO();
            }
            return list;
        }
    }

    static void main(){
        Dossier_medical_impl dossier= Application_contexte.getDossierMedicalRepository();
        List<Dossier_Medical_vu_generale_DTO> dossier1=new ArrayList<>();
        try{
            dossier1=dossier.find_all_view();
            for(var dossier2:dossier1){
                System.out.println("l'id du dossier est "+dossier2.getIdDossier()+" le nom du patient c'est "+dossier2.getPatient_nom()+" le totale des consultatiosn est "+dossier2.getTotal_conusltations());
            }
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
}
