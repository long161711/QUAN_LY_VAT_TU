package com.example.demo.Controller.quanly;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.configs.models.VatTuModel;
import com.example.demo.configs.models.VatTuThanhLyModel;
import com.example.demo.entities.quanly.VatTuEntity;
import com.example.demo.entities.quanly.VatTu_HongEntity;
import com.example.demo.entities.quanly.VatTu_ThanhLyEntity;
import com.example.demo.repositories.quanly.KhoaRepository;
import com.example.demo.repositories.quanly.PhongRepository;
import com.example.demo.repositories.quanly.VatTuRepository;
import com.example.demo.repositories.quanly.VatTu_HongRepository;
import com.example.demo.repositories.quanly.VatTu_PhongRepository;
import com.example.demo.repositories.quanly.VatTu_ThanhLyRepository;

@Controller
public class VatTuHongController {
	@Autowired
	VatTuRepository vattuRepository;
	@Autowired
	PhongRepository phongRepository;
	@Autowired
	VatTu_ThanhLyRepository vattu_thanhlyRepository;
	@Autowired
	VatTu_PhongRepository vattu_phongRepository;
	@Autowired
	VatTu_HongRepository vattu_hongRepository;
	@Autowired
	KhoaRepository khoaRepository;
	 @GetMapping(value ="/timkiemvattuhong" )
	    public String timkiemvattuhong(Model c) {
		 List<VatTu_HongEntity> vattuhong = (List<VatTu_HongEntity>) vattu_hongRepository.findAll();
		 List<VatTuEntity> list = new ArrayList<VatTuEntity>();
		 for(VatTu_HongEntity vattu :vattuhong) {
			 VatTuEntity a = vattuRepository.findOneById1(vattu.getIdVatTu());
			 list.add(a);
		 }
		 c.addAttribute("vattu", list);
		 return "layouts/admin/pages/vattu/vattuhong/timkiemvattuhong";
		 
	 }
	 @PostMapping(value ="/timkiemvattuhong")
	 	public String timkiemvattuhong1(@ModelAttribute VatTuEntity vattuEntity,Model c,HttpServletRequest request) {
		 String phuongthuc = request.getParameter("xacnhan");
		 if(phuongthuc.equals("Tìm Kiếm")) {
		 VatTuEntity mavattu = vattuRepository.findByMaVatTu1(vattuEntity.getMaVatTu());
		 List<VatTuEntity> tenvattu = vattuRepository.findAllByTenVatTu1(vattuEntity.getTenVatTu(),vattuEntity.getMaVatTu());
		 List<VatTuEntity> namsudung = vattuRepository.findAllByNamSuDung1(vattuEntity.getNamSuDung(),vattuEntity.getMaVatTu(),vattuEntity.getTenVatTu());
		 List<VatTuModel> list = new ArrayList<VatTuModel>();
		 if(mavattu!=null) {
			 VatTu_HongEntity vattuhong1 = vattu_hongRepository.findOneByIdVatTu(mavattu.getId());
			 if(vattuhong1!=null) {
				 VatTuModel vattumodel = new VatTuModel();
				 vattumodel.setId(mavattu.getId());
				 vattumodel.setMaVatTu(mavattu.getMaVatTu());
				 vattumodel.setTenVatTu(mavattu.getTenVatTu());
				 vattumodel.setThongSoKiThuat(mavattu.getThongSoKiThuat());
				 vattumodel.setNamSuDung(mavattu.getNamSuDung());
				 vattumodel.setSoLuong(vattuhong1.getSoLuong());
				 list.add(vattumodel);
			 }
		 }
		 if(tenvattu!=null) {
			 for(VatTuEntity s :tenvattu) {
				 VatTu_HongEntity vattuhong11 = vattu_hongRepository.findOneByIdVatTu(s.getId());
				 if(vattuhong11!=null) {
					 VatTuModel vattumodel = new VatTuModel();
					 vattumodel.setId(s.getId());
					 vattumodel.setMaVatTu(s.getMaVatTu());
					 vattumodel.setTenVatTu(s.getTenVatTu());
					 vattumodel.setThongSoKiThuat(s.getThongSoKiThuat());
					 vattumodel.setNamSuDung(s.getNamSuDung());
					 vattumodel.setSoLuong(vattuhong11.getSoLuong());
					 list.add(vattumodel);
				 }
			 }
		 }
		 if(namsudung!=null) {
			 for(VatTuEntity s :namsudung) {
				 VatTu_HongEntity vattuhong11 = vattu_hongRepository.findOneByIdVatTu(s.getId());
				 if(vattuhong11!=null) {
					 VatTuModel vattumodel = new VatTuModel();
					 vattumodel.setId(s.getId());
					 vattumodel.setMaVatTu(s.getMaVatTu());
					 vattumodel.setTenVatTu(s.getTenVatTu());
					 vattumodel.setThongSoKiThuat(s.getThongSoKiThuat());
					 vattumodel.setNamSuDung(s.getNamSuDung());
					 vattumodel.setSoLuong(vattuhong11.getSoLuong());
					 list.add(vattumodel);
				 }
			 }
		 }
		 c.addAttribute("list", list);
		 return "layouts/admin/pages/vattu/vattuhong/hienthivattuhong";
		 }
		 else  {
			 List<VatTu_HongEntity> vattuhong = (List<VatTu_HongEntity>) vattu_hongRepository.findAll();
			 List<VatTuModel> list = new ArrayList<VatTuModel>();
			 for(VatTu_HongEntity vattu : vattuhong) {
				 VatTuModel vattumodel = new VatTuModel();
				 VatTuEntity cc = vattuRepository.findOneById1(vattu.getIdVatTu());
				 vattumodel.setId(vattu.getId());
				 vattumodel.setMaVatTu(cc.getMaVatTu());
				 vattumodel.setTenVatTu(cc.getTenVatTu());
				 vattumodel.setThongSoKiThuat(cc.getThongSoKiThuat());
				 vattumodel.setNamSuDung(cc.getNamSuDung());
				 vattumodel.setSoLuong(vattu.getSoLuong());
				 list.add(vattumodel);
				 
			 }
				
			 c.addAttribute("list",list);
			 return "layouts/admin/pages/vattu/vattuhong/hienthivattuhong";
		 }
			
	 }
	 @GetMapping(value ="/deletevattuhong/{id}" )
	    public String deletevattuhong(@PathVariable(value = "id") int id,Model c) {
		 VatTu_HongEntity vattuhong1 = vattu_hongRepository.findOneByIdVatTuHong(id);
		 VatTu_ThanhLyEntity vattuthanhly = vattu_thanhlyRepository.findOneByIdVatTu(vattuhong1.getIdVatTu());
		 VatTuEntity vattu1 = vattuRepository.findOneById1(vattuhong1.getIdVatTu());
		 vattu_hongRepository.delete(vattuhong1);
		 if(vattuthanhly!=null) {
		 }
		 else {
			 if(vattu1.getSoLuong()==0) {
				 vattuRepository.delete(vattu1);
			 }
		 }
		 List<VatTu_HongEntity> vattuhong = (List<VatTu_HongEntity>) vattu_hongRepository.findAll();
		 List<VatTuModel> list = new ArrayList<VatTuModel>();
		 for(VatTu_HongEntity vattu : vattuhong) {
			 VatTuModel vattumodel = new VatTuModel();
			 VatTuEntity cc = vattuRepository.findOneById1(vattu.getIdVatTu());
			 vattumodel.setId(vattu.getId());
			 vattumodel.setMaVatTu(cc.getMaVatTu());
			 vattumodel.setTenVatTu(cc.getTenVatTu());
			 vattumodel.setThongSoKiThuat(cc.getThongSoKiThuat());
			 vattumodel.setNamSuDung(cc.getNamSuDung());
			 vattumodel.setSoLuong(vattu.getSoLuong());
			 list.add(vattumodel);
			 
		 }
			
		 c.addAttribute("list",list);
		 return "layouts/admin/pages/vattu/vattuhong/hienthivattuhong";
	 }
}
