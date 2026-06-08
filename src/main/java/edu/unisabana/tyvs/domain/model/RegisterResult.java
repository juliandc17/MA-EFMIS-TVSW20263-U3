package edu.unisabana.tyvs.domain.model;

/**
 * Resultado posible al intentar registrar un votante en el sistema.
 */
public enum RegisterResult {
    /** La persona fue registrada exitosamente. */
    VALID,
    /** Ya existe un registro con el mismo número de documento. */
    DUPLICATED,
    /** La persona no cumple los requisitos básicos (null, id inválido, edad fuera de rango). */
    INVALID,
    /** La persona está registrada como fallecida. */
    DEAD,
    /** La persona es menor de 18 años. */
    UNDERAGE,
    /** La edad proporcionada está fuera del rango permitido (0-120). */
    INVALID_AGE
}
