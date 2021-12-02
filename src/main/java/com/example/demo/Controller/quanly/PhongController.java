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

import com.example.demo.entities.quanly.KhoaEntity;
import com.example.demo.entities.quanly.PhongEntity;
import com.example.demo.entities.quanly.VatTuEntity;
import com.example.demo.entities.quanly.VatTu_HongEntity;
import com.example.demo.entities.quanly.VatTu_KhoaEntity;
import com.example.demo.entities.quanly.VatTu_PhongEntity;
import com.example.demo.entities.quanly.VatTu_ThanhLyEntity;
import com.example.demo.repositories.quanly.KhoaRepository;
import com.example.demo.repositories.quanly.PhongRepository;
import com.example.demo.repositories.quanly.VatTuRepository;
import com.example.demo.repositories.quanly.VatTu_HongRepository;
import com.example.demo.repositories.quanly.VatTu_KhoaRepository;
import com.example.demo.repositories.quanly.VatTu_PhongRepository;
import com.example.demo.configs.models.VatTuModel;

@Controller
public class PhongController {
	@Autowired
	PhongRepository phongRepository;
	@Autowired
	VatTu_PhongRepository vattu_phongRepository;
	@Autowired
	VatTu_HongRepository vattu_hongRepository;
	@Autowired
	VatTu_KhoaRepository vattu_khoaRepository;
	@Autowired
	VatTuRepository vattuRepository;
	@Autowired
	KhoaRepository khoaRepository;
	  @GetMapping(value ="/themphong/{id}" )
	    public String themphong(@PathVariable(value="id") int id,Model c) {
		 c.addAttribute("idkhoa",id);
		 return "layouts/admin/pages/phong/themphong";
		  
		 
	    }
	 
