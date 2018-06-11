package com.ruizton.main.service.front;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruizton.main.dao.FvirtualwalletDAO;
import com.ruizton.main.model.Fvirtualwallet;

@Service
public class FvirtualWalletService {
	private static final Logger log = LoggerFactory.getLogger(FvirtualwalletDAO.class);
	@Autowired
	private FvirtualwalletDAO fvirtualwalletDAO ;
	
	public Fvirtualwallet findFvirtualwallet(int fuser,int vid){
		List<Fvirtualwallet> fvirtualwallets = this.fvirtualwalletDAO.findByParam(0, 0, " where fuser.fid=? and fvirtualcointype.fid=? ", false,Fvirtualwallet.class,fuser,vid) ;
		if(fvirtualwallets.size()==1){
			return fvirtualwallets.get(0) ;
		}else{
			log.error("Fvirtualwallet fuser="+fuser+",vid="+vid+" exists "+fvirtualwallets.size());
			return null ;
		}
		
	}
}
