package controller;

import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class EditorsController {

    @Autowired
    private ProductService productService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private RestaurantRepository restaurantRepo;

    @RequestMapping("/edit")
    public String viewEditPage(Model model){
        List<Product> listProducts = productService.listAll();
        model.addAttribute("listProducts", listProducts);
        return "edit";
    }

    @RequestMapping("/editRestaurants")
    public String viewEditResPage(Model model){
        List<Restaurant> listRestaurants = restaurantService.listAll();
        model.addAttribute("listRestaurants", listRestaurants);
        return "edit_rest";
    }

    @RequestMapping("/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") int id) {
        productService.delete(id);
        return "redirect:/edit";
    }

    @RequestMapping("/deleteRestaurants/{id}")
    public String deleteRestaurant(@PathVariable(name = "id") int id) {
        restaurantService.delete(id);
        return "redirect:/editRestaurants";
    }

    @RequestMapping("/newProd")
    public String showNewProductPage(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);

        return "new_prod";
    }

    @RequestMapping("/newRestaurant")
    public String showNewRestaurantPage(Model model) {
        Restaurant restaurant = new Restaurant();
        model.addAttribute("restaurant", restaurant);

        return "new_restaurant";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView showEditProductPage(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("edit_prod");
        Product product = productService.get(id);
        mav.addObject("product", product);

        return mav;
    }

    @RequestMapping("/editRestaurants/{id}")
    public ModelAndView showEditRestaurantPage(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("edit_restaurant");
        Restaurant restaurant = restaurantService.get(id);
        mav.addObject("restaurant", restaurant);

        return mav;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveProduct(@ModelAttribute("product") Product product) {
        productService.save(product);

        return "redirect:/edit";
    }

    @RequestMapping(value = "/saveRestaurant", method = RequestMethod.POST)
    public String saveRestaurant(@ModelAttribute("restaurant") Restaurant restaurant) {
        restaurantService.save(restaurant);

        return "redirect:/editRestaurants";
    }

}
