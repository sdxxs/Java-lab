
package zoo;
import zoo.animals.Animal;
import zoo.cages.Cage;
import java.util.ArrayList;
import java.util.List;
public class Zoo {
    private final List<Cage<? extends Animal>> cages = new ArrayList<>();
    public int getCountOfAnimals(){
        int total = 0;
        for (Cage<? extends Animal> c : cages) total += c.getOccupied();
        return total;
    }
    public void addCage(Cage<? extends Animal> cage){ cages.add(cage); }
    public List<Cage<? extends Animal>> getCages(){ return List.copyOf(cages); }
}
