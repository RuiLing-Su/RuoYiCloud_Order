import request from '@/utils/request'

// 查询套餐列表
export function listSetmeal(query) {
  return request({
    url: '/order2/setmeal/list',
    method: 'get',
    params: query
  })
}

// 查询套餐详细
export function getSetmeal(id) {
  return request({
    url: '/order2/setmeal/' + id,
    method: 'get'
  })
}

// 新增套餐
export function addSetmeal(data) {
  return request({
    url: '/order2/setmeal',
    method: 'post',
    data: data
  })
}

// 修改套餐
export function updateSetmeal(data) {
  return request({
    url: '/order2/setmeal',
    method: 'put',
    data: data
  })
}

// 删除套餐
export function delSetmeal(id) {
  return request({
    url: '/order2/setmeal/' + id,
    method: 'delete'
  })
}

// 查询菜品及套餐分类列表
export function listCategory(query) {
  return request({
    url: '/order2/category/list',
    method: 'get',
    params: query
  })
}