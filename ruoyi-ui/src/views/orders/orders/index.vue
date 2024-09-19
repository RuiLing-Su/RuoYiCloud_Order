<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单号" prop="number">
        <el-input
          v-model="queryParams.number"
          placeholder="请输入订单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="订单状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择订单状态" clearable>
          <el-option
            v-for="dict in dict.type.order_system"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="结账时间">
        <el-date-picker
          v-model="daterangeCheckoutTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['orders:orders:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['orders:orders:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['orders:orders:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['orders:orders:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="ordersList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="桌号" align="center" prop="tableNo" />
      <el-table-column label="主键" align="center" prop="id" />
      <el-table-column label="订单号" align="center" prop="number" />
      <el-table-column label="订单状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.order_system" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="结账时间" align="center" prop="checkoutTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.checkoutTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="实收金额" align="center" prop="amount" />
      <el-table-column label="用户手机号" align="center" prop="phone" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['orders:orders:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['orders:orders:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改用户订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-divider content-position="center">订单明细信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddOrderDetail">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteOrderDetail">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="orderDetailList" :row-class-name="rowOrderDetailIndex" @selection-change="handleOrderDetailSelectionChange" ref="orderDetail">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="名字" prop="name" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.name" placeholder="请输入名字" />
            </template>
          </el-table-column>
          <el-table-column label="菜品id" prop="dishId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.dishId" placeholder="请输入菜品id" />
            </template>
          </el-table-column>
          <el-table-column label="套餐id" prop="setmealId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.setmealId" placeholder="请输入套餐id" />
            </template>
          </el-table-column>
          <el-table-column label="口味" prop="dishFlavor" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.dishFlavor" placeholder="请输入口味" />
            </template>
          </el-table-column>
          <el-table-column label="数量" prop="number" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.number" placeholder="请输入数量" />
            </template>
          </el-table-column>
          <el-table-column label="金额" prop="amount" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.amount" placeholder="请输入金额" />
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listOrders, getOrders, delOrders, addOrders, updateOrders } from "@/api/orders/orders";

export default {
  name: "Orders",
  dicts: ['order_system'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedOrderDetail: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 用户订单表格数据
      ordersList: [],
      // 订单明细表格数据
      orderDetailList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 金额时间范围
      daterangeCheckoutTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        number: null,
        status: null,
        checkoutTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询用户订单列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeCheckoutTime && '' != this.daterangeCheckoutTime) {
        this.queryParams.params["beginCheckoutTime"] = this.daterangeCheckoutTime[0];
        this.queryParams.params["endCheckoutTime"] = this.daterangeCheckoutTime[1];
      }
      listOrders(this.queryParams).then(response => {
        this.ordersList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        tableNo: null,
        id: null,
        number: null,
        status: null,
        userId: null,
        orderTime: null,
        checkoutTime: null,
        payMethod: null,
        amount: null,
        remark: null,
        phone: null
      };
      this.orderDetailList = [];
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeCheckoutTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加用户订单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getOrders(id).then(response => {
        this.form = response.data;
        this.orderDetailList = response.data.orderDetailList;
        this.open = true;
        this.title = "修改用户订单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.orderDetailList = this.orderDetailList;
          if (this.form.id != null) {
            updateOrders(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOrders(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除用户订单编号为"' + ids + '"的数据项？').then(function() {
        return delOrders(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
	/** 订单明细序号 */
    rowOrderDetailIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 订单明细添加按钮操作 */
    handleAddOrderDetail() {
      let obj = {};
      obj.name = "";
      obj.image = "";
      obj.dishId = "";
      obj.setmealId = "";
      obj.dishFlavor = "";
      obj.number = "";
      obj.amount = "";
      this.orderDetailList.push(obj);
    },
    /** 订单明细删除按钮操作 */
    handleDeleteOrderDetail() {
      if (this.checkedOrderDetail.length == 0) {
        this.$modal.msgError("请先选择要删除的订单明细数据");
      } else {
        const orderDetailList = this.orderDetailList;
        const checkedOrderDetail = this.checkedOrderDetail;
        this.orderDetailList = orderDetailList.filter(function(item) {
          return checkedOrderDetail.indexOf(item.index) == -1
        });
      }
    },
    /** 复选框选中数据 */
    handleOrderDetailSelectionChange(selection) {
      this.checkedOrderDetail = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('orders/orders/export', {
        ...this.queryParams
      }, `orders_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
