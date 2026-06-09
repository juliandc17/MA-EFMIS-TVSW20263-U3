package edu.unisabana.tyvs.domain.service;

import edu.unisabana.tyvs.domain.model.Person;
import edu.unisabana.tyvs.domain.model.RegisterResult;

public class Registry {

    public RegisterResult registerVoter(Person p) {
        // Validación defensiva: persona nula no puede registrarse
        if (p == null) return RegisterResult.INVALID;

        // Una persona fallecida no puede votar
        if (!p.isAlive()) return RegisterResult.DEAD;

        return RegisterResult.VALID;
    }
}