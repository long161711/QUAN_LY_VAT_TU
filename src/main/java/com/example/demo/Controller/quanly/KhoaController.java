package com.example.demo.Controller.quanly;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.configs.models.VatTuKhoaModel;
import com.example.demo.configs.models.VatTuModel;
import com.example.demo.entities.quanly.KhoaEntity;
import com.example.demo.entities.quanly.VatTu_KhoaEntity;
import com.example.demo.entities.quanly.PhongEntity;
import com.example.demo.entities.quanly.VatTuEntity;
import com.example.demo.entities.quanly.VatTu_HongEntity;
import com.example.demo.entities.quanly.VatTu_PhongEntity;
import com.example.demo.repositories.quanly.KhoaRepository;
import com.example.demo.repositories.quanly.PhongRepository;
import com.example.demo.repositories.quanly.VatTuRepository;
import com.example.demo.repositories.quanly.VatTu_HongRepository;
import com.example.demo.repositories.quanly.VatTu_KhoaRepository;
import com.example.demo.repositories.quanly.VatTu_PhongRepository;

@Controller
public class KhoaController {

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
	@Autowired
	PhongRepository phongRepository;

	@GetMapping(value = "/hienthikhoa")
	@PreAuthorize("hasAuthority('Administrator') or hasAuthority('Personal')")
	public String quanlykhoa(Model c) {
		List<KhoaEntity> aa = (List<KhoaEntity>) khoaRepository.findAll();
		c.addAttribute("long", aa);
		return "layouts/admin/pages/hienthikhoa";
	}

	@GetMapping(value = "/themkhoa")
	@PreAuthorize("hasAuthority('Administrator') ")
	public String themkhoa(Model c) {
		return "layouts/admin/pages/khoa/themkhoamoi";
	}

	@PostMapping(value = "/themkhoa")
	@PreAuthorize(value = "hasAuthority('Administrator')")
	public String tt(@ModelAttribute KhoaEntity khoaEntity, Model c, HttpServletRequest request) {
		String phuongthuc = request.getParameter("xacnhan");
		System.out.print(phuongthuc);
		if (phuongthuc.equals("Tạo mới")) {
			KhoaEntity kiemtra = khoaRepository.findOneByMaKhoa(khoaEntity.getMakhoa());
			if (kiemtra != null) {
				c.addAttribute("thongbao", "Ma Khoa Muốn Thêm Đã Tồn Tại !");
				return "layouts/admin/pages/khoa/themkhoamoi";
			} else {
				khoaRepository.save(khoaEntity);
				return quanlykhoa(c);
			}
		} else {
			System.out.print(phuongthuc);
			return quanlykhoa(c);
		}
	}

	@GetMapping(value = "/danhsachphong/{id}")
	public String danhsachphong(@PathVariable(value = "id") int id, Model c) {
		List<PhongEntity> aa = (List<PhongEntity>) phongRepository.khoa_phong(id);
		c.addAttribute("idkhoa", id);
		c.addAttribute("khoa_phong", aa);
		return "layouts/admin/pages/khoa/khoa_phong";
	}

	@GetMapping(value = "/danhsachvattukhoa/{id}")
	public String danhsachvattukhoa(@PathVariable(value = "id") int id, Model c) {
		List<VatTu_KhoaEntity> vattukhoa = vattu_khoaRepository.findAllByIdKhoa(id);
		List<VatTuKhoaModel> list = new ArrayList<VatTuKhoaModel>();
		for (VatTu_KhoaEntity vattu1 : vattukhoa) {
			VatTuEntity vattu = vattuRepository.findOneById1(vattu1.getIdVatTu());
			VatTuKhoaModel vattuhienthi = new VatTuKhoaModel();
			int tien = vattu1.getSoLuong() * (Integer.parseInt(vattu.getTongTien()) / vattu.getSoLuong());
			vattuhienthi.setId(vattu1.getId());
			vattuhienthi.setMaVatTu(vattu.getMaVatTu());
			vattuhienthi.setTenVatTu(vattu.getTenVatTu());
			vattuhienthi.setThongSoKiThuat(vattu.getThongSoKiThuat());
			vattuhienthi.setNamSuDung(vattu.getNamSuDung());
			vattuhienthi.setSoLuong(vattu1.getSoLuong());
			vattuhienthi.setSoDu(vattu1.getSoDu());
			vattuhienthi.setTileCL(vattu.getTileCl());
			vattuhienthi.setTongTien(String.valueOf(tien));
			vattuhienthi.setSoVatTuHong(vattu1.getSoVatTuHong());
			list.add(vattuhienthi);
		}
		c.addAttribute("idkhoa", id);
		c.addAttribute("vattukhoa", list);
		return "layouts/admin/pages/khoa/vattukhoa";
	}

