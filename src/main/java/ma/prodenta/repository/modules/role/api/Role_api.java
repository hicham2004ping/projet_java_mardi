package ma.prodenta.repository.modules.role.api;
import ma.prodenta.entities.En.Role;
import ma.prodenta.repository.common.CrudRepository;

public interface Role_api extends CrudRepository<Role,Integer> {
    public Role find_by_nom(String nom);
}
