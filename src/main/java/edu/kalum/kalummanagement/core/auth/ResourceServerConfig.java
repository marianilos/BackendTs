package edu.kalum.kalummanagement.core.auth;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import java.util.Arrays;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests()
                .antMatchers(HttpMethod.GET,"/kalum-management/v1/carreras-tecnicas/**").permitAll() //Permite la busqueda sin necesidad de token
                .antMatchers(HttpMethod.GET,"/kalum-management/v1/examenes-admision/**").permitAll()
                .antMatchers(HttpMethod.GET,"/kalum-management/v1/jornadas/**").permitAll()
               // .antMatchers(HttpMethod.POST,"/kalum-management/v1/usuario").permitAll()
                .anyRequest().authenticated()
                .and().cors().configurationSource(corsConfigurationSource());
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration config = new CorsConfiguration();
        //config.setAllowedOrigins(Arrays.asList("http://localhost:3000","http://kalum-app:3000"));
        config.setAllowedOrigins(Arrays.asList("http://kalum-app:5173","http://localhost:5173")); //permitiendo la conexion de los cors que no vienen del puerto expuesto, en este caso 5173 ya que el app utliza el 9000
        config.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS")); //los metodos que estamos permitiendo utilizar
        config.setAllowCredentials(true);
        config.setAllowedHeaders(Arrays.asList("Content-Type","Authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",config);
        return source;
    }
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter(){
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource()));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}

