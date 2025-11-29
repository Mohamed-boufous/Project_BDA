package com.bd.gestion_bancaire.services;

import com.bd.gestion_bancaire.enums.RoleName;
import com.bd.gestion_bancaire.models.Client;
import com.bd.gestion_bancaire.models.Employe;
import com.bd.gestion_bancaire.repositories.ClientRepository;
import com.bd.gestion_bancaire.repositories.EmployeRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ClientRepository clientRepository;
    private final EmployeRepository employeRepository;

    public UserDetailsServiceImpl(ClientRepository clientRepository, EmployeRepository employeRepository){
        this.clientRepository = clientRepository;
        this.employeRepository = employeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Employe> employeOptional = employeRepository.findByEmail(email);
        if(employeOptional.isPresent()){
            Employe employe = employeOptional.get();
            System.out.println(employeOptional.get().getRole().name());
            return new User(
                    employe.getEmail(),
                    employe.getMotDePasse(),
                    List.of(new SimpleGrantedAuthority(employe.getRole().name()))
            );
        }

        Optional<Client> clientOptional = clientRepository.findByEmail(email);
        if(clientOptional.isPresent() && clientOptional.get().getCompte().getEtatCompte()==1){
            Client client = clientOptional.get();
            return new User(
                    client.getEmail(),
                    client.getMotDePasse(),
                    List.of(new SimpleGrantedAuthority(RoleName.ROLE_CLIENT.name()))
            );
        }

        throw new UsernameNotFoundException("Aucun utilisateur trouve avec l'email : " + email);
    }
}
