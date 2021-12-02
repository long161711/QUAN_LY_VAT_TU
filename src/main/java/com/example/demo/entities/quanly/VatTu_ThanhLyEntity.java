package com.example.demo.entities.quanly;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vattu_thanhly", schema = "da_ltm")
public class VatTu_ThanhLyEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name = "idvattu")
	private Integer idvattu;
	@Column(name = "soluong")
	private Integer soluong;
	@Column(name = "tongtien")
	private String tongtien;
	@Column(name = "soluongvattudathanhly")
	private Integer soluongvattudathanhly;
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
    public String getTongTien() {
    	return tongtien;
    }
    public void setTongTien(String tongtien) {
    	this.tongtien = tongtien;
    } 
    public Integer getSoLuongVatTuDaThanhLy() {
    	return soluongvattudathanhly;
    }
    public void setSoLuongVatTuDaThanhLy(Integer soluongvattudathanhly) {
    	this.soluongvattudathanhly = soluongvattudathanhly;
    } 
}
