package com.example.demo.configs.models;
/**
 * Model dành cho việt hiển thị vật tư Khoa
 */
public class VatTuKhoaModel {
	private Integer id;
	private String mavattu;
	private String tenvattu;
	private String thongsokithuat;
	private String namsudung;
	private String tilecl;
	private String tongtien;
	private Integer soluong;
	private Integer sodu;
	private Integer sovattuhong;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
	public Integer getSoLuong() {
		return soluong;
	}

	public void setSoLuong(Integer soluong) {
		this.soluong = soluong;
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
	public String getTongTien() {
		return tongtien;
	}
	public void setTongTien(String tongtien) {
		this.tongtien = tongtien;
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
	public String getTileCL() {
		return tilecl;
	}
	public void setTileCL(String tilecl) {
		this.tilecl= tilecl;
	}
	


	
}
