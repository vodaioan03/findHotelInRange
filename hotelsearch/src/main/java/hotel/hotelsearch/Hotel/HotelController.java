package hotel.hotelsearch.Hotel;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import hotel.hotelsearch.Exceptions.HotelCollectionException;
import hotel.hotelsearch.Hotel.Service.HotelServiceImplementation;
import hotel.hotelsearch.Hotel.model.CancelReservation;
import hotel.hotelsearch.Hotel.model.Hotel;
import hotel.hotelsearch.Hotel.model.Reservation;
import hotel.hotelsearch.Hotel.model.ReservationApi;
import hotel.hotelsearch.Hotel.model.Review;
import jakarta.validation.ConstraintViolationException;

@RestController
@RequestMapping(path = "/api")
@Controller
public class HotelController {
  private final HotelServiceImplementation hotelService;

  @Autowired
  public HotelController(HotelServiceImplementation hotelService) {
    this.hotelService = hotelService;
  } 

  @GetMapping("/getHotels")
  public ResponseEntity<?> fetchAllHotels() {
    List<Hotel> hotels = hotelService.getHotels();
    return new ResponseEntity<>(hotels,hotels.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
  }

  @GetMapping("/getHotelsRange/{range}")
  public ResponseEntity<?> findHotelsInRange(@PathVariable("range") String range) {
      int rangeMeters = Integer.parseInt(range);
      List<Hotel> hotels = hotelService.getHotelsInRange(rangeMeters);
      return new ResponseEntity<>(hotels, hotels.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
  }

  @GetMapping("/getHotel/{idNumber}")
  public ResponseEntity<?> getHotelById(@PathVariable("idNumber") String idNumber) {
    try {
      return new ResponseEntity<>(hotelService.getHotelById(idNumber),HttpStatus.OK);
    } catch (HotelCollectionException e) {
      return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/bookRoom")
  public ResponseEntity<?> bookRoom(@RequestBody ReservationApi resAPI) {
    try {

      Reservation reservation = new Reservation(
        resAPI.getIdUser(), 
        this.hotelService.getHotelById(Integer.toString(resAPI.getHotelID())).get(), 
        resAPI.getRoomNumber(), resAPI.getFrom(), 
        resAPI.getTo()
        );

      this.hotelService.reserveRoom(reservation);

      return new ResponseEntity<>("Room is now reserved.",HttpStatus.OK);
    } catch (HotelCollectionException e) {
      return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
    }
  }

  @PostMapping("/cancelReservation")
  public ResponseEntity<?> cancelReservation(@RequestBody CancelReservation cancel) {
      try {

          this.hotelService.cancelReservation(cancel.getIdReservation());
          return new ResponseEntity<>("Reservation is now canceled.", HttpStatus.OK);
      } catch (HotelCollectionException e) {
          return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
      } catch (Exception e) {
          return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
      }
  }

  @PostMapping("/addHotel")
  public ResponseEntity<?> addHotel(@RequestBody Hotel Hotel) {
    try {
      hotelService.saveEntity(Hotel);
      return new ResponseEntity<Hotel>(Hotel,HttpStatus.OK);
    } catch (ConstraintViolationException e) {
      return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
    } catch (HotelCollectionException e) {
      return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
    }
  }  
  @PostMapping("/addHotels")
  public ResponseEntity<?> addHotels(@RequestBody List<Hotel> hotels) {
    try {
      for (Hotel hotel : hotels) {
        hotelService.saveEntity(hotel);
      }
      return new ResponseEntity<List<Hotel>>(hotels,HttpStatus.OK);
    } catch (ConstraintViolationException e) {
      return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
    } catch (HotelCollectionException e) {
      return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
    }
  }  

  @PostMapping("/addReview/{name}")
  public ResponseEntity<?> addReview(@RequestBody Review review, @PathVariable("name") String nameHotel) {
    try {
      Optional<Hotel> hotelOpt = hotelService.getHotelByName(nameHotel);
      Hotel Hotel = hotelOpt.get();
      hotelService.addReview(review, Hotel);
      return new ResponseEntity<Hotel>(Hotel,HttpStatus.OK);
    } catch (ConstraintViolationException e) {
      return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
    } catch (HotelCollectionException e) {
      return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
    }
  } 


  @DeleteMapping("/deleteHotels")
  public ResponseEntity<?> deleteAllHotels() {
    hotelService.deleteHotels();
    List<Hotel> Hotels = hotelService.getHotels();
    if(Hotels.size() == 0) {
      return new ResponseEntity<>("All Hotels deleted!",HttpStatus.OK);
    }
    else {
      return new ResponseEntity<>("ERROR! Hotels are in database.",HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
