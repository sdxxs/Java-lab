package zoo.animals;

import java.util.Objects;

public abstract class Animal {
    private final String name;

    protected Animal(String name){ this.name = name; }

    public String getName(){ return name; }

    @Override
    public String toString(){ return getClass().getSimpleName() + "(" + name + ")"; }

    // вважаємо, що ім'я унікальне в межах виду.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false; // тип має співпадати
        Animal animal = (Animal) o;
        return Objects.equals(name, animal.name);
    }

}
