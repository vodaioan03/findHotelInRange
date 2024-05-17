package hotel.hotelsearch.user.repository;

import java.util.Optional;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import hotel.hotelsearch.user.model.myUser;

@Repository
public interface userRepository extends MongoRepository<myUser,String> {
  Optional<myUser> findUserByUsername(String username);
  Optional<myUser> findUserByPhoneNumber(String phoneNumber);
  Optional<myUser> findUserByEmail(String email);
}
