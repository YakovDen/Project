package by.library.entities;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

/** A class describes the essence of the order. 
 * It has properties that describe the user and the book that he ordered or already using, 
 * and date fields (order date, date of giving the book and return date) 
 * and status how user will use book - in reading room or circulating library.
 * Objects support versioning. */

@Entity
@Table(name="T_ORDER", catalog="PerfectLibrary")
@GenericGenerator(name="increment", strategy = "increment")
/*@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)*/
public class Order implements Serializable {
	private static final long serialVersionUID = 22L;
	
	@Id
	@GeneratedValue(generator="increment")
	private int id;

	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToOne
	@JoinColumn(name="inventory_id")
	private Inventory inventory;
	
	/** how to read - reading room or circulating library */
	private String status;
	
	/** give out book date */
	private java.sql.Date dateOn;
	
	/** return book date */
	private java.sql.Date dateOff;
	
	/** order book date */
	private java.sql.Date dateOrder;
	
	@Version
	private int version;
	
	public Order() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public java.sql.Date getDateOn() {
		return dateOn;
	}

	public void setDateOn(java.sql.Date dateOn) {
		this.dateOn = dateOn;
	}

	public java.sql.Date getDateOff() {
		return dateOff;
	}

	public void setDateOff(java.sql.Date dateOff) {
		this.dateOff = dateOff;
	}

	public java.sql.Date getDateOrder() {
		return dateOrder;
	}

	public void setDateOrder(java.sql.Date dateOrder) {
		this.dateOrder = dateOrder;
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
		result = prime * result + ((dateOff == null) ? 0 : dateOff.hashCode());
		result = prime * result + ((dateOn == null) ? 0 : dateOn.hashCode());
		result = prime * result + ((dateOrder == null) ? 0 : dateOrder.hashCode());
		result = prime * result + id;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		if (!(obj instanceof Order)) {
			return false;
		}
		Order other = (Order) obj;
		if (dateOff == null) {
			if (other.dateOff != null) {
				return false;
			}
		} else if (!dateOff.equals(other.dateOff)) {
			return false;
		}
		if (dateOn == null) {
			if (other.dateOn != null) {
				return false;
			}
		} else if (!dateOn.equals(other.dateOn)) {
			return false;
		}
		if (dateOrder == null) {
			if (other.dateOrder != null) {
				return false;
			}
		} else if (!dateOrder.equals(other.dateOrder)) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		if (status == null) {
			if (other.status != null) {
				return false;
			}
		} else if (!status.equals(other.status)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", status=" + status + ", dateOn=" + dateOn + ", dateOff=" + dateOff + ", dateOrder="
				+ dateOrder + ", inventory_id=" + inventory.getInventory_id() + ", user_id=" + user.getUser_id() + "]";
	}
}
