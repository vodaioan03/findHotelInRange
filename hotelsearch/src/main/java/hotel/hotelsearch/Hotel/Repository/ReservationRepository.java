package hotel.hotelsearch.Hotel.Repository;

import hotel.hotelsearch.Hotel.model.Hotel;
import hotel.hotelsearch.Hotel.model.Reservation;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ReservationRepository extends MongoRepository<Reservation, String> {
  @Query("{'idUser': ?0}")
  Optional<Reservation> findReservationByUser(int idUser);
  @Query("{'idReservation': ?0}")
  Optional<Reservation> findReservationById(String idReservation);
}
