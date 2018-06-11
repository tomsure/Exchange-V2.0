package com.broctagon.exchangeadmin.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.broctagon.exchangeadmin.constant.StatusCode;
import com.broctagon.exchangeadmin.dao.AdminRepository;
import com.broctagon.exchangeadmin.dao.RoleRepository;
import com.broctagon.exchangeadmin.message.BaseResponse;
import com.broctagon.exchangeadmin.message.EnableRequest;
import com.broctagon.exchangeadmin.message.listrequest.AdminListRequest;
import com.broctagon.exchangeadmin.message.user.ForgetPasswordRequest;
import com.broctagon.exchangeadmin.message.user.LoginRequest;
import com.broctagon.exchangeadmin.message.user.LoginResponse;
import com.broctagon.exchangeadmin.message.user.LogoutRequest;
import com.broctagon.exchangeadmin.message.user.ResetPasswordAfterLoginRequest;
import com.broctagon.exchangeadmin.message.user.ResetPasswordRequest;
import com.broctagon.exchangeadmin.model.Admin;
import com.broctagon.exchangeadmin.model.Role;
import com.broctagon.exchangeadmin.util.DateUtil;
import com.broctagon.exchangeadmin.vo.AdminVo;
import com.google.common.collect.Lists;

@Service
@Transactional
public class AdminService {

	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	public Admin findByUserName(String userName) {
		return adminRepository.findByUserName(userName);
	}
	
	public LoginResponse login(LoginRequest loginRequest) {
		LoginResponse loginResponse = new LoginResponse();
		Admin admin = adminRepository.findByUserName(loginRequest.getEmail());
		if(admin == null){
			loginResponse.setStatus(StatusCode.EMAIL_ERROR);
		}else {
			if (admin.getPassword().equals(loginRequest.getPassword())) {
				loginResponse.setStatus(StatusCode.SUCCESS);
				loginResponse.setUserId(admin.getId());
			}else {
				loginResponse.setStatus(StatusCode.LOGIN_PASSWORD_ERROR);
			}
		}
		return loginResponse;
	}

	public BaseResponse forgetPwd(ForgetPasswordRequest forgetPasswordRequest) {
		BaseResponse forgetPwdResponse = new BaseResponse();
		int id = adminRepository.findIdByUserName(forgetPasswordRequest.getEmail());
		if(id == 0) {
			forgetPwdResponse.setStatus(StatusCode.EMAIL_ERROR);
		}else {
			// TODO send an email to user with reset link
			forgetPwdResponse.setStatus(StatusCode.SUCCESS);
		}
		return forgetPwdResponse;
	}

	public BaseResponse resetPwd(ResetPasswordRequest resetPasswordRequest) {
		BaseResponse resetPwdResponse = new BaseResponse();
		adminRepository.updateLoginPasswordById(resetPasswordRequest.getId(), resetPasswordRequest.getPassword());
		resetPwdResponse.setStatus(StatusCode.SUCCESS);
		return resetPwdResponse;
	}

	public BaseResponse resetPwdAfterLogin(ResetPasswordAfterLoginRequest resetPasswordRequest) {
		BaseResponse resetPwdReponse = new BaseResponse();
		String loginPassword = adminRepository.findLoginPasswordById(resetPasswordRequest.getId());
		if(loginPassword.equals(resetPasswordRequest.getOldPassword())){
			adminRepository.updateLoginPasswordById(resetPasswordRequest.getId(), resetPasswordRequest.getNewPassword());
			resetPwdReponse.setStatus(StatusCode.SUCCESS);
		}else {
			resetPwdReponse.setStatus(StatusCode.OLD_PASSWORD_ERROR);
		}
		return resetPwdReponse;
	}

	public BaseResponse logout(LogoutRequest logoutRequest) {
		BaseResponse logoutResponse = new BaseResponse();
		logoutResponse.setStatus(StatusCode.SUCCESS);
		return logoutResponse;
	}
	
	@SuppressWarnings("serial")
	public List<AdminVo> list(AdminListRequest listRequest) {
		List<AdminVo> adminVos = adminRepository.findAll(new Specification<AdminVo>() {
			
			@Override
			public Predicate toPredicate(Root<AdminVo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if(listRequest.getRoleId() != 0) {
					predicates.add(criteriaBuilder.equal(root.get("roleId"), listRequest.getRoleId()));
				}
				if(listRequest.getStatus() != -1) {
					predicates.add(criteriaBuilder.equal(root.get("status"), listRequest.getStatus()));
				}
				if(!StringUtils.isEmpty(listRequest.getUserName())) {
					predicates.add(criteriaBuilder.like(root.get("userName"), listRequest.getUserName() + "%"));
				}
				query.where(predicates.toArray(new Predicate[0]));
				return null;
			}
		});
		return adminVos == null ? new ArrayList<>() : adminVos;
	}

	public BaseResponse enable(EnableRequest enableRequest) {
		BaseResponse enableResponse = new BaseResponse();
		adminRepository.enable(enableRequest.getIds(), enableRequest.getStatus());
		enableResponse.setStatus(StatusCode.SUCCESS);
		return enableResponse;
	}

	public BaseResponse save(Admin saveRequest) {
		BaseResponse saveResponse = new BaseResponse();
		saveRequest.setLastLoginTime(DateUtil.dateTimeNow());
		if(saveRequest.getId() == 0) {
			saveRequest.setCreateTime(DateUtil.dateTimeNow());
		}
		adminRepository.save(saveRequest);
		saveResponse.setStatus(StatusCode.SUCCESS);
		return saveResponse;
	}

	public BaseResponse delete(List<Integer> ids) {
		BaseResponse deleteResponse = new BaseResponse();
		adminRepository.delete(ids);
		deleteResponse.setStatus(StatusCode.SUCCESS);
		return deleteResponse;
	}

	public List<Role> roles() {
		return Lists.newArrayList(roleRepository.findAll());
	}

}
