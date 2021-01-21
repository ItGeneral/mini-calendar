package com.mini.calendar.service;

import com.mini.calendar.controller.request.SpaceDetailListRequest;
import com.mini.calendar.controller.request.SpaceSubjectSaveRequest;
import com.mini.calendar.controller.request.SubjectCommentRequest;
import com.mini.calendar.controller.request.SubjectPraiseRequest;
import com.mini.calendar.controller.vo.DomainSubjectVO;
import com.mini.calendar.controller.vo.SubjectCommentVO;
import com.mini.calendar.controller.vo.SubjectPraiseVO;
import com.mini.calendar.dao.mapper.CalendarUserMapper;
import com.mini.calendar.dao.mapper.SpaceSubjectMapper;
import com.mini.calendar.dao.mapper.SubjectCommentMapper;
import com.mini.calendar.dao.mapper.SubjectPraiseMapper;
import com.mini.calendar.dao.model.CalendarUser;
import com.mini.calendar.dao.model.SpaceSubject;
import com.mini.calendar.dao.model.SubjectComment;
import com.mini.calendar.dao.model.SubjectCommentDTO;
import com.mini.calendar.dao.model.SubjectPraise;
import com.mini.calendar.dao.model.SubjectPraiseDTO;
import com.mini.calendar.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
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
        if (CollectionUtils.isEmpty(subjectList)){
            return subjectVOList;
        }
        Set<Integer> userIdSet = new HashSet<>();
        //获取某些主题下的所有评论
        List<Integer> subjectIdList = subjectList.stream().map(SpaceSubject::getId).collect(Collectors.toList());
        List<SubjectComment> commentList = subjectCommentMapper.queryBySpaceIdAndSubjectIdList(request.getSpaceId(), subjectIdList);
        Map<Integer, List<SubjectComment>> subjectMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(commentList)){
            for (SubjectComment subjectComment : commentList) {
                List<SubjectComment> list = subjectMap.get(subjectComment.getSubjectId());
                if (CollectionUtils.isEmpty(list)){
                    list = new ArrayList<>();
                }
                list.add(subjectComment);
                subjectMap.put(subjectComment.getSubjectId(), list);
                userIdSet.add(subjectComment.getUserId());
            }
        }
        //获取某些主题下的所有点赞
        List<SubjectPraise> praiseList = subjectPraiseMapper.queryBySpaceIdAndSubjectIdList(request.getSpaceId(), subjectIdList);
        Map<Integer, List<SubjectPraise>> praiseMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(praiseList)){
            for (SubjectPraise subjectPraise : praiseList) {
                List<SubjectPraise> list = praiseMap.get(subjectPraise.getSubjectId());
                if(CollectionUtils.isEmpty(list)){
                    list = new ArrayList<>();
                }
                list.add(subjectPraise);
                praiseMap.put(subjectPraise.getSubjectId(), list);
                userIdSet.add(subjectPraise.getUserId());
            }
        }

        Set<Integer> subUserIdList = subjectList.stream().map(SpaceSubject::getUserId).collect(Collectors.toSet());
        userIdSet.addAll(subUserIdList);
        List<CalendarUser> userList = calendarUserMapper.queryByUserIdList(userIdSet);
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
            List<SubjectComment> subjectCommentList = subjectMap.get(spaceSubject.getId());
            List<SubjectCommentVO> commentVOList = new ArrayList<>();
            subjectVO.setCommentList(commentVOList);
            if (CollectionUtils.isNotEmpty(subjectCommentList)){
                for (SubjectComment subjectComment : subjectCommentList) {
                    SubjectCommentVO commentVO = new SubjectCommentVO();
                    commentVO.setSpaceId(subjectComment.getSpaceId());
                    commentVO.setSubjectId(subjectComment.getSubjectId());
                    commentVO.setUserId(subjectComment.getUserId());
                    commentVO.setUserName(userMap.get(subjectComment.getUserId()).getNickName());
                    commentVO.setContent(subjectComment.getContent());
                    commentVOList.add(commentVO);
                }
            }
            List<SubjectPraise> subjectPraiseList = praiseMap.get(spaceSubject.getId());
            List<SubjectPraiseVO> praiseVOList = new ArrayList<>();
            subjectVO.setPraiseList(praiseVOList);
            if (CollectionUtils.isNotEmpty(subjectPraiseList)){
                for (SubjectPraise subjectPraise : subjectPraiseList) {
                    SubjectPraiseVO praiseVO = new SubjectPraiseVO();
                    praiseVO.setSpaceId(subjectPraise.getSpaceId());
                    praiseVO.setSubjectId(subjectPraise.getSubjectId());
                    praiseVO.setUserId(subjectPraise.getUserId());
                    praiseVO.setUserName(userMap.get(subjectPraise.getUserId()).getNickName());
                    praiseVOList.add(praiseVO);
                }
            }
            subjectVOList.add(subjectVO);
        }
        return subjectVOList;
    }

    public List<SubjectCommentVO> saveSubjectComment(SubjectCommentRequest request){
        SubjectComment subjectComment = new SubjectComment();
        subjectComment.setSpaceId(request.getSpaceId());
        subjectComment.setSubjectId(request.getSubjectId());
        subjectComment.setUserId(request.getUserId());
        subjectComment.setContent(request.getComment());
        subjectComment.setDeleted(0);
        subjectCommentMapper.saveSubjectComment(subjectComment);

        List<SubjectCommentDTO> commentList = subjectCommentMapper.querySubjectCommentList(request.getSpaceId(), request.getSubjectId());
        List<SubjectCommentVO> commentVOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(commentList)){
            for (SubjectCommentDTO commentDTO : commentList) {
                SubjectCommentVO commentVO = new SubjectCommentVO();
                commentVO.setSpaceId(commentDTO.getSpaceId());
                commentVO.setSubjectId(commentDTO.getSubjectId());
                commentVO.setUserId(commentDTO.getUserId());
                commentVO.setUserName(commentDTO.getNickName());
                commentVO.setContent(commentDTO.getContent());
                commentVOList.add(commentVO);
            }
        }
        return commentVOList;
    }

    public List<SubjectPraiseVO> saveSubjectPraise(SubjectPraiseRequest request){
        SubjectPraise existPraise = subjectPraiseMapper.queryUniquePraise(request.getSpaceId(),
                request.getSubjectId(), request.getUserId());
        if (existPraise != null && existPraise.getId() != null){
            Integer deleted;
            if (existPraise.getDeleted() == 0){
                deleted = 1;
            }else {
                deleted = 0;
            }
            subjectPraiseMapper.updatePraise(existPraise.getId(), deleted);
        }else {
            SubjectPraise subjectPraise = new SubjectPraise();
            subjectPraise.setSpaceId(request.getSpaceId());
            subjectPraise.setSubjectId(request.getSubjectId());
            subjectPraise.setUserId(request.getUserId());
            subjectPraise.setDeleted(0);
            subjectPraiseMapper.saveSubjectPraise(subjectPraise);
        }

        List<SubjectPraiseDTO> subjectPraiseDTOS = subjectPraiseMapper.querySubjectPraiseList(request.getSpaceId(), request.getSubjectId());
        List<SubjectPraiseVO> praiseVOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(subjectPraiseDTOS)){
            for (SubjectPraiseDTO praiseDTO : subjectPraiseDTOS) {
                SubjectPraiseVO praiseVO = new SubjectPraiseVO();
                praiseVO.setSpaceId(praiseDTO.getSpaceId());
                praiseVO.setSubjectId(praiseDTO.getSubjectId());
                praiseVO.setUserId(praiseDTO.getUserId());
                praiseVO.setUserName(praiseDTO.getNickName());
                praiseVOList.add(praiseVO);
            }
        }
        return praiseVOList;
    }

}
