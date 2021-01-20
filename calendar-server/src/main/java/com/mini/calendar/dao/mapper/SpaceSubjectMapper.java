package com.mini.calendar.dao.mapper;

import com.mini.calendar.dao.model.SpaceSubject;
import com.mini.db.annotation.RecDB;
import com.mini.db.annotation.RecDBWritable;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author songjiuhua
 * Created by 2021/1/6 15:49
 */
@RecDBWritable
@RecDB(name = "calendar")
public interface SpaceSubjectMapper {

    void saveSpaceSubject(@Param("subject") SpaceSubject spaceSubject);

    List<SpaceSubject> queryBySpaceId(@Param("spaceId") Integer spaceId, @Param("offset") Integer offset, @Param("limit") Integer limit);

}
