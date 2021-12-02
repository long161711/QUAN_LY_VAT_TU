package com.example.demo.entities.quanly;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder.In;

@Entity
@Table(name = "vattu_hong", schema = "da_ltm")
public class VatTu_HongEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name ="idvattu")
	private Integer idvattu;
	@Column(name ="soluong")
	private Integer soluong;
	public Integer getId() {
    	return id;
    }
	public void setId(Integer id) {
    	this.id = id;
    }
	public Integer getIdVatTu() {
    	return idvattu;
    }
	public void setIdVatTu(Integer idvattu) {
    	this.idvattu = idvattu;
    }
	public Integer getSoLuong() {
    	return soluong;
    }
	public void setSoLuong(Integer soluong) {
    	this.soluong = soluong;
    }
}
