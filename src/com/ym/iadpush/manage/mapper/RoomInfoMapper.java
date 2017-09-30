/**
 * 
 */
package com.ym.iadpush.manage.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ym.iadpush.manage.entity.RoomInfo;

/**
 * @Author: lixingbiao 2017-6-11 下午4:37:32
 * @Version: $Id$
 * @Desc: <p>
 * </p>
 */
public interface RoomInfoMapper {
    //根据参数删除房屋
    Integer deleteRoomByParams(Map<String, Object> paramMap);
    //删除房屋
    Integer deleteRoom(int roomId);
    //修改物业
    Integer changeDepartment(Map<String, Object> paramMap);
    int getRoomInfosCount(Map<String, Object> paramMap);

    Integer saveRoomInfo(@Param("roomInfo") RoomInfo roomInfo, @Param("id") int id);

    Integer insertRoomInfo(RoomInfo roomInfo);

    List<RoomInfo> getRoomInfos(Map<String, Object> paramMap);

    RoomInfo getRoomInfoById(Integer id);

    RoomInfo getMaxOut_room_id(@Param("date") String date);

    Integer updateRoomInfo(@Param("room_id") String room_id, @Param("out_room_id") String out_room_id);

    RoomInfo getRoomInfoByOut_room_id(String out_room_id);

}
