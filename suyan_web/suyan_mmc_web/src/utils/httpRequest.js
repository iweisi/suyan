import Vue from 'vue';
import Notice from 'iview/src/components/notice';
import axios from 'axios';
import merge from 'lodash/merge';
import router from '@/router';
import qs from 'qs';
axios.defaults.baseURL = "/";
const http = axios.create({
  timeout: 1000 * 30,
  withCredentials: false,
  headers: {
    'Content-Type': 'application/json; charset=utf-8'
  }
})

/**
 * 请求拦截
 */
http.interceptors.request.use(config => {
  var token = sessionStorage.getItem('access_token');
  if (token) {
    config.headers['Authorization'] = token;
  }

  return config
}, error => {
  return Promise.reject(error)
})

http.interceptors.response.use((response) => {
  console.log(response)
  if (response.data.access_token) {
    return response;
  }
  if (response && response.data.code != 200) {
    if (response.data.code == 404) {
      console.log('未登录');
      router.push({name: 'login'});
    } else {
      Notice.error({
        title: response.data.message
      });
    }
  }
  return response;
}, function (error) {
  console.log(error)
  if (403 === error.response.status) {
    console.log('forbidden')
  } else {
    return Promise.reject(error);
  }
});

/**
 * 请求地址处理
 * @param {*} actionName action方法名称
 */
http.adornUrl = (actionName) => {
  return '/api/mmc/' + actionName;
}

http.adornParams = (params = {}, openDefultParams = true) => {
  var defaults = {
    't': new Date().getTime()
  }
  return openDefultParams ? merge(defaults, params) : params
}

/**
 * post请求数据处理
 * @param {*} data 数据对象
 * @param {*} openDefultdata 是否开启默认数据?
 * @param {*} contentType 数据格式
 *  json: 'application/json; charset=utf-8'
 *  form: 'application/x-www-form-urlencoded; charset=utf-8'
 */
http.adornData = (data = {}, openDefultdata = true, contentType = 'json') => {
  var defaults = {
    't': new Date().getTime()
  }
  data = openDefultdata ? merge(defaults, data) : data
  return contentType === 'json' ? JSON.stringify(data) : qs.stringify(data)
}

function checkStatus(response) {
  if (response.data.code != 200) {
    if (response.data.code === 404) {
      // 未登录
      router.push({name: 'login'});
    }
  }
  return response.data;
}

function checkError(error) {
  console.log(error);
}

export default http

