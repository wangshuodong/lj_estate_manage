package com.ym.iadpush.manage.mapper;

import java.util.List;
import java.util.Map;

import com.ym.iadpush.manage.entity.News;

public interface NewsMapper {

	Integer getAllCountNews(Map<String, Object> paramMap);

	List<News> getAllNews(Map<String, Object> paramMap);
	
	Integer updateNews(Map<String, Object> paramMap);

	Integer insertNews(Map<String, Object> paramMap);

	News selectNewsById(Map<String, Object> paramMap);

	Integer deleteNews(Map<String, Object> paramMap);

	Integer insertNotice(Map<String, Object> paramMap);
	
	News getMaxIdOrCount(Map<String,Object> paramMap);
	
	Integer getNoticeId(Map<String,Object> paramMap);
	
	News getNewestNotice(Map<String, Object> paramMap);

	Integer getNextMaxId(Map<String, Object> param);

	Integer deleteNotice(Map<String, Object> paramMap);
}
