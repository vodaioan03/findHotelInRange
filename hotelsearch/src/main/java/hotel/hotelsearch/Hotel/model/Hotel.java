package hotel.hotelsearch.Hotel.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//FROM PROJECT
import hotel.hotelsearch.Hotel.model.Review;
import hotel.hotelsearch.Hotel.model.Room;


@Document(value="hotel")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hotel {
  private int id;
  @Indexed(unique = true)
  private String name;
  private float latitude;
  private float longitude;
  private List<Room> rooms = new ArrayList<>();
  private List<Review> reviews = new ArrayList<>();

  public void addReview(Review review) {
    this.reviews.add(review);
    System.err.println("Added review");
  }

  public int getid() {
    return id;
  }
  public void setid(int id) {
    this.id = id;
  }
  public List<Review> getReviews() {
    return reviews;
  }
  public void setReviews(List<Review> reviews) {
    this.reviews = reviews;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public float getLatitude() {
    return latitude;
  }
  public void setLatitude(float latitude) {
    this.latitude = latitude;
  }
  public float getLongitude() {
    return longitude;
  }
  public void setLongitude(float longitude) {
    this.longitude = longitude;
  }
  public List<Room> getRooms() {
    return rooms;
  }
  public void setRooms(List<Room> rooms) {
    this.rooms = rooms;
  }

  
}
