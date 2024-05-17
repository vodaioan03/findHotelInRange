package hotel.hotelsearch.Hotel.model;

import hotel.hotelsearch.user.model.myUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    private double stars;
    private String text;
    private String username;
}
