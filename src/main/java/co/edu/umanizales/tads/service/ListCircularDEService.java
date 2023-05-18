package co.edu.umanizales.tads.service;

import co.edu.umanizales.tads.model.ListCircularDE;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class ListCircularDEService {
    private ListCircularDE petsCircular;
    public ListCircularDEService() {
        petsCircular = new ListCircularDE();
    }
}