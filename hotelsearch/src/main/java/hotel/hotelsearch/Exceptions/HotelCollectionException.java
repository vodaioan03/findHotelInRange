package hotel.hotelsearch.Exceptions;

public class HotelCollectionException extends Exception {


  private static final long serialVersionUID = 1L;

  public HotelCollectionException(String message) {
    super(message);
  }

  public static String NotFound(String id) {
    return "Hotel with " + id + " not found";
  }

  public static String AlreadyExist(String id) {
    return "Hotel with " + id + " already exists";
  }
}
