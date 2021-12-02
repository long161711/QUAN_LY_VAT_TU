package com.example.demo.entities.quanly;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vattu_phong", schema = "da_ltm")
public class VatTu_PhongEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name ="idphong")
	private Integer idphong;
	@Column(name ="idvattu")
	private Integer idvattu;
	@Column(name ="sovattudanghoatdongtot")
	private Integer sovattudanghoatdongtot;
	@Column(name ="soluong")
	private Integer soluong;
	public Integer getId() {
    	return id;
    }
    public void setId(Integer id) {
    	this.id = id;
    }
    public Integer getIdPhong() {
    	return idphong;
    }
    public void setIdPhong(Integer idphong) {
    	this.idphong = idphong;
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
    public Integer getSoVatTuDangHoatDongTot() {
    	return sovattudanghoatdongtot;
    }
    public void setSoVatTuDangHoatDongTot(Integer sovattudanghoatdongtot) {
    	this.sovattudanghoatdongtot = sovattudanghoatdongtot;
    }
    

	
}