	@GetMapping(value = "/deletekhoa/{id}")
	@PreAuthorize(value = "hasAuthority('Administrator')")
	public String deletekhoa(@PathVariable(value = "id") int id, Model c) {
		List<PhongEntity> phong = (List<PhongEntity>) phongRepository.khoa_phong(id);
		for (PhongEntity phong1 : phong) {
			List<VatTu_PhongEntity> aa = (List<VatTu_PhongEntity>) this.vattu_phongRepository
					.findAllById(phong1.getId());
			for (VatTu_PhongEntity s : aa) {
				int binhthuong = s.getSoVatTuDangHoatDongTot();
				int hong = s.getSoLuong() - binhthuong;
				VatTu_KhoaEntity vattukhoa = vattu_khoaRepository.findOneByIdVatTuIdKhoa(s.getIdVatTu(),
						phong1.getIdKhoa());
				vattukhoa.setSoDu(vattukhoa.getSoDu() + binhthuong);
				vattukhoa.setSoVatTuHong(vattukhoa.getSoVatTuHong() + hong);
				vattu_khoaRepository.save(vattukhoa);
				vattu_phongRepository.delete(s);
			}
			phongRepository.delete(phong1);
		}
		List<VatTu_KhoaEntity> vattukhoa = vattu_khoaRepository.findAllByIdKhoa(id);
		for (VatTu_KhoaEntity vtk : vattukhoa) {
			VatTuEntity vattu = vattuRepository.findOneById1(vtk.getIdVatTu());
			vattu.setSoLuong(vattu.getSoLuong() - vtk.getSoVatTuHong());
			vattu.setSoDu(vattu.getSoDu() + vtk.getSoDu());
			vattuRepository.save(vattu);
			VatTu_HongEntity vattuhong = vattu_hongRepository.findOneByIdVatTu(vtk.getIdVatTu());
			if (vattuhong != null) {
				vattuhong.setSoLuong(vattuhong.getSoLuong() + vtk.getSoVatTuHong());
				vattu_hongRepository.save(vattuhong);
			} else {
				VatTu_HongEntity vattuhong1 = new VatTu_HongEntity();
				vattuhong1.setIdVatTu(vtk.getIdVatTu());
				vattuhong1.setSoLuong(vtk.getSoVatTuHong());
				vattu_hongRepository.save(vattuhong1);
			}
		}
		KhoaEntity khoa = khoaRepository.findOneById(id);
		khoaRepository.delete(khoa);
		return quanlykhoa(c);
	}

	@GetMapping(value = "/updatekhoa/{id}")
	@PreAuthorize(value = "hasAuthority('Administrator')")
	public String updatekhoa(@PathVariable(value = "id") int id, Model c) {
		KhoaEntity alo = khoaRepository.findOneById(id);
		c.addAttribute("khoa", alo);
		return "layouts/admin/pages/updatekhoa";
	}

	@PostMapping(value = "/updatekhoa/{id}")
	@PreAuthorize(value = "hasAuthority('Administrator')")
	public String updatekhoa(@PathVariable(value = "id") int id, @ModelAttribute KhoaEntity khoaEntity, Model c,
			HttpServletRequest request) {
		String phuongthuc = request.getParameter("xacnhan");
		if (phuongthuc.equals("Update")) {
			KhoaEntity khoa = khoaRepository.findOneByMaKhoaid(khoaEntity.getMakhoa(), id);
			if (khoa != null) {
				c.addAttribute("thongbao",
						"Dữ liệu mã khoa mới muốn sửa đỗi trùng khớp với mã khoa khác. Bạn cần sửa lại");
				KhoaEntity alo = khoaRepository.findOneById(id);
				c.addAttribute("khoa", alo);
				return "layouts/admin/pages/updatekhoa";
			} else {
				khoaRepository.save(khoaEntity);
			}
		}
		return quanlykhoa(c);
	}

