package com.ym.iadpush.manage.services.news.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ym.iadpush.manage.entity.News;
import com.ym.iadpush.manage.mapper.NewsMapper;
import com.ym.iadpush.manage.services.news.INewsService;

@Service
public class NewsServiceImpl implements INewsService {

    @Autowired
    private NewsMapper newsMapper;

    @Override
    public List<News> getAllNews(Map<String, Object> paramMap) {
        return newsMapper.getAllNews(paramMap);
    }

    @Override
    public Integer getAllCountNews(Map<String, Object> paramMap) {
        return newsMapper.getAllCountNews(paramMap);
    }

    @Override
    public Integer updateNews(Map<String, Object> paramMap) {
        return newsMapper.updateNews(paramMap);
    }

    @Override
    public Integer insertNews(Map<String, Object> paramMap) {
        return newsMapper.insertNews(paramMap);
    }

    @Override
    public News selectNewsById(Map<String, Object> paramMap) {
        return newsMapper.selectNewsById(paramMap);
    }

    @Override
    public Integer deleteNews(Map<String, Object> paramMap) {
        return newsMapper.deleteNews(paramMap);
    }

    @Override
    public News getNewestNotice(Map<String, Object> paramMap) {
        return newsMapper.getNewestNotice(paramMap);
    }

    @Override
    public Integer insertNotice(Map<String, Object> paramMap) {
        // 查询记录是否存在
        paramMap.put("isInsert", "isInsert");
        if (newsMapper.getNoticeId(paramMap) != null) {
            newsMapper.deleteNotice(paramMap);
        }

        return newsMapper.insertNotice(paramMap);
    }

    @Override
    public Integer getNoticeId(Map<String, Object> param) {
        return newsMapper.getNoticeId(param);
    }

    @Override
    public News getMaxIdOrCount(Map<String, Object> param) {
        return newsMapper.getMaxIdOrCount(param);
    }

    @Override
    public Integer getNextMaxId(Map<String, Object> param) {
        return newsMapper.getNextMaxId(param);
    }

}
