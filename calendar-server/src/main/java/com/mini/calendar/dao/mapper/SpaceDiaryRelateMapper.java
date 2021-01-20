package com.mini.calendar.dao.mapper;

import com.mini.calendar.dao.model.SpaceDiaryRelate;
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
public interface SpaceDiaryRelateMapper {

    void saveSpaceDiaryRelate(@Param("relate") SpaceDiaryRelate diaryRelate);

    List<SpaceDiaryRelate> queryBySpaceId(@Param("spaceId") Integer spaceId);

    List<Integer> queryUserListBySpaceId(@Param("spaceId") Integer spaceId);
}
