package com.mini.calendar.dao.model;

/**
 * @author songjiuhua
 * Created by 2020/12/25 16:35
 */
public class CalendarInfo {
    
    private Integer id;
    /**
     * 阳历年
     */
    private Integer solarYear;
    /**
     * 阳历月
     */
    private Integer solarMonth;
    /**
     * 阳历日
     */
    private Integer solarDay;
    /**
     * 阴历年
     */
    private Integer lunarYear;
    /**
     * 阴历月
     */
    private String lunarMonth;
    /**
     * 阴历日
     */
    private String lunarDay;
    /**
     * 干支年,如庚子
     */
    private String gzYear;
    /**
     * 干支月,如戊寅
     */
    private String gzMonth;
    /**
     * 干支日,如壬寅
     */
    private String gzDay;
    /**
     * 生肖
     */
    private String animal;
    /**
     * 忌事
     */
    private String avoid;
    /**
     * 宜事
     */
    private String suit;
    /**
     * 星期
     */
    private String weekDay;
    /**
     * 节日名称
     */
    private String remark;
    /**
     * 0默认，1：表示休息，2：调休上班
     */
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSolarYear() {
        return solarYear;
    }

    public void setSolarYear(Integer solarYear) {
        this.solarYear = solarYear;
    }

    public Integer getSolarMonth() {
        return solarMonth;
    }

    public void setSolarMonth(Integer solarMonth) {
        this.solarMonth = solarMonth;
    }

    public Integer getSolarDay() {
        return solarDay;
    }

    public void setSolarDay(Integer solarDay) {
        this.solarDay = solarDay;
    }

    public Integer getLunarYear() {
        return lunarYear;
    }

    public void setLunarYear(Integer lunarYear) {
        this.lunarYear = lunarYear;
    }

    public String getLunarMonth() {
        return lunarMonth;
    }

    public void setLunarMonth(String lunarMonth) {
        this.lunarMonth = lunarMonth;
    }

    public String getLunarDay() {
        return lunarDay;
    }

    public void setLunarDay(String lunarDay) {
        this.lunarDay = lunarDay;
    }

    public String getGzYear() {
        return gzYear;
    }

    public void setGzYear(String gzYear) {
        this.gzYear = gzYear;
    }

    public String getGzMonth() {
        return gzMonth;
    }

    public void setGzMonth(String gzMonth) {
        this.gzMonth = gzMonth;
    }

    public String getGzDay() {
        return gzDay;
    }

    public void setGzDay(String gzDay) {
        this.gzDay = gzDay;
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    public String getAvoid() {
        return avoid;
    }

    public void setAvoid(String avoid) {
        this.avoid = avoid;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
