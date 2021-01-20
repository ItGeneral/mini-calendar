package com.mini.calendar.dao.mapper;

import com.mini.calendar.dao.model.DomainSpace;
import com.mini.calendar.dao.model.DomainSpaceUserDTO;
import com.mini.calendar.dao.model.DomainSubjectDTO;
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
public interface DomainSpaceMapper {

    void saveDomainSpace(@Param("space") DomainSpace domainSpace);

    List<DomainSpaceUserDTO> queryByIdList(@Param("idList") List<Integer> idList);

    List<DomainSpace> queryByUserId(@Param("userId") Integer userId);

    List<DomainSubjectDTO> querySubjectListById(@Param("id") Integer id);
}
