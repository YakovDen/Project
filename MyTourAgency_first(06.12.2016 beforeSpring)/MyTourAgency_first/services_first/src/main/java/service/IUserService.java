package service;
import java.util.List;

import agency.User;

public interface IUserService {
	//в роли турагента, вставка скидки на тур для user
		public void InsertDiscount(int id, String discountBytour);
		//для туроператора выбор user
		public User getUserById(int id);
		public List<User> getUserDiscount();	
		public int authenticate(String username, String password);
		public List<User> getTestAllUsers();

}
