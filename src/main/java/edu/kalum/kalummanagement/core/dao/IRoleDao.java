package edu.kalum.kalummanagement.core.dao;
import edu.kalum.kalummanagement.core.model.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleDao extends JpaRepository<Role,Long> {

}
