package com.shak.refdata.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;


@Entity
@Table(name="instrument")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
public class Instrument {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length=12, nullable=false, unique=true)
	private String isin;
	
	@Column(length=9, unique=true)
	private String cusip;
	
	@Column(length=10)
	private String ticker;
	
	@Column(length=50)
	private String exchange;
	
	@Column(length=3)
	private String currency;
	
	@Column(name="created_at", updatable=false)
	private LocalDateTime createdAt;
	
	@Column(name="updated_at")
	private LocalDateTime updatedAt;
	
	@PrePersist
	public void prePersist() {
		createdAt = LocalDateTime.now();
		updatedAt = LocalDateTime.now();
	}
	
	@PreUpdate
	public void preUpdate() {
		updatedAt = LocalDateTime.now();
	}
	
	//Gettters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIsin() {
		return isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	public String getCusip() {
		return cusip;
	}

	public void setCusip(String cusip) {
		this.cusip = cusip;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	//Constructors
	public Instrument(Long id, String isin, String cusip, String ticker, String exchange, String currency,
			LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.id = id;
		this.isin = isin;
		this.cusip = cusip;
		this.ticker = ticker;
		this.exchange = exchange;
		this.currency = currency;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Instrument() {
		super();
	}

	//toString method
	
	@Override
	public String toString() {
		return "Instrument [id=" + id + ", isin=" + isin + ", cusip=" + cusip + ", ticker=" + ticker + ", exchange="
				+ exchange + ", currency=" + currency + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
	
	
	
}
