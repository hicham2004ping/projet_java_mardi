package ma.prodenta.repository.modules.certificat.api;

import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Certificat;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CertificatDao {

    // Récupérer tous les certificats
    List<Certificat> findAll() throws Exception;

    // Trouver un certificat par ID
    Certificat findById(Integer id) throws Exception;

    // Créer un certificat
    boolean create(Certificat cert) throws SQLException, IOException;

    // Mettre à jour un certificat
    void update(Certificat cert) throws Exception;

    // Supprimer un certificat via l'objet
    boolean delete(Certificat cert) throws Exception;

    // Supprimer un certificat via son ID
    boolean deleteById(Integer id) throws Exception;

    // Fonction héritée (non utilisée ici)
    Optional<Antecedent> findByNom(String nom);
    public int get_last_id();
    public List<Certificat> findByConsultation(Integer idConsult) throws Exception;
    public List<Certificat> findByDossier(Integer idDossier) throws Exception;
}

