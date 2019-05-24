package com.example.gamedemo.server.game.bag.service;

import com.example.gamedemo.server.game.account.model.Account;
import org.springframework.stereotype.Service;

/**
 * @author wengj
 * @description
 * @date 2019/5/21
 */
@Service
public class BagServiceImpl implements BagService {

    @Override
    public int addItem(Account account, String itemId) {
        return 0;
    }

    @Override
    public int useItem(Account account, String itemId) {
        return 0;
    }
}
