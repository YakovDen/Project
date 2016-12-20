package by.library.entities;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

/** A class describes the physical object of the book 
 * with a unique inventory number. It has property that describe
 * book specification (title, writer, year, ISBN), 
 * property which indicates the presence of book in the library at this moment
 * (1 - there is, 0 - is absent), and link to order with this book.
 * Objects support versioning. */

@Entity
@Table(name="T_INVENTORY",catalog="PerfectLibrary")
@GenericGenerator(name="increment", strategy = "increment")
/*@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)*/
public class Inventory implements Serializable {
	private static final long serialVersionUID = 15L;
	
	@Id
	@GeneratedValue(generator="increment")
	@Column(nullable = false)
	private int inventory_id;
	
	@ManyToOne (fetch=FetchType.LAZY)
	@JoinColumn(name="isbn")
	private Book book;
	
	@Column
	private int state;
	
	@OneToOne(mappedBy="inventory")
	private Order order;
	
	@Version
	private int version;

	public int getInventory_id() {
		return inventory_id;
	}

	public void setInventory_id(int inventory_id) {
		this.inventory_id = inventory_id;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((book == null) ? 0 : book.hashCode());
		result = prime * result + inventory_id;
		result = prime * result + state;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Inventory)) {
			return false;
		}
		Inventory other = (Inventory) obj;
		if (book == null) {
			if (other.book != null) {
				return false;
			}
		} else if (!book.equals(other.book)) {
			return false;
		}
		if (inventory_id != other.inventory_id) {
			return false;
		}
		if (state != other.state) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Inventory [inventory_id=" + inventory_id + ", state=" 
	+ state + ", order=" + order + ", book=" + book.getIsbn()+ "]";
	}
	
	
	
	

}