	@GetMapping(value = "/timkiemvattukhoa/{id}")
	public String timkiemvattukhoa(@PathVariable(value = "id") int id, Model c) {
		List<VatTuEntity> vattu = new ArrayList<VatTuEntity>();
		List<VatTu_KhoaEntity> vattukhoa = vattu_khoaRepository.findAllByIdKhoa(id);
		for (VatTu_KhoaEntity vattu1 : vattukhoa) {
			VatTuEntity vatuluachon = vattuRepository.findOneById1(vattu1.getIdVatTu());
			vattu.add(vatuluachon);
		}
		c.addAttribute("vattu", vattu);
		c.addAttribute("idkhoa", id);
		return "layouts/admin/pages/khoa/timkiemvattukhoa";
	}

	@PostMapping(value = "/timkiemvattukhoa/{id}")
	public String timkiemvattukhoa(@PathVariable(value = "id") int id, @ModelAttribute VatTuEntity vattuEntity, Model c,
			HttpServletRequest request) {
		String phuongthuc = request.getParameter("xacnhan");
		if (phuongthuc.equals("Tìm Kiếm")) {
			VatTuEntity mavattu = vattuRepository.findByMaVatTu(vattuEntity.getMaVatTu());
			List<VatTuEntity> tenvattu = vattuRepository.findAllByTenVatTu(vattuEntity.getTenVatTu(),
					vattuEntity.getMaVatTu());
			List<VatTuEntity> namsudung = vattuRepository.findAllByNamSuDung(vattuEntity.getNamSuDung(),
					vattuEntity.getMaVatTu(), vattuEntity.getTenVatTu());
			List<VatTuKhoaModel> list = new ArrayList<VatTuKhoaModel>();
			if (mavattu != null) {
				VatTu_KhoaEntity vattukhoahienthi = vattu_khoaRepository.findOneByMaVatTu(mavattu.getId(), id);
				VatTuKhoaModel vattuhienthi = new VatTuKhoaModel();
				int tien = vattukhoahienthi.getSoLuong()
						* (Integer.parseInt(mavattu.getTongTien()) / mavattu.getSoLuong());
				vattuhienthi.setId(vattukhoahienthi.getId());
				vattuhienthi.setMaVatTu(mavattu.getMaVatTu());
				vattuhienthi.setTenVatTu(mavattu.getTenVatTu());
				vattuhienthi.setThongSoKiThuat(mavattu.getThongSoKiThuat());
				vattuhienthi.setNamSuDung(mavattu.getNamSuDung());
				vattuhienthi.setSoLuong(vattukhoahienthi.getSoLuong());
				vattuhienthi.setSoDu(vattukhoahienthi.getSoDu());
				vattuhienthi.setTileCL(mavattu.getTileCl());
				vattuhienthi.setTongTien(String.valueOf(tien));
				vattuhienthi.setSoVatTuHong(vattukhoahienthi.getSoVatTuHong());
				list.add(vattuhienthi);
			}
			if (tenvattu != null) {
				for (VatTuEntity vattu : tenvattu) {
					VatTu_KhoaEntity vattukhoahienthi = vattu_khoaRepository.findOneByMaVatTu(vattu.getId(), id);
					VatTuKhoaModel vattuhienthi = new VatTuKhoaModel();
					int tien = vattukhoahienthi.getSoLuong()
							* (Integer.parseInt(vattu.getTongTien()) / vattu.getSoLuong());
					vattuhienthi.setId(vattukhoahienthi.getId());
					vattuhienthi.setMaVatTu(vattu.getMaVatTu());
					vattuhienthi.setTenVatTu(vattu.getTenVatTu());
					vattuhienthi.setThongSoKiThuat(vattu.getThongSoKiThuat());
					vattuhienthi.setNamSuDung(vattu.getNamSuDung());
					vattuhienthi.setSoLuong(vattukhoahienthi.getSoLuong());
					vattuhienthi.setSoDu(vattukhoahienthi.getSoDu());
					vattuhienthi.setTileCL(vattu.getTileCl());
					vattuhienthi.setTongTien(String.valueOf(tien));
					vattuhienthi.setSoVatTuHong(vattukhoahienthi.getSoVatTuHong());
					list.add(vattuhienthi);
				}
			}
			if (namsudung != null) {
				for (VatTuEntity vattu : namsudung) {
					VatTu_KhoaEntity vattukhoahienthi = vattu_khoaRepository.findOneByMaVatTu(vattu.getId(), id);
					VatTuKhoaModel vattuhienthi = new VatTuKhoaModel();
					int tien = vattukhoahienthi.getSoLuong()
							* (Integer.parseInt(vattu.getTongTien()) / vattu.getSoLuong());
					vattuhienthi.setId(vattukhoahienthi.getId());
					vattuhienthi.setMaVatTu(vattu.getMaVatTu());
					vattuhienthi.setTenVatTu(vattu.getTenVatTu());
					vattuhienthi.setThongSoKiThuat(vattu.getThongSoKiThuat());
					vattuhienthi.setNamSuDung(vattu.getNamSuDung());
					vattuhienthi.setSoLuong(vattukhoahienthi.getSoLuong());
					vattuhienthi.setSoDu(vattukhoahienthi.getSoDu());
					vattuhienthi.setTileCL(vattu.getTileCl());
					vattuhienthi.setTongTien(String.valueOf(tien));
					vattuhienthi.setSoVatTuHong(vattukhoahienthi.getSoVatTuHong());
					list.add(vattuhienthi);
				}
			}
			c.addAttribute("idkhoa", id);
			c.addAttribute("vattukhoa", list);
			return "layouts/admin/pages/khoa/vattukhoa";
		} else
			return danhsachvattukhoa(id, c);

	}

