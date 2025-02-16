package com.ucsy.ams.dto;

public class ShowTimeSlotByTeacherIdDTO {
    private String dayOfWeeks;
    private String time9_10;
    private String time10_11;
    private String time11_12;
    private String time12_1;
    private String time1_2;
    private String time2_3;

    public ShowTimeSlotByTeacherIdDTO() {}

    public String getDayOfWeeks() {
        return dayOfWeeks;
    }

    public void setDayOfWeeks(String dayOfWeeks) {
        this.dayOfWeeks = dayOfWeeks;
    }

    public String getTime9_10() {
        return time9_10;
    }

    public void setTime9_10(String time9_10) {
        this.time9_10 = time9_10;
    }

    public String getTime10_11() {
        return time10_11;
    }

    public void setTime10_11(String time10_11) {
        this.time10_11 = time10_11;
    }

    public String getTime11_12() {
        return time11_12;
    }

    public void setTime11_12(String time11_12) {
        this.time11_12 = time11_12;
    }

    public String getTime12_1() {
        return time12_1;
    }

    public void setTime12_1(String time12_1) {
        this.time12_1 = time12_1;
    }

    public String getTime1_2() {
        return time1_2;
    }

    public void setTime1_2(String time1_2) {
        this.time1_2 = time1_2;
    }

    public String getTime2_3() {
        return time2_3;
    }

    public void setTime2_3(String time2_3) {
        this.time2_3 = time2_3;
    }

    @Override
    public String toString() {
        return "ShowTimeSlotByTeacherIdDTO{" +
                "dayOfWeeks='" + dayOfWeeks + '\'' +
                ", time9_10='" + time9_10 + '\'' +
                ", time10_11='" + time10_11 + '\'' +
                ", time11_12='" + time11_12 + '\'' +
                ", time12_1='" + time12_1 + '\'' +
                ", time1_2='" + time1_2 + '\'' +
                ", time2_3='" + time2_3 + '\'' +
                '}';
    }
}
