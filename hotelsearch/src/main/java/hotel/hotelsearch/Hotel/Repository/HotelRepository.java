package hotel.hotelsearch.Hotel.Repository;

import hotel.hotelsearch.Hotel.model.Hotel;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface HotelRepository extends MongoRepository<Hotel, String> {
  @Query("{'_id': ?0}")
  Optional<Hotel> findHotelById(int _id);
  @Query("{'name': ?0}")
  Optional<Hotel> findHotelByName(String name);
}
