package com.ruoyi.setmeal.controller;

import java.util.List;
import java.io.IOException;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.category.domain.Category;
import com.ruoyi.category.service.ICategoryService;
import com.ruoyi.setmeal.domain.SetmealDto;
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
import com.ruoyi.setmeal.domain.Setmeal;
import com.ruoyi.setmeal.service.ISetmealService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 套餐Controller
 * 
 * @author SuRL
 * @date 2024-09-18
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController extends BaseController
{
    @Autowired
    private ISetmealService setmealService;

    @Autowired
    private ICategoryService categoryService;

    /**
     * 查询套餐列表
     */
    @RequiresPermissions("setmeal:setmeal:list")
    @GetMapping("/list")
    public TableDataInfo list(Setmeal setmeal)
    {
        startPage();
        List<Setmeal> list = setmealService.selectSetmealList(setmeal);
        List<SetmealDto> listDto = list.stream().map((item)->{
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);
            Long categoryId = item.getCategoryId();
            setmealDto.setCategoryName(categoryService.selectCategoryById(categoryId).getName());
            System.out.println(setmealDto);
            return setmealDto;
        }).collect(Collectors.toList());
        return getDataTable(listDto);
    }

    /**
     * 导出套餐列表
     */
    @RequiresPermissions("setmeal:setmeal:export")
    @Log(title = "套餐", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Setmeal setmeal)
    {
        List<Setmeal> list = setmealService.selectSetmealList(setmeal);
        ExcelUtil<Setmeal> util = new ExcelUtil<Setmeal>(Setmeal.class);
        util.exportExcel(response, list, "套餐数据");
    }

    /**
     * 获取套餐详细信息
     */
    @RequiresPermissions("setmeal:setmeal:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(setmealService.selectSetmealById(id));
    }

    /**
     * 新增套餐
     */
    @RequiresPermissions("setmeal:setmeal:add")
    @Log(title = "套餐", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Setmeal setmeal)
    {
        return toAjax(setmealService.insertSetmeal(setmeal));
    }

    /**
     * 修改套餐
     */
    @RequiresPermissions("setmeal:setmeal:edit")
    @Log(title = "套餐", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Setmeal setmeal)
    {
        return toAjax(setmealService.updateSetmeal(setmeal));
    }

    /**
     * 删除套餐
     */
    @RequiresPermissions("setmeal:setmeal:remove")
    @Log(title = "套餐", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(setmealService.deleteSetmealByIds(ids));
    }
}
