package com.pj.planbee.dto;


public class CalendarDTO {
   int calId;
   String calDetail, calDate, userId; //userId는 나중에 찬교님이 사용한 userID와 맞춰주면 됨
   double calProgress;
   public int getCalId() {
      return calId;
   }
   public void setCalId(int calId) {
      this.calId = calId;
   }
   public String getCalDetail() {
      return calDetail;
   }
   public void setCalDetail(String calDetail) {
      this.calDetail = calDetail;
   }
   public String getCalDate() {
      return calDate;
   }
   public void setCalDate(String calDate) {
      this.calDate = calDate;
   }
   public String getUserId() {
      return userId;
   }
   public void setUserId(String userId) {
      this.userId = userId;
   }
   public double getCalProgress() {
      return calProgress;
   }
   public void setCalProgress(double calProgress) {
      this.calProgress = calProgress;
   }
   
   
   
   
   
}