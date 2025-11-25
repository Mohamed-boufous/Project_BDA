-- Table : HISTORIQUE_SALAIRE (Audit des changements de salaire)
create table HISTORIQUE_SALAIRE 
(
   ID_LOG               INTEGER               not null,
   CIN_EMPLOYE          VARCHAR2(10)          not null,
   ANCIEN_SALAIRE       NUMBER(10,2),
   NOUVEAU_SALAIRE      NUMBER(10,2),
   DATE_MODIFICATION    TIMESTAMP             DEFAULT SYSDATE,
   MODIFIE_PAR          VARCHAR2(30),         )
   constraint PK_HISTO_SALAIRE primary key (ID_LOG)
);
alter table HISTORIQUE_SALAIRE
   add constraint FK_HISTO_EMPLOYE foreign key (CIN_EMPLOYE)
      references EMPLOYE (CIN);
CREATE SEQUENCE HISTORIQUE_SEQ START WITH 1 INCREMENT BY 1;
-- Droit de consulter l'historique et d'y insérer via le Trigger
GRANT SELECT, INSERT ON HISTORIQUE_SALAIRE TO R_ADMIN;
-- Contrôle total sur l'audit
GRANT SELECT, INSERT, UPDATE, DELETE ON HISTORIQUE_SALAIRE TO R_SUPER_ADMIN;