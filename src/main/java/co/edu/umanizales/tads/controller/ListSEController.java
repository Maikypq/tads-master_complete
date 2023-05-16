package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.*;
import co.edu.umanizales.tads.exception.ListSEException;
import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.model.Ranges;
import co.edu.umanizales.tads.service.ListSEService;
import co.edu.umanizales.tads.service.LocationService;
import co.edu.umanizales.tads.service.RangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/listse")
public class ListSEController {
    @Autowired
    private ListSEService listSEService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private RangeService rangeService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getKids(){
        return new ResponseEntity<>(new ResponseDTO(
                200,listSEService.getKids().getHead(),null), HttpStatus.OK);
    }

    // 1
    @GetMapping("/invert")
    public ResponseEntity<ResponseDTO> invert() throws ListSEException {
        try {
            if (listSEService.getKids() != null) {
                listSEService.invert();
                return new ResponseEntity<>(new ResponseDTO(200,
                        "La lista se ha invertido", null), HttpStatus.OK);

            } else {
                return new ResponseEntity<>(new ResponseDTO(409,
                        "No se puede realizar la acción", null), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500,
                    "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 2
    @GetMapping(path = "/orderboystostart")
    public ResponseEntity<ResponseDTO> orderBoyToStart() throws ListSEException {
        try {
            if (listSEService.getKids() != null) {
                listSEService.getKids().orderBoysToStart();
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

    // 3
    @GetMapping(path = "/intervaleboys")
    public ResponseEntity<ResponseDTO> intervaleboysandgirls() throws ListSEException {
        try {
            listSEService.getKids().interleaveBoysAndGirls();
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Se produjo un error al alternar las mascotas", null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, "La lista se alternó", null), HttpStatus.OK);
    }

    //4

    @GetMapping(path = "/deleteboysbyage/{age}")
    public ResponseEntity<ResponseDTO> removeBoysByAge(@PathVariable byte age) {
        try {
            listSEService.getKids().removeBoysByAge(age);
            return new ResponseEntity<>(new ResponseDTO(
                    200, "El niño fue eliminado", null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Ocurrió un error al eliminar el niño", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 5
    @GetMapping(path = "/getAverageAgeBoys")
    public ResponseEntity<ResponseDTO> getAverageAgeBoys() throws ListSEException {
        try {
            float averageAge = (float) listSEService.getKids().getAverageAgeBoys();
            return new ResponseEntity<>(new ResponseDTO(
                    200, "La edad promedio del niño es: " + averageAge, null),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Se produjo un error al calcular la edad promedio del niño", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 6

    @GetMapping(path = "/kidsbylocation")
    public ResponseEntity<ResponseDTO> getCountKidsByLocationCode() {
        List<KidsByLocationDTO> kidsByLocationDTOS = new ArrayList<>();
        try {
            for (Location loc : locationService.getLocations()) {

                int count = listSEService.getKids().getCountKidsByLocationCode(loc.getCode());
                if (count > 0) {
                    kidsByLocationDTOS.add(new KidsByLocationDTO(loc, count));
                }
            }
            return new ResponseEntity<>(new ResponseDTO(
                    200, kidsByLocationDTOS,
                    null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 7

    @GetMapping(path = "/movekid/{id}/{numposition}")
    public ResponseEntity<ResponseDTO> winPosition(@PathVariable String id,
                                                   @PathVariable int numposition) throws ListSEException {
        try {
            if (listSEService.getKids() != null) {
                listSEService.getKids().movekid(id, numposition, listSEService.getKids());
                return new ResponseEntity<>(new ResponseDTO(
                        200, "El niño se ha movido con éxito",
                        null), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseDTO(409,
                        "No se puede realizar la acción", null), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 8

    @GetMapping(path = "/loseposition/{id}/{numposition}")
    public ResponseEntity<ResponseDTO> losePosition(@PathVariable String id,
                                                    @PathVariable int numposition) throws ListSEException {
        try {

            if (listSEService.getKids() != null) {
                listSEService.getKids().losePosition(id, numposition, listSEService.getKids());
                return new ResponseEntity<>(new ResponseDTO(
                        200, "El niño se ha movido con éxito",
                        null), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseDTO(409, "No se puede realizar la acción", null), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //9

    @GetMapping(path = "/rangebyage")
    public ResponseEntity<ResponseDTO> getRangeByAge() throws ListSEException {
        try {
            List<RangeDTO> kidsRangeList = new ArrayList<>();

            if (listSEService.getKids() != null) {
                for (Ranges i : rangeService.getRanges()) {
                    int quantity = listSEService.getKids().getRangeByAge(i.getFrom(), i.getTo());
                    kidsRangeList.add(new RangeDTO(i, quantity));
                }
                return new ResponseEntity<>(new ResponseDTO(200, "el rango de los niños es: " + kidsRangeList, null), HttpStatus.OK);

            } else {
                return new ResponseEntity<>(new ResponseDTO(409, "No se puede realizar la acción", null), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 10

    @GetMapping(path = "/moveToEndByInitial/{letter}")
    public ResponseEntity<ResponseDTO> moveToEndByInitial(@PathVariable char letter) {
        try {
            listSEService.getKids().moveToEndByInitial(Character.toUpperCase(letter));
            return new ResponseEntity<>(new ResponseDTO(
                    200, "los niños con la letra dada se han enviado al final",
                    null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





    @GetMapping(path = "/addToHead")
    public ResponseEntity<ResponseDTO> getaddToHead(){
        List<KidsByLocationDTO> kidsByLocationDTOList = new ArrayList<>();
        for(Location loc: locationService.getLocations()){
            int count = listSEService.getKids().getCountKidsByLocationCode(loc.getCode());
            if(count>0){
                kidsByLocationDTOList.add(new KidsByLocationDTO(loc,count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(
                200,kidsByLocationDTOList,
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/getKidsByAgeRange")
    public ResponseEntity<ResponseDTO> getKidsByAgeRange(){
        List<KidsByLocationDTO> kidsByLocationDTOList = new ArrayList<>();
        for(Location loc: locationService.getLocations()){
            int count = listSEService.getKids().getCountKidsByLocationCode(loc.getCode());
            if(count>0){
                kidsByLocationDTOList.add(new KidsByLocationDTO(loc,count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(
                200,kidsByLocationDTOList,
                null), HttpStatus.OK);
    }
    @GetMapping(path = "/moveToEndByInitial")
    public ResponseEntity<ResponseDTO> getmoveToEndByInitial(){
        List<KidsByLocationDTO> kidsByLocationDTOList = new ArrayList<>();
        for(Location loc: locationService.getLocations()){
            int count = listSEService.getKids().getCountKidsByLocationCode(loc.getCode());
            if(count>0){
                kidsByLocationDTOList.add(new KidsByLocationDTO(loc,count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(
                200,kidsByLocationDTOList,
                null), HttpStatus.OK);
    }


    @GetMapping(path = "/change_extremes")
    public ResponseEntity<ResponseDTO> changeExtremes() {
        listSEService.getKids().changeExtremes();
        return new ResponseEntity<>(new ResponseDTO(
                200,"Se han intercambiado los extremos",
                null), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> addKid(@RequestBody KidDTO kidDTO){
        Location location = locationService.getLocationByCode(kidDTO.getCodeLocation());
        if(location == null){
            return new ResponseEntity<>(new ResponseDTO(
                    404,"La ubicación no existe",
                    null), HttpStatus.OK);
        }
        try {
            listSEService.getKids().add(
                    new Kid(kidDTO.getIdentification(),
                            kidDTO.getName(), kidDTO.getAge(),
                            kidDTO.getGender(), location));
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    409,e.getMessage(),
                    null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200,"Se ha adicionado",
                null), HttpStatus.OK);

    }

    @GetMapping(path = "/kidsbylocations")
    public ResponseEntity<ResponseDTO> getKidsByLocation(){
        List<KidsByLocationDTO> kidsByLocationDTOList = new ArrayList<>();
        for(Location loc: locationService.getLocations()){
            int count = listSEService.getKids().getCountKidsByLocationCode(loc.getCode());
            if(count>0){
                kidsByLocationDTOList.add(new KidsByLocationDTO(loc,count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(
                200,kidsByLocationDTOList,
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/kidsbylocationgenders/{age}")
    public ResponseEntity<ResponseDTO> getReportKisLocationGenders(@PathVariable byte age) {
        ReportKidsLocationGenderDTO report =
                new ReportKidsLocationGenderDTO(locationService.getLocationsByCodeSize(8));
        listSEService.getKids()
                .getReportKidsByLocationGendersByAge(age,report);
        return new ResponseEntity<>(new ResponseDTO(
                200,report,
                null), HttpStatus.OK);
    }
}

