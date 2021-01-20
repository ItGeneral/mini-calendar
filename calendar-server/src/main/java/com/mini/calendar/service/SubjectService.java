package com.mini.calendar.service;

import com.mini.calendar.controller.request.SpaceDetailListRequest;
import com.mini.calendar.controller.request.SpaceSubjectSaveRequest;
import com.mini.calendar.controller.request.SubjectCommentRequest;
import com.mini.calendar.controller.request.SubjectPraiseRequest;
import com.mini.calendar.controller.vo.DomainSubjectVO;
import com.mini.calendar.dao.mapper.CalendarUserMapper;
import com.mini.calendar.dao.mapper.SpaceSubjectMapper;
import com.mini.calendar.dao.mapper.SubjectCommentMapper;
import com.mini.calendar.dao.mapper.SubjectPraiseMapper;
import com.mini.calendar.dao.model.CalendarUser;
import com.mini.calendar.dao.model.SpaceSubject;
import com.mini.calendar.dao.model.SubjectComment;
import com.mini.calendar.dao.model.SubjectPraise;
import com.mini.calendar.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author songjiuhua
 * Created by 2021/1/19 17:16
 */
@Service
public class SubjectService {

    @Autowired
    protected SpaceSubjectMapper spaceSubjectMapper;
    @Autowired
    protected SubjectCommentMapper subjectCommentMapper;
    @Autowired
    protected SubjectPraiseMapper subjectPraiseMapper;
    @Autowired
    protected CalendarUserMapper calendarUserMapper;

    public void saveSubject(SpaceSubjectSaveRequest saveRequest){
        SpaceSubject spaceSubject = new SpaceSubject();
        spaceSubject.setSpaceId(saveRequest.getSpaceId());
        spaceSubject.setUserId(saveRequest.getUserId());
        spaceSubject.setContent(saveRequest.getContent());
        spaceSubject.setDeleted(0);
        spaceSubjectMapper.saveSpaceSubject(spaceSubject);
    }

    public List<DomainSubjectVO> querySubjectListById(SpaceDetailListRequest request){
        List<DomainSubjectVO> subjectVOList = new ArrayList<>();
        Integer offset = request.getPageNo() * request.getPageSize();
        Integer limit = request.getPageSize();
        List<SpaceSubject> subjectList = spaceSubjectMapper.queryBySpaceId(request.getSpaceId(), offset, limit);
        List<Integer> userIdList = subjectList.stream().map(SpaceSubject::getUserId).distinct().collect(Collectors.toList());
        List<CalendarUser> userList = calendarUserMapper.queryByUserIdList(userIdList);
        Map<Integer, CalendarUser> userMap = new HashMap<>();
        for (CalendarUser calendarUser : userList) {
            userMap.put(calendarUser.getId(), calendarUser);
        }
        for (SpaceSubject spaceSubject : subjectList) {
            DomainSubjectVO subjectVO = new DomainSubjectVO();
            CalendarUser calendarUser = userMap.get(spaceSubject.getUserId());
            subjectVO.setId(spaceSubject.getId());
            subjectVO.setUserId(calendarUser.getId());
            subjectVO.setNickName(calendarUser.getNickName());
            subjectVO.setUserAvatarUrl(calendarUser.getAvatarUrl());
            subjectVO.setSubjectCreateTimeStr(DateUtil.formatDate(spaceSubject.getUpdateTime(), DateUtil.TIMESTAMP_CHILD_PATTERN));
            subjectVO.setContent(spaceSubject.getContent());
            subjectVOList.add(subjectVO);
        }
        return subjectVOList;
    }

    public void saveSubjectComment(SubjectCommentRequest commentRequest){
        SubjectComment subjectComment = new SubjectComment();
        subjectComment.setSpaceId(commentRequest.getSpaceId());
        subjectComment.setSubjectId(commentRequest.getSubjectId());
        subjectComment.setUserId(commentRequest.getUserId());
        subjectComment.setContent(commentRequest.getComment());
        subjectComment.setDeleted(0);
        subjectCommentMapper.saveSubjectComment(subjectComment);
    }

    public void saveSubjectPraise(SubjectPraiseRequest subjectPraiseRequest){
        SubjectPraise subjectPraise = new SubjectPraise();
        subjectPraise.setSpaceId(subjectPraiseRequest.getSpaceId());
        subjectPraise.setSubjectId(subjectPraiseRequest.getSubjectId());
        subjectPraise.setUserId(subjectPraiseRequest.getUserId());
        subjectPraise.setDeleted(0);
        subjectPraiseMapper.saveSubjectPraise(subjectPraise);
    }

}