	  @GetMapping(value ="/danhsachvattu/{id}" )
	    public String danhsachvattuphong(@PathVariable(value="id") int id,Model c) {
		  List<VatTu_PhongEntity> aa = (List<VatTu_PhongEntity>) this.vattu_phongRepository.findAllById(id);
		  List<VatTuModel> list = new ArrayList<VatTuModel>();
		 
		  for (VatTu_PhongEntity s : aa) {
			  VatTuModel an = new VatTuModel(); 
			  VatTuEntity vattu = vattuRepository.findOneById(s.getIdVatTu());
			  an.setId(s.getId());
			  an.setMaVatTu(vattu.getMaVatTu());
			  an.setTenVatTu(vattu.getTenVatTu());
			  an.setNamSuDung(vattu.getNamSuDung());
			  an.setSoLuong(s.getSoLuong());
			  an.setThongSoKiThuat(vattu.getThongSoKiThuat());
			  an.setTileCL(vattu.getTileCl());
			  an.setSoVatTuDangHoatDongTot(s.getSoVatTuDangHoatDongTot());
			  list.add(an);
	        }
		  c.addAttribute("list",list);
		  c.addAttribute("idphong",id);
		
		 return "layouts/admin/pages/phong/hienthivattu_phong";
		  
		 
	    }
	  @PostMapping(value = "/themphong/{id}")
	    public String themphong (@PathVariable(value="id") int id,@ModelAttribute PhongEntity phongEntity,Model c,HttpServletRequest request) {
		  String phuongthuc = request.getParameter("xacnhan");
		  PhongEntity a = new PhongEntity();
	    	System.out.print(phuongthuc);
	    	if(phuongthuc.equals("Tạo mới")) {
	    	PhongEntity kiemtra = phongRepository.findOneByMaphong(phongEntity.getMaPhong());
			 if (kiemtra != null) {
				 c.addAttribute("idkhoa",id);
				 c.addAttribute("thongbao","Mã Phòng Đã Tồn Tại Nhập lại Mã phòng !");
				 return "layouts/admin/pages/phong/themphong";
		        }
			 else {  
				 a.setIdKhoa(id);
				 a.setMaPhong(phongEntity.getMaPhong());
				 a.setTenPhong(phongEntity.getTenPhong());
				 phongRepository.save(a);
				 List<PhongEntity> aa = (List<PhongEntity>) phongRepository.khoa_phong(id);
				 c.addAttribute("idkhoa",id);
				 c.addAttribute("khoa_phong", aa);
				 return "layouts/admin/pages/khoa/khoa_phong";
				 
			 }
	    	}
	    	else  {
	    		 List<PhongEntity> aa = (List<PhongEntity>) phongRepository.khoa_phong(id);
				 c.addAttribute("idkhoa",id);
				 c.addAttribute("khoa_phong", aa);
				 return "layouts/admin/pages/khoa/khoa_phong";
	    	}
		  
	  }
	  @GetMapping(value ="/updatephong/{id}" )
	    public String update(@PathVariable(value="id") int id,Model c) {
		  PhongEntity phong = phongRepository.findOneById(id);
		  c.addAttribute("phong",phong);
		  return "layouts/admin/pages/khoa/updatephong";
	  }
	  @PostMapping(value = "/updatephong/{id}")
	    public String update (@PathVariable(value="id") int id,@ModelAttribute PhongEntity phongEntity,Model c,HttpServletRequest request) {
		  String phuongthuc = request.getParameter("xacnhan");
		  PhongEntity phong = phongRepository.findOneById(id);
		  phongEntity.setIdKhoa(phong.getIdKhoa());
		  if(phuongthuc.equals("Tạo mới")) {
			  phongRepository.save(phongEntity);
		  }
		  List<PhongEntity> aa = (List<PhongEntity>) phongRepository.khoa_phong(phongEntity.getIdKhoa());
			 c.addAttribute("idkhoa",phongEntity.getIdKhoa());
			 c.addAttribute("khoa_phong", aa);
			 return "layouts/admin/pages/khoa/khoa_phong";
	  }
	  @GetMapping(value ="/deletephong/{id}" )
	    public String delete(@PathVariable(value="id") int id,Model c) {
		  List<VatTu_PhongEntity> aa = (List<VatTu_PhongEntity>) this.vattu_phongRepository.findAllById(id);
		  PhongEntity phong = phongRepository.findOneById(id);
		  for (VatTu_PhongEntity s : aa) {
			  int binhthuong = s.getSoVatTuDangHoatDongTot();
			  int hong = s.getSoLuong()-binhthuong;
			  VatTu_KhoaEntity vattukhoa = vattu_khoaRepository.findOneByIdVatTuIdKhoa(s.getIdVatTu(),phong.getIdKhoa());
			  int sodu = vattukhoa.getSoDu();
			  vattukhoa.setSoDu(sodu+binhthuong);
			  int sohong = vattukhoa.getSoVatTuHong();
			  vattukhoa.setSoVatTuHong(sohong+hong);
			  vattu_phongRepository.delete(s);
			  vattu_khoaRepository.save(vattukhoa);
			  
		  }
		  
		  int idkhoa = phong.getIdKhoa();
		  phongRepository.delete(phong);
		  List<PhongEntity> as = (List<PhongEntity>) phongRepository.khoa_phong(idkhoa);
	    	c.addAttribute("idkhoa",idkhoa);
	    	c.addAttribute("khoa_phong", as);
	    	 return "layouts/admin/pages/khoa/khoa_phong";
	  }
	  
