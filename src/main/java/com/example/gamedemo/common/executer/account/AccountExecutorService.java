package com.example.gamedemo.common.executer.account;

import com.example.gamedemo.common.executer.Command;
import com.example.gamedemo.common.executer.account.impl.AbstractAccountDelayCommand;
import com.example.gamedemo.common.executer.account.impl.AbstractAccountRateCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wengj
 * @description：账号任务执行服务
 * @date 2019/7/8
 */
@Component
public class AccountExecutorService implements IAccountExecutorService {
  @Autowired private AccountExecutor accountExecutor;

  @Override
  public void submit(Command command) {
    if (command instanceof AbstractAccountDelayCommand) {
      // TODO
      AbstractAccountDelayCommand accountDelayCommand = (AbstractAccountDelayCommand) command;
    } else if (command instanceof AbstractAccountRateCommand) {
      AbstractAccountRateCommand accountRateCommand = (AbstractAccountRateCommand) command;
      // TODO
    } else {
      accountExecutor.addTask(command);
    }
  }
}
