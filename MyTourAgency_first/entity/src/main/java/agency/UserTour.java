package agency;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "usertour", catalog = "travelagency")
// переопределяю перекрываемые поля
@AssociationOverrides({ @AssociationOverride(name = "id.user", joinColumns = @JoinColumn(name = "id_user")),
	@AssociationOverride(name = "id.tour", joinColumns = @JoinColumn(name = "id_tour")) })
public class UserTour implements Serializable  {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private UserTourPK id = new UserTourPK();	
		
	@Column(name = "idUT")//столбец добавлен в таблицу, чтобы по нему вытащить связь usertour	 
	private int idUT;
	
	@Column(name = "isPaid")
	private boolean isPaid;
	
		/*private User users;
		private Tour tours;*/

		/*private int id_user;	
		private int id_tour;*/
		
		
		
		public UserTour() {
		}

		/*public UserTour( int id_user, int id_tour) {			
			this.id_user = id_user;
			this.id_tour = id_tour;
		}*/
		
		
		
		/*public Usertour( User users, Tour tours) {			
			this.users = users;
			this.tours = tours;
		}*/

				
		/*@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "id_user", nullable = false, insertable = false, updatable = false)*/
		/*public User getUsers() {
			return this.users;
		}

		public void setUsers(User users) {
			this.users = users;
		}*/
	
		
		/*public int getId_user(){
			return this.id_user;
		}
		public void setId_user(int id_user){
			this.id_user = id_user;
		}
		
		public int getId_tour(){
			return this.id_tour;
		}
		public void setId_tour(int id_tour){
			this.id_tour = id_tour;
		}*/
				
		
		/*@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "id_tour", nullable = false, insertable = false, updatable = false)*/
		/*public Tour getTours() {
			return this.tours;
		}

		public void setTours(Tour tours) {
			this.tours = tours;
		}*/
	
		
	public UserTourPK getId() {
		return id;
	}

	public void setId(UserTourPK id) {
		this.id = id;
	}

	public User getUser() {
		return getId().getUser();

	}

	public void setUser(User user) {
		getId().setUser(user);

	}

	public Tour getTour() {
		return getId().getTour();

	}

	public void setTour(Tour tour) {
		getId().setTour(tour);

	}

	public boolean isPaid() {
		return isPaid;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}
	
	public int getIdUT() {
		return idUT;
	}

	public void setIdUT(int idUT) {
		this.idUT = idUT;
	}

	@Override
	public String toString() {
		return "UserTour [id=" + id + ", isPaid=" + isPaid + "]";
	}

	
		
}
