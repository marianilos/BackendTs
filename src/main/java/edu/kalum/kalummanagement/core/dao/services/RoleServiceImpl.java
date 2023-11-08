package edu.kalum.kalummanagement.core.dao.services;
import edu.kalum.kalummanagement.core.dao.IRoleDao;
import edu.kalum.kalummanagement.core.model.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements IRoleService{
    @Autowired
    private IRoleDao roleDao;
    @Override
    public Page<Role> findAll(Pageable page) {
        return this.roleDao.findAll(page);
    }
    @Override
    public Role findById(Long id) {
        return this.roleDao.findById(id).orElse(null);
    }
    @Override
    public Role save(Role role) {
        return this.roleDao.save(role);
    }
    @Override
    public void delete(Long id) {
        this.roleDao.deleteById(id);
    }
}
