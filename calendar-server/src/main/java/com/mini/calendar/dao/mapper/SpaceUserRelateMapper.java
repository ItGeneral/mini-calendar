package com.mini.calendar.dao.mapper;

import com.mini.calendar.dao.model.SpaceUserRelate;
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
public interface SpaceUserRelateMapper {

    void saveSpaceUserRelate(@Param("relate") SpaceUserRelate spaceUserRelate);

    List<SpaceUserRelate> queryByUserId(@Param("userId") Integer userId);
}