	@GetMapping(value = "/themvattukhoavaophong/{id}")
	@PreAuthorize(value = "hasAuthority('Administrator')")
	public String themvattuvaophong(Model c, @PathVariable(value = "id") int id) {
		VatTu_KhoaEntity vattukhoa = vattu_khoaRepository.findOneById(id);
		KhoaEntity khoa = khoaRepository.findOneById(vattukhoa.getIdKhoa());
		List<PhongEntity> phong = phongRepository.khoa_phong(khoa.getId());
		for (PhongEntity ahi : phong) {
			c.addAttribute("khoa", khoa);
			c.addAttribute("phong", phong);
			c.addAttribute("id", id);
			return "layouts/admin/pages/khoa/chonphong";
		}
		c.addAttribute("thongbao", "Khoa này hiện tại chưa có phòng nào.");
		return danhsachvattukhoa(vattukhoa.getIdKhoa(), c);

	}

	@PostMapping(value = "/themvattukhoavaophong/{id}")
	public String themvattukhoavaophong(@ModelAttribute PhongEntity phongEntity, Model c,
			@PathVariable(value = "id") int id, HttpServletRequest request) {
		String phuongthuc = request.getParameter("xacnhan");
		if (phuongthuc.equals("Xác Nhận")) {
			if (request.getParameter("soluong") != "") {
				try {
					int soluong = Integer.parseInt(request.getParameter("soluong"));
					VatTu_KhoaEntity vattukhoa = vattu_khoaRepository.findOneById(id);
					if (soluong > vattukhoa.getSoDu() || soluong <= 0) {
						c.addAttribute("thongbao", "Số lượng vật tư muốn chuyển không hợp lệ.");
						return themvattuvaophong(c, id);
					} else {
						vattukhoa.setSoDu(vattukhoa.getSoDu() - soluong);
						vattu_khoaRepository.save(vattukhoa);
						PhongEntity phong1 = phongRepository.findOneByMaphong(phongEntity.getMaPhong());
						VatTu_PhongEntity vattuphong = vattu_phongRepository.findOneByIdPhongIdVatTu(phong1.getId(),
								vattukhoa.getIdVatTu());
						if (vattuphong != null) {
							vattuphong.setSoLuong(vattuphong.getSoLuong() + soluong);
							vattuphong.setSoVatTuDangHoatDongTot(vattuphong.getSoVatTuDangHoatDongTot() + soluong);
							vattu_phongRepository.save(vattuphong);
						} else {
							VatTu_PhongEntity vattuphong1 = new VatTu_PhongEntity();
							vattuphong1.setIdPhong(phong1.getId());
							vattuphong1.setIdVatTu(vattukhoa.getIdVatTu());
							vattuphong1.setSoLuong(soluong);
							vattuphong1.setSoVatTuDangHoatDongTot(soluong);
							vattu_phongRepository.save(vattuphong1);
						}
						List<VatTu_PhongEntity> aa = (List<VatTu_PhongEntity>) this.vattu_phongRepository
								.findAllById(phong1.getId());
						List<VatTuModel> list = new ArrayList<VatTuModel>();

						for (VatTu_PhongEntity s : aa) {
							VatTuModel an = new VatTuModel();
							VatTuEntity vattu3 = vattuRepository.findOneById(s.getIdVatTu());
							an.setId(s.getId());
							an.setMaVatTu(vattu3.getMaVatTu());
							an.setTenVatTu(vattu3.getTenVatTu());
							an.setNamSuDung(vattu3.getNamSuDung());
							an.setSoLuong(s.getSoLuong());
							an.setThongSoKiThuat(vattu3.getThongSoKiThuat());
							an.setTileCL(vattu3.getTileCl());
							an.setSoVatTuDangHoatDongTot(s.getSoVatTuDangHoatDongTot());
							list.add(an);
						}
						c.addAttribute("list", list);
						c.addAttribute("idphong", id);

						return "layouts/admin/pages/phong/hienthivattu_phong";
					}
				} catch (Exception e) {
					c.addAttribute("thongbao", "Vật Tư Muốn chuyển Nhập vào không hợp lệ.");
					return themvattuvaophong(c, id);
				}
			} else {
				c.addAttribute("thongbao", "Chưa nhập số vật tư muốn chuyển.");
				return themvattuvaophong(c, id);
			}
		}
		VatTu_KhoaEntity vattukhoa = vattu_khoaRepository.findOneById(id);
		return danhsachvattukhoa(vattukhoa.getIdKhoa(), c);

	}

