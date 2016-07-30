package br.com.ciscience.scienceci.util;

import java.util.UUID;

/**
 * Created by pedrodimoura on 29/07/16.
 */
public class StringUtils {

    /**
     * Este método gera uma String randomica
     * Utilize em todas as requisições para webservices para evitar receber dados desatualizados
     * devido a armazenamento em memória cache
     *
     * @return String randomica
     */
    public static String geraRandom() {
        UUID uuid = UUID.randomUUID();
        String myRandom = uuid.toString();
        return myRandom.substring(0, 20);
    }

}
