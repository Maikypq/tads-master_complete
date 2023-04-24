package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.service.KidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(path = "/informe")
public class InformeController {
    @Autowired
    private KidService kidService;

    @GetMapping
    public ResponseEntity<List<Kid>> getInforme(@RequestParam("age") int age) {
        List<Kid> informe = kidService.getKidsByAge(age);
        if (informe.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(informe);
    }
}
