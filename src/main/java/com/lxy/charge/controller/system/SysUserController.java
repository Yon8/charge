package com.lxy.charge.controller.system;


import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lxy.charge.pojo.PageVo;
import com.lxy.charge.pojo.system.SysUser;

import com.lxy.charge.service.system.SysUserService;

import com.lxy.charge.utils.ExcelUtil;
import com.lxy.charge.utils.WrapMapper;
import com.lxy.charge.utils.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/systemManage/sysUser")
public class SysUserController {


    private final SysUserService sysUserService;

    @Autowired
    public SysUserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }


    @GetMapping("/getSysUserList")
    public Wrapper<PageInfo<SysUser>> getSysUserList(PageVo page, SysUser sysUser) {
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<SysUser> sysUserList = sysUserService.getSysUserList(sysUser);
        PageInfo<SysUser> sysUserPageInfo = new PageInfo<>(sysUserList);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, sysUserPageInfo);
    }
    @GetMapping("/getUserIdAndName")
    public Wrapper<List<SysUser>> getUserIdAndName() {
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, sysUserService.getUserIdAndName());
    }
    @DeleteMapping("/sysUserDelete")
    public Wrapper<Boolean> sysUserDelete(Integer id) {
        SysUser sysUser = new SysUser();
        sysUser.setId(id);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, sysUserService.sysUserDelete(sysUser));
    }

    @PostMapping("/sysUserAdd")
    public Wrapper<SysUser> sysUserAdd(@RequestBody SysUser sysUser) {
        SysUser sysUserResult = sysUserService.sysUserAdd(sysUser);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, sysUserResult);
    }

    @PostMapping("/sysUserEdit")
    public Wrapper<Boolean> sysUserEdit(@RequestBody SysUser sysUser) {

        try {
            boolean success = sysUserService.sysUserEdit(sysUser);
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, success);
        } catch (DataIntegrityViolationException e) {
            // 捕获唯一性约束异常
            return WrapMapper.error(Wrapper.ERROR_CODE, "唯一性约束异常：该主管已属于其他站点！");
        } catch (Exception e) {
            // 捕获其他异常
            return WrapMapper.error(Wrapper.ERROR_CODE, "发生异常：请联系管理员！");
        }
    }

    @GetMapping("/sysUserDeleteByIds")
    public Wrapper<Boolean> sysUserDeleteByIds(@RequestParam List<Integer> ids) {
        System.out.println(ids);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, sysUserService.sysUserDeleteByIds(ids));
    }

    @GetMapping("/sysUserExcel")
    public void sysUserExcel(HttpServletResponse response) throws UnsupportedEncodingException {
        List<SysUser> sysUserList = sysUserService.getSysUserList(new SysUser());
        //开放权限，让前端可以接收到header中的content-disposition
        response.setHeader("Access-Control-Expose-Headers","content-disposition,filename");
        System.out.println(sysUserList);

        ExcelUtil.exportExcel(sysUserList,null,"系统管理员",SysUser.class,"系统管理员.xls",response);
    }
    @PostMapping(value = "/importSysUserList")
    public Wrapper<List<SysUser>> importList(@RequestPart("file") MultipartFile excel) throws Exception {
        List<SysUser> list = null;
        if (!excel.isEmpty()) {
            ImportParams params = new ImportParams();
            params.setTitleRows(0);
            //params.setHeadRows(1);

            list = ExcelUtil.importExcel(excel,0,1,SysUser.class);
        }
        sysUserService.saveBatch(list);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE,list);
    }


}