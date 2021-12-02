package com.example.demo.entities.quanly;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "phong", schema = "da_ltm")
public class PhongEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name="idkhoa")
	private int idkhoa;
	@Column(name="maphong")
	private String maphong;
	@Column(name="tenphong")
	private String tenphong;
	
	public Integer getId() {
	    	return id;
	    }
	public void setId(Integer id) {
	    	this.id = id;
	    }
	public int  getIdKhoa() {
    	return idkhoa;
    }
	public void setIdKhoa(int idkhoa) {
    	this.idkhoa = idkhoa;
    }
	public String getMaPhong() {
    	return maphong;
    }
	public void setMaPhong(String maphong) {
    	this.maphong = maphong;
    }
	public String getTenPhong() {
    	return tenphong;
    }
	public void setTenPhong(String tenphong) {
    	this.tenphong = tenphong;
    }
}
