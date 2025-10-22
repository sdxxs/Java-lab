package zoo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import zoo.animals.*;
import zoo.cages.*;
import zoo.cages.exceptions.*;

class ZooTest {
    @Test
    void countAnimals() {
        Zoo zoo = new Zoo();
        LionCage lc = new LionCage(2);
        lc.add(new Lion("Simba"));
        lc.add(new Lion("Nala"));
        zoo.addCage(lc);
        assertEquals(2, zoo.getCountOfAnimals());
    }

    @Test
    void overfillThrows() {
        LionCage lc = new LionCage(1);
        lc.add(new Lion("One"));
        assertThrows(CageFullException.class, () -> lc.add(new Lion("Two")));
    }

    @Test
    void removeMissingThrows() {
        BirdCage bc = new BirdCage(1);
        bc.add(new Eagle("E1"));
        assertThrows(AnimalNotFoundException.class, () -> bc.remove(new Eagle("E2")));
    }

    @Test
    void removeByEqualityWorks() {
        BirdCage bc = new BirdCage(2);
        bc.add(new Eagle("E1"));
        bc.remove(new Eagle("E1"));
        assertEquals(0, bc.getOccupied());
    }


}
