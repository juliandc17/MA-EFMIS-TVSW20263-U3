package edu.unisabana.tyvs.domain.service;

import edu.unisabana.tyvs.domain.model.Person;
import edu.unisabana.tyvs.domain.model.RegisterResult;

public class Registry {

    public RegisterResult registerVoter(Person p) {
        // Validación defensiva: persona nula no puede registrarse
        if (p == null) return RegisterResult.INVALID;

        // El identificador debe ser un número positivo
        if (p.getId() <= 0) return RegisterResult.INVALID;

        // Una persona fallecida no puede votar
        if (!p.isAlive()) return RegisterResult.DEAD;

        // La edad debe estar dentro del rango biológico aceptado
        if (p.getAge() < 0 || p.getAge() > 120) return RegisterResult.INVALID_AGE;

        // Solo los mayores de edad pueden votar
        if (p.getAge() < 18) return RegisterResult.UNDERAGE;

        return RegisterResult.VALID;
    }
}