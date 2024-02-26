package com.lxy.charge.controller.charge;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lxy.charge.pojo.PageVo;
import com.lxy.charge.pojo.charge.Warden;
import com.lxy.charge.service.charge.WardenService;
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
@RequestMapping("/chargeManage/warden")
public class WardenController {
    private final WardenService wardenService;

    @Autowired
    public WardenController(WardenService wardenService) {
        this.wardenService = wardenService;
    }

    @GetMapping("/getWardenList")
    public Wrapper<PageInfo<Warden>> getWardenList(PageVo page, Warden warden) {
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<Warden> wardenList = wardenService.getWardenList(warden);
        PageInfo<Warden> wardenPageInfo = new PageInfo<>(wardenList);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, wardenPageInfo);
    }
    //用于添加Warden时的站点选择
    @GetMapping("/getWardenIdAndName")
    public Wrapper<List<Warden>> getWardenIdAndName() {
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, wardenService.getWardenIdAndName());
    }

    @DeleteMapping("/wardenDelete")
    public Wrapper<Boolean> wardenDelete(Integer id) {
        Warden warden = new Warden();
        warden.setId(id);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, wardenService.wardenDelete(warden));
    }

    @PostMapping("/wardenAdd")
    public Wrapper<Warden> wardenAdd(@RequestBody Warden warden) {
        Warden wardenResult = wardenService.wardenAdd(warden);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, wardenResult);
    }

    @PostMapping("/wardenEdit")
    public Wrapper<Boolean> wardenEdit(@RequestBody Warden warden) {
        try {
            // 调用 wardenService.wardenEdit 方法，该方法可能触发唯一性约束异常
            boolean success = wardenService.wardenEdit(warden);
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, success);
        } catch (DataIntegrityViolationException e) {
            // 捕获唯一性约束异常
            return WrapMapper.error(Wrapper.ERROR_CODE, "唯一性约束异常：该充电站已分配主管！");
        } catch (Exception e) {
            // 捕获其他异常
            return WrapMapper.error(Wrapper.ERROR_CODE, "发生异常：请联系管理员！");
        }
    }


    @GetMapping("/wardenDeleteByIds")
    public Wrapper<Boolean> wardenDeleteByIds(@RequestParam List<Integer> ids) {
        System.out.println(ids);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, wardenService.wardenDeleteByIds(ids));
    }

    @GetMapping("/wardenExcel")
    public void wardenExcel(HttpServletResponse response) throws UnsupportedEncodingException {
        List<Warden> wardenList = wardenService.getWardenList(new Warden());
        //开放权限，让前端可以接收到header中的content-disposition
        response.setHeader("Access-Control-Expose-Headers","content-disposition,filename");
        System.out.println(wardenList);

        ExcelUtil.exportExcel(wardenList,null,"充电桩管理员",Warden.class,"充电桩管理员.xls",response);
    }
    @PostMapping(value = "/importWardenList")
    public Wrapper<List<Warden>> importList(@RequestPart("file") MultipartFile excel) throws Exception {
        List<Warden> list = null;
        if (!excel.isEmpty()) {
            ImportParams params = new ImportParams();
            params.setTitleRows(0);
            //params.setHeadRows(1);

            list = ExcelUtil.importExcel(excel,0,1,Warden.class);
        }
        wardenService.saveBatch(list);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE,list);
    }


}
