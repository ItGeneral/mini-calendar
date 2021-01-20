package com.mini.calendar.dao.mapper;

import com.mini.calendar.dao.model.SubjectPraise;
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
public interface SubjectPraiseMapper {

    void saveSubjectPraise(@Param("praise") SubjectPraise subjectPraise);

    List<SubjectPraise> queryBySpaceIdAndSubjectIdList(@Param("spaceId") Integer spaceId, @Param("subjectIdList") List<Integer> subjectIdList);

}
