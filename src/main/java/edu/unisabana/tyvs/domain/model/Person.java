package edu.unisabana.tyvs.domain.model;

/**
 * Representa a una persona que desea registrarse como votante.
 * Objeto de dominio puro: no depende de ningún framework ni infraestructura.
 */
public class Person {

    private final String name;
    private final int id;
    private final int age;
    private final Gender gender;
    private final boolean alive;

    public Person(String name, int id, int age, Gender gender, boolean alive) {
        this.name   = name;
        this.id     = id;
        this.age    = age;
        this.gender = gender;
        this.alive  = alive;
    }

    public String getName()   { return name;   }
    public int    getId()     { return id;      }
    public int    getAge()    { return age;     }
    public Gender getGender() { return gender;  }
    public boolean isAlive()  { return alive;   }
}
