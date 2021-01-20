package com.mini.calendar.controller;

import com.mini.calendar.controller.request.DomainSpaceQueryRequest;
import com.mini.calendar.controller.request.DomainSpaceSaveRequest;
import com.mini.calendar.controller.request.SessionRequest;
import com.mini.calendar.controller.request.SpaceDetailListRequest;
import com.mini.calendar.controller.response.BaseResponse;
import com.mini.calendar.client.FastDFSClient;
import com.mini.calendar.controller.vo.DomainSpaceVO;
import com.mini.calendar.controller.vo.DomainSubjectVO;
import com.mini.calendar.service.DomainService;
import com.mini.calendar.wx.model.AuthSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author songjiuhua
 * Created by 2021/1/15 19:16
 */
@RestController
@RequestMapping(value = "/domain")
public class DomainController {

    @Autowired
    private DomainService domainService;

    @PostMapping(value = "/upload")
    public BaseResponse<String> uploadFile(@RequestParam(value="file",required=false) MultipartFile file, DomainSpaceSaveRequest saveRequest){
        domainService.saveDomainSpace(file, saveRequest);
        return BaseResponse.success("success");
    }

    @PostMapping(value = "/list")
    public BaseResponse<List<DomainSpaceVO>> getSpaceList(@RequestBody DomainSpaceQueryRequest queryRequest){
        List<DomainSpaceVO> spaceVOList = domainService.queryMyRelateSpace(queryRequest);
        return BaseResponse.success(spaceVOList);
    }


}
