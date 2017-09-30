/**
 * 
 */
package com.ym.iadpush.manage.services.news;

import java.util.List;
import java.util.Map;

import com.ym.iadpush.manage.entity.News;


public interface INewsService {

	List<News> getAllNews(Map<String, Object> paramMap);

	Integer getAllCountNews(Map<String, Object> paramMap);

	Integer updateNews(Map<String, Object> paramMap);

	Integer insertNews(Map<String, Object> paramMap);

	News selectNewsById(Map<String, Object> paramMap);

	Integer deleteNews(Map<String, Object> paramMap);

	News getNewestNotice(Map<String, Object> param);

	Integer insertNotice(Map<String, Object> paramMap);

	Integer getNoticeId(Map<String, Object> param);

	News getMaxIdOrCount(Map<String, Object> param);

	Integer getNextMaxId(Map<String, Object> param);


}
