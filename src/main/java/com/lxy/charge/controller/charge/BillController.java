package com.lxy.charge.controller.charge;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lxy.charge.pojo.PageVo;
import com.lxy.charge.pojo.charge.Bill;
import com.lxy.charge.pojo.charge.Stack;
import com.lxy.charge.pojo.charge.Station;
import com.lxy.charge.pojo.charge.User;
import com.lxy.charge.service.charge.BillService;
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
@RequestMapping("/chargeManage/bill")
public class BillController {
    private final BillService billService;

    @Autowired
    public BillController(BillService billService) {
        this.billService = billService;
    }

    @GetMapping("/getBillList")
    public Wrapper<PageInfo<Bill>> getBillList(PageVo page, Bill bill) {
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<Bill> billList = billService.getBillList(bill);
        PageInfo<Bill> billPageInfo = new PageInfo<>(billList);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, billPageInfo);
    }
    @GetMapping("/getStationIdAndName")
    public Wrapper<List<Station>> getStationIdAndName() {
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, billService.getStationIdAndName());
    }
    @GetMapping("/getStackIdAndName")
    public Wrapper<List<Stack>>  getStackIdAndName() {
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, billService.getStackIdAndName());
    }
    @GetMapping("/getUserIdAndName")
    public Wrapper<List<User>> getUserIdAndName() {
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, billService.getUserIdAndName());
    }
    @DeleteMapping("/billDelete")
    public Wrapper<Boolean> billDelete(Integer id) {
        Bill bill = new Bill();
        bill.setId(id);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, billService.billDelete(bill));
    }

    @PostMapping("/billAdd")
    public Wrapper<Bill> billAdd(@RequestBody Bill bill) {
        Bill billResult = billService.billAdd(bill);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, billResult);
    }

    @PostMapping("/billEdit")
    public Wrapper<Boolean> billEdit(@RequestBody Bill bill) {

        try {
            boolean success = billService.billEdit(bill);
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, success);
        } catch (DataIntegrityViolationException e) {
            // 捕获唯一性约束异常
            return WrapMapper.error(Wrapper.ERROR_CODE, "唯一性约束异常：该主管已属于其他站点！");
        } catch (Exception e) {
            // 捕获其他异常
            return WrapMapper.error(Wrapper.ERROR_CODE, "发生异常：请联系管理员！");
        }
    }

    @GetMapping("/billDeleteByIds")
    public Wrapper<Boolean> billDeleteByIds(@RequestParam List<Integer> ids) {
        System.out.println(ids);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, billService.billDeleteByIds(ids));
    }

    @GetMapping("/billExcel")
    public void billExcel(HttpServletResponse response) throws UnsupportedEncodingException {
        List<Bill> billList = billService.getBillList(new Bill());
        //开放权限，让前端可以接收到header中的content-disposition
        response.setHeader("Access-Control-Expose-Headers","content-disposition,filename");
        System.out.println(billList);

        ExcelUtil.exportExcel(billList,null,"账单",Bill.class,"账单.xls",response);
    }
    @PostMapping(value = "/importBillList")
    public Wrapper<List<Bill>> importList(@RequestPart("file") MultipartFile excel) throws Exception {
        List<Bill> list = null;
        if (!excel.isEmpty()) {
            ImportParams params = new ImportParams();
            params.setTitleRows(0);
            //params.setHeadRows(1);

            list = ExcelUtil.importExcel(excel,0,1,Bill.class);
        }
        billService.saveBatch(list);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE,list);
    }


}