	@GetMapping(value = "/thuhoivattukhoa/{id}")
	public String thuhoivattukhoa(@PathVariable(value = "id") int id, Model c) {
		VatTu_KhoaEntity vattukhoa = vattu_khoaRepository.findOneById(id);
//		  VatTu_PhongEntity vattuphong = vattu_phongRepository.findOneById(id); 
		VatTuEntity vattu = vattuRepository.findOneById(vattukhoa.getIdVatTu());
		VatTuKhoaModel hienthi = new VatTuKhoaModel();
		hienthi.setId(vattukhoa.getId());
		hienthi.setMaVatTu(vattu.getMaVatTu());
		hienthi.setTenVatTu(vattu.getTenVatTu());
		hienthi.setThongSoKiThuat(vattu.getThongSoKiThuat());
		hienthi.setNamSuDung(vattu.getNamSuDung());
		hienthi.setTileCL(vattu.getTileCl());
		hienthi.setSoLuong(vattukhoa.getSoLuong());
		hienthi.setSoDu(vattukhoa.getSoDu());
		hienthi.setSoVatTuHong(vattukhoa.getSoVatTuHong());
		c.addAttribute("vattu", hienthi);
		c.addAttribute("id", id);
		return "layouts/admin/pages/khoa/thuhoivattukhoa";
	}

