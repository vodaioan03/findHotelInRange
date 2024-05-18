package hotel.hotelsearch.Hotel.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationApi {
   private int idUser;
  private int hotelID;
  private int roomNumber;
  private LocalDate from;
  private LocalDate to;
}
