package ma.prodenta.service.common;

public interface Service<T,ID> {
    boolean create(T objet) throws Exception;
    boolean update(T objet) throws Exception;
    void delete(T objet) throws Exception;
    T find(ID id) throws Exception;
}

