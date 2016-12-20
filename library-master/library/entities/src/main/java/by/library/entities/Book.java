package by.library.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/** A class describes the information about the book
 *  - title, author, year of publication, ISBN, 
 *  and a list of the physical entities of concrete book 
 *  with such data, but with different inventory numbers. 
 *  The information can not be changed or deleted, 
 *  just remove all the physical entities, 
 *  book object will still remain in this table.*/

@Entity
@Table(name="T_BOOK", catalog="PerfectLibrary")
/*@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)*/
public class Book implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(nullable = false)
	@Pattern(regexp="^[0-9-]+$", 
	message="ISBN must contain only numbers and hyphens")
	@Size(min = 1, max = 20, 
	message="ISBN must be between 1 and 20 characters long.")
	private String isbn;
	
	@Size(min = 2, max = 200, 
			message="Title must be between 2 and 200 characters long.")
	private String title;
	
	@Size(min = 2, max = 200, 
			message="Writer must be between 2 and 200 characters long.")
	private String writer;
	
	@Pattern(regexp="^[0-9]+$", 
			message="Year must must contain only numbers")
	@Size(min = 1, max = 4, 
	message="Year must be between 1 and 4 characters long.")
	private String year;
	
	/** quantity of the same book in library */
	@OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Inventory> inventories;
	
	public Book() {}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Set<Inventory> getInventory() {
		return inventories;
	}

	public void setInventory(Set<Inventory> inventory) {
		this.inventories = inventory;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((writer == null) ? 0 : writer.hashCode());
		result = prime * result + ((year == null) ? 0 : year.hashCode());
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
		if (!(obj instanceof Book)) {
			return false;
		}
		Book other = (Book) obj;
		if (inventories == null) {
			if (other.inventories != null) {
				return false;
			}
		} else if (!inventories.equals(other.inventories)) {
			return false;
		}
		if (isbn == null) {
			if (other.isbn != null) {
				return false;
			}
		} else if (!isbn.equals(other.isbn)) {
			return false;
		}
		if (title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!title.equals(other.title)) {
			return false;
		}
		if (writer == null) {
			if (other.writer != null) {
				return false;
			}
		} else if (!writer.equals(other.writer)) {
			return false;
		}
		if (year == null) {
			if (other.year != null) {
				return false;
			}
		} else if (!year.equals(other.year)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Book [title=" + title + ", writer=" + writer + ", year=" + year + ", isbn=" + isbn + "]";
	}
}
