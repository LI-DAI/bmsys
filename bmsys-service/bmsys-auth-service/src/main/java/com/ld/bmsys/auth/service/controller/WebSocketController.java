package com.ld.bmsys.auth.service.controller;

import com.ld.bmsys.auth.service.websocket.WebSocketServer;
import com.ld.bmsys.common.entity.Result;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @Author LD
 * @Date 2021/4/19 15:39
 */
@RestController
@RequestMapping("/webSocket")
public class WebSocketController {

    @PostMapping("/push/{cid}")
    public Result<String> pushInfo(@PathVariable String cid, @RequestParam("msg") String msg) {
        try {
            WebSocketServer.sendInfo(cid, msg);
            return Result.success();
        } catch (IOException e) {
            e.printStackTrace();
            return Result.fail();
        }
    }
}
