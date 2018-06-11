package com.ruizton.main.dao;

import static org.hibernate.criterion.Example.create;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ruizton.main.Enum.CoinTypeEnum;
import com.ruizton.main.dao.comm.HibernateDaoSupport;
import com.ruizton.main.model.Fvirtualwallet;

/**
 * A data access object (DAO) providing persistence and search support for
 * Fvirtualwallet entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.ruizton.main.com.ruizton.main.model.Fvirtualwallet
 * @author MyEclipse Persistence Tools
 */
@Repository
public class FvirtualwalletDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(FvirtualwalletDAO.class);
	// property constants
	public static final String FTOTAL = "ftotal";
	public static final String FFROZEN = "ffrozen";

	public void save(Fvirtualwallet transientInstance) {
		log.debug("saving Fvirtualwallet instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Fvirtualwallet persistentInstance) {
		log.debug("deleting Fvirtualwallet instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Fvirtualwallet findById(java.lang.Integer id) {
		log.debug("getting Fvirtualwallet instance with id: " + id);
		try {
			Fvirtualwallet instance = (Fvirtualwallet) getSession().get(
					"com.ruizton.main.model.Fvirtualwallet", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Fvirtualwallet> findByExample(Fvirtualwallet instance) {
		log.debug("finding Fvirtualwallet instance by example");
		try {
			List<Fvirtualwallet> results = (List<Fvirtualwallet>) getSession()
					.createCriteria("com.ruizton.main.model.Fvirtualwallet")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Fvirtualwallet instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Fvirtualwallet as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	public List findByTwoProperty(String propertyName1, Object value1,String propertyName2, Object value2) {
		log.debug("finding Fvirtualwallet instance with property");
		try {
			String queryString = "from Fvirtualwallet as model where model."
					+ propertyName1 + "= ? and model."+propertyName2+"= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value1);
			queryObject.setParameter(1, value2);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Fvirtualwallet> findByFtotal(Object ftotal) {
		return findByProperty(FTOTAL, ftotal);
	}

	public List<Fvirtualwallet> findByFfrozen(Object ffrozen) {
		return findByProperty(FFROZEN, ffrozen);
	}

	public List findAll() {
		log.debug("finding all Fvirtualwallet instances");
		try {
			String queryString = "from Fvirtualwallet";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Fvirtualwallet merge(Fvirtualwallet detachedInstance) {
		log.debug("merging Fvirtualwallet instance");
		try {
			Fvirtualwallet result = (Fvirtualwallet) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Fvirtualwallet instance) {
		log.debug("attaching dirty Fvirtualwallet instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Fvirtualwallet instance) {
		log.debug("attaching clean Fvirtualwallet instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List<Fvirtualwallet> find(int fuid,int status,int cointype){
		log.debug("finding all Fvirtualwallet instances");
		try {
			String queryString = "from Fvirtualwallet where fuser.fid=? and fvirtualcointype.fstatus=? and fvirtualcointype.ftype <>?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, fuid) ;
			queryObject.setParameter(1, status) ;
			queryObject.setParameter(2, cointype) ;
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public Fvirtualwallet findVirtualWallet(int fuid,int fcoinId){
		log.debug("finding all Fvirtualwallet instances");
		try {
			String queryString = "from Fvirtualwallet where fuser.fid=? and fvirtualcointype.fid=?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, fuid) ;
			queryObject.setParameter(1, fcoinId) ;
			List<Fvirtualwallet> list = queryObject.list();
			if(list.size()==1){
				return list.get(0) ;
			}else{
				log.error("Fvirtualwallet count:"+list.size()) ;
				return null ;
			}
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	//根据UID找会员人民币钱包
	public Fvirtualwallet findWallet(int fuid){
		log.debug("finding all Fvirtualwallet instances");
		try {
			String queryString = "from Fvirtualwallet where fuser.fid=? and fvirtualcointype.ftype=?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, fuid) ;
			queryObject.setParameter(1, CoinTypeEnum.FB_CNY_VALUE) ;
			List<Fvirtualwallet> list = queryObject.list();
			if(list.size()==1){
				return list.get(0) ;
			}else{
				log.error("Fvirtualwallet count:"+list.size()) ;
				return null ;
			}
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public List<Fvirtualwallet> list(int firstResult, int maxResults, String filter,boolean isFY) {
		List<Fvirtualwallet> list = null;
		log.debug("finding Fvirtualwallet instance with filter");
		try {
			String queryString = "from Fvirtualwallet "+filter;
			Query queryObject = getSession().createQuery(queryString);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by filter name failed", re);
			throw re;
		}
		return list;
	}
	
	public List<Map> getTotalQty() {
		List<Map> all = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT sum(ifnull(a.ftotal,0)+ifnull(a.fFrozen,0)) totalQty, \n");
		sql.append("sum(ifnull(a.fFrozen,0)) frozenQty,b.fName \n");
		sql.append("FROM fvirtualwallet a left outer join fvirtualcointype b \n");
		sql.append("on a.fVi_fId = b.fId group by b.fName \n");
		SQLQuery queryObject = getSession().createSQLQuery(sql.toString());
		List allList = queryObject.list();
		Iterator it = allList.iterator();
		while(it.hasNext()) {
			Map map = new HashMap();
			Object[] o = (Object[]) it.next();
			map.put("totalQty", o[0]);
			map.put("frozenQty", o[1]);
			map.put("fName", o[2]);
			all.add(map);
		}
		return all;
	}

	public BigDecimal getTotalQty(int vid) {
		List<Map> all = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT sum(ifnull(ftotal,0)+ifnull(fFrozen,0)) totalQty \n");
		sql.append("FROM fvirtualwallet \n");
		sql.append("where fVi_fId = "+vid+" \n");
		SQLQuery queryObject = getSession().createSQLQuery(sql.toString());
		List allList = queryObject.list();
		if(allList == null || allList.size() ==0 || allList.get(0) == null){
			return BigDecimal.ZERO;
		}
		Iterator it = allList.iterator();
		return new BigDecimal(it.next().toString());
	}
	
	public void updateCanSellQty(double rate,int fid) {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" update fvirtualwallet set fcanSellQty=ftotal*"+rate+" where fVi_fId="+fid+" ");
			SQLQuery queryObject = getSession().createSQLQuery(sql.toString());
			queryObject.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}