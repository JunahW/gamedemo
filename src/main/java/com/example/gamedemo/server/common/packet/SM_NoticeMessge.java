package com.example.gamedemo.server.common.packet;

/**
 * @author wengj
 * @description:通知消息
 * @date 2019/6/11
 */
public class SM_NoticeMessge {
  private String content;

  public static SM_NoticeMessge valueOf(String content) {
    SM_NoticeMessge sm_noticeMessge = new SM_NoticeMessge();
    sm_noticeMessge.setContent(content);
    return sm_noticeMessge;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
