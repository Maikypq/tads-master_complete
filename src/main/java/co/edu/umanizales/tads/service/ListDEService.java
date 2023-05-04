package co.edu.umanizales.tads.service;

import co.edu.umanizales.tads.controller.dto.KidDTO;
import co.edu.umanizales.tads.exception.ListDEException;
import co.edu.umanizales.tads.model.ListDE;

public class ListDEService {

    private ListDE list;

    public ListDEService() {
        this.list = new ListDE();
    }

    public int getSize() {
        return list.getSize();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void moveToEndByInitial(String initial) throws ListDEException {
        list.moveToEndByInitial(initial);
    }

    public KidDTO[] getKidsByAgeRange(int minAge, int maxAge) {
        KidDTO[] kids = list.getKidsByAgeRange(minAge, maxAge);
        KidDTO[] kidsDTO = new KidDTO[kids.length];
        for (int i = 0; i < kids.length; i++) {
            KidDTO kidDTO = new KidDTO();
            kidDTO.setName(kids[i].getName());
            kidDTO.setAge(kids[i].getAge());
            kidDTO.setGender(kids[i].getGender());
            kidsDTO[i] = kidDTO;
        }
        return kidsDTO;
    }
}