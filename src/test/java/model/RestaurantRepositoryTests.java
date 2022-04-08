package model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class RestaurantRepositoryTests {

    @Autowired
    private RestaurantRepository restaurantsRepo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateRestaurant(){
        Restaurant restaurant = new Restaurant();
        restaurant.setName("TestRestaurant");
        restaurant.setAddress("Str. test, nr. 1");
        restaurant.setDeliveryPrice(5F);

        Restaurant savedRestaurant = restaurantsRepo.save(restaurant);

        Restaurant existingRestaurant = entityManager.find(Restaurant.class, savedRestaurant.getId());
        assertThat(existingRestaurant.getName()).isEqualTo(restaurant.getName());
    }
}
