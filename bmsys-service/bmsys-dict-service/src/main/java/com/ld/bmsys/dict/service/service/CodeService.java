package com.ld.bmsys.dict.service.service;

import com.ld.bmsys.dict.service.dao.CodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author LD
 * @date 2021/4/28 11:02
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CodeService {

    @Autowired
    CodeMapper codeMapper;

    public void test() {
        System.out.println("code service test......");
    }
}
