package com.ruizton.main.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ruizton.main.dao.comm.HibernateDaoSupport;
import com.ruizton.main.model.Fabout;

/**
 * A data access object (DAO) providing persistence and search support for
 * Fabout entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see ztmp.Fabout
 * @author MyEclipse Persistence Tools
 */

@Repository
public class FaboutDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(FaboutDAO.class);
	// property constants
	public static final String FTITLE = "ftitle";
	public static final String FCONTENT = "fcontent";

	public void save(Fabout transientInstance) {
		log.debug("saving Fabout instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Fabout persistentInstance) {
		log.debug("deleting Fabout instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Fabout findById(java.lang.Integer id) {
		log.debug("getting Fabout instance with id: " + id);
		try {
			Fabout instance = (Fabout) getSession().get("com.ruizton.main.model.Fabout", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Fabout> findByExample(Fabout instance) {
		log.debug("finding Fabout instance by example");
		try {
			List<Fabout> results = (List<Fabout>) getSession()
					.createCriteria("com.ruizton.main.model.Fabout").add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Fabout instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Fabout as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Fabout> findByFtitle(Object ftitle) {
		return findByProperty(FTITLE, ftitle);
	}

	public List<Fabout> findByFcontent(Object fcontent) {
		return findByProperty(FCONTENT, fcontent);
	}

	public List findAll() {
		log.debug("finding all Fabout instances");
		try {
			String queryString = "from Fabout";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Fabout merge(Fabout detachedInstance) {
		log.debug("merging Fabout instance");
		try {
			Fabout result = (Fabout) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Fabout instance) {
		log.debug("attaching dirty Fabout instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Fabout instance) {
		log.debug("attaching clean Fabout instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List<Fabout> list(int firstResult, int maxResults, String filter,boolean isFY) {
		List<Fabout> list = null;
		log.debug("finding Fabout instance with filter");
		try {
			String queryString = "from Fabout "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by Fabout name failed", re);
			throw re;
		}
		return list;
	}
	
}