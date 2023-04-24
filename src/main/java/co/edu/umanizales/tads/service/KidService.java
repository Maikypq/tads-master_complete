package co.edu.umanizales.tads.service;

import co.edu.umanizales.tads.model.Kid;

import java.util.ArrayList;
import java.util.List;
public class KidService {
    // Lista de niños (simulando una base de datos)
    private static List<Kid> kids = new ArrayList<>();

    // Constructor
    public KidService() {
        // Agregamos algunos niños para probar
        kids.add(new Kid(1021512628, "juan",  7, "M","manizales"));
        kids.add(new Kid(1021512623, "Ana",  6, "F","pereira"));
        kids.add(new Kid(1021512732, "Pedro",  9, "M","chinchina"));
        kids.add(new Kid(1021512629, "Lucia",  12, "F","manizales"));
        kids.add(new Kid(1021629873, "Luis",  8, "M","risaralda"));
        kids.add(new Kid(1021512620, "Maria",  10, "F","palestina"));
    }

    // Método para obtener los niños por edad mínima
    public static List<Kid> getKidsByAge(int minAge) {
        List<Kid> result = new ArrayList<>();
        for (Kid kid : kids) {
            if (kid.getAge() >= minAge) {
                result.add(kid);
            }
        }
        return result;
    }
}