package hotel.hotelsearch.Hotel.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    private int roomNumber;
    private int type;
    private int price;
    private boolean isAvailable;

    
    public int getRoomNumber() {
      return roomNumber;
    }
    public void setRoomNumber(int roomNumber) {
      this.roomNumber = roomNumber;
    }
    public int getType() {
      return type;
    }
    public void setType(int type) {
      this.type = type;
    }
    public int getPrice() {
      return price;
    }
    public void setPrice(int price) {
      this.price = price;
    }
    public boolean isAvailable() {
      return isAvailable;
    }
    public void setAvalible(boolean isAvailable) {
      this.isAvailable = isAvailable;
    }

    
}
