package com.yoogun.chart.application.controller;

import com.yoogun.core.application.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 权限-菜单-控制层
 * @author Liu Jun at 2016-8-11 21:24:18
 * @since v1.0.0
 */
@Controller
@RequestMapping(value = "**/chart/demo")
class DemoController extends BaseController {

    /**
     * 线形图首页
     */
    @RequestMapping(value = { "/lineHomeView" })
    public String lineHome() {
        return pageView("/chart/demo/lineHome");
    }

    /**
     * 资产数据首页
     */
    @RequestMapping(value = { "/assetHomeView" })
    public String assetHome() {
        return pageView("/chart/demo/assetHome");
    }

    /**
     * 大额资金首页
     */
    @RequestMapping(value = { "/amountHomeView" })
    public String amountHome() {
        return pageView("/chart/demo/largeAmountHome");
    }


    /**
     * 合同采购额首页
     */
    @RequestMapping(value = { "/procurementView" })
    public String procurementHome() {
        return pageView("/chart/demo/procurementHome");
    }


    /**
     * 销售收入情况
     */
    @RequestMapping(value = { "/incomeHomeView" })
    public String incomeHome() {
        return pageView("/chart/demo/incomeHome");
    }

    /**
     * 采购合同情况
     */
    @RequestMapping(value = { "/purchaseHomeView" })
    public String purchaseHome() {
        return pageView("/chart/demo/purchaseHome");
    }

    /**
     * 能源线形图首页
     */
    @RequestMapping(value = { "/energyHomeView" })
    public String energyHome() {
        return pageView("/chart/demo/energyHome");
    }

    /**
     * 国资委出资企业资金监管演示
     */
    @RequestMapping(value = { "/moneyManHome" })
    public String moneyManHome() {
        return pageView("/chart/demo/moneyManHome");
    }

}