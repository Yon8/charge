package com.lxy.charge.controller.system;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lxy.charge.pojo.PageVo;
import com.lxy.charge.pojo.system.LoginRecord;
import com.lxy.charge.service.system.LoginRecordService;
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
@RequestMapping("/systemManage/loginRecord")
public class LoginRecordController {
  



    private final LoginRecordService loginRecordService;

    @Autowired
    public LoginRecordController(LoginRecordService loginRecordService) {
        this.loginRecordService = loginRecordService;
    }

    @GetMapping("/getLoginRecordList")
    public Wrapper<PageInfo<LoginRecord>> getLoginRecordList(PageVo page, LoginRecord loginRecord) {
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<LoginRecord> loginRecordList = loginRecordService.getLoginRecordList(loginRecord);
        PageInfo<LoginRecord> loginRecordPageInfo = new PageInfo<>(loginRecordList);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, loginRecordPageInfo);
    }
    @GetMapping("/getRoleIdAndName")
    public Wrapper<List<LoginRecord>> getRoleIdAndName() {
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, loginRecordService.getRoleIdAndName());
    }
    @DeleteMapping("/loginRecordDelete")
    public Wrapper<Boolean> loginRecordDelete(Integer id) {
        LoginRecord loginRecord = new LoginRecord();
        loginRecord.setId(id);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, loginRecordService.loginRecordDelete(loginRecord));
    }

    @PostMapping("/loginRecordAdd")
    public Wrapper<LoginRecord> loginRecordAdd(@RequestBody LoginRecord loginRecord) {
        LoginRecord loginRecordResult = loginRecordService.loginRecordAdd(loginRecord);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, loginRecordResult);
    }

    @PostMapping("/loginRecordEdit")
    public Wrapper<Boolean> loginRecordEdit(@RequestBody LoginRecord loginRecord) {

        try {
            boolean success = loginRecordService.loginRecordEdit(loginRecord);
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, success);
        } catch (DataIntegrityViolationException e) {
            // 捕获唯一性约束异常
            return WrapMapper.error(Wrapper.ERROR_CODE, "唯一性约束异常：该主管已属于其他站点！");
        } catch (Exception e) {
            // 捕获其他异常
            return WrapMapper.error(Wrapper.ERROR_CODE, "发生异常：请联系管理员！");
        }
    }

    @GetMapping("/loginRecordDeleteByIds")
    public Wrapper<Boolean> loginRecordDeleteByIds(@RequestParam List<Integer> ids) {
        System.out.println(ids);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, loginRecordService.loginRecordDeleteByIds(ids));
    }

    @GetMapping("/loginRecordExcel")
    public void loginRecordExcel(HttpServletResponse response) throws UnsupportedEncodingException {
        List<LoginRecord> loginRecordList = loginRecordService.getLoginRecordList(new LoginRecord());
        //开放权限，让前端可以接收到header中的content-disposition
        response.setHeader("Access-Control-Expose-Headers","content-disposition,filename");
        System.out.println(loginRecordList);

        ExcelUtil.exportExcel(loginRecordList,null,"登录日记",LoginRecord.class,"登陆日记.xls",response);
    }
    @PostMapping(value = "/importLoginRecordList")
    public Wrapper<List<LoginRecord>> importList(@RequestPart("file") MultipartFile excel) throws Exception {
        List<LoginRecord> list = null;
        if (!excel.isEmpty()) {
            ImportParams params = new ImportParams();
            params.setTitleRows(0);
            //params.setHeadRows(1);

            list = ExcelUtil.importExcel(excel,0,1,LoginRecord.class);
        }
        loginRecordService.saveBatch(list);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE,list);
    }

}