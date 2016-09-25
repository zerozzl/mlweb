package com.zerozzl.mlweb.service;

import java.util.Date;
import java.util.List;

import com.zerozzl.mlweb.domain.MLSystemVisitsRecord;

public interface SystemVisitsRecordService {

	/**
	 * 更新当天系统访问量统计
	 */
	void updateDailyVisitsCount(int uniqueVisitorCount,
			int visitorOpinionCount, int pedestrianDetectionCount,
			int faceDetectionCount, int semanticSegmentationCount);
	
	/**
	 * 获取当前访问情况
	 */
	MLSystemVisitsRecord getCurrentVisitsCount();
	
	/**
	 * 根据时间范围查找访问情况
	 */
	List<MLSystemVisitsRecord> findByDate(Date begin, Date end);
	
}
