package fit.xiaozhang.blog.controller;


import fit.xiaozhang.blog.annotation.OptLog;
import fit.xiaozhang.blog.constant.StatusConst;
import fit.xiaozhang.blog.dto.PageDTO;
import fit.xiaozhang.blog.dto.UserOnlineDTO;
import fit.xiaozhang.blog.service.UserInfoService;
import fit.xiaozhang.blog.util.Result;
import fit.xiaozhang.blog.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import static fit.xiaozhang.blog.constant.OptTypeConst.UPDATE;

/**
 * 处理用户信息
 *
 * @author zhangzhi
 */
@Api(tags = "用户信息模块")
@RestController
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation(value = "修改用户资料")
    @PutMapping("/users/info")
    public Result updateUserInfo(@Valid @RequestBody UserInfoVO userInfoVO) {
        userInfoService.updateUserInfo(userInfoVO);
        return new Result<>(true, StatusConst.OK, "修改成功！");
    }

    @ApiOperation(value = "修改用户头像")
    @ApiImplicitParam(name = "file", value = "用户头像", required = true, dataType = "MultipartFile")
    @PostMapping("/users/avatar")
    public Result<String> updateUserInfo(MultipartFile file) {
        return new Result<>(true, StatusConst.OK, "修改成功！", userInfoService.updateUserAvatar(file));
    }

    @ApiOperation(value = "绑定用户邮箱")
    @PostMapping("/users/email")
    public Result saveUserEmail(@Valid @RequestBody EmailVO emailVO) {
        userInfoService.saveUserEmail(emailVO);
        return new Result(true, StatusConst.OK, "绑定成功！");
    }

    @OptLog(optType = UPDATE)
    @ApiOperation(value = "修改用户角色")
    @PutMapping("/admin/users/role")
    public Result<String> updateUserRole(@Valid @RequestBody UserRoleVO userRoleVO) {
        userInfoService.updateUserRole(userRoleVO);
        return new Result<>(true, StatusConst.OK, "修改成功！");
    }

    @OptLog(optType = UPDATE)
    @ApiOperation(value = "修改用户禁用状态")
    @PutMapping("/admin/users/disable/{userInfoId}")
    public Result updateUserSilence(@PathVariable("userInfoId") Integer userInfoId, Integer isDisable) {
        userInfoService.updateUserDisable(userInfoId, isDisable);
        return new Result<>(true, StatusConst.OK, "修改成功！");
    }

    @ApiOperation(value = "查看在线用户")
    @GetMapping("/admin/users/online")
    public Result<PageDTO<UserOnlineDTO>> listOnlineUsers(ConditionVO conditionVO) {
        return new Result<>(true, StatusConst.OK, "查询成功！", userInfoService.listOnlineUsers(conditionVO));
    }

    @ApiOperation(value = "下线用户")
    @DeleteMapping("/admin/users/online/{userInfoId}")
    public Result removeOnlineUser(@PathVariable("userInfoId") Integer userInfoId) {
        userInfoService.removeOnlineUser(userInfoId);
        return new Result<>(true, StatusConst.OK, "操作成功！");
    }
}

