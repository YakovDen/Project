package by.library.entities;

import java.io.Serializable;

/** Class displays a description of the book 
 * with the inventory number of physical entity, 
 * which is available now, and you can order it.
 * Read-only object for view with searching results */

public class BookDTO implements Serializable{

	private static final long serialVersionUID = 111L;
	
	private int id;
	private String title;
	private String writer;
	private String isbn;
	private String year;
	
	public BookDTO() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		if (!(obj instanceof BookDTO)) {
			return false;
		}
		BookDTO other = (BookDTO) obj;
		if (id != other.id) {
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
		return "BookDTO [id=" + id + ", title=" + title + ", writer=" + writer + ", isbn=" + isbn + ", year=" + year
				+ "]";
	}

}
