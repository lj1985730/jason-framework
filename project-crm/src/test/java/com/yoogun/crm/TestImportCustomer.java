package com.yoogun.crm;

import com.yoogun.auth.application.service.PermissionService;
import com.yoogun.crm.admin.domain.model.Contact;
import com.yoogun.crm.admin.domain.model.Customer;
import com.yoogun.crm.admin.repository.ContactDao;
import com.yoogun.crm.admin.repository.CustomerDao;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-context.xml"})
@Transactional(transactionManager = "transactionManager")
@Rollback(value = false)
public class TestImportCustomer {

    private static final String tenantId = "BF707CC2-51A5-11E8-A62A-00163E064C5A";

    @Resource
    private ContactDao contactDao;

    @Resource
    private CustomerDao customerDao;

    @Test
    public void testLoadExcel() {
        List<Map<Integer, String>> datas = loadExcel();
        if(datas == null) {
            return;
        }
        Map<String, Customer> customers = new HashMap<>();
        List<Contact> contacts = new ArrayList<>();
        Customer customer;
        Contact contact;
        for (Map<Integer, String> map : datas) {
            customer = map2Customer(map);
            if(!customers.containsKey(customer.getName())) {    //如果企业没有被登记，则登记企业
                customers.put(customer.getName(), customer);
            }
            customer = customers.get(customer.getName());   // 使用已登记企业

            contact = map2Contact(map);
            contact.setCustomerId(customer.getId());
            contacts.add(contact);
        }

        customers.forEach((k,v) -> customerDao.insert(v));
        contacts.forEach(contact1 -> contactDao.insert(contact1));
    }

    private Customer map2Customer(Map<Integer, String> map) {
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID().toString().toUpperCase()); //生成ID
        customer.setName(map.get(1));   //名称
        customer.setAddress(map.get(2));    //地址
        if(StringUtils.isNotBlank(map.get(5))) {    //企业性质
            customer.setNature(transEntNature(map.get(5)));
        }
        if(StringUtils.isNotBlank(map.get(6))) {    //年产值
            try {
                customer.setAnnualOutputValue(new BigDecimal(map.get(6)));
            } catch (NumberFormatException e) {
                // do nothing
            }
        }
        if(StringUtils.isNotBlank(map.get(8))) {    //主要软件品牌
            customer.setMainSoftwareBrand(transSoftwareBrand(map.get(8)));
        }

        customer.setCreateTime(LocalDateTime.now());

        customer.setTenantId(tenantId);
        customer.setLastModifyTime(LocalDateTime.now());
        customer.setLastModifyAccountId(PermissionService.superAdminId);
        customer.setDeleted(false);
        return customer;
    }

    private Contact map2Contact(Map<Integer, String> map) {
        Contact contact = new Contact();
        contact.setId(UUID.randomUUID().toString().toUpperCase()); //生成ID
        contact.setName(StringUtils.isBlank(map.get(10)) ? "-" : map.get(10));   //名称
        contact.setGender(this.transGender(map.get(11)));    //性别
        contact.setDepartment(this.transDepartment(map.get(12)));    //部门
        contact.setBackground(map.get(13)); //职务放到背景里
        contact.setOfficeNumber(map.get(14));   //座机1
        if(StringUtils.isNotBlank(map.get(15))) {
            contact.setOfficeNumber(contact.getOfficeNumber() + ";" + map.get(15)); //座机2
        }
        contact.setMobileNumber(map.get(16));   //手机
        contact.setEmail(map.get(17));   //邮箱
        contact.setWeixin(map.get(19)); //微信
        contact.setFactions(map.get(20));  //标签放进派系

        contact.setTenantId(tenantId);
        contact.setLastModifyTime(LocalDateTime.now());
        contact.setLastModifyAccountId(PermissionService.superAdminId);
        contact.setDeleted(false);
        return contact;
    }

    private List<Map<Integer, String>> loadExcel() {
        String excelPath = "C:\\Document\\CRM\\数字营销\\统一营销数据导入名单-第一版20180719.xlsx";
        FileInputStream file = null;
        XSSFWorkbook workbook = null;
        XSSFSheet sheet;
        List<Map<Integer, String>> res;
        Row row;
        Cell cell;
        int cellCount;
        Map<Integer, String> map;
        try {
            file = new FileInputStream(excelPath);
            workbook = new XSSFWorkbook(file);
            sheet = workbook.getSheet("客户名录V1.0");
            int rowCount = sheet.getLastRowNum();
            res = new ArrayList<>();
            for(int i = 1; i <= rowCount; i ++) {
                row = sheet.getRow(i);
                cellCount = row.getLastCellNum();
                map = new HashMap<>();
                for(int j = 0; j <= cellCount; j++) {
                    cell = row.getCell(j);
                    if(cell != null) {
                        Object value = getCellValue(cell);
                        map.put(j, value == null ? "" : value + "");
                    }
                }
                res.add(map);
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            IOUtils.closeQuietly(workbook);
            IOUtils.closeQuietly(file);
        }
    }

    /**
     * 获取单元格值
     * @return 单元格值
     */
    private Object getCellValue(Cell cell) {
        Object val = "";
        try {
            if(cell != null) {
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        val = cell.getStringCellValue().trim();
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        val = cell.getNumericCellValue();
                        break;
                    case Cell.CELL_TYPE_FORMULA:
                        val = cell.getCellFormula();
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        val = cell.getBooleanCellValue();
                        break;
                    case Cell.CELL_TYPE_ERROR:
                        val = cell.getErrorCellValue();
                        break;
                }
            }
        } catch (Exception e) {
            return val;
        }
        return val;
    }

    private String transSoftwareBrand(String name) {
        if(StringUtils.isBlank(name)) {
            return "";
        }
        switch (name) {
            case "SAP":
                return "1";
            case "Oracle":
                return "2";
            case "用友":
                return "3";
            case "金蝶":
                return "4";
            case "浪潮":
                return "5";
            case "新中大":
                return "6";
            case "宝信":
                return "7";
            case "其他":
                return "8";
            default:
                return "";
        }
    }


    private Integer transEntNature(String name) {
        if(StringUtils.isBlank(name)) {
            return null;
        }
        switch (name) {
            case "央企":
                return 1;
            case "军工企业":
                return 2;
            case "国企":
            case "省企":
            case "国有企业":
                return 3;
            case "民营":
                return 4;
            case "合资":
                return 5;
            case "外企":
            case "日企":
                return 6;
            case "事业单位":
            case "其他":
                return 7;
            default:
                return null;
        }
    }

    private Integer transGender(String name) {
        if(StringUtils.isBlank(name)) {
            return 3;
        }
        switch (name) {
            case "男":
                return 1;
            case "女":
                return 2;
            default:
                return 3;
        }
    }

    private Integer transDepartment(String name) {
        if(StringUtils.isBlank(name)) {
            return null;
        }
        switch (name) {
            case "高管":
                return 1;
            case "财务":
            case "财务部":
                return 2;
            case "人力资源":
            case "人力资源部":
                return 3;
            case "it":
            case "IT":
            case "信息部":
                return 4;
            case "生产部":
                return 5;
            case "采购部":
                return 6;
            case "企管":
            case "企管部":
                return 7;
            default:
                return null;
        }
    }
}
