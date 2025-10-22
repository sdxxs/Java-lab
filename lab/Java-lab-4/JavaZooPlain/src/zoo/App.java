package zoo;

import zoo.animals.*;
import zoo.cages.*;

public class App {
    public static void main(String[] args) {
        Zoo zoo = new Zoo();


        LionCage lionCage = new LionCage(2);
        HoofedCage hoofedCage = new HoofedCage(3);
        BirdCage birdCage = new BirdCage(3);

        zoo.addCage(lionCage);
        zoo.addCage(hoofedCage);
        zoo.addCage(birdCage);

        Lion simba = new Lion("Simba");
        Lion nala  = new Lion("Nala");
        lionCage.add(simba);
        lionCage.add(nala);
        //lionCage.add(new Lion("Nala"));      // Тут кинеться помилка CageFullException

        hoofedCage.add(new Zebra("Marty"));
        hoofedCage.add(new Giraffe("Melman"));

        birdCage.add(new Eagle("Freedom"));
        birdCage.add(new Eagle("Sky"));

        // remove кине виняток, якщо не знайде
       // birdCage.remove(new Eagle("Mava"));   // тут кинеться помилка AnimalNotFoundException

        System.out.println("Старт: total animals = " + zoo.getCountOfAnimals());


        // (1) Видалення тим самим об'єктом
        boolean ok1 = lionCage.tryRemove(simba); // true
        System.out.println("tryRemove(simba) -> " + ok1);

        // (2) Видалення іншим екземпляром з тим самим ім'ям
        boolean ok2 = birdCage.tryRemove(new Eagle("Freedom")); // true, бо рівність за ім'ям і типом
        System.out.println("tryRemove(new Eagle(\"Freedom\")) -> " + ok2);

        // (3) Видалення "за ім'ям" (без створення нового об'єкта)
        boolean ok3 = hoofedCage.removeByName("Marty"); // true
        System.out.println("removeByName(\"Marty\") -> " + ok3);

        System.out.println("Після видалень: total animals = " + zoo.getCountOfAnimals());


        //Приклад компіляторної помилки
        //lionCage.add(new Eagle("Wrong place")); // код не скомпілюється

    }
}
