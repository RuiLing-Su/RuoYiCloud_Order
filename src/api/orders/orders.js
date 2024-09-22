import request from '@/utils/request'

// 查询用户订单列表
export function listOrders(query) {
  return request({
    url: '/order2/orders/list',
    method: 'get',
    params: query
  })
}

// 查询用户订单详细
export function getOrders(id) {
  return request({
    url: '/order2/orders/' + id,
    method: 'get'
  })
}

// 新增用户订单
export function addOrders(data) {
  return request({
    url: '/order2/orders',
    method: 'post',
    data: data
  })
}

// 修改用户订单
export function updateOrders(data) {
  return request({
    url: '/order2/orders',
    method: 'put',
    data: data
  })
}

// 删除用户订单
export function delOrders(id) {
  return request({
    url: '/order2/orders/' + id,
    method: 'delete'
  })
}
