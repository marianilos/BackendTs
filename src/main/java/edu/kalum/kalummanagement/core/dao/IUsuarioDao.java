package edu.kalum.kalummanagement.core.dao;
import edu.kalum.kalummanagement.core.model.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IUsuarioDao extends JpaRepository<Usuario,Long> {
    public Usuario findByUsername(String username);
    //public Usuario findUsuarioByApellidos(String apellidos);

    //Ejemplo para demostrar que se pueden realizar queries
    @Query("select u from Usuario u where u.email=?1")
    public Usuario findByEmail(String email);

}