	@PostMapping(value = "/thuhoivattukhoa/{id}")
	public String thuhoivattukhoa(@PathVariable(value = "id") int id, Model c, HttpServletRequest request) {
		String phuongthuc = request.getParameter("xacnhan");
		if (phuongthuc.equals("thuhoivattuhong")) {
			VatTu_KhoaEntity vattukhoa = vattu_khoaRepository.findOneById(id);
			int vattuhong = vattukhoa.getSoVatTuHong();
			vattukhoa.setSoluong(vattukhoa.getSoLuong() - vattuhong);
			vattukhoa.setSoVatTuHong(0);
			if (vattukhoa.getSoLuong() == 0) {
				vattu_khoaRepository.delete(vattukhoa);
			} else {
				vattu_khoaRepository.save(vattukhoa);
			}
			VatTuEntity vattu = vattuRepository.findOneById1(vattukhoa.getIdVatTu());
			vattu.setSoLuong(vattu.getSoLuong() - vattuhong);
			vattuRepository.save(vattu);
			VatTu_HongEntity vattuhong1 = vattu_hongRepository.findOneByIdVatTu(vattu.getId());
			if (vattuhong1 != null) {
				vattuhong1.setSoLuong(vattuhong1.getSoLuong() + vattuhong);
				vattu_hongRepository.save(vattuhong1);
			} else {
				VatTu_HongEntity vattuhong2 = new VatTu_HongEntity();
				vattuhong2.setIdVatTu(vattu.getId());
				vattuhong2.setSoLuong(vattuhong);
				vattu_hongRepository.save(vattuhong2);
			}

		}
		if (phuongthuc.equals("thuhoivattudu")) {
			VatTu_KhoaEntity vattukhoa = vattu_khoaRepository.findOneById(id);
			int sodu = vattukhoa.getSoDu();
			vattukhoa.setSoluong(vattukhoa.getSoLuong() - vattukhoa.getSoDu());
			vattukhoa.setSoDu(0);
			if (vattukhoa.getSoLuong() != 0) {
				vattu_khoaRepository.save(vattukhoa);
			} else
				vattu_khoaRepository.delete(vattukhoa);
			VatTuEntity vattu = vattuRepository.findOneById1(vattukhoa.getIdVatTu());
			vattu.setSoDu(vattu.getSoDu() + sodu);
			vattuRepository.save(vattu);
		}
		if (phuongthuc.equals("thuhoi")) {
			String sovattu = request.getParameter("sovatttuthuhoi");
			if (sovattu != "") {
				try {
					int sovattuthuhoi = Integer.parseInt(sovattu);
					VatTu_KhoaEntity vattukhoa = vattu_khoaRepository.findOneById(id);

					if (sovattuthuhoi <= vattukhoa.getSoLuong() && sovattuthuhoi >= 0) {

						int sovattuhong = vattukhoa.getSoVatTuHong();
						if (sovattuthuhoi <= sovattuhong) {
							vattukhoa.setSoluong(vattukhoa.getSoLuong() - sovattuthuhoi);
							vattukhoa.setSoVatTuHong(sovattuhong - sovattuthuhoi);
							if (vattukhoa.getSoLuong() != 0)
								vattu_khoaRepository.save(vattukhoa);
							else
								vattu_khoaRepository.delete(vattukhoa);
							VatTuEntity vattu = vattuRepository.findOneById1(vattukhoa.getIdVatTu());
							vattu.setSoLuong(vattu.getSoLuong() - sovattuthuhoi);
							vattuRepository.save(vattu);
						} else {
							if (sovattuthuhoi <= vattukhoa.getSoDu() + sovattuhong) {
								vattukhoa.setSoluong(vattukhoa.getSoLuong() - sovattuthuhoi);
								vattukhoa.setSoDu(vattukhoa.getSoDu() - (sovattuthuhoi - sovattuhong));
								vattukhoa.setSoVatTuHong(0);
								if (vattukhoa.getSoLuong() != 0) {
									vattu_khoaRepository.save(vattukhoa);
								} else
									vattu_khoaRepository.delete(vattukhoa);
								VatTuEntity vattu = vattuRepository.findOneById1(vattukhoa.getIdVatTu());
								vattu.setSoLuong(vattu.getSoLuong() - sovattuhong);
								vattu.setSoDu(vattu.getSoDu() + (sovattuthuhoi - sovattuhong));
								vattuRepository.save(vattu);
							} else {
								c.addAttribute("thongbao",
										"Số Lượng vật tư thu hồi không đủ vì đang được sử dụng ở trong các phòng.");
								return thuhoivattukhoa(id, c);
							}
						}
						return danhsachvattukhoa(vattukhoa.getIdKhoa(), c);
					} else {
						c.addAttribute("thongbao", "Số vật tư muốn thu hồi nhập vào không hợp lệ");
						return thuhoivattukhoa(id, c);
					}
				} catch (Exception e) {
					c.addAttribute("thongbao", "Số vật tư muốn thu hồi nhập vào không hợp lệ");
					return thuhoivattukhoa(id, c);
				}
			} else {
				c.addAttribute("thongbao", "Chưa nhập số vật tư muốn thu hồi");
				return thuhoivattukhoa(id, c);
			}
		}
		VatTu_KhoaEntity vattukhoa = vattu_khoaRepository.findOneById(id);
		return danhsachvattukhoa(vattukhoa.getIdKhoa(), c);
	}

}
