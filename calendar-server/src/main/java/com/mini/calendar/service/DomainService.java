package com.mini.calendar.service;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.mini.calendar.client.FastDFSClient;
import com.mini.calendar.controller.request.DomainSpaceQueryRequest;
import com.mini.calendar.controller.request.DomainSpaceSaveRequest;
import com.mini.calendar.controller.vo.DomainSpaceVO;
import com.mini.calendar.dao.mapper.CalendarUserMapper;
import com.mini.calendar.dao.mapper.DomainSpaceMapper;
import com.mini.calendar.dao.mapper.SpaceUserRelateMapper;
import com.mini.calendar.dao.model.DomainSpace;
import com.mini.calendar.dao.model.DomainSpaceUserDTO;
import com.mini.calendar.dao.model.SpaceUserRelate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
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
    protected CalendarUserMapper calendarUserMapper;

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
            spaceVO.setSpaceName(spaceUserDTO.getSpaceName());
            spaceVO.setAvatarUrl(fastDfsUrl + spaceUserDTO.getAvatarUrl());
            spaceVO.setAuthor(spaceUserDTO.getNickName());
            spaceVO.setType(spaceIdTypeMap.get(spaceUserDTO.getSpaceId()));
            spaceVOList.add(spaceVO);
        }
        return spaceVOList;
    }

}
