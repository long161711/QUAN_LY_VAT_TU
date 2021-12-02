package com.example.demo.entities.quanly;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vattu", schema = "da_ltm")
public class VatTuEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name="mavattu")
	private String mavattu;
	@Column(name="tenvattu")
	private String tenvattu;
	@Column(name="thongsokithuat")
	private String thongsokithuat;
	@Column(name="namsudung")
	private String namsudung;
	@Column(name="soluong")
	private int soluong;
	@Column(name="tilecl")
	private String tilecl;
	@Column(name="tongtien")
	private String tongtien;
	@Column(name="sodu")
	private int sodu;
	  	public Integer getId() {
	    	return id;
	    }
	    public void setId(Integer id) {
	    	this.id = id;
	    }
	    public String getMaVatTu() {
	        return mavattu;
	    }
	    public void setMaVatTu(String mavattu) {
	        this.mavattu = mavattu;
	    }
	    public String getTenVatTu() {
	        return tenvattu;
	    }

	    public void setTenVatTu(String tenvattu) {
	        this.tenvattu = tenvattu;
	    }
	   public String getThongSoKiThuat() {
		   return thongsokithuat;
	   }
	   public void setThongSoKiThuat(String thongsokithuat) {
		   this.thongsokithuat= thongsokithuat;
	   }
	   public String getNamSuDung() {
		   return namsudung;
	   }
	   public void setNamSuDung(String namsudung) {
		   this.namsudung= namsudung;
	   }
	   public String getTileCl() {
		   return tilecl;
	   }
	   public void setTileCl(String tilecl) {
		   this.tilecl=tilecl;
	   }
	   public int getSoLuong() {
		   return soluong;
	   }
	   public void setSoLuong(int soluong) {
		   this.soluong=soluong;
	   }
	   public String getTongTien() {
		   return tongtien;
	   }
	   public void setTongTien(String tongtien) {
		   this.tongtien=tongtien;
	   }
	   public int getSoDu() {
		   return sodu;
	   }
	   public void setSoDu(int sodu) {
		   this.sodu=sodu;
	   }
}
