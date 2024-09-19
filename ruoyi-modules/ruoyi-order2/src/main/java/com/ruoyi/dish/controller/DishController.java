package com.ruoyi.dish.controller;

import java.math.BigInteger;
import java.util.List;
import java.io.IOException;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.category.domain.Category;
import com.ruoyi.category.service.ICategoryService;
import com.ruoyi.dish.domain.DishDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.dish.domain.Dish;
import com.ruoyi.dish.service.IDishService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 菜品管理Controller
 * 
 * @author SuRL
 * @date 2024-09-18
 */
@RestController
@RequestMapping("/dish")
public class DishController extends BaseController
{
    @Autowired
    private IDishService dishService;

    @Autowired
    private ICategoryService categoryService;

    /**
     * 查询菜品管理列表
     */
    @RequiresPermissions("dish:dish:list")
    @GetMapping("/list")
    public TableDataInfo list(Dish dish)
    {
        startPage();
        List<Dish> list = dishService.selectDishList(dish);

        List<DishDto> listDto = list.stream().map((item)-> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.selectCategoryById(categoryId);
            dishDto.setCategoryName(category.getName());
            return dishDto;
        }).collect(Collectors.toList());
        return getDataTable(listDto);
    }

    /**
     * 导出菜品管理列表
     */
    @RequiresPermissions("dish:dish:export")
    @Log(title = "菜品管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Dish dish)
    {
        List<Dish> list = dishService.selectDishList(dish);
        ExcelUtil<Dish> util = new ExcelUtil<Dish>(Dish.class);
        util.exportExcel(response, list, "菜品管理数据");
    }

    /**
     * 获取菜品管理详细信息
     */
    @RequiresPermissions("dish:dish:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(dishService.selectDishById(id));
    }

    /**
     * 新增菜品管理
     */
    @RequiresPermissions("dish:dish:add")
    @Log(title = "菜品管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Dish dish)
    {
        return toAjax(dishService.insertDish(dish));
    }

    /**
     * 修改菜品管理
     */
    @RequiresPermissions("dish:dish:edit")
    @Log(title = "菜品管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Dish dish)
    {
        return toAjax(dishService.updateDish(dish));
    }

    /**
     * 删除菜品管理
     */
    @RequiresPermissions("dish:dish:remove")
    @Log(title = "菜品管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(dishService.deleteDishByIds(ids));
    }
}
