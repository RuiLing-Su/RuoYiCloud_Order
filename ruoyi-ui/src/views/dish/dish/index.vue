<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="菜品名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入菜品名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="售卖状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="售卖状态"
          clearable
        >
          <el-option
            v-for="dict in dict.type.seal_system"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
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
          v-hasPermi="['dish:dish:add']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['dish:dish:edit']"
          >修改</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['dish:dish:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['dish:dish:export']"
          >导出</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="dishList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="菜品名称" align="center" prop="name" />
      <el-table-column label="图片" align="center" prop="image" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.image" :width="50" :height="50" />
        </template>
      </el-table-column>
      <el-table-column label="分类名称" align="center" prop="categoryName" />
      <el-table-column label="菜品价格" align="center" prop="price" />
      <el-table-column label="售卖状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.seal_system"
            :value="scope.row.status"
          />
        </template>
      </el-table-column>
      <el-table-column
        label="更新时间"
        align="center"
        prop="updateTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, "{y}-{m}-{d} {h}:{s}:{i}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['dish:dish:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['dish:dish:remove']"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改菜品管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="菜品名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入菜品名称" />
        </el-form-item>
        <el-form-item label="菜品分类" prop="categoryId">
          <el-select v-model="form.categoryId" placeholder="请选择菜品分类" style="width: 150px" @change="$forceUpdate()" clearable>
            <el-option
              v-for="(key,value) in categoryList"
              :key="value"
              :label="key"
              :value="value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="菜品价格" prop="price">
          <el-input v-model="form.price" placeholder="请输入菜品价格" />
        </el-form-item>
        <el-form-item label="图片" prop="image">
          <image-upload v-model="form.image" />
        </el-form-item>
        <el-form-item label="描述信息" prop="description">
          <el-input v-model="form.description" placeholder="请输入描述信息" />
        </el-form-item>
        <el-form-item label="售卖状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.seal_system"
              :key="dict.value"
              :label="parseInt(dict.value)"
              >{{ dict.label }}</el-radio
            >
          </el-radio-group>
        </el-form-item>
        <el-divider content-position="center">菜品口味关系信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              icon="el-icon-plus"
              size="mini"
              @click="handleAddDishFlavor"
              >添加</el-button
            >
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"
              icon="el-icon-delete"
              size="mini"
              @click="handleDeleteDishFlavor"
              >删除</el-button
            >
          </el-col>
        </el-row>
        <el-table
          :data="dishFlavorList"
          :row-class-name="rowDishFlavorIndex"
          @selection-change="handleDishFlavorSelectionChange"
          ref="dishFlavor"
        >
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column
            label="序号"
            align="center"
            prop="index"
            width="50"
          />
          <el-table-column label="口味名称" prop="name" width="150">
            <template slot-scope="scope">
              <el-select
                v-model="scope.row.name"
                placeholder="请输入口味名称"
                @change="changeFlavorName(scope.row)"
              >
                <el-option
                  v-for="item in dishFlavorsData"
                  :key="item.id"
                  :label="item.name"
                  :value="item.name"
                  >{{ item.name }}</el-option
                >
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="口味列表" prop="value" width="150">
            <template slot-scope="scope">
              <el-select
                v-model="scope.row.value"
                multiple
                placeholder="请输入口味名称"
              >
                <el-option
                  v-for="(it, ind) in checkValueList"
                  :key="ind"
                  :label="it"
                  :value="it"
                >
                  {{ it }}</el-option
                >
              </el-select>
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
import {
  listDish,
  getDish,
  delDish,
  addDish,
  updateDish,
  getCategoryList
} from "@/api/dish/dish";

export default {
  name: "Dish",
  dicts: ["seal_system"],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedDishFlavor: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 菜品管理表格数据
      dishList: [],
      // 菜品口味关系表格数据
      dishFlavorList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //获取分类列表数据
      categoryList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        categoryId: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "菜品名称不能为空", trigger: "blur" },
        ],
        categoryId: [
          { required: true, message: "分类名称不能为空", trigger: "change" },
        ],
        price: [
          { required: true, message: "菜品价格不能为空", trigger: "blur" },
        ],
        image: [{ required: true, message: "图片不能为空", trigger: "blur" }],
        status: [
          { required: true, message: "售卖状态不能为空", trigger: "change" },
        ],
      },
    };
  },
  created() {
    this.getList();
    this.getFlavorListHand();
  },
  async mounted() {
    await this.getCategoryListHand();
  },
  methods: {

    //查询菜品分类列表
    async getCategoryListHand() {
      try{
        const res = await getCategoryList();
        console.log("categorylist:",res);
        this.categoryList = res;
      }catch(err){
        console.log(err);
      }
    },

    // 获取口味列表
    getFlavorListHand() {
      this.dishFlavorsData = [
        { name: "甜味", value: ["无糖", "少糖", "半糖", "多糖", "全糖"] },
        { name: "温度", value: ["热饮", "常温", "去冰", "少冰", "多冰"] },
        { name: "忌口", value: ["不要葱", "不要蒜", "不要香菜", "不要辣"] },
        { name: "辣度", value: ["不辣", "微辣", "中辣", "重辣"] },
      ];
    },
    // 存储当前选中口味列表数组
    checkValueList: [],

    // 定义改变口味时更新当前选中的口味列表数据
    changeFlavorName(row) {
      // 清空当前行的value
      row.value = [];
      //根据选中的name去查找静态数据的value
      const flavor = this.dishFlavorsData.find(
        (item) => item.name === row.name
      );
      if (flavor) {
        this.checkValueList = flavor.value;
      }
    },

    // 定义口味列表获取焦点时更新当前选中的口味列表数据
    handleFlavorFocus(row) {
      //根据选中的name去查找静态数据的value
      const flavor = this.dishFlavorsData.find(
        (item) => item.name === row.name
      );
      if (flavor) {
        this.checkValueList = flavor.value;
      }
    },

    /** 查询菜品管理列表 */
    getList() {
      this.loading = true;
      listDish(this.queryParams).then((response) => {
        this.dishList = response.rows;
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
        id: null,
        name: null,
        categoryId: null,
        price: null,
        image: null,
        description: null,
        status: null,
        createTime: null,
        updateTime: null,
      };
      this.dishFlavorList = [];
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加菜品管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getDish(id).then((response) => {
        this.form = response.data;
        this.dishFlavorList = response.data.dishFlavorList;
        // 将口味列表中的value字符串转为数组
        if (this.dishFlavorList != null) {
          this.dishFlavorList.forEach((item) => {
            item.value = JSON.parse(item.value);
          });
        }
        this.open = true;
        this.title = "修改菜品管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          this.form.dishFlavorList = this.dishFlavorList;
          // 将口味列表中的value通过json工具转化为字符串
          if (this.form.dishFlavorList) {
            this.form.dishFlavorList.forEach((item) => {
              item.value = JSON.stringify(item.value);
            });
          }
          if (this.form.id != null) {
            updateDish(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDish(this.form).then((response) => {
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
      this.$modal
        .confirm('是否确认删除菜品管理编号为"' + ids + '"的数据项？')
        .then(function () {
          return delDish(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 菜品口味关系序号 */
    rowDishFlavorIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 菜品口味关系添加按钮操作 */
    handleAddDishFlavor() {
      let obj = {};
      obj.name = "";
      obj.value = "";
      this.dishFlavorList.push(obj);
    },
    /** 菜品口味关系删除按钮操作 */
    handleDeleteDishFlavor() {
      if (this.checkedDishFlavor.length == 0) {
        this.$modal.msgError("请先选择要删除的菜品口味关系数据");
      } else {
        const dishFlavors = this.dishFlavorList;
        const checkedDishFlavor = this.checkedDishFlavor;
        this.dishFlavorList = dishFlavors.filter(function (item) {
          return checkedDishFlavor.indexOf(item.index) == -1;
        });
      }
    },
    /** 复选框选中数据 */
    handleDishFlavorSelectionChange(selection) {
      this.checkedDishFlavor = selection.map((item) => item.index);
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "dish/dish/export",
        {
          ...this.queryParams,
        },
        `dish_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
