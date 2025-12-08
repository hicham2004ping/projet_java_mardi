package ma.prodenta.repository.modules.dossierMedical.implementation;
import ma.prodenta.config.SessionFactory;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.DossierMedical;
import ma.prodenta.entities.En.Patient;
import ma.prodenta.repository.modules.dossierMedical.api.DossierMedicalRepository;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
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
            PreparedStatement ps=conn.prepareStatement(requete);)
        {
            ps.setDate(1, Date.valueOf(LocalDate.now()));
            ps.setInt(2, objet.getIdPatient());
            ps.setInt(3, objet.getIdMedecin());
            return ps.executeUpdate()>0;
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
}
