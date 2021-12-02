package com.example.demo.entities.quanly;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vattu_khoa", schema = "da_ltm")
public class VatTu_KhoaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name= "idkhoa")
	private Integer idkhoa;
	@Column(name= "idvattu")
	private Integer idvattu;
	@Column(name= "soluong")
	private Integer soluong;
	@Column(name= "sodu")
	private Integer sodu;
	@Column(name= "sovattuhong")
	private Integer sovattuhong;
	public Integer getId() {
    	return id;
    }
    public void setId(Integer id) {
    	this.id = id;
    }
    public Integer getIdKhoa() {
    	return idkhoa;
    }
    public void setIdKhoa(Integer idkhoa) {
    	this.idkhoa = idkhoa;
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
    public void setSoluong(Integer soluong) {
    	this.soluong = soluong;
    }
    public Integer getSoDu() {
    	return sodu;
    }
    public void setSoDu(Integer sodu) {
    	this.sodu = sodu;
    }
    public Integer getSoVatTuHong() {
    	return sovattuhong;
    }
    public void setSoVatTuHong(Integer sovattuhong) {
    	this.sovattuhong = sovattuhong;
    }
}
