/**
 * 
 */
package com.ym.iadpush.manage.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ym.iadpush.manage.entity.Proprietor;

/**
 * @Author: lixingbiao 2017-6-11 下午4:37:32
 * @Version: $Id$
 * @Desc: <p>
 * </p>
 */
public interface ProprietorMapper {
    //删除业主根据参数
    Integer deleteProprietorByParams(Map<String, Object> paramMap);
    //修改物业
    Integer changeDepartment(Map<String, Object> paramMap);
    
    int getProprietorsCount(Map<String, Object> paramMap);

    Integer insertProprietor(Proprietor proprietor);

    List<Proprietor> getProprietors(Map<String, Object> paramMap);

    Proprietor getProprietorById(Integer id);

    Proprietor getProprietorByOut_room_id(String out_room_id);

    Integer saveProprietor(@Param("proprietor") Proprietor proprietor, @Param("id") int id);
    
    int deleteProprietorById(Integer id);

}
