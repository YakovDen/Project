package agency;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;



/**
 * класс Тур с добавленными свойствами видов и типов тура
 * @author Den
 * @param <KindOfTour>
 *
 */@Entity
@Table(name = "tours",catalog="travelagency")
public class Tour implements Serializable {
	 private static final long serialVersionUID = 1L;
	 
	 public Tour() {			
			
		}
	 public Tour( String dateOfBeginning, String dateEnd, String name, int numberOfNights, 
	 			int cost, String discount, TypeOfTour typeOfTour, KindOfTour kindOfTour) {		

this.dateOfBeginning = dateOfBeginning;
this.dateEnd = dateEnd;
this.name = name;
this.numberOfNights = numberOfNights;
this.cost = cost;
this.discount = discount;
this.typeOfTour = typeOfTour;
this.kindOfTour = kindOfTour;

}
	 
	 public Tour( String dateOfBeginning, String dateEnd, String name, int numberOfNights, 
			 			int cost, String discount, TypeOfTour typeOfTour, KindOfTour kindOfTour, Set<UserTour> usertours) {		
		
		this.dateOfBeginning = dateOfBeginning;
		this.dateEnd = dateEnd;
		this.name = name;
		this.numberOfNights = numberOfNights;
		this.cost = cost;
		this.discount = discount;
		this.typeOfTour = typeOfTour;
		this.kindOfTour = kindOfTour;
		this.usertours = usertours;
	}
	 
	
	 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name = "id_tour")
	private int id_tour;
	
	@Column(name = "dateOfBeginning")
	private String dateOfBeginning;
	
	@Column(name = "dateEnd")
	private String dateEnd;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "numberOfNights")
	private int numberOfNights;
	
	@Column(name = "cost")
	private int cost;
	
	@Column(name = "discount")
	private String discount;		
	
	
	
		
	@ManyToOne
	@JoinColumn (name = "id_typeOfTour")
	private TypeOfTour typeOfTour;
	
	@ManyToOne
	@JoinColumn (name = "id_kindOfTour")
	private KindOfTour kindOfTour;

	/*@ManyToMany(cascade = CascadeType.ALL)
	//@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usertour", catalog = "travelagency", 
            joinColumns = {@JoinColumn(name = "id_tour")},
            inverseJoinColumns ={ @JoinColumn(name = "id_user")}
    )
	private List<User> users;*/	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "id.tour")	
	 private Set<UserTour> usertours = new HashSet<UserTour>();	
	
	public Set<UserTour> getUserTours() {
		return usertours;
	}
	public void setUserTours(Set<UserTour> usertours) {
		this.usertours = usertours;
	}
	
	//ссылка на объект TypeOfTour для формирования запроса в TourDAOImpl
	//private TypeOfTour TypeOfTour;
	//ссылка на объект KindOfTour для формирования запроса в TourDAOImpl
	//private User User;

	public int getId_tour() {
		return id_tour;
	}

	public void setId_tour(int id_tour) {
		this.id_tour = id_tour;
	}

	public String getDateOfBeginning() {
		return dateOfBeginning;
	}

	public void setDateOfBeginning(String dateOfBeginning) {
		this.dateOfBeginning = dateOfBeginning;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumberOfNights() {
		return numberOfNights;
	}

	public void setNumberOfNights(int numberOfNights) {
		this.numberOfNights = numberOfNights;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public KindOfTour getKindOfTour() {
		return kindOfTour;
	}

	public void setKindOfTour(KindOfTour kindOfTour) {
		this.kindOfTour = kindOfTour;
	}
	
	public TypeOfTour getTypeOfTour() {
		return typeOfTour;
	}
	public void setTypeOfTour(TypeOfTour typeOfTour) {
		this.typeOfTour = typeOfTour;
	}
	
	/*public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}*/
	
	@Override
	public String toString() {
		return "Tour [id_tour=" + id_tour + ", dateOfBeginning=" + dateOfBeginning + 
				", dateEnd=" + dateEnd + ", name=" + name + ", numberOfNights=" + numberOfNights + 
				", cost=" + cost + ", discount=" + discount + ", typeOfTour=" + typeOfTour
				+ ", kindOfTour=" + kindOfTour + "]";
	}
			
	
}
