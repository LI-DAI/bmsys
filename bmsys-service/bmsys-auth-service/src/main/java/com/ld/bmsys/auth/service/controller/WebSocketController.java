package com.ld.bmsys.auth.service.controller;

import com.ld.bmsys.auth.service.websocket.WebSocketServer;
import com.ld.bmsys.common.entity.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author LD
 * @date 2021/4/19 15:39
 */
@RestController
@RequestMapping("/webSocket")
@Api(tags = "socket demo")
public class WebSocketController {

    @PostMapping("/push")
    public Result<String> pushInfo(@RequestParam(value = "cid", required = false) String cid, @RequestParam("msg") String msg) {
        try {
            WebSocketServer.sendInfo(cid, msg);
            return Result.success();
        } catch (IOException e) {
            e.printStackTrace();
            return Result.fail();
        }
    }
}
