package com.example.demo.configs.models;
/**
 * Model dành cho việt hiển thị vật tư
 */
public class VatTuModel {
	private Integer id;
	private String mavattu;
	private String tenvattu;
	private String thongsokithuat;
	private String namsudung;
	private String tilecl;
	private Integer sovattudanghoatdongtot;
	private Integer soluong;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
	public Integer getSoVatTuDangHoatDongTot() {
		return sovattudanghoatdongtot;
	}
	public void setSoVatTuDangHoatDongTot(Integer sovattudanghoatdongtot) {
		this.sovattudanghoatdongtot= sovattudanghoatdongtot;
	}


	
}
