package com.ak.demo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Entity
@Table(name = "PURCHASE_ORDERLINE")
public class OrderLine implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_TKSK_SURVEY_GEN")
    @SequenceGenerator(name="SEQ_TKSK_SURVEY_GEN", sequenceName="SEQ_TKSK_SURVEY_ID", allocationSize = 1)
    @Column(name = "ORDERLINE_ID", updatable = false, nullable = false)
    private Long id;
    @Version
    @Column(name = "VERSION")
    private Integer version;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "BOOK_ID")
    private Long bookId;
    

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="ORDER_ID", nullable=false)
    private Order order;
    
    @Transient
    @JsonSerialize
    @JsonDeserialize
    private String bookName;
    
    @Transient
    @JsonSerialize
    @JsonDeserialize
    private Integer bookVersion;

    @Transient
    @JsonSerialize
    @JsonDeserialize
    private String bookIsbn;

    @Transient
    @JsonSerialize
    @JsonDeserialize
    private String bookAuthor;
    
    @Transient
    @JsonSerialize
    @JsonDeserialize
    private String bookDescription;

    @Transient
    @JsonSerialize
    @JsonDeserialize
    private Float bookPrice;
    
    @Column(name = "CREATION_DATE", updatable = false)
    private Date creationDate;

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }
    
    public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof OrderLine)) {
            return false;
        }
        OrderLine other = (OrderLine) obj;
        if (id != null) {
            if (!id.equals(other.id)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}
	
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Override
    public String toString() {
        String result = getClass().getSimpleName() + " ";
        if (quantity != null)
            result += "quantity: " + quantity;
        return result;
    }

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public Integer getBookVersion() {
		return bookVersion;
	}

	public void setBookVersion(Integer bookVersion) {
		this.bookVersion = bookVersion;
	}

	public String getBookIsbn() {
		return bookIsbn;
	}

	public void setBookIsbn(String bookIsbn) {
		this.bookIsbn = bookIsbn;
	}

	public String getBookDescription() {
		return bookDescription;
	}

	public void setBookDescription(String bookDescription) {
		this.bookDescription = bookDescription;
	}

	public Float getBookPrice() {
		return bookPrice;
	}

	public void setBookPrice(Float bookPrice) {
		this.bookPrice = bookPrice;
	}

	public String getBookAuthor() {
		return bookAuthor;
	}

	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	

   
}
