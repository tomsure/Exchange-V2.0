package com.ruizton.main.service.front;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruizton.main.dao.FsystemargsDAO;
import com.ruizton.main.dao.FwebbaseinfoDAO;
import com.ruizton.main.model.Fsystemargs;
import com.ruizton.main.model.Fwebbaseinfo;

@Service
public class FrontSystemArgsService {

	@Autowired
	private FsystemargsDAO fsystemargsDAO ;
	@Autowired
	private FwebbaseinfoDAO fwebbaseinfoDAO ;
	
	public String getSystemArgs(String key){
		String value = null ;
		List<Fsystemargs> list = this.fsystemargsDAO.findByFkey(key) ;
		if(list.size()>0){
			value = list.get(0).getFvalue() ;
		}
		return value ;
	}
	
	public Map<String, String> findAllMap(){
		Map<String, String> map = new HashMap<String, String>() ;
		List<Fsystemargs> list = this.fsystemargsDAO.findAll() ;
		for (Fsystemargs fsystemargs : list) {
			map.put(fsystemargs.getFkey(), fsystemargs.getFvalue()) ;
		}
		return map ;
	}
	
	public Fwebbaseinfo findFwebbaseinfoById(int id){
		return this.fwebbaseinfoDAO.findById(id) ;
	}
}
