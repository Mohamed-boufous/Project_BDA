package com.bd.gestion_bancaire.services;

import com.bd.gestion_bancaire.dto.AffectationRequest;
import com.bd.gestion_bancaire.dto.EmployeRecrutementRequest;
import com.bd.gestion_bancaire.dto.EmployeResponse;
import com.bd.gestion_bancaire.helpers.OracleExceptionHelper;
import com.bd.gestion_bancaire.models.Employe;
import com.bd.gestion_bancaire.repositories.EmployeRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeService {

    private final EmployeRepository employeRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeService(EmployeRepository employeRepository, PasswordEncoder passwordEncoder) {
        this.employeRepository = employeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<EmployeResponse> getAllEmployes() {
        List<Employe> employes = employeRepository.findAll();

        return employes.stream()
                .map(employe -> new EmployeResponse(
                        employe.getCin(), employe.getNom(), employe.getPrenom(),
                        employe.getPoste(), employe.getSalaire(), employe.getDateRecrutement(),
                        employe.getEmail(), employe.getTelephone()
                ))
                .collect(Collectors.toList());
    }

    public EmployeResponse getEmploye(String cin){
        try {
            if (employeRepository.findByCin(cin).isPresent()){
                Employe employe = employeRepository.findByCin(cin).get();
                return new EmployeResponse(
                        employe.getCin(), employe.getNom(), employe.getPrenom(),
                        employe.getPoste(), employe.getSalaire(), employe.getDateRecrutement(),
                        employe.getEmail(), employe.getTelephone()
                );
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        return null;
    }

    @Transactional(rollbackOn = Exception.class)
    public void recruterEmploye(EmployeRecrutementRequest request){
        try{
            employeRepository.recruterEmploye(
                    request.getCin(),
                    request.getManagerCin(),
                    request.getIdAgence(),
                    request.getNom(),
                    request.getPrenom(),
                    request.getPoste(),
                    request.getSalaire(),
                    passwordEncoder.encode(request.getMotDePasse()),
                    request.getEmail(),
                    request.getTelephone()
            );
        } catch (DataAccessException dae){
            throw new RuntimeException(OracleExceptionHelper.extractCleanMessage(dae));
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public void licencierEmploye(String cin) {
        try {
            employeRepository.licencierEmploye(cin);
        } catch (DataAccessException dae) {
            throw new RuntimeException(OracleExceptionHelper.extractCleanMessage(dae));
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public void changerAffectation(AffectationRequest affectRequest) {
        try {
            employeRepository.changerAffectation(
                    affectRequest.getCin(),
                    affectRequest.getManagerId(),
                    affectRequest.getIdAgence()
            );
        } catch (DataAccessException dae) {
            throw new RuntimeException(OracleExceptionHelper.extractCleanMessage(dae));
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public void augmenterSalaire(String cin, BigDecimal taux_augmentation) {
        try {
            employeRepository.augmenterSalaire(cin, taux_augmentation);
        } catch (DataAccessException dae) {
            throw new RuntimeException(OracleExceptionHelper.extractCleanMessage(dae));
        }
    }

    public BigDecimal calculerEmployeAnciennete(String cin){
        try {
            return employeRepository.calculerAnciennete(cin);
        } catch (DataAccessException dae) {
            throw new RuntimeException(OracleExceptionHelper.extractCleanMessage(dae));
        }
    }
}
