package model;

import model.Restaurant;
import model.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class
RestaurantService {

    @Autowired
    private RestaurantRepository repo;

    public List<Restaurant> listAll(){
        return repo.findAll();
    }

    public void save(Restaurant restaurant){
        repo.save(restaurant);
    }

    public Restaurant get(long id){
        return repo.findById(id).get();
    }

    public void delete(long id){
        repo.deleteById(id);
    }
}
