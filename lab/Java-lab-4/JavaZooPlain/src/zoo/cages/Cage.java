package zoo.cages;

import zoo.animals.Animal;
import zoo.cages.exceptions.AnimalNotFoundException;
import zoo.cages.exceptions.CageFullException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Cage<T extends Animal> {
    private final int capacity;
    private final List<T> animals = new ArrayList<>();

    protected Cage(int capacity){
        if (capacity <= 0) throw new IllegalArgumentException("Capacity must be > 0");
        this.capacity = capacity;
    }

    public int getCapacity(){ return capacity; }
    public int getOccupied(){ return animals.size(); }
    public List<T> getAnimals(){ return Collections.unmodifiableList(animals); }

    public void add(T animal){
        if (animals.size() >= capacity) throw new CageFullException(getClass().getSimpleName() + " is full");
        animals.add(animal);
    }

    // Жорстке видалення — з винятком, якщо не знайдено
    public void remove(T animal){
        boolean removed = animals.remove(animal);
        if (!removed) throw new AnimalNotFoundException(animal + " not found in " + getClass().getSimpleName());
    }

    // true, якщо видалено; без винятку
    public boolean tryRemove(T animal) {
        return animals.remove(animal);
    }

    //видалення за ім’ям
    public boolean removeByName(String name) {
        for (int i = 0; i < animals.size(); i++) {
            if (animals.get(i).getName().equals(name)) {
                animals.remove(i);
                return true;
            }
        }
        return false;
    }
}
