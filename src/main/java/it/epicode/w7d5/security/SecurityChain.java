package it.epicode.w7d5.security;

import it.epicode.w7d5.enums.Ruolo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityChain {

    @Autowired
    private JwtTools jwtTools;
    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.cors(AbstractHttpConfigurer::disable);

        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/auth/**").permitAll());
        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/evento/create/**").hasAnyAuthority(Ruolo.ADMIN.name(), Ruolo.ORGANIZZATORE.name()));
        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/evento/update/**").hasAnyAuthority(Ruolo.ADMIN.name(), Ruolo.ORGANIZZATORE.name()));
        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/evento/delete/**").hasAnyAuthority(Ruolo.ADMIN.name(), Ruolo.ORGANIZZATORE.name()));
        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/evento/{id}/assign").permitAll());
        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/evento/search/**").permitAll());

        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/utente/create").hasAnyAuthority(Ruolo.ADMIN.name(), Ruolo.ORGANIZZATORE.name()));
        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/utente/update/**").hasAnyAuthority(Ruolo.ADMIN.name(), Ruolo.ORGANIZZATORE.name()));
        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/utente/delete/**").hasAnyAuthority(Ruolo.ADMIN.name(), Ruolo.ORGANIZZATORE.name()));
        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/utente/role/**").hasAnyAuthority(Ruolo.ADMIN.name(), Ruolo.ORGANIZZATORE.name()));
        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/utente/search/**").hasAnyAuthority(Ruolo.ADMIN.name()));

        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/**").denyAll());

        return httpSecurity.build();
    }

}
