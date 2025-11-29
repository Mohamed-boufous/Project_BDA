package com.bd.gestion_bancaire.services;

import com.bd.gestion_bancaire.models.Agence;
import com.bd.gestion_bancaire.models.Client;
import com.bd.gestion_bancaire.dto.ClientRegistrationRequest;
import com.bd.gestion_bancaire.repositories.AgenceRepository;
import com.bd.gestion_bancaire.repositories.ClientRepository;
import com.bd.gestion_bancaire.repositories.EmployeRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final ClientRepository clientRepository;
    private final EmployeRepository employeRepository;
    private final AgenceRepository agenceRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(ClientRepository clientRepository, EmployeRepository employeRepository, AgenceRepository agenceRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.employeRepository = employeRepository;
        this.agenceRepository = agenceRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registerClient(ClientRegistrationRequest request) {
        if (clientRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email deja utilise par un clinet !");
        }
        if (employeRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email deja utilise par un Employe !");
        }

        Agence agence = agenceRepository.findById(request.getIdAgence())
                .orElseThrow(() -> new RuntimeException("Agence n'est pas trouvee."));

        Client client = new Client();
        client.setTypeClient(request.getTypeClient());
        client.setNom(request.getNom());
        client.setPrenom(request.getPrenom());
        client.setNationalite(request.getNationalite());
        client.setRaisonSociale(request.getRaisonSociale());
        client.setCinPassport(request.getCinPassport());
        client.setEmail(request.getEmail());
        client.setTelephone(request.getTelephone());
        client.setAdresse(request.getAdresse());

        client.setMotDePasse(passwordEncoder.encode(request.getMotDePasse()));
        client.setAgence(agence);

        clientRepository.save(client);
    }
}
