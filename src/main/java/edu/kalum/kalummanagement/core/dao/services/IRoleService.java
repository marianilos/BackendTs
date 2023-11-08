package edu.kalum.kalummanagement.core.dao.services;
import edu.kalum.kalummanagement.core.model.entities.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRoleService {
    public Page<Role> findAll(Pageable page);
    public Role findById(Long id);
    public Role save(Role role);
    public void delete(Long id);
}
