package com.example.demo.Controller.quanly;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.demo.entities.quanly.VatTu_PhongEntity;
import com.example.demo.entities.quanly.VatTu_ThanhLyEntity;
import com.example.demo.configs.models.VatTuKhoaModel;
import com.example.demo.configs.models.VatTuModel;
import com.example.demo.configs.models.VatTuThanhLyModel;
import com.example.demo.entities.quanly.KhoaEntity;
import com.example.demo.entities.quanly.PhongEntity;
import com.example.demo.entities.quanly.VatTuEntity;
import com.example.demo.entities.quanly.VatTu_HongEntity;
import com.example.demo.entities.quanly.VatTu_KhoaEntity;
import com.example.demo.repositories.quanly.KhoaRepository;
import com.example.demo.repositories.quanly.PhongRepository;
import com.example.demo.repositories.quanly.VatTuRepository;
import com.example.demo.repositories.quanly.VatTu_HongRepository;
import com.example.demo.repositories.quanly.VatTu_KhoaRepository;
import com.example.demo.repositories.quanly.VatTu_PhongRepository;
import com.example.demo.repositories.quanly.VatTu_ThanhLyRepository;

@Controller
public class VatTuController {
	@Autowired
	VatTuRepository vattuRepository;
	@Autowired
	PhongRepository phongRepository;
	@Autowired
	VatTu_ThanhLyRepository vattu_thanhlyRepository;
	@Autowired
	VatTu_KhoaRepository vattu_khoaRepository;
	@Autowired
	VatTu_PhongRepository vattu_phongRepository;
	@Autowired
	VatTu_HongRepository vattu_hongRepository;
	@Autowired
	KhoaRepository khoaRepository;
	 @GetMapping(value ="/themvattu_phong/{id}" )
	    public String themphong(@PathVariable(value="id") int id,Model c) {
		 PhongEntity phong = phongRepository.findOneById(id);
		 List<VatTu_KhoaEntity> vattukhoa = vattu_khoaRepository.findAllByIdKhoa(phong.getIdKhoa());
		int dem =0;
			 List<VatTuEntity> list = new ArrayList<VatTuEntity>();
			 for(VatTu_KhoaEntity vattukhoalist : vattukhoa) {
				 dem++;
				 VatTuEntity ab = vattuRepository.findOneById1(vattukhoalist.getIdVatTu());
				 list.add(ab);
			 }
			 if(dem!=0) {
			 c.addAttribute("vattu",list);
			 c.addAttribute("idphong",id);
			 return "layouts/admin/pages/phong/themvattu_phong";
			 }
		 else {
			 List<VatTu_PhongEntity> aa = (List<VatTu_PhongEntity>) this.vattu_phongRepository.findAllById(id);
			  List<VatTuModel> list1 = new ArrayList<VatTuModel>();
			 
			  for (VatTu_PhongEntity s : aa) {
				  VatTuModel an = new VatTuModel(); 
				  VatTuEntity vattu1 = vattuRepository.findOneById(s.getIdVatTu());
				  an.setId(s.getId());
				  an.setMaVatTu(vattu1.getMaVatTu());
				  an.setTenVatTu(vattu1.getTenVatTu());
				  an.setNamSuDung(vattu1.getNamSuDung());
				  an.setSoLuong(s.getSoLuong());
				  an.setThongSoKiThuat(vattu1.getThongSoKiThuat());
				  an.setTileCL(vattu1.getTileCl());
				  an.setSoVatTuDangHoatDongTot(s.getSoVatTuDangHoatDongTot());
				  list1.add(an);
		        }
			  c.addAttribute("list",list1);
			  c.addAttribute("idphong",id);
			  c.addAttribute("thongbao", "Khoa Của Phòng Này Hiện Chưa Có Vật Tư Nào.");
			 return "layouts/admin/pages/phong/hienthivattu_phong";
		 }
	    }
	 @PostMapping(value ="/themvattu_phong/{id}")
	 	public String themphong1(@PathVariable(value ="id") int id, Model c,HttpServletRequest request,@ModelAttribute VatTuEntity vattuEntity) {
		 String phuongthuc = request.getParameter("xacnhan");
		 if(phuongthuc.equals("Tạo mới")) {
			 VatTuEntity vattu = vattuRepository.findOneByMaVatTu(vattuEntity.getMaVatTu());
			 PhongEntity phong = phongRepository.findOneById(id);
			 VatTu_KhoaEntity vattukhoa = vattu_khoaRepository.findOneByIdVatTuIdKhoa(vattu.getId(),phong.getIdKhoa());
			 int conlai = vattukhoa.getSoDu()-vattuEntity.getSoLuong();
			 if(conlai>=0) {
				 vattukhoa.setSoDu(conlai);
				 vattu_khoaRepository.save(vattukhoa);
				 int dem =0;
				 List<VatTu_PhongEntity> vtp = vattu_phongRepository.findAllByIdVatTu(vattu.getId());
				 for (VatTu_PhongEntity s : vtp) {
					 if(s.getIdPhong()==id) {
						 dem=1;
						 s.setSoVatTuDangHoatDongTot(s.getSoVatTuDangHoatDongTot()+vattuEntity.getSoLuong());
						 s.setSoLuong(s.getSoLuong()+vattuEntity.getSoLuong());
						 vattu_phongRepository.save(s);
					 }
				 }
				 if(dem==0) {
					 VatTu_PhongEntity vattuphong1 = new VatTu_PhongEntity();
					 vattuphong1.setIdPhong(id);
					 vattuphong1.setIdVatTu(vattu.getId());
					 vattuphong1.setSoLuong(vattuEntity.getSoLuong());
					 vattuphong1.setSoVatTuDangHoatDongTot(vattuEntity.getSoLuong());
					 vattu_phongRepository.save(vattuphong1);
				 }
//				 VatTu_PhongEntity vattuphong = vattu_phongRepository.findOneById(vattu.getId());
//				 if(vattuphong!=null) {
//					 vattuphong.setSoVatTuDangHoatDongTot(vattuphong.getSoVatTuDangHoatDongTot()+vattuEntity.getSoLuong());
//					 vattuphong.setSoLuong(vattuphong.getSoLuong()+vattuEntity.getSoLuong());
//					 vattu_phongRepository.save(vattuphong);
//				 }
//				 else {
//					 VatTu_PhongEntity vattuphong1 = new VatTu_PhongEntity();
//					 vattuphong1.setIdPhong(id);
//					 vattuphong1.setIdVatTu(vattu.getId());
//					 vattuphong1.setSoLuong(vattuEntity.getSoLuong());
//					 vattuphong1.setSoVatTuDangHoatDongTot(vattuEntity.getSoLuong());
//					 vattu_phongRepository.save(vattuphong1);
//				 }
			 }
			 else {
				 c.addAttribute("thongbao", "Số lượng vật tư thêm vào không hợp lệ");
				 return themphong(id, c);
			 }
		 }
		 List<VatTu_PhongEntity> aa = (List<VatTu_PhongEntity>) this.vattu_phongRepository.findAllById(id);
		  List<VatTuModel> list = new ArrayList<VatTuModel>();
		 
		  for (VatTu_PhongEntity s : aa) {
			  VatTuModel an = new VatTuModel(); 
			  VatTuEntity vattu1 = vattuRepository.findOneById(s.getIdVatTu());
			  an.setId(s.getId());
			  an.setMaVatTu(vattu1.getMaVatTu());
			  an.setTenVatTu(vattu1.getTenVatTu());
			  an.setNamSuDung(vattu1.getNamSuDung());
			  an.setSoLuong(s.getSoLuong());
			  an.setThongSoKiThuat(vattu1.getThongSoKiThuat());
			  an.setTileCL(vattu1.getTileCl());
			  an.setSoVatTuDangHoatDongTot(s.getSoVatTuDangHoatDongTot());
			  list.add(an);
	        }
		  c.addAttribute("list",list);
		  c.addAttribute("idphong",id);
		
		 return "layouts/admin/pages/phong/hienthivattu_phong";
		 
		 
		 
		 	
	 }
	 @GetMapping(value ="/hienthitatcavattu" )
	    public String hienthitatcavattu(Model c) {
		 List<VatTuEntity> vattu = (List<VatTuEntity>) vattuRepository.findAll1();
		 c.addAttribute("vattu",vattu);
		 return "layouts/admin/pages/vattu/hienthitatcavattu";
		 	
	    }
	 @GetMapping(value="/timkiemvattu")
	 	public String timkiemvattu(Model c) {
		 List<VatTuEntity> vattu = (List<VatTuEntity>) vattuRepository.findAll1();
		 c.addAttribute("vattu",vattu);
		 return "layouts/admin/pages/vattu/timkiemvattu";
	 }
	 @PostMapping(value="/timkiemvattu")
	 	public String timkiemvattu1(@ModelAttribute VatTuEntity vattuEntity, Model c, HttpServletRequest request){
		 String phuongthuc = request.getParameter("xacnhan");
		 if(phuongthuc.equals("Tìm Kiếm")) {
		 VatTuEntity mavattu = vattuRepository.findByMaVatTu(vattuEntity.getMaVatTu());
		 List<VatTuEntity> tenvattu = vattuRepository.findAllByTenVatTu(vattuEntity.getTenVatTu(),vattuEntity.getMaVatTu());
		 List<VatTuEntity> namsudung = vattuRepository.findAllByNamSuDung(vattuEntity.getNamSuDung(),vattuEntity.getMaVatTu(),vattuEntity.getTenVatTu());
		 if(mavattu!=null) {
			 c.addAttribute("mavattu", mavattu);
		 }
		 if(tenvattu!=null) {
			 c.addAttribute("tenvattu", tenvattu);
		 }
		 if(namsudung!=null) {
			 c.addAttribute("namsudung", namsudung);
		 }
		 return "layouts/admin/pages/vattu/hienthitimkiem";
		 }
		 else  return hienthitatcavattu(c);
		 
	 }
	 @GetMapping(value="/themvattutruong")
	 	public String themvattutruong(Model c) {
		 return "layouts/admin/pages/vattu/themvattutruong";
	 }
	 @PostMapping(value="/themvattutruong")
	 	public String themvattutruong(Model c,@ModelAttribute VatTuEntity vattuEntity,HttpServletRequest request) {
		 String phuongthuc = request.getParameter("xacnhan");
		 if(phuongthuc.equals("Tạo mới")) {
			 VatTuEntity vattu = vattuRepository.findOneByMaVatTu(vattuEntity.getMaVatTu());
			 if(vattu!=null) {
				 c.addAttribute("thongbao","Mã Vật Tư Đã Tồn Tại Nhập lại Thông Tin Vật Tư");
				 return  "layouts/admin/pages/vattu/themvattutruong";
			 }
			 else {
			 vattuEntity.setSoDu(vattuEntity.getSoLuong());
			 vattuEntity.setTongTien(Integer.toString(Integer.parseInt(vattuEntity.getTongTien())*vattuEntity.getSoLuong()));
			 try {
			 vattuRepository.save(vattuEntity);
			 }
			 catch (Exception e) {
				 c.addAttribute("thongbao","Dữ Liệu vật tư không hợp lệ!");
				 return  "layouts/admin/pages/vattu/themvattutruong";
			}
		 }
		 }
		 
		 return hienthitatcavattu(c);
		 
	 }
	 @GetMapping(value="/hienthivattuthanhly")
		 public String hienthivattuthanhly(Model c) {
		 List<VatTu_ThanhLyEntity> allvattuthanhly = (List<VatTu_ThanhLyEntity>) vattu_thanhlyRepository.findAll();
		 List<VatTuThanhLyModel> list = new ArrayList<VatTuThanhLyModel>();
		 for(VatTu_ThanhLyEntity ab : allvattuthanhly) {
			 VatTuThanhLyModel vattuthanhlymodel = new VatTuThanhLyModel();
			 VatTuEntity cc = vattuRepository.findOneById1(ab.getIdVatTu());
			 vattuthanhlymodel.setId(ab.getId());
			 vattuthanhlymodel.setMaVatTu(cc.getMaVatTu());
			 vattuthanhlymodel.setTenVatTu(cc.getTenVatTu());
			 vattuthanhlymodel.setNamSuDung(cc.getNamSuDung());
			 vattuthanhlymodel.setSoLuong(ab.getSoLuong());
			 vattuthanhlymodel.setSoLuongVatTuDaThanhLy(ab.getSoLuongVatTuDaThanhLy());
			 vattuthanhlymodel.setThongSoKiThuat(cc.getThongSoKiThuat());
			 vattuthanhlymodel.setTongTien(ab.getTongTien());
			 list.add(vattuthanhlymodel);
		 }
		 c.addAttribute("list",list);
		 return "layouts/admin/pages/vattu/vattuthanhly/hienthivattuthanhly";
			
			 
		 }
	 @GetMapping(value="/timkiemvattuthanhly")
	 	public String timkiemvattuthanhly(Model c) {
		 List<VatTuEntity> vattu = (List<VatTuEntity>) vattuRepository.findAll();
		 c.addAttribute("vattu",vattu);
		 return "layouts/admin/pages/vattu/vattuthanhly/timkiemvattuthanhly";
	 }
	 @PostMapping(value="/timkiemvattuthanhly")
	 	public String timkiemvattuthanhly(@ModelAttribute VatTuEntity vattuEntity, Model c, HttpServletRequest request){
		 String phuongthuc = request.getParameter("xacnhan");
		 if(phuongthuc.equals("Tìm Kiếm")) {
			 VatTuEntity mavattu = vattuRepository.findByMaVatTu1(vattuEntity.getMaVatTu());
			 List<VatTuEntity> tenvattu = vattuRepository.findAllByTenVatTu1(vattuEntity.getTenVatTu(),vattuEntity.getMaVatTu());
			 List<VatTuEntity> namsudung = vattuRepository.findAllByNamSuDung1(vattuEntity.getNamSuDung(),vattuEntity.getMaVatTu(),vattuEntity.getTenVatTu());
			 List<VatTu_ThanhLyEntity> allvattuthanhly = new ArrayList<VatTu_ThanhLyEntity>();
			 if(mavattu!=null) {
				 VatTu_ThanhLyEntity vattuthanhly = vattu_thanhlyRepository.findOneByIdVatTu(mavattu.getId());
				 if(vattuthanhly!=null)
				 allvattuthanhly.add(vattuthanhly);
			 }
			 for(VatTuEntity vattu : tenvattu) {
				 VatTu_ThanhLyEntity vattuthanhly = vattu_thanhlyRepository.findOneByIdVatTu(vattu.getId());
				 if(vattuthanhly!=null)
				 allvattuthanhly.add(vattuthanhly);
			 }
			 for(VatTuEntity vattu : namsudung) {
				 VatTu_ThanhLyEntity vattuthanhly = vattu_thanhlyRepository.findOneByIdVatTu(vattu.getId());
				 if(vattuthanhly!=null)
				 allvattuthanhly.add(vattuthanhly);
			 }
				 List<VatTuThanhLyModel> list = new ArrayList<VatTuThanhLyModel>();
				 for(VatTu_ThanhLyEntity ab : allvattuthanhly) {
					 VatTuThanhLyModel vattuthanhlymodel = new VatTuThanhLyModel();
					 VatTuEntity cc = vattuRepository.findOneById1(ab.getIdVatTu());
					 vattuthanhlymodel.setId(ab.getId());
					 vattuthanhlymodel.setMaVatTu(cc.getMaVatTu());
					 vattuthanhlymodel.setTenVatTu(cc.getTenVatTu());
					 vattuthanhlymodel.setNamSuDung(cc.getNamSuDung());
					 vattuthanhlymodel.setSoLuong(ab.getSoLuong());
					 vattuthanhlymodel.setSoLuongVatTuDaThanhLy(ab.getSoLuongVatTuDaThanhLy());
					 vattuthanhlymodel.setThongSoKiThuat(cc.getThongSoKiThuat());
					 vattuthanhlymodel.setTongTien(ab.getTongTien());
					 list.add(vattuthanhlymodel);
				 }
				 c.addAttribute("list",list);
				 return "layouts/admin/pages/vattu/vattuthanhly/hienthivattuthanhly";
		 }
		 else  return hienthivattuthanhly(c);
		 
	 }
	 @GetMapping(value="/thanhlyvattutruong/{id}")
	 	public String thanhlyvattutruong(Model c, @PathVariable(value ="id") int id) {
		 VatTuEntity vattu = vattuRepository.findOneById(id);
		 int soluongdangdung = vattu.getSoLuong()-vattu.getSoDu();
		 c.addAttribute("vattu",vattu);
		 c.addAttribute("soluong",soluongdangdung);
		 c.addAttribute("id",id);
		 return "layouts/admin/pages/vattu/thanhlyvattutruong";
	 }
	 @PostMapping(value="/thanhlyvattutruong/{id}")
	  public String thanhlyvattutruong(Model c, @PathVariable(value = "id") int id,HttpServletRequest request) {
		 String phuongthuc = request.getParameter("xacnhan");
		 if(phuongthuc.equals("Xác Nhận")) {
			 int soluong = Integer.parseInt(request.getParameter("thanhly"));
			 String sotien = request.getParameter("sotien");
			 VatTuEntity vattu = vattuRepository.findOneById(id);
			 if(soluong>vattu.getSoDu()) {
				 c.addAttribute("thongbao","Không đủ số lượng để thanh lý. Mời bạn chọn lại số lượng số vật tư muốn thanh lý.");
				 int soluongdangdung = vattu.getSoLuong()-vattu.getSoDu();
				 c.addAttribute("vattu",vattu);
				 c.addAttribute("soluong",soluongdangdung);
				 c.addAttribute("id",id);
				 return "layouts/admin/pages/vattu/thanhlyvattutruong";
			 }
			 else {
				 vattu.setTongTien(Integer.toString(Integer.parseInt(vattu.getTongTien())/vattu.getSoLuong()*(vattu.getSoLuong()-soluong)));
				 vattu.setSoLuong(vattu.getSoLuong()-soluong);
				 vattu.setSoDu(vattu.getSoDu()-soluong);
				 vattuRepository.save(vattu);
				 VatTu_ThanhLyEntity alo = vattu_thanhlyRepository.findOneByIdVatTu(id);
				 if(alo!=null) {
					 alo.setSoLuong(alo.getSoLuong()+soluong);
					 alo.setTongTien(Integer.toString(Integer.parseInt(sotien)*soluong+Integer.parseInt(alo.getTongTien())));
					 vattu_thanhlyRepository.save(alo);
				 }
				 else {
				 VatTu_ThanhLyEntity vattuthanhly = new VatTu_ThanhLyEntity();
				 vattuthanhly.setIdVatTu(id);
				 vattuthanhly.setSoLuong(soluong);
				 vattuthanhly.setTongTien(Integer.toString(Integer.parseInt(sotien)*soluong));
				 vattuthanhly.setSoLuongVatTuDaThanhLy(0);
				 vattu_thanhlyRepository.save(vattuthanhly);
				 }
			 }
			 return hienthivattuthanhly(c); 
		 }
//		 List<VatTu_ThanhLyEntity> allvattuthanhly = (List<VatTu_ThanhLyEntity>) vattu_thanhlyRepository.findAll();
//		 List<VatTuThanhLyModel> list = new ArrayList<VatTuThanhLyModel>();
//		 for(VatTu_ThanhLyEntity ab : allvattuthanhly) {
//			 VatTuThanhLyModel vattuthanhlymodel = new VatTuThanhLyModel();
//			 VatTuEntity cc = vattuRepository.findOneById1(ab.getIdVatTu());
//			 vattuthanhlymodel.setId(ab.getId());
//			 vattuthanhlymodel.setMaVatTu(cc.getMaVatTu());
//			 vattuthanhlymodel.setNamSuDung(cc.getNamSuDung());
//			 vattuthanhlymodel.setSoLuong(ab.getSoLuong());
//			 vattuthanhlymodel.setSoLuongVatTuDaThanhLy(ab.getSoLuongVatTuDaThanhLy());
//			 vattuthanhlymodel.setThongSoKiThuat(cc.getThongSoKiThuat());
//			 vattuthanhlymodel.setTongTien(ab.getTongTien());
//			 list.add(vattuthanhlymodel);
//		 }
//		 c.addAttribute("list",list);
		 else {
			 return hienthitatcavattu(c);
		 }
	 }
	 @GetMapping(value="/updatevattutruong/{id}")
	 	public String updatevattutruong(Model c, @PathVariable(value ="id") int id) {
		 VatTuEntity vattu = vattuRepository.findOneById(id);
		 c.addAttribute("vattu", vattu);
		 int sotien1vattu =  Integer.parseInt(vattu.getTongTien())/vattu.getSoLuong();
		 c.addAttribute("sotien1vattu", sotien1vattu);
			return "layouts/admin/pages/vattu/updatevattutruong";
	 }
	 @PostMapping(value="/updatevattutruong/{id}")
	  public String updatevattutruong(@ModelAttribute VatTuEntity vattuEntity,Model c, @PathVariable(value = "id") int id,HttpServletRequest request) {
		 String phuongthuc = request.getParameter("xacnhan");
		 if(phuongthuc.equals("Update")) {
		 VatTuEntity vattu = vattuRepository.findOneById(id);
		 VatTuEntity vattu1 = vattuRepository.findOneByMaVaTuid(vattuEntity.getMaVatTu(),id);
		 if(vattuEntity.getSoLuong()<(vattu.getSoLuong()-vattu.getSoDu())||vattu1!=null) {
			 c.addAttribute("vattu", vattu);
			 int sotien1vattu =  Integer.parseInt(vattu.getTongTien())/vattu.getSoLuong();
			 c.addAttribute("sotien1vattu", sotien1vattu);
			 c.addAttribute("thongbao","Dữ liệu cập nhật không hợp lệ ! Nguyên nhân có thể do Mã Vật Tư muốn đổi lại đã tồn tại hoạc số lượng vật tư muốn sửa ít hơn số lượng vật tư đang sử dụng");
				return "layouts/admin/pages/vattu/updatevattutruong";
		 }
		 else {
			 int soluong = vattu.getSoLuong()-vattu.getSoDu();
			 vattuEntity.setSoDu(vattuEntity.getSoLuong()-soluong);
			 vattuEntity.setTongTien(Integer.toString(Integer.parseInt(vattuEntity.getTongTien())*vattuEntity.getSoLuong()));
			 vattuRepository.save(vattuEntity);
		 }
		 	VatTuEntity mavattu = vattuRepository.findByMaVatTu(vattuEntity.getMaVatTu());
		 	c.addAttribute("mavattu", mavattu);
		 	return "layouts/admin/pages/vattu/hienthitimkiem";
		 }
		 else {
			 return hienthitatcavattu(c);
		 }
	 }
	 
