package com.mini.calendar.dao.mapper;

import com.mini.calendar.dao.model.SubjectComment;
import com.mini.calendar.dao.model.SubjectCommentDTO;
import com.mini.calendar.dao.model.SubjectPraiseDTO;
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
public interface SubjectCommentMapper {

    void saveSubjectComment(@Param("comment") SubjectComment subjectComment);

    List<SubjectComment> queryBySpaceIdAndSubjectIdList(@Param("spaceId") Integer spaceId, @Param("subjectIdList") List<Integer> subjectIdList);

    List<SubjectCommentDTO> querySubjectCommentList(@Param("spaceId") Integer spaceId, @Param("subjectId") Integer subjectId);

}
