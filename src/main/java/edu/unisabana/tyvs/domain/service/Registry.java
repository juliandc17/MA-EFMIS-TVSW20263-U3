package edu.unisabana.tyvs.domain.service;

import edu.unisabana.tyvs.domain.model.Person;
import edu.unisabana.tyvs.domain.model.RegisterResult;

import java.util.HashSet;
import java.util.Set;

/**
 * Servicio de dominio para el registro de votantes en la registraduría.
 *
 * <p>Reglas de negocio implementadas:</p>
 * <ul>
 *   <li>No se aceptan personas nulas.</li>
 *   <li>El id debe ser mayor a cero.</li>
 *   <li>La persona debe estar viva.</li>
 *   <li>La edad debe estar en el rango [18, 120].</li>
 *   <li>No se permiten registros duplicados por id.</li>
 * </ul>
 */
public class Registry {

    /** Edad mínima para votar (inclusive). */
    private static final int MIN_VOTING_AGE = 18;

    /** Edad máxima biológicamente aceptada (inclusive). */
    private static final int MAX_AGE = 120;

    /** Ids de personas ya registradas (unicidad por documento). */
    private final Set<Integer> registeredIds = new HashSet<>();

    /**
     * Intenta registrar a una persona como votante.
     *
     * @param p la persona a registrar
     * @return el resultado del intento de registro
     */
    public RegisterResult registerVoter(Person p) {

        // 1. Validación defensiva: null
        if (p == null) {
            return RegisterResult.INVALID;
        }

        // 2. Id inválido (debe ser positivo)
        if (p.getId() <= 0) {
            return RegisterResult.INVALID;
        }

        // 3. Persona fallecida
        if (!p.isAlive()) {
            return RegisterResult.DEAD;
        }

        // 4. Edad fuera de rango biológico
        if (p.getAge() < 0 || p.getAge() > MAX_AGE) {
            return RegisterResult.INVALID_AGE;
        }

        // 5. Menor de edad
        if (p.getAge() < MIN_VOTING_AGE) {
            return RegisterResult.UNDERAGE;
        }

        // 6. Registro duplicado
        if (registeredIds.contains(p.getId())) {
            return RegisterResult.DUPLICATED;
        }

        // 7. Todo correcto: registrar
        registeredIds.add(p.getId());
        return RegisterResult.VALID;
    }
}
