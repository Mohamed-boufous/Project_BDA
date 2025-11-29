package com.bd.gestion_bancaire.services;

import com.bd.gestion_bancaire.dto.AgenceResponse;
import com.bd.gestion_bancaire.helpers.OracleExceptionHelper;
import com.bd.gestion_bancaire.models.Agence;
import com.bd.gestion_bancaire.repositories.AgenceRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgenceService {
    private final AgenceRepository agenceRepository;

    public AgenceService(AgenceRepository agenceRepository) {
        this.agenceRepository = agenceRepository;
    }

    public List<AgenceResponse> getAllAgences() {
        List<Agence> agences = agenceRepository.findAll();

        return agences.stream()
                .map(agence -> new AgenceResponse(
                        agence.getIdAgence(),
                        agence.getNomAgence(),
                        agence.getNumTelephone(),
                        agence.getVille(),
                        agence.getCodePostal()
                ))
                .collect(Collectors.toList());
    }

    public AgenceResponse getAgence(Long idAgence){
        try {
            if (agenceRepository.findAgenceByIdAgence(idAgence).isPresent()){
                Agence agence = agenceRepository.findAgenceByIdAgence(idAgence).get();
                return new AgenceResponse(
                  agence.getIdAgence(), agence.getNomAgence(), agence.getNumTelephone(),
                  agence.getVille(), agence.getCodePostal()
                );
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        return null;
    }


    public BigDecimal getSalaireTotalAgence(Long idAgence) {
        try {
            return agenceRepository.calculerSalaireTotalAgence(idAgence);
        } catch (DataAccessException dae) {
            throw new RuntimeException(OracleExceptionHelper.extractCleanMessage(dae));
        }
    }
}
