package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.KidDTO;
import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.exception.ListDEException;
import co.edu.umanizales.tads.service.ListDEService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/listde")
public class ListDEController {

    private ListDEService listDEService;

    public ListDEController() {
        this.listDEService = new ListDEService();
    }

    @GetMapping(path = "/getSize")
    public ResponseEntity<ResponseDTO> getSize() {
        int size = listDEService.getSize();
        return new ResponseEntity<>(new ResponseDTO(
                200, size,
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/isEmpty")
    public ResponseEntity<ResponseDTO> isEmpty() {
        boolean empty = listDEService.isEmpty();
        return new ResponseEntity<>(new ResponseDTO(
                200, empty,
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/removeKamicase")
    public ResponseEntity<ResponseDTO> kamikaze() {
        boolean empty = listDEService.isEmpty();
        return new ResponseEntity<>(new ResponseDTO(
                200, empty,
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/moveToEndByInitial/{initial}")
    public ResponseEntity<ResponseDTO> moveToEndByInitial(@PathVariable("initial") String initial) {
        try {
            listDEService.moveToEndByInitial(initial);
            return new ResponseEntity<>(new ResponseDTO(
                    200, "Kids moved successfully",
                    null), HttpStatus.OK);
        } catch (ListDEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, e.getMessage(),
                    null), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/getKidsByAgeRange/{minAge}/{maxAge}")
    public ResponseEntity<ResponseDTO> getKidsByAgeRange(@PathVariable("minAge") int minAge,
                                                         @PathVariable("maxAge") int maxAge) {
        KidDTO[] kidsDTO = listDEService.getKidsByAgeRange(minAge, maxAge);
        return new ResponseEntity<>(new ResponseDTO(
                200, kidsDTO,
                null), HttpStatus.OK);
    }

}