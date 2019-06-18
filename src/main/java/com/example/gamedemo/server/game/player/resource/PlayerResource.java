package com.example.gamedemo.server.game.player.resource;

import com.example.gamedemo.common.anno.ExcelColumn;
import com.example.gamedemo.common.anno.Resource;
import com.example.gamedemo.common.resource.ResourceInterface;

/**
 * @author wengj
 * @description
 * @date 2019/5/31
 */
@Resource
public class PlayerResource implements ResourceInterface {
  @ExcelColumn(columnName = "jobId")
  private int jobId;

  @ExcelColumn(columnName = "jobName")
  private String jobName;

  public int getJobId() {
    return jobId;
  }

  public void setJobId(int jobId) {
    this.jobId = jobId;
  }

  public String getJobName() {
    return jobName;
  }

  public void setJobName(String jobName) {
    this.jobName = jobName;
  }

  @Override
  public Object getId() {
    return jobId;
  }

  @Override
  public void postInit() {}
}
