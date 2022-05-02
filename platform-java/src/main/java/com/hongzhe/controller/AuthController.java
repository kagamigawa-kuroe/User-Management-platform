package com.hongzhe.controller;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.map.MapUtil;
import com.google.code.kaptcha.Producer;
import com.hongzhe.common.lang.Const;
import com.hongzhe.common.lang.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

@RestController
public class AuthController extends BaseController{
    @Autowired
    Producer producer;

    @GetMapping("/captcha")
    public Result captcha() throws IOException {
        String key = UUID.randomUUID().toString();
        String code = producer.createText();

        key = "aaaaa";
        code = "11111";

        BufferedImage image = producer.createImage(code);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image,"jpg",outputStream);
        Base64Encoder encoder = new Base64Encoder();
        String str = "data:image/jpeg;base64,";

        String base64Img = str+encoder.encode(outputStream.toByteArray());

        redisUtil.hset(Const.CAPTCHA_KET,key,code,120);
        return Result.succ(
                MapUtil.builder().put("token",key).put("codeimage",base64Img).build()
        );
    }
}
