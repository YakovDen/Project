package agency;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
/**
 * Встраиваемый класс в качестве компонента в класс UserTour
 * @author Den
 *
 */
@Embeddable
public class UserTourPK implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Tour tour;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Tour getTour() {
		return tour;
	}

	public void setTour(Tour tour) {
		this.tour = tour;
	}
	

}
