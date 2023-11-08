package edu.kalum.kalummanagement.core.auth;
import edu.kalum.kalummanagement.core.dao.services.IUsuarioService;
import edu.kalum.kalummanagement.core.model.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
@Component
public class InfoAditionalToken implements TokenEnhancer {
    @Autowired
    private IUsuarioService usuarioService;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        Usuario usuario = this.usuarioService.findByUsername(oAuth2Authentication.getName());
        Map<String,Object> info = new HashMap<>();
        info.put("apellidos", usuario.getApellidos());
        info.put("nombres", usuario.getNombres());
        info.put("email",usuario.getEmail());
        info.put("identificationId","0");
        ((DefaultOAuth2AccessToken)oAuth2AccessToken).setAdditionalInformation(info);
        return oAuth2AccessToken;
    }
}
