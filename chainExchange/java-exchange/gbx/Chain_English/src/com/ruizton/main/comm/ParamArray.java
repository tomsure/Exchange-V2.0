package com.ruizton.main.comm;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import com.ruizton.main.model.Fadmin;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.model.Fwebbaseinfo;

public class ParamArray  implements Serializable{
	private Fadmin fadmin;
	private MultipartFile filedata;
	private Fwebbaseinfo fwebbaseinfo;
	private Fuser fuser ;

	public Fwebbaseinfo getFwebbaseinfo() {
		return fwebbaseinfo;
	}

	public void setFwebbaseinfo(Fwebbaseinfo fwebbaseinfo) {
		this.fwebbaseinfo = fwebbaseinfo;
	}

	public Fadmin getFadmin() {
		return fadmin;
	}

	public void setFadmin(Fadmin fadmin) {
		this.fadmin = fadmin;
	}

	public MultipartFile getFiledata() {
		return filedata;
	}

	public void setFiledata(MultipartFile filedata) {
		this.filedata = filedata;
	}

	public Fuser getFuser() {
		return fuser;
	}

	public void setFuser(Fuser fuser) {
		this.fuser = fuser;
	}

}
