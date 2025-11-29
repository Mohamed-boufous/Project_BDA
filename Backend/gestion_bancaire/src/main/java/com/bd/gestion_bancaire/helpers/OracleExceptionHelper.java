package com.bd.gestion_bancaire.helpers;

import org.springframework.dao.DataAccessException;
import java.sql.SQLException;

public class OracleExceptionHelper {

    public static String extractCleanMessage(DataAccessException dae) {

        Throwable cause = dae;

        while (cause != null) {

            if (cause instanceof SQLException) {
                String oracleMessage = cause.getMessage();

                if (oracleMessage != null && oracleMessage.contains("ORA-20")) {

                    int startIndex = oracleMessage.indexOf("ORA-");
                    int messageStartIndex = oracleMessage.indexOf(':', startIndex) + 2;

                    if (messageStartIndex > startIndex) {
                        String cleanMessage = oracleMessage.substring(messageStartIndex).trim();

                        int newlineIndex = cleanMessage.indexOf('\n');
                        if (newlineIndex != -1) {
                            cleanMessage = cleanMessage.substring(0, newlineIndex);
                        }

                        return cleanMessage;
                    }
                }
            }

            cause = cause.getCause();
        }

        // Message de secours si le message Oracle n'a pas été trouvé après parcours
        return "Une erreur de base de données inattendue s'est produite.";
    }
}