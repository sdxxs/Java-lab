// src/main/java/zoo/animals/Animal.java
package zoo.animals;

public abstract class Animal {
    private final String name;
    protected Animal(String name) { this.name = name; }
    public String getName() { return name; }
    @Override public String toString() { return getClass().getSimpleName() + "(" + name + ")"; }
}
