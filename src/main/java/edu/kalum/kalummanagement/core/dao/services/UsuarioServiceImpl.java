package edu.kalum.kalummanagement.core.dao.services;
import edu.kalum.kalummanagement.core.dao.IUsuarioDao;
import edu.kalum.kalummanagement.core.model.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements IUsuarioService, UserDetailsService {
    @Autowired
    private IUsuarioDao usuarioDao;
    @Override
    public Usuario findByUsername(String username) {
        return this.usuarioDao.findByUsername(username);
    }

    @Override
    public Usuario findByEmail(String email) {
        return this.usuarioDao.findByEmail(email);
    }

    @Override
    public Page<Usuario> findAll(Pageable page) {
        return this.usuarioDao.findAll(page);
    }

    @Override
    public Usuario findById(Long id) {
        return this.usuarioDao.findById(id).orElse(null);
    }

    @Override
    public Usuario save(Usuario usuario) {
        return this.usuarioDao.save(usuario);
    }

    @Override
    public void delete(Long id) {
        this.usuarioDao.deleteById(id);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioDao.findByUsername(username);
        if (usuario == null){
            throw new UsernameNotFoundException("Error en el login, no existe el usuario ".concat(username).concat(" en el sistema"));
        }
        List<GrantedAuthority> authorities = usuario.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toList());
        return new User(usuario.getUsername(), usuario.getPassword(), usuario.isEnabled(), true,true,true,authorities);
    }
}
