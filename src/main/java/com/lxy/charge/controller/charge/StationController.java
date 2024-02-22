package com.lxy.charge.controller.charge;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lxy.charge.pojo.PageVo;
import com.lxy.charge.pojo.charge.Station;
import com.lxy.charge.pojo.charge.Warden;
import com.lxy.charge.service.charge.StationService;
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
@RequestMapping("/chargeManage/station")
public class StationController {
    private final StationService stationService;

    @Autowired
    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping("/getStationList")
    public Wrapper<PageInfo<Station>> getStationList(PageVo page, Station station) {
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<Station> stationList = stationService.getStationList(station);
        PageInfo<Station> stationPageInfo = new PageInfo<>(stationList);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, stationPageInfo);
    }
    //用于添加Station时的管理员选择
    @GetMapping("/getWardenIdAndName")
    public Wrapper<List<Warden>> getWardenIdAndName() {
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, stationService.getWardenIdAndName());
    }
    @DeleteMapping("/stationDelete")
    public Wrapper<Boolean> stationDelete(Integer id) {
        Station station = new Station();
        station.setId(id);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, stationService.stationDelete(station));
    }

    @PostMapping("/stationAdd")
    public Wrapper<Station> stationAdd(@RequestBody Station station) {
        Station stationResult = stationService.stationAdd(station);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, stationResult);
    }

    @PostMapping("/stationEdit")
    public Wrapper<Boolean> stationEdit(@RequestBody Station station) {

        try {
            boolean success = stationService.stationEdit(station);
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, success);
        } catch (DataIntegrityViolationException e) {
            // 捕获唯一性约束异常
            return WrapMapper.error(Wrapper.ERROR_CODE, "唯一性约束异常：该主管已属于其他站点！");
        } catch (Exception e) {
            // 捕获其他异常
            return WrapMapper.error(Wrapper.ERROR_CODE, "发生异常：请联系管理员！");
        }
    }

    @GetMapping("/stationDeleteByIds")
    public Wrapper<Boolean> stationDeleteByIds(@RequestParam List<Integer> ids) {
        System.out.println(ids);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, stationService.stationDeleteByIds(ids));
    }

    @GetMapping("/stationExcel")
    public void stationExcel(HttpServletResponse response) throws UnsupportedEncodingException {
        List<Station> stationList = stationService.getStationList(new Station());
        //开放权限，让前端可以接收到header中的content-disposition
        response.setHeader("Access-Control-Expose-Headers","content-disposition,filename");
        System.out.println(stationList);

        ExcelUtil.exportExcel(stationList,null,"充电桩管理员",Station.class,"充电桩管理员.xls",response);
    }
    @PostMapping(value = "/importStationList")
    public Wrapper<List<Station>> importList(@RequestPart("file") MultipartFile excel) throws Exception {
        List<Station> list = null;
        if (!excel.isEmpty()) {
            ImportParams params = new ImportParams();
            params.setTitleRows(0);
            //params.setHeadRows(1);

            list = ExcelUtil.importExcel(excel,0,1,Station.class);
        }
        stationService.saveBatch(list);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE,list);
    }


}
