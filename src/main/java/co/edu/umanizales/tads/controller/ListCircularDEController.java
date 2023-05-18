package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.PetDTO;
import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.exception.ListDEException;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.model.Pet;
import co.edu.umanizales.tads.service.ListCircularDEService;
import co.edu.umanizales.tads.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/listcircularde")
public class ListCircularDEController {
    @Autowired
    private ListCircularDEService listDECircularService;
    @Autowired
    private LocationService locationService;


    @GetMapping
    public ResponseEntity<ResponseDTO> getPets() throws ListDEException {
        return new ResponseEntity<>(new ResponseDTO(
                200, listDECircularService.getPetsCircular().listCircular(), null), HttpStatus.OK);
    }



    @PostMapping
    public ResponseEntity<ResponseDTO> addPet(@RequestBody PetDTO petDTO) {
        Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
        if (location == null) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, "La ubicación no existe",
                    null), HttpStatus.OK);
        }
        Pet pet = new Pet(petDTO.getIdPet(),
                petDTO.getNamePet(), petDTO.getAgePet(), petDTO.getTypePet(),
                petDTO.getBreed(), petDTO.getGenderPet(), location, false);
        listDECircularService.getPetsCircular().addPet(pet);
        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha adicionado la mascota",
                null), HttpStatus.OK);

    }

    @PostMapping(path = "/addpettostart")
    public ResponseEntity<ResponseDTO> addToStart(@RequestBody PetDTO petDTO) {
        Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
        listDECircularService.getPetsCircular().addToStart(
                new Pet(petDTO.getIdPet(), petDTO.getNamePet(),
                        petDTO.getAgePet(), petDTO.getTypePet(),
                        petDTO.getBreed(), petDTO.getGenderPet(), location,
                        petDTO.getPetbathed()));

        return new ResponseEntity<>(new ResponseDTO(200, "Mascota adicionada al inicio", null),
                HttpStatus.OK);
    }

    @PostMapping(path = "/addpetbyposition/{position}")
    public ResponseEntity<ResponseDTO> addByPosition(@RequestBody PetDTO petDTO, @PathVariable int position) throws ListDEException {
        Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
        listDECircularService.getPetsCircular().addByPosition(
                new Pet(petDTO.getIdPet(), petDTO.getNamePet(),
                        petDTO.getAgePet(), petDTO.getTypePet(),
                        petDTO.getBreed(), petDTO.getGenderPet(), location,
                        petDTO.getPetbathed()),position);

        return new ResponseEntity<>(new ResponseDTO(200,
                "La mascota fue adicionada en la posicion: " + position, null),
                HttpStatus.OK);
    }

    @GetMapping(path = "/getpetbathed/{direction}")
    public ResponseEntity<ResponseDTO> getBathedPet(@PathVariable char direction) {
        Pet pet = listDECircularService.getPetsCircular().getBathedPets(direction);
        if (pet == null){
            return new ResponseEntity<>(new ResponseDTO(200,
                    "Ese perro ya está bañado", null), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new ResponseDTO(200,
                    "La mascota "+pet.getPetIdentification() + " acaba de ser bañada", null), HttpStatus.OK);
        }
    }
}