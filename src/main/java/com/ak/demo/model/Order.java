package com.ak.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "PURCHASEORDER")
@NamedQueries({
        @NamedQuery(name = Order.FIND_ALL, query = "SELECT o FROM Order o")
})
public class Order implements Serializable {

    public static final String FIND_ALL = "Order.findAll";

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_TKSK_SURVEY_GEN")
    @SequenceGenerator(name="SEQ_TKSK_SURVEY_GEN", sequenceName="SEQ_TKSK_SURVEY_ID", allocationSize = 1)
    @Column(name = "ORDER_ID", updatable = false, nullable = false)
    private Long id;

    @Version
    @Column(name = "VERSION")
    private Integer version;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL,mappedBy = "order")
    private List<OrderLine> orderLines = new ArrayList<>();

    @Column(name = "TOTAL_ORDER")
    private Float totalOrder = 0F;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ADDRESS")
    private String address;

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
        if (!(obj instanceof Order)) {
            return false;
        }
        Order other = (Order) obj;
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

    public List<OrderLine> getOrderLines() {
        return this.orderLines;
    }

    public void setOrderLines(final List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public Float getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(Float total) {
        this.totalOrder = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        String result = getClass().getSimpleName() + " ";
        if (id != null)
            result += "id: " + id;
        result += ", version: " + version;
        if (orderLines != null)
            result += ", orderLines: " + orderLines;
        if (totalOrder != null)
            result += ", totalOrder: " + totalOrder;
        if (name != null && !name.trim().isEmpty())
            result += ", name: " + name;
        if (address != null && !address.trim().isEmpty())
            result += ", address: " + address;
        if (creationDate != null)
            result += ", creationDate: " + creationDate;
        return result;
    }
}