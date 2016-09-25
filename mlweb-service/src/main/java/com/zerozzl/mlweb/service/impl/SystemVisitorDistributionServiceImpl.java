package com.zerozzl.mlweb.service.impl;

import java.util.Date;
import java.util.List;

import com.zerozzl.mlweb.dao.SystemVisitorDistributionDao;
import com.zerozzl.mlweb.domain.MLSystemVisitorDistribution;
import com.zerozzl.mlweb.persistent.SystemVisitorDistribution;
import com.zerozzl.mlweb.service.SystemVisitorDistributionService;

public class SystemVisitorDistributionServiceImpl implements SystemVisitorDistributionService {

	private SystemVisitorDistributionDao systemVisitorDistributionDao;

	public void setSystemVisitorDistributionDao(SystemVisitorDistributionDao systemVisitorDistributionDao) {
		this.systemVisitorDistributionDao = systemVisitorDistributionDao;
	}

	@Override
	public void updateSystemVisitorDistribution(Date date, MLSystemVisitorDistribution distribution) {
		if(distribution != null) {
			List<SystemVisitorDistribution> list = distribution.convertToDBModel(date);
			for(SystemVisitorDistribution newItem : list) {
				SystemVisitorDistribution oldItem = this.get(
						newItem.getDate(), newItem.getCountry(), newItem.getProvince(), newItem.getCity());
				if(oldItem != null) {
					if(oldItem.getQuantity() != newItem.getQuantity()) {
						oldItem.setQuantity(newItem.getQuantity());
						systemVisitorDistributionDao.update(oldItem);
					}
				} else {
					systemVisitorDistributionDao.save(newItem);
				}
			}
		}
	}
	
	private SystemVisitorDistribution get(Date date, String country, String province, String city) {
		SystemVisitorDistribution item = null;
		List<SystemVisitorDistribution> list = systemVisitorDistributionDao.findByCoordinate(date, country, province, city);
		if(list != null && !list.isEmpty()) {
			item = list.get(0);
			if(list.size() > 1) {
				for(int i = 1; i < list.size(); i++) {
					SystemVisitorDistribution o = list.get(i);
					item.setQuantity(item.getQuantity() + o.getQuantity());
					systemVisitorDistributionDao.delete(o);
				}
			}
		}
		return item;
	}

	@Override
	public MLSystemVisitorDistribution findByDate(Date begin, Date end) {
		List<SystemVisitorDistribution> list = systemVisitorDistributionDao.findByDate(begin, end);
		return null;
	}
	
}
