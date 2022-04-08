package controller;

import java.util.ArrayList;
import java.util.List;

import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppController {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private RestaurantRepository restaurantRepo;
	@Autowired
	private ProductRepository productRepo;
	@Autowired
	private ProductService productService;
	
	@GetMapping("")
	public String viewHomePage() {
		return "redirect:/home";
	}

	@GetMapping("/edit")
	public String viewEditPage(){
		return "edit";
	}

	@GetMapping("/logout")
	public String viewLogoutPage(){
		return "login";
	}

	@GetMapping("/home")
	public String viewHome1Page(){return "home";}

	@GetMapping("/login")
	public String viewLoginPage(){return "login2";}

	private List<Product> cart= new ArrayList<Product>();

//	@GetMapping("/login2")
//	public String viewLogin2Page(){return "login2";}
	
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		
		return "signup_form";
	}
	
	@PostMapping("/process_register")
	public String processRegister(User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		
		userRepo.save(user);
		
		return "register_success";
	}
	
	@GetMapping("/users")
	public String listUsers(Model model) {
		List<User> listUsers = userRepo.findAll();
		model.addAttribute("listUsers", listUsers);
		
		return "users";
	}
	@GetMapping("/restaurants")
	public String listRestaurants(Model model) {
		List<Restaurant> listRestaurants = restaurantRepo.findAll();
		model.addAttribute("listRestaurants", listRestaurants);

		return "restaurants";
	}

//	@GetMapping("/showProducts")
//	public String listProducts(Model model) {
//		List<Restaurant> listRestaurants = restaurantRepo.findAll();
//		model.addAttribute("listRestaurants", listRestaurants);
//
//		return "restaurants";
//	}

	@RequestMapping("/print/{id}")
	public String listProducts(@PathVariable(name = "id") int id,Model model) {
		List<Product> l1=productRepo.findAll();
		List<Product> listProducts=new ArrayList<Product>();
		for (Product p :l1) {
			if(p.getRestaurantId()==id)
				listProducts.add(p);
		}
		model.addAttribute("listProducts",listProducts);
		return "products";
	}

	@RequestMapping("/add/{id}")
	public String addToCart(@PathVariable(name = "id") int id)
	{
		List<Product> l1=productRepo.findAll();
		for (Product p :l1) {
			if(p.getRestaurantId()==id)
				cart.add(p);
		}
		return "cart";
	}

	@GetMapping("/Order")
	public String showOrderForm(Model model) {
		float price=0;
		for (Product p:cart) {
			price=price+p.getPrice();
		}
		model.addAttribute("price",price);
		return "order";
	}

	@GetMapping("/cart")
	public String viewCartPage(Model model)
	{
		model.addAttribute("cart",cart);
		return "cart";
	}
}
