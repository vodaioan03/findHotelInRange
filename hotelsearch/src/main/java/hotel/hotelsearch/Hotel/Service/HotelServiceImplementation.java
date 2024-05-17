package hotel.hotelsearch.Hotel.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import hotel.hotelsearch.Exceptions.HotelCollectionException;
import hotel.hotelsearch.Hotel.HotelRepository;
import hotel.hotelsearch.Hotel.model.Hotel;
import hotel.hotelsearch.Hotel.model.Review;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class HotelServiceImplementation implements HotelService {
  private HotelRepository hotelRepository;
  @Override
  public List<Hotel> getHotels() {
    List<Hotel> hotels = hotelRepository.findAll();
    if(hotels.size() != 0) return hotels;
    else return new ArrayList<Hotel>();
  }

  @Override
  public Optional<Hotel> getHotelById(String idNumber) throws HotelCollectionException {
    Optional<Hotel> hotelFound = hotelRepository.findHotelById(Integer.parseInt(idNumber));
    if(hotelFound.isPresent()) return hotelFound;
    else throw new HotelCollectionException(HotelCollectionException.NotFound(idNumber));
  }

  public void saveEntity(Hotel hotel) throws ConstraintViolationException,HotelCollectionException{
    if (this.entityPresent(hotel)) {
      throw new HotelCollectionException(HotelCollectionException.AlreadyExist(Integer.toString(hotel.getid())));
    }
    else{
      hotelRepository.save(hotel);
    }
  }
  @Override
  public Optional<Hotel> getHotelByName(String name) throws HotelCollectionException {
    Optional<Hotel> hotelFound = hotelRepository.findHotelByName(name);
    if(hotelFound.isPresent()) return hotelFound;
    else throw new HotelCollectionException(HotelCollectionException.NotFound(name));
  }

  public void addReview(Review review, Hotel hotel){
    System.err.println("Intra");
    hotel.addReview(review);
    hotelRepository.save(hotel);
  }

  public Boolean entityPresent(Hotel hotel) {
    System.err.println(Integer.toString(hotel.getid()));
    Optional<Hotel> hotelFound = hotelRepository.findHotelById(hotel.getid());
    return hotelFound.isPresent();
  }

  public void deleteHotels() {
    hotelRepository.deleteAll();
  }

}
