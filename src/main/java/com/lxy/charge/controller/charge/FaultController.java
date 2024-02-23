package com.lxy.charge.controller.charge;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lxy.charge.pojo.PageVo;
import com.lxy.charge.pojo.charge.Fault;
import com.lxy.charge.pojo.charge.Stack;
import com.lxy.charge.service.charge.FaultService;
import com.lxy.charge.utils.ExcelUtil;
import com.lxy.charge.utils.WrapMapper;
import com.lxy.charge.utils.Wrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/chargeManage/fault")
public class FaultController {
    private final FaultService faultService;

    @Autowired
    public FaultController(FaultService faultService) {
        this.faultService = faultService;
    }

    @GetMapping("/getFaultList")
    public Wrapper<PageInfo<Fault>> getFaultList(PageVo page, Fault fault) {
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<Fault> faultList = faultService.getFaultList(fault);
        PageInfo<Fault> faultPageInfo = new PageInfo<>(faultList);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, faultPageInfo);
    }
    @GetMapping("/getStackIdAndName")
    public Wrapper<List<Stack>> getStationIdAndName() {
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, faultService.getStackIdAndName());
    }
    @DeleteMapping("/faultDelete")
    public Wrapper<Boolean> faultDelete(Integer id) {
        Fault fault = new Fault();
        fault.setId(id);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, faultService.faultDelete(fault));
    }

    @PostMapping("/faultAdd")
    public Wrapper<Fault> faultAdd(@RequestBody Fault fault) {
        Fault faultResult = faultService.faultAdd(fault);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, faultResult);
    }

    @PostMapping("/faultEdit")
    public Wrapper<Boolean> faultEdit(@RequestBody Fault fault) {

        try {
            boolean success = faultService.faultEdit(fault);
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, success);
        } catch (DataIntegrityViolationException e) {
            // 捕获唯一性约束异常
            return WrapMapper.error(Wrapper.ERROR_CODE, "唯一性约束异常：该主管已属于其他站点！");
        } catch (Exception e) {
            // 捕获其他异常
            return WrapMapper.error(Wrapper.ERROR_CODE, "发生异常：请联系管理员！");
        }
    }

    @GetMapping("/faultDeleteByIds")
    public Wrapper<Boolean> faultDeleteByIds(@RequestParam List<Integer> ids) {
        System.out.println(ids);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, faultService.faultDeleteByIds(ids));
    }

    @GetMapping("/faultExcel")
    public void faultExcel(HttpServletResponse response) throws UnsupportedEncodingException {
        List<Fault> faultList = faultService.getFaultList(new Fault());
        //开放权限，让前端可以接收到header中的content-disposition
        response.setHeader("Access-Control-Expose-Headers","content-disposition,filename");
        System.out.println(faultList);

        ExcelUtil.exportExcel(faultList,null,"故障记录",Fault.class,"故障记录.xls",response);
    }
    @PostMapping(value = "/importFaultList")
    public Wrapper<List<Fault>> importList(@RequestPart("file") MultipartFile excel) throws Exception {
        List<Fault> list = null;
        if (!excel.isEmpty()) {
            ImportParams params = new ImportParams();
            params.setTitleRows(0);
            //params.setHeadRows(1);

            list = ExcelUtil.importExcel(excel,0,1,Fault.class);
        }
        faultService.saveBatch(list);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE,list);
    }


}
