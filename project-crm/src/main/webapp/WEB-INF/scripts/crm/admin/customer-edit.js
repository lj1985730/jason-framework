/***********************************
 * CRM-客户管理-编辑页-脚本
 * @author Liu Jun at 2018-7-12 09:07:10
 * @since v1.0.0
 * *********************************/
!function ($) {

    'use strict';

    var baseUrl = appPath + '/crm/customer';
    var $table, $editWin, $editForm;
    var $viewContactBtn, $createContactBtn, $modifyContactBtn, $deleteContactBtn, $contactTable;
    var $modifyInfoBtn, $infoTable;

    var $id;    //主键标签，用于判断主体数据是否存在，在新增联系人和附件前，需要保存主体数据

    var $createNewBtn;  //保存新数据按钮

    var $fileTable; //附件表格组件

    var $areaSelector, $provinceSelector, $citySelector, $districtSelector;

    // var $personSelectorWin, $personSelectorTable;

    var currentCustomer;

    /**
     * 初始化方法
     */
    $(function() {

        /**
         * 编辑页组件初始化
         */
        $editWin = $('#editCustomerWin');
        $editWin.on('hide.bs.modal', function() {
            $table.bootstrapTable('refresh');
        });
        $editForm = $('#editCustomerForm');
        $id = $('#id');
        $createNewBtn = $('#createNewCustomerBtn');
        $createNewBtn.off('click').on('click', function() { submitNew(); });
        $('#editCustomerWinSubmitBtn').off('click').on('click', function() { submit(); });

        initContactPart();  //初始化联系人

        initInformationizationPart();   //初始化信息化详情

        // initPersonSelectPart(); //初始化人员选择

        //下拉框初始化
        $('#edit_origin').comboData('CRM_CSR_DATA_ORIGIN', false, null, true);  //数据来源
        $('#edit_nature').comboData('COM_ENT_NATURE', false, null, true);  //企业性质
        $('#edit_industry').comboData('COM_ENT_INDUSTRY', false, null, true);  //所属行业
        $('#edit_listingStatus').comboData('COM_ENT_LISTING_STATUS', false, null, true);  //上市情况

        $areaSelector = $('#edit_area');
        $provinceSelector = $('#edit_province');
        $citySelector = $('#edit_city');
        $districtSelector = $('#edit_district');
        $areaSelector.comboData('COM_GEO_AREA', false, null, true);  //所属区域
        $areaSelector.on('change', function() { loadProvince(this.value); });   //区域级联省份
        $provinceSelector.select2({ theme : "bootstrap" }); //省份
        $provinceSelector.append('<option value="">请选择</option>');
        $provinceSelector.on('change', function() { loadCity(this.value); });   //省份级联城市
        $citySelector.select2({ theme : "bootstrap" });  //城市
        $citySelector.append('<option value="">请选择</option>');
        $citySelector.on('change', function() { loadDistrict(this.value); });   //城市级联区县
        $districtSelector.select2({ theme : "bootstrap" });  //区县
        $districtSelector.append('<option value="">请选择</option>');
        // $('#edit_province').comboData('COM_GEO_PROVINCE', false, null, true);  //所属省
        // $('#edit_city').comboData('COM_GEO_CITY', false, null, true);  //所属市
        // $('#edit_district').comboData('COM_GEO_DISTRICT', false, null, true);  //所属区县

        $('#edit_size').comboData('COM_ENT_SIZE', false, null, true);  //公司规模
        $('#edit_annualOutputScale').comboData('COM_ENT_ANNUAL_OUTPUT_SCALE', false, null, true);  //年产值规模
        $('#edit_employeeSize').comboData('COM_ENT_EMPLOYEE_SIZE', false, null, true);  //用工规模
        $('#edit_operateStatus').comboData('COM_ENT_OPERATE_STATUS', false, null, true);  //经营状况
        $('#edit_marketPosition').comboData('COM_ENT_MARKET_POSITION', false, null, true);  //市场地位
        $('#edit_potential').comboData('COM_ENT_POTENTIAL', false, null, true);  //发展潜力
        $('#edit_mainSoftwareBrand').comboData('CRM_SOFTWARE_BRAND', false, null, true);  //主要软件品牌
        $('#edit_mainSoftwareLine').comboData('CRM_PROD_LINE', false, null, true);  //主要软件产品线

        $fileTable = $('#customerDoc'); //初始化附件部分

        $editForm.validation(); //初始化表单验证，需要在各种三方组件之后初始化，以保证监听正确的元素

    });

    /**
     * 初始化联系人部分
     */
    var initContactPart = function() {

        $viewContactBtn = $('#viewContact');
        $createContactBtn = $('#createContact');
        $modifyContactBtn = $('#modifyContact');
        $deleteContactBtn = $('#deleteContact');

        // 初始化联系人table
        $contactTable = $('#contactTable');
        $contactTable.bootstrapTable(
            $.extend(
                {
                    url : '',
                    formatSearch : function () {
                        return '搜索姓名';
                    }
                },
                $.extend({}, generalTableOption, { queryParams: commonParams })
            )
        );

        $viewContactBtn.off('click').on('click', function() {
            $.fn.showViewContactWin($contactTable);
        });

        // 初始化按钮
        // $('#viewContact').off('click').on('click', function() { $.fn.showViewContactWin($table); });
        $createContactBtn.off('click').on('click', function() {
            if(!$id.val()) {
                SysMessage.alertWarning('请先保存客户基本信息！');
                return false;
            }
            $.fn.showEditContactWin(0, $contactTable);
        });
        $modifyContactBtn.off('click').on('click', function() { $.fn.showEditContactWin(1, $contactTable); } );
        $deleteContactBtn.off('click').on('click', function() { deleteContact(); });
    };

    /**
     * 初始化信息化详情部分
     */
    var initInformationizationPart = function() {

        $modifyInfoBtn = $('#modifyInfoBtn');

        // 初始化联系人table
        $infoTable = $('#informationizationTable');
        $infoTable.bootstrapTable(
            $.extend(
                {
                    url : ''
                },
                $.extend({}, generalTableOption, { queryParams: commonParams })
            )
        );
        $modifyInfoBtn.off('click').on('click', function() { $.fn.showEditInfoWin($infoTable); });
    };

    /**
     * 显示编辑窗口
     * @param saveOrUpdate	0 新增；1 修改
     * @param $outTable	数据源table
     */
    $.fn.showEditCustomerWin = function(saveOrUpdate, $outTable) {
        $table = $outTable;
        try {
            clearForm();    //清空表单
            if(saveOrUpdate === 1) {
                var row = $table.bootstrapTable('getSelections')[0];
                if (!row) {
                    SysMessage.alertNoSelection();
                    return false;
                }
                //字符串转数组
                if (typeof row.mainSoftwareBrand === 'string') {
                    row.mainSoftwareBrand = row.mainSoftwareBrand.split(',');
                }
                if (typeof row.mainSoftwareLine === 'string') {
                    row.mainSoftwareLine = row.mainSoftwareLine.split(',');
                }
                fillForm(row);
            } else {
            }

            toggleNewState();

            $editWin.modal('show');
        } catch(e) {
            SysMessage.alertError(e.message);
        }
    };

    /**
     *  清空全部表单
     */
    var clearForm = function() {
        currentCustomer = null;
        $editForm.form('clear');    //清除客户信息
        $contactTable.bootstrapTable('removeAll');    //清除联系人信息
        $contactTable.bootstrapTable('refreshOptions', { url : '' });   // 清空url
        $infoTable.bootstrapTable('removeAll');    //清除信息化详情
        $infoTable.bootstrapTable('refreshOptions', { url : '' });   // 清空url
        $fileTable.fileTable('clear');    //清除附件
    };

    /**
     *  填充表单
     *  @param data 数据
     */
    var fillForm = function(data) {
        currentCustomer = data;
        $editForm.form('load', data);    //加载客户信息
        // $areaSelector.val(data['area']).trigger('change');
        // $provinceSelector.val(data['province']).trigger('change');
        // $citySelector.val(data['city']).trigger('change');
        // $districtSelector.val(data['district']).trigger('change');
        $contactTable.bootstrapTable('refresh', { url : baseUrl + '/' + data.id + '/contacts' });   // 加载联系人
        $infoTable.bootstrapTable('refresh', { url : baseUrl + '/' + data.id + '/infos' });   // 加载信息化详情
    };

    var toggleNewState = function() {
        if($id.val()) { //如果存在ID，则当前为已保存过的/已存在的数据，不需要保存新数据按钮
            $createNewBtn.hide();
            $createContactBtn.attr('disabled', false);
            $createContactBtn.removeAttr('title');
            $modifyContactBtn.attr('disabled', false);
            $modifyContactBtn.removeAttr('title');
            $deleteContactBtn.attr('disabled', false);
            $deleteContactBtn.removeAttr('title');

            $modifyInfoBtn.attr('disabled', false);

            $fileTable.fileTable('readonly', false);
            $fileTable.fileTable('load', $id.val());    //附件组件绑定数据
        } else {    //否则显示保存新数据按钮
            $createNewBtn.show();
            $createContactBtn.attr('disabled', true);
            $createContactBtn.attr('title', '保存基本信息后可操作！');
            $modifyContactBtn.attr('disabled', true);
            $modifyContactBtn.attr('title', '保存基本信息后可操作！');
            $deleteContactBtn.attr('disabled', true);
            $deleteContactBtn.attr('title', '保存基本信息后可操作！');

            $modifyInfoBtn.attr('disabled', true);

            $fileTable.fileTable('readonly', true);
        }
    };

    /**
     * 提交新增主体数据
     */
    var submitNew = function() {

        if(!$editForm.valid()) {    //表单验证
            return false;
        }

        $.fn.http({
            method : 'POST',
            url : baseUrl,
            data : JSON.stringify($editForm.serializeJson()),
            success : function(data) {
                $id.val(data);
                $contactTable.bootstrapTable('refresh', { url : baseUrl + '/' + data + '/contacts' });   // 加载联系人
                $infoTable.bootstrapTable('refresh', { url : baseUrl + '/' + data + '/infos' });   // 加载信息化详情
                $fileTable.fileTable('load', $id.val());    //附件组件绑定新ID
                toggleNewState();
            }
        });
    };

    /**
     * 提交表单
     */
    var submit = function() {

        if(!$editForm.valid()) {    //表单验证
            return false;
        }

        var method = ($id.val() ? 'PUT' : 'POST');

        var data = $editForm.serializeJson();
        var selectedBrands = $("#edit_mainSoftwareBrand").select2("data");
        if(selectedBrands && selectedBrands.length > 0) {
            var selectedBrandIds = [];
            for(var i = 0; i < selectedBrands.length; i ++) {
                selectedBrandIds.push(selectedBrands[i].id);
            }
            data['mainSoftwareBrand'] = selectedBrandIds.join(',');
        } else {
            data['mainSoftwareBrand'] = '';
        }
        var selectedLines = $("#edit_mainSoftwareLine").select2("data");
        if(selectedLines && selectedLines.length > 0) {
            var selectedLineIds = [];
            for(var j = 0; j < selectedLines.length; j ++) {
                selectedLineIds.push(selectedLines[j].id);
            }
            data['mainSoftwareLine'] = selectedLineIds.join(',');
        } else {
            data['mainSoftwareLine'] = '';
        }

        $.fn.http({
            method : method,
            url : baseUrl,
            data : JSON.stringify(data),
            success : function() {
                $editWin.modal('hide');
                $table.bootstrapTable('refresh');
                // $contactTable.bootstrapTable('removeAll');
                // $infoTable.bootstrapTable('removeAll');
            }
        });
    };

    /**
     *  联系人table-删除行
     */
    var deleteContact = function() {
        var row = $contactTable.bootstrapTable('getSelections')[0];
        if (!row) {
            SysMessage.alertNoSelection();
            return false;
        }
        bootbox.confirm('确定要删除该联系人吗？', function (callback) {
            if(callback) {
                $.fn.http({
                    method : 'DELETE',
                    url : appPath + '/crm/contact/' + row.id,
                    success : function() {
                        $contactTable.bootstrapTable('refresh');
                    }
                });
            }
        });
    };

    var loadProvince = function(areaCode) {
        $provinceSelector.empty();
        $provinceSelector.append('<option value="">请选择</option>');
        $provinceSelector.val('').trigger('change');
        if(!areaCode) {
            return;
        }
        $.fn.http({
            method : 'GET',
            url : appPath + '/base/sysDict/area/' + areaCode + '/provinces',
            success : function(data) {
                $.each(data, function() {
                    $provinceSelector.append('<option value="' + this.code + '">' + this.name + '</option>');
                });
                if(currentCustomer) {
                    $provinceSelector.val(currentCustomer['province']).trigger('change');  //select2 use it
                }
            }
        }, false);
    };

    var loadCity = function(provinceCode) {
        $citySelector.empty();
        $citySelector.append('<option value="">请选择</option>');
        $citySelector.val('').trigger('change');
        if(!provinceCode) {
            return;
        }
        $.fn.http({
            method : 'GET',
            url : appPath + '/base/sysDict/province/' + provinceCode + '/cities',
            success : function(data) {
                $.each(data, function() {
                    $citySelector.append('<option value="' + this.code + '">' + this.name + '</option>');
                });
                if(currentCustomer) {
                    $citySelector.val(currentCustomer['city']).trigger('change');  //select2 use it
                }
            }
        }, false);
    };

    var loadDistrict = function(cityCode) {
        $districtSelector.empty();
        $districtSelector.append('<option value="">请选择</option>');
        $districtSelector.val('').trigger('change');
        if(!cityCode) {
            return;
        }
        $.fn.http({
            method : 'GET',
            url : appPath + '/base/sysDict/city/' + cityCode + '/districts',
            success : function(data) {
                $.each(data, function() {
                    $districtSelector.append('<option value="' + this.code + '">' + this.name + '</option>');
                });
                if(currentCustomer) {
                    $districtSelector.val(currentCustomer['district']).trigger('change');  //select2 use it
                }
            }
        }, false);
    }
}(window.jQuery);