	  @GetMapping(value ="/updatevattuphong/{id}" )
	    public String updatevattuphong(@PathVariable(value="id") int id,Model c) {
		  VatTu_PhongEntity vattuphong = vattu_phongRepository.findOneById(id); 
		  VatTuEntity vattu = vattuRepository.findOneById(vattuphong.getIdVatTu());
		  VatTuModel hienthi = new VatTuModel();
		  hienthi.setId(vattuphong.getId());
		  hienthi.setMaVatTu(vattu.getMaVatTu());
		  hienthi.setTenVatTu(vattu.getTenVatTu());
		  hienthi.setThongSoKiThuat(vattu.getThongSoKiThuat());
		  hienthi.setNamSuDung(vattu.getNamSuDung());
		  hienthi.setTileCL(vattu.getTileCl());
		  hienthi.setSoLuong(vattuphong.getSoLuong());
		  hienthi.setSoVatTuDangHoatDongTot(vattuphong.getSoVatTuDangHoatDongTot());
		  c.addAttribute("vattu",hienthi);
		  c.addAttribute("id", id);
			return "layouts/admin/pages/phong/updatevattuphong";
		  
	  }
	  @PostMapping(value = "/updatevattuphong/{id}")
	    public String updatevattuphong (@PathVariable(value="id") int id,Model c,HttpServletRequest request) {
		  String phuongthuc = request.getParameter("xacnhan");
		  VatTu_PhongEntity vattuphong = vattu_phongRepository.findOneById(id);
		  if(phuongthuc.equals("Update")) {
			  String vattuModel = request.getParameter("sovattudanghoatdongtot");
			  if(vattuphong.getSoLuong()<Integer.parseInt(vattuModel)) {
				  c.addAttribute("thongbao", "Số vật Tư đang sử dụng đc vượt quá số lượng vật tư đang có.");
				  return updatevattuphong(id,c);
			  }
			  vattuphong.setSoVatTuDangHoatDongTot(Integer.parseInt(vattuModel));
			  vattu_phongRepository.save(vattuphong);
		  }
		  return danhsachvattuphong(vattuphong.getIdPhong(),c);
		  
	  }
	  @GetMapping(value ="/thuhoivattuphong/{id}" )
	    public String thuhoivattuphong(@PathVariable(value="id") int id,Model c) {
		  VatTu_PhongEntity vattuphong = vattu_phongRepository.findOneById(id); 
		  VatTuEntity vattu = vattuRepository.findOneById(vattuphong.getIdVatTu());
		  VatTuModel hienthi = new VatTuModel();
		  hienthi.setId(vattuphong.getId());
		  hienthi.setMaVatTu(vattu.getMaVatTu());
		  hienthi.setTenVatTu(vattu.getTenVatTu());
		  hienthi.setThongSoKiThuat(vattu.getThongSoKiThuat());
		  hienthi.setNamSuDung(vattu.getNamSuDung());
		  hienthi.setTileCL(vattu.getTileCl());
		  hienthi.setSoLuong(vattuphong.getSoLuong());
		  hienthi.setSoVatTuDangHoatDongTot(vattuphong.getSoVatTuDangHoatDongTot());
		  c.addAttribute("vattu",hienthi);
		  c.addAttribute("id", id);
			return "layouts/admin/pages/phong/thuhoivattu";
	  }
	  @PostMapping(value = "/thuhoivattuphong/{id}")
	    public String thuhoivattuphong (@PathVariable(value="id") int id,Model c,HttpServletRequest request) {
		  String phuongthuc = request.getParameter("xacnhan");
		  if(phuongthuc.equals("thuhoivattuhong")) {
			  VatTu_PhongEntity vattuphong = vattu_phongRepository.findOneById(id);
			  int vattuhong = vattuphong.getSoLuong()-vattuphong.getSoVatTuDangHoatDongTot();
			  vattuphong.setSoLuong(vattuphong.getSoVatTuDangHoatDongTot());
			  if(vattuphong.getSoLuong()==0){
				  vattu_phongRepository.delete(vattuphong);
			  }
			  else {
				  vattu_phongRepository.save(vattuphong);
			  }
			  PhongEntity phong =phongRepository.findOneById(vattuphong.getIdPhong());
			  VatTu_KhoaEntity vattukhoa = vattu_khoaRepository.findOneByIdVatTuIdKhoa(vattuphong.getIdVatTu(),phong.getIdKhoa());
			  vattukhoa.setSoVatTuHong(vattukhoa.getSoVatTuHong()+vattuhong);
			  vattu_khoaRepository.save(vattukhoa);
		  }
		  if(phuongthuc.equals("thuhoitatcavattu")) {
			  VatTu_PhongEntity vattuphong = vattu_phongRepository.findOneById(id);
			  int vattuhong = vattuphong.getSoLuong()-vattuphong.getSoVatTuDangHoatDongTot();
			  int vattutot = vattuphong.getSoVatTuDangHoatDongTot();
			  PhongEntity phong =phongRepository.findOneById(vattuphong.getIdPhong());
			  VatTu_KhoaEntity vattukhoa = vattu_khoaRepository.findOneByIdVatTuIdKhoa(vattuphong.getIdVatTu(),phong.getIdKhoa());
			  vattukhoa.setSoVatTuHong(vattukhoa.getSoVatTuHong()+vattuhong);
			  vattukhoa.setSoDu(vattukhoa.getSoDu()+vattutot);
			  vattu_khoaRepository.save(vattukhoa);
			  vattu_phongRepository.delete(vattuphong);
			  return danhsachvattuphong(vattuphong.getIdPhong(),c);
		  }
		  if(phuongthuc.equals("thuhoi")) {
			  String sovattu = request.getParameter("sovatttuthuhoi");
			  if(sovattu!="") {
				  try {
					  int sovattuthuhoi = Integer.parseInt(sovattu);
					  VatTu_PhongEntity vattuphong = vattu_phongRepository.findOneById(id);
					  if(sovattuthuhoi<=vattuphong.getSoLuong()&&sovattuthuhoi>=0) {
						 
						  int sovattuhong = vattuphong.getSoLuong()-vattuphong.getSoVatTuDangHoatDongTot();
						  if(sovattuthuhoi<=sovattuhong) {
							  vattuphong.setSoLuong(vattuphong.getSoLuong()-sovattuthuhoi);
							  vattu_phongRepository.save(vattuphong);
							  PhongEntity phong =phongRepository.findOneById(vattuphong.getIdPhong());
							  VatTu_KhoaEntity vattukhoa = vattu_khoaRepository.findOneByIdVatTuIdKhoa(vattuphong.getIdVatTu(),phong.getIdKhoa());
							  vattukhoa.setSoVatTuHong(vattukhoa.getSoVatTuHong()+sovattuthuhoi);
							  vattu_khoaRepository.save(vattukhoa);
						  }
						  else {
							  int vattuconlai = vattuphong.getSoVatTuDangHoatDongTot()-(sovattuthuhoi-sovattuhong);
							  int vattubinhthuongthuhoi = sovattuthuhoi-sovattuhong;
							  vattuphong.setSoLuong(vattuconlai);
							  vattuphong.setSoVatTuDangHoatDongTot(vattuconlai);
							  vattu_phongRepository.save(vattuphong);
							  PhongEntity phong =phongRepository.findOneById(vattuphong.getIdPhong());
							  VatTu_KhoaEntity vattukhoa = vattu_khoaRepository.findOneByIdVatTuIdKhoa(vattuphong.getIdVatTu(),phong.getIdKhoa());
							  vattukhoa.setSoVatTuHong(vattukhoa.getSoVatTuHong()+sovattuhong);
							  vattukhoa.setSoDu(vattubinhthuongthuhoi);
							  vattu_khoaRepository.save(vattukhoa);
						  }
						  return danhsachvattuphong(vattuphong.getIdPhong(),c);
					  }
					  else {
						  c.addAttribute("thongbao", "Số vật tư muốn thu hồi nhập vào không hợp lệ");
						  return thuhoivattuphong(id,c);
					  }
				  }
				  catch (Exception e) {
					  c.addAttribute("thongbao", "Số vật tư muốn thu hồi không hợp lệ");
					  return thuhoivattuphong(id,c);
				}
			  }
			  else {
				  c.addAttribute("thongbao", "Chưa nhập số vật tư muốn thu hồi");
				  return thuhoivattuphong(id,c);
			  }
		  }
		  VatTu_PhongEntity vattuphong = vattu_phongRepository.findOneById(id);
		  return danhsachvattuphong(vattuphong.getIdPhong(),c);
	  }
	  @GetMapping(value ="/chuyenvattuphong/{id}" )
	    public String chuyenvattuphong(@PathVariable(value="id") int id,Model c) {
		  VatTu_PhongEntity vattuphong = vattu_phongRepository.findOneById(id);
		  PhongEntity phong = phongRepository.findOneById(vattuphong.getIdPhong());
		  KhoaEntity khoa = khoaRepository.findOneById(phong.getIdKhoa());
		  List<PhongEntity> phonghienthi = phongRepository.khoa_phong(phong.getIdKhoa());
		  for(PhongEntity ahi :phonghienthi) {
				 c.addAttribute("khoa", khoa);
				 c.addAttribute("phong", phonghienthi);
				 c.addAttribute("id", id);
					 return "layouts/admin/pages/phong/chonphongchuyenden";
			 }
		  c.addAttribute("thongbao","Khoa nay hien khong co phong nao!");
			 return danhsachvattuphong(vattuphong.getIdPhong(),c);
	  }
//	  @PostMapping(value="/chonkhoachuyenden/{id}")
//	  	public String chonkhoachuyenden(@ModelAttribute KhoaEntity khoaEntity,@PathVariable(value="id") int id,Model c,HttpServletRequest request ) {
//		   String phuongthuc = request.getParameter("xacnhan");
//		   if(phuongthuc.equals("Tiếp Tục")) {
//			   KhoaEntity khoa = khoaRepository.findOneByMaKhoa(khoaEntity.getMakhoa());
//				 List<PhongEntity> phong = phongRepository.khoa_phong(khoa.getId());
//				 for(PhongEntity ahi :phong) {
//					 c.addAttribute("khoa", khoa);
//					 c.addAttribute("phong", phong);
//					 c.addAttribute("id", id);
//						 return "layouts/admin/pages/phong/chonphongchuyenden";
//				 }
//				 List<KhoaEntity> khoa111 = (List<KhoaEntity>) khoaRepository.findAll();
//				 c.addAttribute("khoa", khoa111);
//				 c.addAttribute("id", id);
//				 c.addAttribute("thongbao","Khoa nay hien khong co phong nao!");
//				 return "layouts/admin/pages/phong/chonkhoachuyenden";
//		   }
//		   VatTu_PhongEntity vattuphong = vattu_phongRepository.findOneById(id);
//			return danhsachvattuphong(vattuphong.getIdPhong(),c);
//		  
//	  }
	  @PostMapping(value="/chonphongchuyenden/{id}")
	  	public String chonphongchuyenden(@ModelAttribute PhongEntity phongEntity,@PathVariable(value="id") int id,Model c,HttpServletRequest request ) {
		  String phuongthuc = request.getParameter("xacnhan");
		  if(phuongthuc.equals("Xác Nhận")) {
			  if(request.getParameter("soluong")!="") {
				 try{
					 int soluong = Integer.parseInt(request.getParameter("soluong"));
					 VatTu_PhongEntity vattuphong = vattu_phongRepository.findOneById(id);
					 int hoatdongtot = vattuphong.getSoVatTuDangHoatDongTot();
					 if(soluong>0&&soluong<=vattuphong.getSoLuong()) {
						 if(soluong<=vattuphong.getSoVatTuDangHoatDongTot()) {
							 vattuphong.setSoLuong(vattuphong.getSoLuong()-soluong);
							 vattuphong.setSoVatTuDangHoatDongTot(vattuphong.getSoVatTuDangHoatDongTot()-soluong);
							 vattu_phongRepository.save(vattuphong);
							 PhongEntity phong = phongRepository.findOneByMaphong(phongEntity.getMaPhong());
							 VatTu_PhongEntity vattuphongden = vattu_phongRepository.findOneByIdPhongIdVatTu(phong.getId(), vattuphong.getIdVatTu());
							 if(vattuphongden!=null) {
								 vattuphongden.setSoLuong(vattuphongden.getSoLuong()+soluong);
								 vattuphongden.setSoVatTuDangHoatDongTot(vattuphongden.getSoVatTuDangHoatDongTot()+soluong);
								 vattu_phongRepository.save(vattuphongden);
							 }
							 else {
								 VatTu_PhongEntity vattuphongden1 = new VatTu_PhongEntity();
								 vattuphongden1.setIdPhong(phong.getId());
								 vattuphongden1.setIdVatTu(vattuphong.getIdVatTu());
								 vattuphongden1.setSoLuong(soluong);
								 vattuphongden1.setSoVatTuDangHoatDongTot(soluong);
								 vattu_phongRepository.save(vattuphongden1);
							 }
						 }
						 else {
							 vattuphong.setSoLuong(vattuphong.getSoLuong()-soluong);
							 vattuphong.setSoVatTuDangHoatDongTot(0);
							 if(vattuphong.getSoLuong()==0) {
								 vattu_phongRepository.delete(vattuphong);
							 }
							 else {
								 vattu_phongRepository.save(vattuphong);
							 }
							 PhongEntity phong = phongRepository.findOneByMaphong(phongEntity.getMaPhong());
							 VatTu_PhongEntity vattuphongden = vattu_phongRepository.findOneByIdPhongIdVatTu(phong.getId(), vattuphong.getIdVatTu());
							 if(vattuphongden!=null) {
								 vattuphongden.setSoLuong(vattuphongden.getSoLuong()+soluong);
								 vattuphongden.setSoVatTuDangHoatDongTot(vattuphongden.getSoVatTuDangHoatDongTot()+hoatdongtot);
								 vattu_phongRepository.save(vattuphongden);
							 }
							 else {
								 VatTu_PhongEntity vattuphongden1 = new VatTu_PhongEntity();
								 vattuphongden1.setIdPhong(phong.getId());
								 vattuphongden1.setIdVatTu(vattuphong.getIdVatTu());
								 vattuphongden1.setSoLuong(soluong);
								 vattuphongden1.setSoVatTuDangHoatDongTot(hoatdongtot);
								 vattu_phongRepository.save(vattuphongden1);
							 }
						 }
					 }
					 else {
						 PhongEntity phong = phongRepository.findOneByMaphong(phongEntity.getMaPhong());
						  KhoaEntity khoa = khoaRepository.findOneById(phong.getIdKhoa());
						  List<PhongEntity> phong1 = (List<PhongEntity>) phongRepository.findAllByIdKhoa(khoa.getId());
						  	c.addAttribute("khoa", khoa);
							c.addAttribute("phong", phong1);
							c.addAttribute("id", id);
							c.addAttribute("thongbao", "Số vật tư muốn chuyển đi không hợp lệ");
							return "layouts/admin/pages/phong/chonphongchuyenden";
					 }
					 PhongEntity phong = phongRepository.findOneByMaphong(phongEntity.getMaPhong());
					 return danhsachvattuphong(phong.getId(),c);
				 }
				 catch (Exception e) {
					 PhongEntity phong = phongRepository.findOneByMaphong(phongEntity.getMaPhong());
					  KhoaEntity khoa = khoaRepository.findOneById(phong.getIdKhoa());
					  List<PhongEntity> phong1 = (List<PhongEntity>) phongRepository.findAllByIdKhoa(khoa.getId());
					  	c.addAttribute("khoa", khoa);
						c.addAttribute("phong", phong1);
						c.addAttribute("id", id);
						c.addAttribute("thongbao", "Số vật tư muốn chuyển đi không hợp lệ");
						return "layouts/admin/pages/phong/chonphongchuyenden";
				}
			  }
			  else {
				  PhongEntity phong = phongRepository.findOneByMaphong(phongEntity.getMaPhong());
				  KhoaEntity khoa = khoaRepository.findOneById(phong.getIdKhoa());
				  List<PhongEntity> phong1 = (List<PhongEntity>) phongRepository.findAllByIdKhoa(khoa.getId());
				  	c.addAttribute("khoa", khoa);
					c.addAttribute("phong", phong1);
					c.addAttribute("id", id);
					c.addAttribute("thongbao", "Số vật tư muốn chuyển đi không hợp lệ");
					return "layouts/admin/pages/phong/chonphongchuyenden";
			  }
			  
		  }
		  PhongEntity phong = phongRepository.findOneByMaphong(phongEntity.getMaPhong());
		  return danhsachvattuphong(phong.getId(),c);
	  }
	  
}
