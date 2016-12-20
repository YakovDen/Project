package agency;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "typeoftour",catalog = "travelagency")
public class TypeOfTour implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_typeOfTour")
	private int id_typeOfTour;
	
	@Column(name = "typeOfTour")
	private String typeOfTour;
	
	@OneToMany(fetch=FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "typeOfTour")
	private List<Tour> tours = new ArrayList<>();

	public TypeOfTour() {

	}
	
	public TypeOfTour(int id_typeOfTour, String typeOfTour) {
		this.id_typeOfTour = id_typeOfTour;
		this.typeOfTour = typeOfTour;
	}

	public TypeOfTour(int id_typeOfTour, String typeOfTour, List<Tour> tours) {
		this.id_typeOfTour = id_typeOfTour;
		this.typeOfTour = typeOfTour;
		this.tours = tours;
	}

	public int getId_typeOfTour() {
		return id_typeOfTour;
	}
	public void setId_typeOfTour(int id_typeOfTour) {
		this.id_typeOfTour = id_typeOfTour;
	}
	public String getTypeOfTour() {
		return typeOfTour;
	}
	public void setTypeOfTour(String typeOfTour) {
		this.typeOfTour = typeOfTour;
	} 	
	
	
	public List<Tour> getTours() {
		return tours;
	}
	public void setTours(List<Tour> tours) {
		this.tours = tours;
	}
	
	
	@Override
	public String toString(){
		return typeOfTour;
	}
			
	
}
