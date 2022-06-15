package com.example.usermanage.controller;

import com.example.common.bean.Mail;
import com.example.common.bean.Result;
import com.example.usermanage.serivce.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 提供个客户端用户的接口
 */
// 相当于@Controller+@RequestBody
@RestController
// 访问目录
@RequestMapping("/mail")
public class MailController {
    // 打印log使用
    private static final Logger logger = LoggerFactory.getLogger(MailController.class);

    @Autowired
    private MailService mailService;



    /**
     * 查询
     *
     * @return Result<List<Mail>> 所有发送的邮件
     */
    @PostMapping(value = "/list")
    public Result<List<Mail>> list() {

        Result<List<Mail>> result = new Result<List<Mail>>();
        List<Mail> list = mailService.getMail();
        result.setDetail(list);
        return result;
    }

}
