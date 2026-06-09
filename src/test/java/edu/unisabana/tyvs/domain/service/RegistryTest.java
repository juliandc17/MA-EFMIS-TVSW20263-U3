package edu.unisabana.tyvs.domain.service;

import edu.unisabana.tyvs.domain.model.Gender;
import edu.unisabana.tyvs.domain.model.Person;
import edu.unisabana.tyvs.domain.model.RegisterResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Pruebas unitarias para {@link Registry}.
 *
 * <p>Metodología aplicada:</p>
 * <ul>
 *   <li>TDD (Red → Green → Refactor)</li>
 *   <li>Patrón AAA (Arrange – Act – Assert)</li>
 *   <li>BDD (Given – When – Then) documentado en cada prueba</li>
 *   <li>Clases de equivalencia y valores límite</li>
 * </ul>
 */
public class RegistryTest {

    private Registry registry;

    @Before
    public void setUp() {
        // Se crea una instancia fresca de Registry antes de cada prueba
        registry = new Registry();
    }

    // =========================================================================
    // 1. CAMINO FELIZ — persona completamente válida
    // =========================================================================

    /**
     * BDD:
     * Given: persona viva, mayor de edad, id positivo y único
     * When:  se intenta registrar
     * Then:  el resultado debe ser VALID
     */
    @Test
    public void shouldRegisterValidPerson() {
        // Arrange
        Person person = new Person("Julian Caicedo", 1001, 30, Gender.MALE, true);

        // Act
        RegisterResult result = registry.registerVoter(person);

        // Assert
        Assert.assertEquals(RegisterResult.VALID, result);
    }

    // =========================================================================
    // 2. NULIDAD — validación defensiva
    // =========================================================================

    /**
     * BDD:
     * Given: la persona es null
     * When:  se intenta registrar
     * Then:  el resultado debe ser INVALID
     */
    @Test
    public void shouldReturnInvalidWhenPersonIsNull() {
        // Arrange
        Person person = null;

        // Act
        RegisterResult result = registry.registerVoter(person);

        // Assert
        Assert.assertEquals(RegisterResult.INVALID, result);
    }

    // =========================================================================
    // 3. ID INVÁLIDO — id cero o negativo
    // =========================================================================

    /**
     * BDD:
     * Given: persona con id = 0
     * When:  se intenta registrar
     * Then:  el resultado debe ser INVALID
     */
    @Test
    public void shouldRejectWhenIdIsZero() {
        // Arrange
        Person person = new Person("Luis Torres", 0, 25, Gender.MALE, true);

        // Act
        RegisterResult result = registry.registerVoter(person);

        // Assert
        Assert.assertEquals(RegisterResult.INVALID, result);
    }

    /**
     * BDD:
     * Given: persona con id negativo
     * When:  se intenta registrar
     * Then:  el resultado debe ser INVALID
     */
    @Test
    public void shouldRejectWhenIdIsNegative() {
        // Arrange
        Person person = new Person("María Pérez", -5, 25, Gender.FEMALE, true);

        // Act
        RegisterResult result = registry.registerVoter(person);

        // Assert
        Assert.assertEquals(RegisterResult.INVALID, result);
    }

    // =========================================================================
    // 4. PERSONA FALLECIDA
    // =========================================================================

    /**
     * BDD:
     * Given: persona con alive = false
     * When:  se intenta registrar
     * Then:  el resultado debe ser DEAD
     */
    @Test
    public void shouldRejectDeadPerson() {
        // Arrange
        Person dead = new Person("Carlos Ruiz", 2002, 40, Gender.MALE, false);

        // Act
        RegisterResult result = registry.registerVoter(dead);

        // Assert
        Assert.assertEquals(RegisterResult.DEAD, result);
    }

    // =========================================================================
    // 5. EDAD FUERA DE RANGO BIOLÓGICO
    // =========================================================================

    /**
     * BDD:
     * Given: persona con edad negativa (-1)
     * When:  se intenta registrar
     * Then:  el resultado debe ser INVALID_AGE
     *
     * Clase: edad < 0 — límite inferior
     */
    @Test
    public void shouldRejectNegativeAge() {
        // Arrange
        Person person = new Person("Pedro Villa", 3003, -1, Gender.MALE, true);

        // Act
        RegisterResult result = registry.registerVoter(person);

        // Assert
        Assert.assertEquals(RegisterResult.INVALID_AGE, result);
    }

