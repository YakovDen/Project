package agency;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Класс видов тура
 * 
 * @author Den
 *
 */
@Entity
@Table(name = "kindoftour", catalog = "travelagency")
public class KindOfTour implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_kindOfTour")
	private int id_kindOfTour;
	
	@Column(name = "kindOfTour")
	private String kindOfTour;
	
	@OneToMany(fetch=FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "kindOfTour")
	private List<Tour> tourses = new ArrayList<>();
	
	public KindOfTour() {
		
	}
	
	public KindOfTour(int id_kindOfTour, String kindOfTour) {
		this.id_kindOfTour = id_kindOfTour;
		this.kindOfTour = kindOfTour;
	}

	public KindOfTour(int id_kindOfTour, String kindOfTour, List<Tour> tourses) {
		this.id_kindOfTour = id_kindOfTour;
		this.kindOfTour = kindOfTour;
		this.tourses = tourses;
	}

	public int getId_kindOfTour() {
		return id_kindOfTour;
	}

	public void setId_kindOfTour(int id_kindOfTour) {
		this.id_kindOfTour = id_kindOfTour;
	}

	public String getKindOfTour() {
		return kindOfTour;
	}

	public void setKindOfTour(String kindOfTour) {
		this.kindOfTour = kindOfTour;
	}

	
	public List<Tour> getTours() {
		return tourses;
	}
	public void setTours(List<Tour> tourses) {
		this.tourses = tourses;
	}
	
	
	@Override
	public String toString(){
		return kindOfTour;
	}
}
