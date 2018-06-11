package com.ruizton.main.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ruizton.main.dao.comm.HibernateDaoSupport;
import com.ruizton.main.model.Fsubscriptionlog;

/**
 * A data access object (DAO) providing persistence and search support for
 * Fsubscriptionlog entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see ztmp.Fsubscriptionlog
 * @author MyEclipse Persistence Tools
 */

@Repository
public class FsubscriptionlogDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(FsubscriptionlogDAO.class);
	// property constants
	public static final String FCOUNT = "fcount";
	public static final String FPRICE = "fprice";
	public static final String FTOTAL_COST = "ftotalCost";

	public void save(Fsubscriptionlog transientInstance) {
		log.debug("saving Fsubscriptionlog instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Fsubscriptionlog persistentInstance) {
		log.debug("deleting Fsubscriptionlog instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Fsubscriptionlog findById(java.lang.Integer id) {
		log.debug("getting Fsubscriptionlog instance with id: " + id);
		try {
			Fsubscriptionlog instance = (Fsubscriptionlog) getSession().get(
					"com.ruizton.main.model.Fsubscriptionlog", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Fsubscriptionlog> findByExample(Fsubscriptionlog instance) {
		log.debug("finding Fsubscriptionlog instance by example");
		try {
			List<Fsubscriptionlog> results = (List<Fsubscriptionlog>) getSession()
					.createCriteria("com.ruizton.main.model.Fsubscriptionlog")
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
		log.debug("finding Fsubscriptionlog instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Fsubscriptionlog as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Fsubscriptionlog> findByFcount(Object fcount) {
		return findByProperty(FCOUNT, fcount);
	}

	public List<Fsubscriptionlog> findByFprice(Object fprice) {
		return findByProperty(FPRICE, fprice);
	}

	public List<Fsubscriptionlog> findByFtotalCost(Object ftotalCost) {
		return findByProperty(FTOTAL_COST, ftotalCost);
	}

	public List findAll() {
		log.debug("finding all Fsubscriptionlog instances");
		try {
			String queryString = "from Fsubscriptionlog";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Fsubscriptionlog merge(Fsubscriptionlog detachedInstance) {
		log.debug("merging Fsubscriptionlog instance");
		try {
			Fsubscriptionlog result = (Fsubscriptionlog) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Fsubscriptionlog instance) {
		log.debug("attaching dirty Fsubscriptionlog instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Fsubscriptionlog instance) {
		log.debug("attaching clean Fsubscriptionlog instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List<Fsubscriptionlog> list(int firstResult, int maxResults, String filter,boolean isFY) {
		List<Fsubscriptionlog> list = null;
		log.debug("finding Fsubscriptionlog instance with filter");
		try {
			String queryString = "from Fsubscriptionlog "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by Fsubscriptionlog name failed", re);
			throw re;
		}
		return list;
	}
}