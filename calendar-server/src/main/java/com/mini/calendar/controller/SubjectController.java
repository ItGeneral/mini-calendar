package com.mini.calendar.controller;

import com.mini.calendar.controller.request.SpaceDetailListRequest;
import com.mini.calendar.controller.request.SpaceSubjectSaveRequest;
import com.mini.calendar.controller.request.SubjectCommentRequest;
import com.mini.calendar.controller.request.SubjectPraiseRequest;
import com.mini.calendar.controller.response.BaseResponse;
import com.mini.calendar.controller.vo.DomainSubjectVO;
import com.mini.calendar.controller.vo.SubjectCommentVO;
import com.mini.calendar.controller.vo.SubjectPraiseVO;
import com.mini.calendar.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author songjiuhua
 * Created by 2021/1/19 17:14
 */
@RestController
@RequestMapping(value = "/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    /**
     * 保存主题
     * @param request
     * @return
     */
    @PostMapping(value = "/save")
    public BaseResponse saveSubject(@RequestBody SpaceSubjectSaveRequest request){
        if (StringUtils.isEmpty(request.getSpaceId())){
            return BaseResponse.fail("01", "参数异常");
        }
        subjectService.saveSubject(request);
        return BaseResponse.success("success");
    }

    /**
     * 获取主题列表
     * @param request
     * @return
     */
    @PostMapping(value = "/list")
    public BaseResponse<List<DomainSubjectVO>> getSpaceSubjectList(@RequestBody SpaceDetailListRequest request){
        if (StringUtils.isEmpty(request.getSpaceId())){
            return BaseResponse.fail("01", "参数异常");
        }
        if (request.getPageNo() == null || request.getPageNo() == 0){
            request.setPageNo(0);
        }else {
            request.setPageNo(request.getPageNo() - 1);
        }
        List<DomainSubjectVO> subjectVOList = subjectService.querySubjectListById(request);
        return BaseResponse.success(subjectVOList);
    }

    /**
     * 保存主题下的评论
     * @param request
     * @return
     */
    @PostMapping(value = "/comment/save")
    public BaseResponse<List<SubjectCommentVO>> saveSubjectComment(@RequestBody SubjectCommentRequest request){
        if (StringUtils.isEmpty(request.getSpaceId())
                || StringUtils.isEmpty(request.getSubjectId())
                || StringUtils.isEmpty(request.getUserId())){
            return BaseResponse.fail("01", "参数异常");
        }
        List<SubjectCommentVO> commentVOList = subjectService.saveSubjectComment(request);
        return BaseResponse.success(commentVOList);
    }

    /**
     * 保存主题下的点赞
     * @param request
     * @return
     */
    @PostMapping(value = "/praise/save")
    public BaseResponse<List<SubjectPraiseVO>> saveSubjectPraise(@RequestBody SubjectPraiseRequest request){
        if (StringUtils.isEmpty(request.getSpaceId())
                || StringUtils.isEmpty(request.getSubjectId())
                || StringUtils.isEmpty(request.getUserId())){
            return BaseResponse.fail("01", "参数异常");
        }
        List<SubjectPraiseVO> praiseVOList = subjectService.saveSubjectPraise(request);
        return BaseResponse.success(praiseVOList);
    }

}
