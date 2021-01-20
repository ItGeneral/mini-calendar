package com.mini.calendar.controller.vo;

import java.util.List;

/**
 * @author songjiuhua
 * Created by 2021/1/18 19:06
 */
public class DomainSubjectVO {

    /**
     * 主题id
     */
    private Integer id;

    private Integer userId;

    private String nickName;

    private String userAvatarUrl;

    private String subjectCreateTimeStr;
    /**
     * 主题内容
     */
    private String content;
    /**
     * 评论列表
     */
    private List<SubjectCommentVO> commentList;
    /**
     * 点赞列表
     */
    private List<SubjectPraiseVO> praiseList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }

    public void setUserAvatarUrl(String userAvatarUrl) {
        this.userAvatarUrl = userAvatarUrl;
    }

    public String getSubjectCreateTimeStr() {
        return subjectCreateTimeStr;
    }

    public void setSubjectCreateTimeStr(String subjectCreateTimeStr) {
        this.subjectCreateTimeStr = subjectCreateTimeStr;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<SubjectCommentVO> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<SubjectCommentVO> commentList) {
        this.commentList = commentList;
    }

    public List<SubjectPraiseVO> getPraiseList() {
        return praiseList;
    }

    public void setPraiseList(List<SubjectPraiseVO> praiseList) {
        this.praiseList = praiseList;
    }
}
