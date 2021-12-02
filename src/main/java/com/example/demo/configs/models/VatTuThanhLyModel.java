package com.example.demo.configs.models;

public class VatTuThanhLyModel {
	private Integer id;
	private String mavattu;
	private String tenvattu;
	private String thongsokithuat;
	private String namsudung;
	private String tongtien;
	private Integer soluong;
	private Integer sovattudaduocthanhly;
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
	public String getTongTien() {
		return tongtien;
	}
	public void setTongTien(String tongtien) {
		this.tongtien= tongtien;
	}
	public Integer getSoLuongVatTuDaThanhLy() {
		return sovattudaduocthanhly;
	}
	public void setSoLuongVatTuDaThanhLy(Integer sovattudaduocthanhly) {
		this.sovattudaduocthanhly= sovattudaduocthanhly;
	}

	

}
