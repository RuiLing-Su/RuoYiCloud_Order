package com.ruoyi.setmeal.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SetmealDto extends Setmeal{
    private List<SetmealDish> setmealDishList = new ArrayList<>();
    private String categoryName;
}
