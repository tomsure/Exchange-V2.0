package com.ruizton.main.service.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruizton.main.Enum.SystemBankInfoEnum;
import com.ruizton.main.dao.SystembankinfoDAO;
import com.ruizton.main.model.Systembankinfo;

@Service
public class FrontBankInfoService {
	@Autowired
	private SystembankinfoDAO systembankinfoDAO ;
	
	public List<Systembankinfo> findAllSystemBankInfo() throws Exception{
		return this.systembankinfoDAO.findByProperty("fstatus", SystemBankInfoEnum.NORMAL_VALUE) ;
	}
	
	public Systembankinfo findSystembankinfoById(int id) throws Exception{
		return this.systembankinfoDAO.findById(id) ;
	}
}
