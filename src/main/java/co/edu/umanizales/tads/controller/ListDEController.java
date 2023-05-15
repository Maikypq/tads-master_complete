package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.KidsByLocationDTO;
import co.edu.umanizales.tads.controller.dto.PetDTO;
import co.edu.umanizales.tads.controller.dto.RangeDTO;
import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.exception.ListDEException;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.model.Pet;
import co.edu.umanizales.tads.model.Ranges;
import co.edu.umanizales.tads.service.ListDEService;
import co.edu.umanizales.tads.service.LocationService;
import co.edu.umanizales.tads.service.RangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/listde")
public class ListDEController {
    @Autowired
    private ListDEService listDEService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private RangeService rangeService;


    @GetMapping
    public ResponseEntity<ResponseDTO> getPets() {
        return new ResponseEntity<>(new ResponseDTO(
                200, listDEService.getPets().print(), null), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<ResponseDTO> addPets(@RequestBody PetDTO petDTO) throws ListDEException {
        Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
        if (location == null) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, "La ubicación no existe",
                    null), HttpStatus.OK);
        }
        Pet pet = new Pet(petDTO.getPetIdentification(),
                petDTO.getNamePet(), petDTO.getAgePet(), petDTO.getPetType(),
                petDTO.getBreed(), petDTO.getPetGender(), location);
        listDEService.getPets().addPets(pet);
        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha adicionado al chandoso",
                null), HttpStatus.OK);

    }
    @GetMapping(path = "/deletebyid/{id}")
    public ResponseEntity<ResponseDTO> deletePetById(@PathVariable String id) throws ListDEException {
        try {
            listDEService.getPets().deletePetById(id);
            return new ResponseEntity<>(new ResponseDTO(
                    200, "La/s mascota/s fue/ron eliminada/s", null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Ocurrió un error al eliminar la/s mascota/s", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(path = "/addpetbyposition/{position}")
    public ResponseEntity<ResponseDTO> addPetByPositionPet(@RequestBody Pet pet, @PathVariable int position) throws ListDEException {
        try {
            listDEService.getPets().addPetByPosition(pet, position);
            return new ResponseEntity<>(new ResponseDTO(
                    200, "La mascota fue añadida en la posición solicitada", null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Se produjo un error al agregar la mascota en la posición solicitada", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/invertpetslist")
    public ResponseEntity<ResponseDTO> invertPetsList() throws ListDEException {
        try {
            if (listDEService.getPets() != null) {
                listDEService.invertPetsList();
                return new ResponseEntity<>(new ResponseDTO(200,
                        "La lista de mascotas se ha invertido", null), HttpStatus.OK);

            } else {
                return new ResponseEntity<>(new ResponseDTO(409,
                        "No se puede realizar la acción", null), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500,
                    "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(path = "/orderpetsmtostart")
    public ResponseEntity<ResponseDTO> orderMasculinePetsToStart() throws ListDEException {
        try {
            if (listDEService.getPets() != null) {
                listDEService.getPets().orderMasculinePetsToStart();
                return new ResponseEntity<>(new ResponseDTO(200,
                        "Se ha ordenado la lista con las mascotas masculinas al comienzo",
                        null), HttpStatus.OK);

            } else {
                return new ResponseEntity<>(new ResponseDTO(409,
                        "No se puede realizar la acción", null), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500,
                    "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(path = "/intervalepets")
    public ResponseEntity<ResponseDTO> intervalePets() throws ListDEException {
        try {
            listDEService.getPets().intertervalePets();
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Se produjo un error al alternar las mascotas", null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, "La lista se alternó", null), HttpStatus.OK);
    }

    @GetMapping(path = "/deletepetsbyage/{age}")
    public ResponseEntity<ResponseDTO> deletePetsByAge(@PathVariable byte age) {
        try {
            listDEService.getPets().deletePetsByAge(age);
            return new ResponseEntity<>(new ResponseDTO(
                    200, "La mascota fue eliminada", null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Ocurrió un error al eliminar la mascota", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(path = "/averagepet")
    public ResponseEntity<ResponseDTO> averageAgePet() throws ListDEException {
        try {
            float averageAge = listDEService.getPets().averagePetAge();
            return new ResponseEntity<>(new ResponseDTO(
                    200, "La edad promedio de mascotas es: " + averageAge, null),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Se produjo un error al calcular la edad promedio de las mascotas", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(path = "/petsbylocation")
    public ResponseEntity<ResponseDTO> getPetsCountByLocationCode() {
        List<KidsByLocationDTO> petsByLocationDTOList = new ArrayList<>();
        try {
            for (Location loc : locationService.getLocations()) {
                int count = listDEService.getPets().getPetsCountByLocationCode(loc.getCode());
                if (count > 0) {
                    petsByLocationDTOList.add(new KidsByLocationDTO(loc, count));
                }
            }
            return new ResponseEntity<>(new ResponseDTO(
                    200, petsByLocationDTOList,
                    null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(path = "/movepetposition/{code}/{num}")
    public ResponseEntity<ResponseDTO> winPetPosition(@PathVariable String code, @PathVariable int num)
            throws ListDEException {
        try {
            listDEService.getPets().MovePetPosition(code, num);
            return new ResponseEntity<>(new ResponseDTO(
                    200, "La mascota gano las posiciones en la lista especificadas", null)
                    , HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Se produjo un error en ganar posiciones de la mascota en la lista",
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(path = "/losePetposition/{code}/{num}")
    public ResponseEntity<ResponseDTO> losePetPosition(@PathVariable String code, @PathVariable int num)
            throws ListDEException {
        try {
            listDEService.getPets().losePetPosition(code, num);
            return new ResponseEntity<>(new ResponseDTO(
                    200, "La mascota perdio posiciones en la lista", null),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Se produjo un error al retroceder la mascota en la lista", null)
                    , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(path = "/rangeage")
    public ResponseEntity<ResponseDTO> getPetRangeByAge() throws ListDEException {
        try {
            List<RangeDTO> listPetRange = new ArrayList<>();
            for (Ranges i : rangeService.getRanges()) {
                int quantity = listDEService.getPets().getPetRangeByAge(i.getFrom(), i.getTo());
                listPetRange.add(new RangeDTO(i, quantity));
            }
            return new ResponseEntity<>(new ResponseDTO(
                    200, "el rango de los niños es: " + listPetRange,
                    null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Error al obtener el rango de edades",
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(path = "/movepetfinish/{letter}")
    public ResponseEntity<ResponseDTO> petToFinishByLetter(@PathVariable char letter) {
        try {
            listDEService.getPets().movePetToTheEndByLetter(Character.toUpperCase(letter));
            return new ResponseEntity<>(new ResponseDTO(
                    200, "los niños con la letra dada se han enviado al final",
                    null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}