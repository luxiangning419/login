package com.example.userlogin.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import com.example.common.bean.CodeConstant;
import com.example.common.bean.Result;
import com.example.common.bean.User;
import com.example.userlogin.serivce.UserService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 提供个客户端用户的接口
 */
// 相当于@Controller+@RequestBody
@RestController
// 访问目录
@RequestMapping("/user")
public class UserController {
    // 打印log使用
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 注册接口
     *
     * @param user 客户端传入的users数据
     * @return Result<User> 返回注册成功或者注册失败的UserBean
     */
    @ApiOperation(value = "注册用户信息")
    @PostMapping(value = "/register")
    public Result<User> register(@RequestBody User user) {
        logger.info("调用register,传入的 user: " + user.toString());
        return userService.register(user);
    }

    /**
     * 查询
     *
     * @param mail 客户端传入的user邮箱信息
     * @return Result<User> 查询到的用户信息，没有查询到返回null
     */
    @ApiOperation(value = "读取一个用户信息")
    @PostMapping(value = "/read")
    public Result<User> read(@RequestBody String mail) {
        logger.info("传入的 参数: " + mail);
        return userService.readUser(mail);
    }

    /**
     * 编辑
     *
     * @param user  客户端传入用户信息
     * @return  Result<User> 编辑结果
     */
    @ApiOperation(value = "编辑用户信息，只能编辑phone字段")
    @PostMapping(value = "/edit")
    public Result<User> edit(@RequestBody User user){
        logger.info("传入的 参数: " + JSON.toJSONString(user));
        return userService.editUser(user);
    }

    /**
     * 删除
     *
     * @param mails   删除的用户邮件
     * @return        删除结果，逻辑删除
     */
    @ApiOperation(value = "删除用户信息，可以同时删除多个")
    @PostMapping(value = "/delete")
    public Result<Boolean> delete(@RequestBody String mails){
        logger.info("传入的 参数: " + mails);
        if(StringUtils.isBlank(mails)){
            Result<Boolean> mResult = new Result<>();
            mResult.setCode(CodeConstant.FAIL);
            mResult.setMsg("参数不能为空");
            mResult.setDetail(false);
            return mResult;
        }
        return userService.deleteUsers(mails);
    }

}
