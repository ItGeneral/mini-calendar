package com.mini.calendar.service;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.mini.calendar.client.FastDFSClient;
import com.mini.calendar.controller.request.DomainSpaceQueryRequest;
import com.mini.calendar.controller.request.DomainSpaceSaveRequest;
import com.mini.calendar.controller.request.SpaceDetailListRequest;
import com.mini.calendar.controller.request.SpaceJoinRequest;
import com.mini.calendar.controller.vo.DomainSpaceVO;
import com.mini.calendar.controller.vo.SpaceMemberVO;
import com.mini.calendar.dao.mapper.CalendarUserMapper;
import com.mini.calendar.dao.mapper.DomainSpaceMapper;
import com.mini.calendar.dao.mapper.SpaceSubjectMapper;
import com.mini.calendar.dao.mapper.SpaceUserRelateMapper;
import com.mini.calendar.dao.model.CalendarUser;
import com.mini.calendar.dao.model.DomainSpace;
import com.mini.calendar.dao.model.DomainSpaceUserDTO;
import com.mini.calendar.dao.model.SpaceUserRelate;
import com.mini.calendar.dao.model.SpaceUserRelateDTO;
import com.mini.calendar.util.DateUtil;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author songjiuhua
 * Created by 2021/1/16 10:47
 */
@Service
public class DomainService {

    @Value("${fdfs.image-url}")
    private String fastDfsUrl;

    @Autowired
    protected FastDFSClient fastDFSClient;
    @Autowired
    protected DomainSpaceMapper domainSpaceMapper;
    @Autowired
    protected SpaceUserRelateMapper spaceUserRelateMapper;
    @Autowired
    protected SpaceSubjectMapper spaceSubjectMapper;
    @Autowired
    protected CalendarUserMapper calendarUserMapper;

    /**
     * 保存空间数据
     * @param file
     * @param saveRequest
     */
    public void saveDomainSpace(MultipartFile file, DomainSpaceSaveRequest saveRequest){
        StorePath storePath = fastDFSClient.upload(file);
        DomainSpace domainSpace = new DomainSpace();
        domainSpace.setUserId(saveRequest.getUserId());
        domainSpace.setSpaceName(saveRequest.getTitle());
        domainSpace.setAvatarUrl(storePath.getFullPath());
        domainSpaceMapper.saveDomainSpace(domainSpace);

        SpaceUserRelate spaceUserRelate = new SpaceUserRelate();
        spaceUserRelate.setUserId(saveRequest.getUserId());
        spaceUserRelate.setDomainSpaceId(domainSpace.getId());
        spaceUserRelate.setType(0);
        spaceUserRelateMapper.saveSpaceUserRelate(spaceUserRelate);
    }

    /**
     * 查询与我相关的空间列表
     * @param queryRequest
     * @return
     */
    public List<DomainSpaceVO> queryMyRelateSpace(DomainSpaceQueryRequest queryRequest){
        List<DomainSpaceVO> spaceVOList = new ArrayList<>();
        List<SpaceUserRelate> relateList = spaceUserRelateMapper.queryByUserId(queryRequest.getUserId());
        if (CollectionUtils.isEmpty(relateList)){
            return spaceVOList;
        }
        Map<Integer, Integer> spaceIdTypeMap = new HashMap<>();
        for (SpaceUserRelate spaceUserRelate : relateList) {
            spaceIdTypeMap.put(spaceUserRelate.getDomainSpaceId(), spaceUserRelate.getType());
        }
        List<DomainSpaceUserDTO> domainSpaceList = domainSpaceMapper.queryByIdList(new ArrayList<>(spaceIdTypeMap.keySet()));
        for (DomainSpaceUserDTO spaceUserDTO : domainSpaceList) {
            DomainSpaceVO spaceVO = new DomainSpaceVO();
            spaceVO.setId(spaceUserDTO.getSpaceId());
            spaceVO.setSpaceName(spaceUserDTO.getSpaceName());
            spaceVO.setAvatarUrl(fastDfsUrl + spaceUserDTO.getAvatarUrl());
            spaceVO.setAuthor(spaceUserDTO.getNickName());
            spaceVO.setType(spaceIdTypeMap.get(spaceUserDTO.getSpaceId()));
            spaceVOList.add(spaceVO);
        }
        return spaceVOList;
    }

    /**
     * 查询成员列表
     * @param request
     * @return
     */
    public List<SpaceMemberVO> queryMemberList(SpaceDetailListRequest request){
        List<SpaceMemberVO> memberVOList = new ArrayList<>();
        Integer offset = request.getPageNo() * request.getPageSize();
        List<SpaceUserRelateDTO> relateDTOList = spaceUserRelateMapper.queryBySpaceId(request.getSpaceId(), offset, 50);
        if(CollectionUtils.isNotEmpty(relateDTOList)){
            for (SpaceUserRelateDTO relateDTO : relateDTOList) {
                SpaceMemberVO memberVO = new SpaceMemberVO();
                memberVO.setUserId(relateDTO.getUserId());
                memberVO.setSpaceId(relateDTO.getSpaceId());
                memberVO.setNickName(relateDTO.getNickName());
                memberVO.setAvatarUrl(relateDTO.getAvatarUrl());
                memberVO.setAddSpaceTime(DateUtil.formatDate(relateDTO.getCreateTime(), DateUtil.TIMESTAMP_CHILD_PATTERN));
                memberVOList.add(memberVO);
            }
        }
        return memberVOList;
    }

    /**
     * 加入空间
     * @param request
     */
    public SpaceMemberVO joinSpace(SpaceJoinRequest request){
        SpaceMemberVO memberVO = new SpaceMemberVO();
        SpaceUserRelate existRelate = spaceUserRelateMapper.queryByUserIdAndSpaceId(request.getUserId(), request.getSpaceId());
        if (existRelate != null && existRelate.getUserId() != null){
            return memberVO;
        }

        SpaceUserRelate spaceUserRelate = new SpaceUserRelate();
        spaceUserRelate.setUserId(request.getUserId());
        spaceUserRelate.setDomainSpaceId(request.getSpaceId());
        spaceUserRelate.setType(1);
        spaceUserRelateMapper.saveSpaceUserRelate(spaceUserRelate);

        CalendarUser calendarUser = calendarUserMapper.queryByOpenIdOrId(request.getUserId(), null);


        memberVO.setUserId(request.getUserId());
        memberVO.setSpaceId(request.getSpaceId());
        memberVO.setNickName(calendarUser.getNickName());
        memberVO.setAvatarUrl(calendarUser.getAvatarUrl());
        memberVO.setAddSpaceTime(DateUtil.formatDate(new Date(), DateUtil.TIMESTAMP_CHILD_PATTERN));
        return memberVO;
    }

}
