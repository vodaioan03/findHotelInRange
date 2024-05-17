package hotel.hotelsearch.Hotel.Service;

import java.util.List;
import java.util.Optional;

import hotel.hotelsearch.Hotel.model.Hotel;
import hotel.hotelsearch.Exceptions.HotelCollectionException;

public interface HotelService {


  public List<Hotel> getHotels();
  public Optional<Hotel> getHotelById(String idNumber) throws HotelCollectionException;
  public Optional<Hotel> getHotelByName(String name) throws HotelCollectionException;

}
