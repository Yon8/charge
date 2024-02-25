package com.lxy.charge.controller.system;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lxy.charge.pojo.PageVo;
import com.lxy.charge.pojo.system.SysRole;

import com.lxy.charge.service.system.SysRoleService;
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
@RequestMapping("/systemManage/sysRole")
public class SysRoleController {
  



    private final SysRoleService sysRoleService;

    @Autowired
    public SysRoleController(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    @GetMapping("/getSysRoleList")
    public Wrapper<PageInfo<SysRole>> getSysRoleList(PageVo page, SysRole sysRole) {
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<SysRole> sysRoleList = sysRoleService.getSysRoleList(sysRole);
        PageInfo<SysRole> sysRolePageInfo = new PageInfo<>(sysRoleList);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, sysRolePageInfo);
    }
    @GetMapping("/getRoleIdAndName")
    public Wrapper<List<SysRole>> getRoleIdAndName() {
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, sysRoleService.getRoleIdAndName());
    }
    @DeleteMapping("/sysRoleDelete")
    public Wrapper<Boolean> sysRoleDelete(Integer id) {
        SysRole sysRole = new SysRole();
        sysRole.setId(id);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, sysRoleService.sysRoleDelete(sysRole));
    }

    @PostMapping("/sysRoleAdd")
    public Wrapper<SysRole> sysRoleAdd(@RequestBody SysRole sysRole) {
        SysRole sysRoleResult = sysRoleService.sysRoleAdd(sysRole);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, sysRoleResult);
    }

    @PostMapping("/sysRoleEdit")
    public Wrapper<Boolean> sysRoleEdit(@RequestBody SysRole sysRole) {

        try {
            boolean success = sysRoleService.sysRoleEdit(sysRole);
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, success);
        } catch (DataIntegrityViolationException e) {
            // 捕获唯一性约束异常
            return WrapMapper.error(Wrapper.ERROR_CODE, "唯一性约束异常：该主管已属于其他站点！");
        } catch (Exception e) {
            // 捕获其他异常
            return WrapMapper.error(Wrapper.ERROR_CODE, "发生异常：请联系管理员！");
        }
    }

    @GetMapping("/sysRoleDeleteByIds")
    public Wrapper<Boolean> sysRoleDeleteByIds(@RequestParam List<Integer> ids) {
        System.out.println(ids);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, sysRoleService.sysRoleDeleteByIds(ids));
    }

    @GetMapping("/sysRoleExcel")
    public void sysRoleExcel(HttpServletResponse response) throws UnsupportedEncodingException {
        List<SysRole> sysRoleList = sysRoleService.getSysRoleList(new SysRole());
        //开放权限，让前端可以接收到header中的content-disposition
        response.setHeader("Access-Control-Expose-Headers","content-disposition,filename");
        System.out.println(sysRoleList);

        ExcelUtil.exportExcel(sysRoleList,null,"系统角色",SysRole.class,"系统角色.xls",response);
    }
    @PostMapping(value = "/importSysRoleList")
    public Wrapper<List<SysRole>> importList(@RequestPart("file") MultipartFile excel) throws Exception {
        List<SysRole> list = null;
        if (!excel.isEmpty()) {
            ImportParams params = new ImportParams();
            params.setTitleRows(0);
            //params.setHeadRows(1);

            list = ExcelUtil.importExcel(excel,0,1,SysRole.class);
        }
        sysRoleService.saveBatch(list);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE,list);
    }

}