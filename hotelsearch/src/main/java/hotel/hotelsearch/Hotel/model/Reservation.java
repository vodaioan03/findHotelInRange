package hotel.hotelsearch.Hotel.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(value="booking")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
  @Id
  private String id; // Add this field
  
  private int idUser;
  private Hotel hotel;
  private int roomNumber;
  private LocalDate from;
  private LocalDate to;


  public Reservation(int idUser, Hotel hotel, int roomNumber, LocalDate from, LocalDate to) {
    this.idUser = idUser;
    this.hotel = hotel;
    this.roomNumber = roomNumber;
    this.from = from;
    this.to = to;
  }


  public void reserveRoom() throws Exception{
    List<Room> rooms = hotel.getRooms();
    for (Room room : rooms) {
      if(room.getRoomNumber() == this.roomNumber && room.isAvailable() == true) {
        room.setAvailable(false);
        return ;
      }
    }
    throw new Exception("Room is already reserved.");
  }
}
