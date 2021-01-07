package com.mini.calendar.dao.mapper;

import com.mini.calendar.dao.model.CalendarUser;
import com.mini.db.annotation.RecDB;
import com.mini.db.annotation.RecDBWritable;
import org.apache.ibatis.annotations.Param;

/**
 * @author songjiuhua
 * Created by 2021/1/6 15:49
 */
@RecDBWritable
@RecDB(name = "calendar")
public interface CalendarUserMapper {

    void saveUser(@Param("user")CalendarUser calendarUser);

    void updateUser(@Param("user")CalendarUser calendarUser);

    CalendarUser queryByOpenIdOrId(@Param("id") Integer id, @Param("openId") String openId);

}
