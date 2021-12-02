package com.example.demo.Controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.configs.models.structures.AccountInfo;
import com.example.demo.entities.AccountEntity;
import com.example.demo.entities.AccountRoleEntity;
import com.example.demo.entities.RoleEntity;
import com.example.demo.repositories.AccountRepository;
import com.example.demo.repositories.AccountRoleRepository;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.utils.EncrytedPasswordUtils;
@Controller
public class DashboardController {
	private AccountInfo accountInfo;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	AccountRoleRepository accountRoleRepository;
    @GetMapping(value = "/dashboard")
    public String dashboard(HttpSession session) {
    	session.setAttribute("username","");
        return "layouts/admin/pages/dashboard";
    }
    @GetMapping(value ="/quanlyuser" )
    @PreAuthorize("hasAuthority('Administrator') or hasAuthority('Personal')" )
    public String quanlyuser(Model c) {
	List<AccountEntity> aa = (List<AccountEntity>) this.accountRepository.findAll();
	c.addAttribute("long", aa);
       	return "layouts/admin/pages/quanlyuser";
    }
    @GetMapping(value = "/themaccount" )
    @PreAuthorize(value="hasAuthority('Administrator')")
    	public String them(Model c) {
    				List<RoleEntity> role = (List<RoleEntity>) roleRepository.findAll();
    				c.addAttribute("role", role);
    				return "layouts/admin/pages/themaccount";
    }
    @PostMapping(value = "/themaccount")
    @PreAuthorize(value="hasAuthority('Administrator')")
    public String tt (@ModelAttribute AccountInfo accountInfo, ModelMap map,Model c, HttpServletRequest request) {
    	String phuongthuc = request.getParameter("xacnhan");
    	if(phuongthuc.equals("Tạo mới")) {
	    	this.accountInfo=accountInfo;
	    	System.out.println(accountInfo.getEmail());
	    	 AccountEntity kiemtra = accountRepository.findOneByEmail(accountInfo.getEmail());
			 if (kiemtra != null) {
				 List<RoleEntity> role = (List<RoleEntity>) roleRepository.findAll();
					c.addAttribute("role", role);
					 c.addAttribute("thongbao","Email đã tồn tại! Mời Nhập lại email");
					return "layouts/admin/pages/themaccount";
		        }
			 else {  
				 if(accountInfo.getEncrytedPassword()=="") {
					 List<RoleEntity> role = (List<RoleEntity>) roleRepository.findAll();
						c.addAttribute("role", role);
						 c.addAttribute("thongbao1","Nhập Mật Khẩu!");
						return "layouts/admin/pages/themaccount";
				 }
				 System.out.println("llllolololol"+accountInfo.getEncrytedPassword());
				 map.addAttribute("email",accountInfo.getEmail());
				 map.addAttribute("fullName",accountInfo.getFullName());			 
				 map.addAttribute("role",accountInfo.getRoleName());		
				 
				 return "layouts/admin/pages/xacnhanthemaccount";
			 }
		 }
    	else return quanlyuser(c);
		
    }
    @GetMapping(value = "/xacnhan")
    @PreAuthorize(value="hasAuthority('Administrator')")
	public String xacnhanlai(@RequestParam("nhapEmail") String email,
		    @RequestParam("nhapName") String fullName, @RequestParam("nhapRole") String role, Model model) {
		model.addAttribute("email", email);
		model.addAttribute("fullNama", fullName);
		model.addAttribute("role", role);
		return "layouts/admin/pages/xacnhanthemaccount";
	}
    private AccountEntity accountEntity;
    private RoleEntity roleEntity;
    private AccountRoleEntity accountroleEntity;
    @GetMapping (value = "/xemchitiet/{id}")
	@PreAuthorize(value="hasAuthority('Administrator')")
	public String xemchitiet(@PathVariable(value="id") int id,ModelMap model) {
    	accountEntity = (AccountEntity) accountRepository.findOneById(id);
		accountroleEntity = (AccountRoleEntity)accountRoleRepository.findOneById(id);
		roleEntity = (RoleEntity) roleRepository.findOneById(accountroleEntity.getRoleId());
		AccountInfo ac = new AccountInfo();
		ac.setEmail(accountEntity.getEmail());
		ac.setFullName(accountEntity.getFullName());
		ac.setRoleName(roleEntity.getRoleName());
		model.addAttribute("dulieu", ac);
		return "layouts/admin/pages/xemchitiet";
	}
    
    @PostMapping (value = "/xemchitiet")
	@PreAuthorize(value="hasAuthority('Administrator')")
	public String xemchitiet(@ModelAttribute AccountInfo accountInfo,Model model,HttpServletRequest request) {
    	String phuongthuc = request.getParameter("phuongthuc");
    	System.out.print(phuongthuc);
    	if(phuongthuc.equals("Cancel"))
    		return quanlyuser(model);
    	else if(phuongthuc.equals("Update")) {
    		String email = request.getParameter("email");
    		String hoten = request.getParameter("hoten");
    		String tenquyen = request.getParameter("quyen");
    		accountEntity.setEmail(email);
    		accountEntity.setFullName(hoten);
    		accountRepository.save(accountEntity);
    		int id = roleRepository.timid(tenquyen);
    		accountroleEntity.setRoleId(id);
    		accountRoleRepository.save(accountroleEntity);
    		return quanlyuser(model);
    		}
    	else
    		accountRoleRepository.delete(accountroleEntity);
    		accountRepository.delete(accountEntity);
    		return quanlyuser(model);
    	
	}
    @PostMapping(value = "/xacnhan")
    @PreAuthorize(value="hasAuthority('Administrator')")
   	public String xn (Model c) {
    		AccountEntity hihi = new AccountEntity();
    		hihi.setEmail(accountInfo.getEmail());
    		hihi.setEncrytedPassword(EncrytedPasswordUtils.encrytedPassword(accountInfo.getEncrytedPassword()));
    		System.out.print(hihi.getEncrytedPassword());
    		hihi.setFullName( accountInfo.getFullName());
   			//accountRepository.save( new AccountEntity(accountInfo.getEmail(), EncrytedPasswordUtils.encrytedPassword(accountInfo.getEncrytedPassword()), accountInfo.getFullName()));
    		accountRepository.save(hihi);
    		RoleEntity roleEntity = roleRepository.findOneByName(accountInfo.getRoleName());
   	        AccountEntity accountEntity = accountRepository.findOneByEmail1(accountInfo.getEmail());
   	        AccountRoleEntity hoho= new AccountRoleEntity();
   	        hoho.setAccountId(accountEntity.getId());
   	        hoho.setRoleId(roleEntity.getId());
   	        //accountRoleRepository.save( new AccountRoleEntity(accountEntity.getId(), roleEntity.getId()));
   	        accountRoleRepository.save(hoho);
   	     List<AccountEntity> aa = (List<AccountEntity>) this.accountRepository.findAll();
   	     c.addAttribute("long", aa);
   		return "layouts/admin/pages/quanlyuser";
   	}
    
}
