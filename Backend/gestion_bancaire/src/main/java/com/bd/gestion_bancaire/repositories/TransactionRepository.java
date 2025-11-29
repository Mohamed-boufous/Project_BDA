package com.bd.gestion_bancaire.repositories;

import com.bd.gestion_bancaire.models.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions, Long> {
    @Query("SELECT t FROM Transactions t WHERE t.emetteur.rib = :rib OR t.destinataire.rib = :rib ORDER BY t.dateTransaction DESC")
    List<Transactions> findAllByRib(@Param("rib") String rib);
}
