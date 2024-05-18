package hotel.hotelsearch.Hotel.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import hotel.hotelsearch.Exceptions.HotelCollectionException;
import hotel.hotelsearch.Hotel.Repository.HotelRepository;
import hotel.hotelsearch.Hotel.Repository.ReservationRepository;
import hotel.hotelsearch.Hotel.model.Hotel;
import hotel.hotelsearch.Hotel.model.Reservation;
import hotel.hotelsearch.Hotel.model.Review;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class HotelServiceImplementation implements HotelService {
  private HotelRepository hotelRepository;
  private ReservationRepository reservationRepository;
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

  public void reserveRoom(Reservation reservation) throws Exception {
    try {
      reservation.reserveRoom();
      hotelRepository.save(reservation.getHotel());
      this.reservationRepository.save(reservation);
    } catch (Exception e) {
      throw e;
    }
  }

  public void cancelReservation(String idReservation) throws Exception {
    try {
      System.err.println(idReservation.length());
      System.err.println(idReservation);
      ObjectId objectId = new ObjectId(idReservation);
      Reservation reservation = this.reservationRepository.findById(objectId.toString()).orElseThrow(() -> new Exception("Reservation not found"));
      LocalDateTime localDate = LocalDateTime.now();

       // Assuming the check-in time is 14:00 on the reservation
      LocalDateTime checkInTime = reservation.getFrom().atTime(LocalTime.of(14, 0));
      LocalDateTime cutoffTime = checkInTime.minusHours(2);
      
      if (localDate.isAfter(cutoffTime)) {
          throw new Exception("Cannot cancel the reservation less than 2 hours before check-in time.");
      }
      reservation.getHotel().cancelReservation(reservation.getRoomNumber());
      this.hotelRepository.save(reservation.getHotel());
      this.reservationRepository.delete(reservation);
    } catch (Exception e) {
      throw e;
    }
  }

  @Override
  public Optional<Hotel> getHotelByName(String name) throws HotelCollectionException {
    Optional<Hotel> hotelFound = hotelRepository.findHotelByName(name);
    if(hotelFound.isPresent()) return hotelFound;
    else throw new HotelCollectionException(HotelCollectionException.NotFound(name));
  }

  public void addReview(Review review, Hotel hotel){
    hotel.addReview(review);
    hotelRepository.save(hotel);
  }

  public Boolean entityPresent(Hotel hotel) {
    Optional<Hotel> hotelFound = hotelRepository.findHotelById(hotel.getid());
    return hotelFound.isPresent();
  }

  public void deleteHotels() {
    hotelRepository.deleteAll();
  }

  public List<Hotel> getHotelsInRange(int range) {
    List<Hotel> allHotels = hotelRepository.findAll();
    double[] hostLocation = getLatLongFromIpAddress(getCurrentIpAddress());
    System.err.println(hostLocation);
    return allHotels.stream()
            .filter(hotel -> {
                double distance = calculateDistance((float) hostLocation[0], (float)hostLocation[1], (float)hotel.getLatitude(), (float)hotel.getLongitude());
                return distance <= range;
            })
            .collect(Collectors.toList()  );

  }


  public String getCurrentIpAddress() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://api.ipify.org?format=json";
            Map<String, String> response = restTemplate.getForObject(url, Map.class);
            return response.get("ip");
        } catch (Exception e) {
            e.printStackTrace();
            return "IP address could not be retrieved";
        }
    }
    public double[] getLatLongFromIpAddress(String ipAddress1) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://ip-api.com/json/" +  ipAddress1;
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        System.out.println("IP Address: " + ipAddress1);
        System.out.println("API Response: " + response);
        if (response.containsKey("lat") && response.containsKey("lon")) {
            double latitude = (double) response.get("lat");
            double longitude = (double) response.get("lon");
            return new double[]{latitude, longitude};
        } else {
            return new double[]{0, 0};
        }
    }
    public double calculateDistance(float lat1, float lon1, float lat2, float lon2) {
        int earthRadiusKm = 6371;

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        lat1 = (float) Math.toRadians(lat1);
        lat2 = (float) Math.toRadians(lat2);

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return earthRadiusKm*c;
    }
}
