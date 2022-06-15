package com.example.userlogin.serivce;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.common.bean.CodeConstant;
import com.example.common.bean.Result;
import com.example.userlogin.dao.UserMapper;
import com.example.userlogin.tool.KafkaSender;
import com.example.userlogin.tool.MailTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.common.bean.User;
import java.util.HashMap;
import java.util.List;

/**
 * User注册和登录的业务逻辑都这个类了
 * 你可以修改这个类的逻辑或者扩展，但先保证先跑通了，防止其他问题干扰的业务逻辑代码。老手直接开始好吧。
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class UserService {

    @Autowired
    private UserMapper userMapper;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private KafkaSender sender;
    @Value("${kafka.topic.regist_topic}")
    private String welcomeTopic;

    /**
     * 注册
     *
     * @param user 用户名和密码
     * @return Result 带有结果
     */
    public Result<User> register(User user) {
        Result<User> mResult = new Result<>();
        User selectUser = selectUser(user);

        // 判断是否注册
        if (selectUser == null) {
            boolean mailCheck = MailTool.checkEmail(user.getEmail());
            if(!mailCheck){
                mResult.setCode(CodeConstant.FAIL);
                mResult.setMsg("邮箱格式错误");
                return mResult;
            }
            // 没有注册，执行注册，返回成功
            int insert = userMapper.insert(user);
            if (insert >= 1) {
                mResult.setCode(CodeConstant.SUCCESS);
                mResult.setMsg("账号注册成功");
                mResult.setDetail(selectUser(user));
                sender.sendAsynchronize(welcomeTopic, JSONObject.toJSONString(user));
            }
        } else {
            // 已经注册，直接返回账号已经注册
            mResult.setCode(-1);
            mResult.setMsg("账号已经注册");
            mResult.setDetail(selectUser(user));
        }
        return mResult;
    }

    /**
     * 登录
     *
     * @param user 用户名信息
     * @return Result带有结果
     */
    public Result<User> editUser(User user) {
        Result<User> mResult = new Result<>();
        User selectUser = selectUser(user);
        if(selectUser == null){
            // 不满足，登录失败
            mResult.setCode(CodeConstant.FAIL);
            mResult.setMsg("用户不存在");
            mResult.setDetail(null);
            return mResult;
        }
        ////只能修改此信息，其他的不能修改
        LambdaUpdateWrapper<User> userUpdateWrapper = new LambdaUpdateWrapper<>();
        userUpdateWrapper.set(User::getPhone, user.getPhone());

        userUpdateWrapper.eq(User::getEmail, user.getEmail());
        userMapper.update(null, userUpdateWrapper);
        mResult.setCode(CodeConstant.SUCCESS);
        mResult.setMsg("更新成功");
        mResult.setDetail(null);
        return mResult;
    }

    /**
     * 查询一个用户
     * @param mail  用户邮箱
     * @return      查询结果
     */
    public Result<User> readUser(String mail) {
        Result<User> mResult = new Result<>();

        HashMap<String, Object> map = new HashMap<>();
        map.put("mail", mail);
        List<User> users = userMapper.selectByMap(map);
        if(users.size() == 0) {
            // 满足，登录成功
            mResult.setCode(CodeConstant.FAIL);
            mResult.setMsg("查询失败");
            mResult.setDetail(null);
        }else {
            // 满足，登录成功
            mResult.setCode(CodeConstant.SUCCESS);
            mResult.setMsg("查询成功");
            mResult.setDetail(users.get(0));
        }
        return mResult;
    }

    /**
     * 通过邮箱删除用户
     * @param mails     邮箱列表
     * @return          删除结果
     */
    public Result<Boolean> deleteUsers(String mails){

        String[] mailGroup = mails.split(",");
        QueryWrapper<User> query = new QueryWrapper<>();
        query.in("mail",mailGroup);
        int c = userMapper.selectCount(query);

        Result<Boolean> mResult = new Result<>();
        if(c != mailGroup.length){
            mResult.setCode(CodeConstant.FAIL);
            mResult.setMsg("查询失败");
            mResult.setDetail(false);
            return mResult;
        }

        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .in(User::getEmail, mailGroup)
                .set(User::getStatus,1);
        int rt = userMapper.update(null,updateWrapper);
        if(rt == mailGroup.length){
            mResult.setCode(CodeConstant.SUCCESS);
            mResult.setMsg("更新成功");
            mResult.setDetail(true);
            return mResult;
        }
        mResult.setCode(CodeConstant.FAIL);
        mResult.setMsg("更新失败");
        mResult.setDetail(false);
        return mResult;
    }




    // 查询数据库是否存在该用户
    private User selectUser(User user) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("email", user.getEmail());
        List<User> users = userMapper.selectByMap(map);
        if (users.size() == 0) {
            return null;
        }
        logger.info("register user: " + users.toString());
        return users.get(0);
    }
}
