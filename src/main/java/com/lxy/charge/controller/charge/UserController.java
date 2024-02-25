package com.lxy.charge.controller.charge;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lxy.charge.pojo.PageVo;
import com.lxy.charge.pojo.charge.User;
import com.lxy.charge.service.charge.UserService;
import com.lxy.charge.utils.ExcelUtil;
import com.lxy.charge.utils.WrapMapper;
import com.lxy.charge.utils.Wrapper;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/chargeManage/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getUserList")
    public Wrapper<PageInfo<User>> getUserList(PageVo page, User user) {
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<User> userList = userService.getUserList(user);
        PageInfo<User> userPageInfo = new PageInfo<>(userList);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, userPageInfo);
    }
    @DeleteMapping("/userDelete")
    public Wrapper<Boolean> userDelete(Integer id) {
        User user = new User();
        user.setId(id);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, userService.userDelete(user));
    }

    @PostMapping("/userAdd")
    public Wrapper<User> userAdd(@RequestBody User user) {
        User userResult = userService.userAdd(user);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, userResult);
    }

    @PostMapping("/userEdit")
    public Wrapper<Boolean> userEdit(@RequestBody User user) {

        try {
            boolean success = userService.userEdit(user);
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, success);
        } catch (DataIntegrityViolationException e) {
            // 捕获唯一性约束异常
            return WrapMapper.error(Wrapper.ERROR_CODE, "唯一性约束异常：该主管已属于其他站点！");
        } catch (Exception e) {
            // 捕获其他异常
            return WrapMapper.error(Wrapper.ERROR_CODE, "发生异常：请联系管理员！");
        }
    }

    @GetMapping("/userDeleteByIds")
    public Wrapper<Boolean> userDeleteByIds(@RequestParam List<Integer> ids) {
        System.out.println(ids);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, userService.userDeleteByIds(ids));
    }

    @GetMapping("/userExcel")
    public void userExcel(HttpServletResponse response) throws UnsupportedEncodingException {
        List<User> userList = userService.getUserList(new User());
        //开放权限，让前端可以接收到header中的content-disposition
        response.setHeader("Access-Control-Expose-Headers","content-disposition,filename");
        System.out.println(userList);

        ExcelUtil.exportExcel(userList,null,"用户",User.class,"用户.xls",response);
    }
    @PostMapping(value = "/importUserList")
    public Wrapper<List<User>> importList(@RequestPart("file") MultipartFile excel) throws Exception {
        List<User> list = null;
        if (!excel.isEmpty()) {
            ImportParams params = new ImportParams();
            params.setTitleRows(0);
            //params.setHeadRows(1);

            list = ExcelUtil.importExcel(excel,0,1,User.class);
        }
        userService.saveBatch(list);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE,list);
    }


}