	 @GetMapping(value="/themvattuvaokhoa/{id}")
	 	public String themvattuvaokhoa(Model c, @PathVariable(value ="id") int id) {
		 List<KhoaEntity> khoa = (List<KhoaEntity>) khoaRepository.findAll();
		 c.addAttribute("khoa", khoa);
		 c.addAttribute("id", id);
		 return  "layouts/admin/pages/vattu/themvattu-phong/chonkhoa";
		 
	 }
	 @PostMapping(value="/themvattuvaokhoa/{id}")
	  public String themvattuvaokhoa(@ModelAttribute KhoaEntity khoaEntity,Model c, @PathVariable(value = "id") int id,HttpServletRequest request) {
		 String phuongthuc = request.getParameter("xacnhan");
		 if(phuongthuc.equals("Xác nhận")) {
			 if(request.getParameter("soluong")!="") {
				 try {
					 int soluong= Integer.parseInt(request.getParameter("soluong"));
					 VatTuEntity vattu= vattuRepository.findOneById(id);
				 if (soluong>vattu.getSoDu()||soluong<=0) {
					 c.addAttribute("thongbao","Số lượng vật tư muốn chuyển không hợp lệ.");
					 return themvattuvaokhoa(c, id);
				 }
				 else {
					 vattu.setSoDu(vattu.getSoDu()-soluong);
					 vattuRepository.save(vattu);
					 KhoaEntity khoa = khoaRepository.findOneByMaKhoa(khoaEntity.getMakhoa());
					 VatTu_KhoaEntity vattukhoa = vattu_khoaRepository.findOneByIdVatTuIdKhoa(id, khoa.getId());
					 if(vattukhoa!=null) {
						 vattukhoa.setSoluong(vattukhoa.getSoLuong()+soluong);
						 vattukhoa.setSoDu(vattukhoa.getSoDu()+soluong);
						 vattu_khoaRepository.save(vattukhoa);
					 }
					 else {
						 VatTu_KhoaEntity vattukhoa1 = new VatTu_KhoaEntity();
						 vattukhoa1.setIdKhoa(khoa.getId());
						 vattukhoa1.setIdVatTu(id);
						 vattukhoa1.setSoluong(soluong);
						 vattukhoa1.setSoDu(soluong);
						 vattukhoa1.setSoVatTuHong(0);
						 vattu_khoaRepository.save(vattukhoa1);
					 }
					 List<VatTu_KhoaEntity> vattukhoa2 = vattu_khoaRepository.findAllByIdKhoa(khoa.getId());
				    	List<VatTuKhoaModel> list = new ArrayList<VatTuKhoaModel>();
				    	for(VatTu_KhoaEntity vattu1 :  vattukhoa2) {
				    		VatTuEntity vattu2  = vattuRepository.findOneById1(vattu1.getIdVatTu());
				    		VatTuKhoaModel vattuhienthi = new VatTuKhoaModel();
				    		int tien = vattu1.getSoLuong()*(Integer.parseInt(vattu2.getTongTien())/vattu2.getSoLuong());
				    		vattuhienthi.setId(vattu1.getId());
				    		vattuhienthi.setMaVatTu(vattu2.getMaVatTu());
				    		vattuhienthi.setTenVatTu(vattu2.getTenVatTu());
				    		vattuhienthi.setThongSoKiThuat(vattu2.getThongSoKiThuat());
				    		vattuhienthi.setNamSuDung(vattu2.getNamSuDung());
				    		vattuhienthi.setSoLuong(vattu1.getSoLuong());
				    		vattuhienthi.setSoDu(vattu1.getSoDu());
				    		vattuhienthi.setTileCL(vattu2.getTileCl());
				    		vattuhienthi.setTongTien(String.valueOf(tien));
				    		vattuhienthi.setSoVatTuHong(vattu1.getSoVatTuHong());
				    		list.add(vattuhienthi);
				    	}
				    	c.addAttribute("idkhoa",id);
				    	c.addAttribute("vattukhoa", list);
				    	 return "layouts/admin/pages/khoa/vattukhoa";
					 }
				 }
				 catch (Exception e) {
					 c.addAttribute("thongbao","Số lượng vật tư muốn chuyển không hợp lệ.");
					 return themvattuvaokhoa(c, id);
				}
		 }
			 else {
				 c.addAttribute("thongbao","Chưa nhập số vật tư muốn chuyển.");
				 return themvattuvaokhoa(c, id);
			 }
		 }
		 return  hienthitatcavattu(c);
		 
		 
	 }
	 @GetMapping(value="/hienthivattuhong")
	 	public String hienthivattuhong(Model c) {
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
	 @GetMapping(value="/exportvattutruong")
	 	public void exporttoExcel(HttpServletResponse response) throws IOException {
		 response.setContentType("application/octet-stream");
		 String headerKey = "Content-Disposition";
		 
		 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		 String curentDateTime = dateFormat.format(new Date());
		 String fileName = "DanhSachVatTuTruong_"+curentDateTime+".xlsx";
		 String headerValue = "attachement; filename="+fileName;
		 
		 
		 response.setHeader(headerKey, headerValue);
		 List<VatTuEntity> list = (List<VatTuEntity>) vattuRepository.findAll();
		 
		 UserExcelExporter excelExporter = null;
		try {
			excelExporter = new UserExcelExporter(list,curentDateTime);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 excelExporter.export(response);
	 }
}
