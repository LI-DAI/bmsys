package com.ld.bmsys.auth.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.ld.bmsys.auth.service.security.vo.OnlineUser;
import com.ld.bmsys.auth.service.utils.MinioUtil;
import com.ld.bmsys.common.entity.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.time.LocalDateTime;

/**
 * @Author LD
 * @Date 2021/4/22 17:38
 */
@RestController
@RequestMapping("/minio")
@Api(tags = "Minio测试")
public class MinioController {

    @PostMapping("/upload")
    @ApiOperation("上传文件 demo")
    public Result<String> uploadObject(@RequestPart("file") MultipartFile file) throws Exception {
        return Result.data(MinioUtil.uploadFileWithContentType(file));
    }

    @PostMapping("/uploadObject")
    @ApiOperation("上传对象 DEMO")
    public Result<Object> uploadObject() throws Exception {
        ImmutableList<OnlineUser> users = ImmutableList.of(
                new OnlineUser(1, "xiaoming", "1.1.1.1", LocalDateTime.now()),
                new OnlineUser(2, "小红", "1.2.2.2", LocalDateTime.now())
        );
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes = objectMapper.writeValueAsBytes(users);
        ByteArrayInputStream ins = new ByteArrayInputStream(bytes);
        return Result.data(MinioUtil.uploadObject("test", "users", ins));
    }

}
