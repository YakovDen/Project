package agency;

import java.io.Serializable;
import java.util.HashSet;

import java.util.Set;

import javax.persistence.*;
import org.hibernate.annotations.OrderBy;


@Entity
@Table(name = "users", catalog = "travelagency")
public class User implements Serializable {	
	private static final long serialVersionUID = 1L;	
	//private Tour tour;
	
	public User() {
	}

	public User(String firstName, String lastName, String username, String password, Integer role_id) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.role_id = role_id;
	}

	public User(String firstName, String lastName, String username, String password, 
			Integer role_id, String discountBytour, String tourReserved, Set<UserTour> usertours/*List<Tour> tourses*/) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.role_id = role_id;
		this.discountBytour = discountBytour;
		this.tourReserved = tourReserved;
		this.usertours = usertours;
		//this.tourses = tourses;		
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name = "id_user", unique = true, nullable = false)
	private int id_user;	

	@Column(name = "first_name", nullable = false, length = 30)
	private String firstName;	

	@Column(name = "last_name", nullable = false, length = 30)
	private String lastName;	

	@Column(name = "username", nullable = false, length = 30)
	private String username;	

	@Column(name = "password", nullable = false, length = 45)
	private String password;	
	
	@Column(name = "role_id", nullable = false)
	private int role_id;	

	@Column(name = "discountBytour", length = 45)
	private String discountBytour;	

	@Column(name = "tourReserved", length = 45)
	private String tourReserved;
	
	
	/*@ManyToMany(fetch = FetchType.LAZY,mappedBy = "users")
	private List<Tour> tourses;*/
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "id.user", cascade = CascadeType.ALL)	
	@OrderBy(clause = "idUT ASC")//сортирую вывод на usertour.jsp для текущего user по idUT
	 private Set<UserTour> usertours = new HashSet<UserTour>();

	
	
	
	public int getId_user() {
		return id_user;
	}

	public void setId_user(int id_user) {
		this.id_user = id_user;
	}
	
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getUserName() {
		return this.username;
	}

	public void setUserName(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Integer getRole_id() {
		return role_id;
	}

	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}
	
	public String getDiscountBytour() {
		return this.discountBytour;
	}

	public void setDiscountBytour(String discountBytour) {
		this.discountBytour = discountBytour;
	}
		
	public String getTourReserved() {
		return this.tourReserved;
	}

	public void setTourReserved(String tourReserved) {
		this.tourReserved = tourReserved;
	}
	//вывод в touragent.jsp параметров тура
	public String getToursNames(){		
		StringBuilder str = new StringBuilder();
		
	    if(!usertours.isEmpty()){	    	
	    	
	    	for (UserTour item  : usertours) {
	    		/*if(str.length() == 0){
	    			str.append(item.getTour().getName())
	    			
	    		}else{*/
	    			str.append("\n\n'"+item.getTour().getName()+ "'- стоимость тура " + item.getTour().getCost()+" y.e;");
	    		//}
	         }	    	
	    }
	    return str.toString();
	}
		
	/*public List<Tour> getTourses() {
		return tourses;
	}

	public void setTourses(List<Tour> tourses) {
		this.tourses = tourses;
	}*/

	//количество зарезервированных туров
	public int TourCount(){
		
		/*if(tourses != null)
			return tourses.size();*/
		if(!usertours.isEmpty())
		return usertours.size();
		 //return tour.getUserTours().size();
		return 0;
	}
	//метод для вывода параметров тура,зарезервированного юзером на usertours.jsp
	public Tour Tour(){
		/*if(tourses != null && tourses.size() > 0 && tourses.get(0) != null)
			return tourses.get(0);*/
		
		return new Tour(); 
	}
	
	//метод для вывода параметров тура,зарезервированного юзером на usertours.jsp
	//public List<Tour> GetTours(){	
	public Set<UserTour> GetTours(){
		/*if(tourses != null)
			return tourses;*/
		if(usertours != null)
		return usertours;
		
		return null; 
	}
	
	
	@Override
	public String toString() {
		return "User [id_user=" + id_user + ", firstName=" + firstName + ", lastName=" + lastName + 
				", username=" + username + ", password=" + password + ", role_id=" + role_id + 
				", discountBytour=" + discountBytour + ", tourReserved="
				+ tourReserved + ", usertours=" + usertours+ "]";
	}

	public Set<UserTour> getUsertours() {
		return usertours;
	}

	public void setUsertours(Set<UserTour> usertours) {
		this.usertours = usertours;
	}

	

					

}
