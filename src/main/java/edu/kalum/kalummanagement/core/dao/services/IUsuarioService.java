package edu.kalum.kalummanagement.core.dao.services;
import edu.kalum.kalummanagement.core.model.entities.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUsuarioService {
    public Usuario findByUsername(String username);
    public Usuario findByEmail(String email);
    public Page<Usuario> findAll(Pageable page);
    public Usuario findById(Long id);
    public Usuario save(Usuario usuario);
    public void delete(Long id);
}
