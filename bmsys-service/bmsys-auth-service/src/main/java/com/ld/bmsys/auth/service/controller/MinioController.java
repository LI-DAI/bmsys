package com.ld.bmsys.auth.service.controller;

import com.ld.bmsys.auth.service.utils.MinioUtil;
import com.ld.bmsys.common.entity.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author LD
 * @Date 2021/4/22 17:38
 */
@RestController
@RequestMapping("/minio")
@Api(tags = "Minio测试")
public class MinioController {

    @PostMapping("/upload")
    @ApiOperation("上传对象")
    public Result<String> uploadObject(@RequestPart("file") MultipartFile file) throws Exception {
        return Result.data(MinioUtil.upload(file));
    }
}