    /**
     * BDD:
     * Given: persona con edad = 121 (supera el máximo biológico de 120)
     * When:  se intenta registrar
     * Then:  el resultado debe ser INVALID_AGE
     *
     * Clase: edad > 120 — límite superior
     */
    @Test
    public void shouldRejectInvalidAgeOver120() {
        // Arrange
        Person person = new Person("Anciana X", 4004, 121, Gender.FEMALE, true);

        // Act
        RegisterResult result = registry.registerVoter(person);

        // Assert
        Assert.assertEquals(RegisterResult.INVALID_AGE, result);
    }

    // =========================================================================
    // 6. MENOR DE EDAD — valores límite 17 y 18
    // =========================================================================

    /**
     * BDD:
     * Given: persona viva con 17 años (un año menor a la mayoría)
     * When:  se intenta registrar
     * Then:  el resultado debe ser UNDERAGE
     *
     * Valor límite: edad = 17 (borde de clase "menor")
     */
    @Test
    public void shouldRejectUnderageAt17() {
        // Arrange
        Person minor = new Person("Joven A", 5005, 17, Gender.MALE, true);

        // Act
        RegisterResult result = registry.registerVoter(minor);

        // Assert
        Assert.assertEquals(RegisterResult.UNDERAGE, result);
    }

    /**
     * BDD:
     * Given: persona viva con exactamente 18 años
     * When:  se intenta registrar
     * Then:  el resultado debe ser VALID
     *
     * Valor límite: edad = 18 (borde inferior de clase "válida")
     */
    @Test
    public void shouldAcceptAdultAt18() {
        // Arrange
        Person adult = new Person("Joven B", 6006, 18, Gender.FEMALE, true);

        // Act
        RegisterResult result = registry.registerVoter(adult);

        // Assert
        Assert.assertEquals(RegisterResult.VALID, result);
    }

    /**
     * BDD:
     * Given: persona viva con exactamente 120 años
     * When:  se intenta registrar
     * Then:  el resultado debe ser VALID
     *
     * Valor límite: edad = 120 (borde superior de clase "válida")
     */
    @Test
    public void shouldAcceptMaxAge120() {
        // Arrange
        Person senior = new Person("Anciana Y", 7007, 120, Gender.FEMALE, true);

        // Act
        RegisterResult result = registry.registerVoter(senior);

        // Assert
        Assert.assertEquals(RegisterResult.VALID, result);
    }

    // =========================================================================
    // 7. DUPLICADO — mismo id registrado dos veces
    // =========================================================================

    /**
     * BDD:
     * Given: persona válida ya registrada con id = 8008
     * When:  se intenta registrar nuevamente con el mismo id
     * Then:  el resultado del segundo intento debe ser DUPLICATED
     */
    @Test
    public void shouldRejectDuplicateRegistration() {
        // Arrange
        Person first  = new Person("Sofía Mora",   8008, 25, Gender.FEMALE, true);
        Person second = new Person("Sofía Mora II", 8008, 26, Gender.FEMALE, true);

        // Act
        registry.registerVoter(first);          // registro inicial
        RegisterResult result = registry.registerVoter(second); // intento duplicado

        // Assert
        Assert.assertEquals(RegisterResult.DUPLICATED, result);
    }

    // =========================================================================
    // 8. MÚLTIPLES REGISTROS ÚNICOS — aislamiento de estado
    // =========================================================================

    /**
     * BDD:
     * Given: dos personas con ids distintos, ambas válidas
     * When:  se registran ambas
     * Then:  las dos deben obtener VALID (el registro no contamina el otro)
     */
    @Test
    public void shouldAcceptTwoDistinctValidVoters() {
        // Arrange
        Person voter1 = new Person("Juan Díaz",  9001, 35, Gender.MALE,   true);
        Person voter2 = new Person("Lucía Ríos", 9002, 28, Gender.FEMALE, true);

        // Act
        RegisterResult result1 = registry.registerVoter(voter1);
        RegisterResult result2 = registry.registerVoter(voter2);

        // Assert
        Assert.assertEquals(RegisterResult.VALID, result1);
        Assert.assertEquals(RegisterResult.VALID, result2);
    }
}
