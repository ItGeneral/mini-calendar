package com.mini.calendar.controller;

import com.mini.calendar.controller.request.DomainSpaceQueryRequest;
import com.mini.calendar.controller.request.DomainSpaceSaveRequest;
import com.mini.calendar.controller.request.SpaceDetailListRequest;
import com.mini.calendar.controller.request.SpaceBaseRequest;
import com.mini.calendar.controller.response.BaseResponse;
import com.mini.calendar.controller.vo.DomainSpaceVO;
import com.mini.calendar.controller.vo.SpaceMemberVO;
import com.mini.calendar.service.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * 创建空间
     * @param file
     * @param saveRequest
     * @return
     */
    @PostMapping(value = "/save")
    public BaseResponse<String> saveSpace(@RequestParam(value="file",required=false) MultipartFile file, DomainSpaceSaveRequest saveRequest){
        domainService.saveDomainSpace(file, saveRequest);
        return BaseResponse.success("success");
    }

    /**
     * 获取与我相关的空间
     * @param queryRequest
     * @return
     */
    @PostMapping(value = "/list")
    public BaseResponse<List<DomainSpaceVO>> getSpaceList(@RequestBody DomainSpaceQueryRequest queryRequest){
        List<DomainSpaceVO> spaceVOList = domainService.queryMyRelateSpace(queryRequest);
        return BaseResponse.success(spaceVOList);
    }


    /**
     * 获取某个空间的成员信息
     * @param request
     * @return
     */
    @PostMapping(value = "/members")
    public BaseResponse<List<SpaceMemberVO>> memberList(@RequestBody SpaceDetailListRequest request){
        if (request.getPageNo() == null || request.getPageNo() == 0){
            request.setPageNo(0);
        }else {
            request.setPageNo(request.getPageNo() - 1);
        }
        List<SpaceMemberVO> memberVOList = domainService.queryMemberList(request);
        return BaseResponse.success(memberVOList);
    }

    @PostMapping(value = "/join")
    public BaseResponse<SpaceMemberVO> joinDomainSpace(@RequestBody SpaceBaseRequest request){
        SpaceMemberVO memberVO = domainService.joinSpace(request);
        return BaseResponse.success(memberVO);
    }

    /**
     * 退出空间
     * @return
     */
    @PostMapping(value = "/quit")
    public BaseResponse quitSpace(@RequestBody SpaceBaseRequest request){
        domainService.quitSpace(request);
        return BaseResponse.success();
    }

    /**
     * 解散空间
     * @return
     */
    @PostMapping(value = "/dismiss")
    public BaseResponse dismissSpace(@RequestBody SpaceBaseRequest request){
        domainService.dismissSpace(request);
        return BaseResponse.success();
    }


}
