# Registro de Defectos — Taller TDD Registraduría

> Archivo de gestión de defectos detectados durante el desarrollo guiado por pruebas.

---

## Defecto 01

| Campo | Detalle |
|---|---|
| **ID** | DEF-001 |
| **Caso** | Registrar persona con edad -1 |
| **Prueba que lo detectó** | `shouldRejectNegativeAge()` |
| **Resultado esperado** | `INVALID_AGE` |
| **Resultado obtenido** | `VALID` (implementación inicial sin validación de edad) |
| **Causa probable** | Falta de validación de límite inferior de edad en `Registry.registerVoter()` |
| **Estado** | Cerrado — corregido en iteración TDD #2 |

---

## Defecto 02

| Campo | Detalle |
|---|---|
| **ID** | DEF-002 |
| **Caso** | Registrar persona con edad > 120 (ej: 121) |
| **Prueba que lo detectó** | `shouldRejectInvalidAgeOver120()` |
| **Resultado esperado** | `INVALID_AGE` |
| **Resultado obtenido** | `VALID` (sin validación de límite superior) |
| **Causa probable** | Solo se validaba el límite inferior de edad, no el superior |
| **Estado** | Cerrado — corregido junto con DEF-001 |

---

## Defecto 03

| Campo | Detalle |
|---|---|
| **ID** | DEF-003 |
| **Caso** | Registrar persona con id = 0 |
| **Prueba que lo detectó** | `shouldRejectWhenIdIsZero()` |
| **Resultado esperado** | `INVALID` |
| **Resultado obtenido** | `VALID` (no se validaba que el id fuera positivo) |
| **Causa probable** | Ausencia de validación de unicidad del identificador en formato correcto |
| **Estado** | Cerrado — corregido en iteración TDD #3 |

---

## Defecto 04

| Campo | Detalle |
|---|---|
| **ID** | DEF-004 |
| **Caso** | Registrar la misma persona dos veces (mismo id) |
| **Prueba que lo detectó** | `shouldRejectDuplicateRegistration()` |
| **Resultado esperado** | `DUPLICATED` en el segundo intento |
| **Resultado obtenido** | `VALID` (no existía control de ids ya registrados) |
| **Causa probable** | No se mantenía estado de los ids ya registrados en el servicio |
| **Estado** | Cerrado — solucionado con `Set<Integer> registeredIds` |

---

## Notas generales

- Todos los defectos fueron detectados gracias al ciclo TDD antes de llegar a entornos de prueba de integración.
- El patrón AAA facilitó identificar exactamente en qué paso fallaba la lógica.
- Los defectos DEF-001 y DEF-002 evidencian la importancia de probar **ambos extremos** de las clases de equivalencia.
