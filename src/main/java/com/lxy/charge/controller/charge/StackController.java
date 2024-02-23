package com.lxy.charge.controller.charge;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lxy.charge.pojo.PageVo;
import com.lxy.charge.pojo.charge.Stack;
import com.lxy.charge.pojo.charge.Station;
import com.lxy.charge.pojo.charge.Warden;
import com.lxy.charge.service.charge.StackService;
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
@RequestMapping("/chargeManage/stack")
public class StackController {
    private final StackService stackService;

    @Autowired
    public StackController(StackService stackService) {
        this.stackService = stackService;
    }

    @GetMapping("/getStackList")
    public Wrapper<PageInfo<Stack>> getStackList(PageVo page, Stack stack) {
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<Stack> stackList = stackService.getStackList(stack);
        PageInfo<Stack> stackPageInfo = new PageInfo<>(stackList);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, stackPageInfo);
    }
    @GetMapping("/getStationIdAndName")
    public Wrapper<List<Station>> getStationIdAndName() {
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, stackService.getStationIdAndName());
    }
    @DeleteMapping("/stackDelete")
    public Wrapper<Boolean> stackDelete(Integer id) {
        Stack stack = new Stack();
        stack.setId(id);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, stackService.stackDelete(stack));
    }

    @PostMapping("/stackAdd")
    public Wrapper<Stack> stackAdd(@RequestBody Stack stack) {
        Stack stackResult = stackService.stackAdd(stack);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, stackResult);
    }

    @PostMapping("/stackEdit")
    public Wrapper<Boolean> stackEdit(@RequestBody Stack stack) {

        try {
            boolean success = stackService.stackEdit(stack);
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, success);
        } catch (DataIntegrityViolationException e) {
            // 捕获唯一性约束异常
            return WrapMapper.error(Wrapper.ERROR_CODE, "唯一性约束异常：该主管已属于其他站点！");
        } catch (Exception e) {
            // 捕获其他异常
            return WrapMapper.error(Wrapper.ERROR_CODE, "发生异常：请联系管理员！");
        }
    }

    @GetMapping("/stackDeleteByIds")
    public Wrapper<Boolean> stackDeleteByIds(@RequestParam List<Integer> ids) {
        System.out.println(ids);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, stackService.stackDeleteByIds(ids));
    }

    @GetMapping("/stackExcel")
    public void stackExcel(HttpServletResponse response) throws UnsupportedEncodingException {
        List<Stack> stackList = stackService.getStackList(new Stack());
        //开放权限，让前端可以接收到header中的content-disposition
        response.setHeader("Access-Control-Expose-Headers","content-disposition,filename");
        System.out.println(stackList);

        ExcelUtil.exportExcel(stackList,null,"充电桩",Stack.class,"充电桩.xls",response);
    }
    @PostMapping(value = "/importStackList")
    public Wrapper<List<Stack>> importList(@RequestPart("file") MultipartFile excel) throws Exception {
        List<Stack> list = null;
        if (!excel.isEmpty()) {
            ImportParams params = new ImportParams();
            params.setTitleRows(0);
            //params.setHeadRows(1);

            list = ExcelUtil.importExcel(excel,0,1,Stack.class);
        }
        stackService.saveBatch(list);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE,list);
    }


}
