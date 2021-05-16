/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Yajun.Zhang
 */
package top.goodz.future.constants;


/**
 * 通用常量
 *
 * @author Yajun.Zhang
 */
public interface CommonConstant {

    /**
     * nacos dev 地址
     */
    String NACOS_DEV_ADDR = "172.68.0.176:8848";

    /**
     * nacos test 地址
     */
    String NACOS_SIT_ADDR = "172.68.0.176:8848";

    /**
     * nacos prod 地址
     */
    String NACOS_PROD_ADDR = "172.68.0.176:8848";

    /**
     * nacos test 地址
     */
    String NACOS_TEST_ADDR = "172.68.0.176:8848";

    /**
     * sentinel dev 地址
     */
    String SENTINEL_DEV_ADDR = "172.68.0.176:8848";


    /**
     * sentinel test 地址
     */
    String SENTINEL_TEST_ADDR = "172.30.0.58:8858";

    /**
     * sword 系统名
     */
    String SWORD_NAME = "sword";

    /**
     * saber 系统名
     */
    String SABER_NAME = "saber";

    /**
     * 顶级父节点id
     */
    Long TOP_PARENT_ID = 0L;

    /**
     * 顶级父节点名称
     */
    String TOP_PARENT_NAME = "顶级";


    /**
     * 默认密码
     */
    String DEFAULT_PASSWORD = "123456";

    /**
     * 自增单据号前缀
     */
    String SEQ_NUMBER_PREFIX = "CONTRACT";

    /**
     * 动态获取nacos地址
     *
     * @param profile 环境变量
     * @return addr
     */
    static String nacosAddr(String profile) {
        switch (profile) {
            case (AppConstant.PROD_CODE):
                return NACOS_PROD_ADDR;
            case (AppConstant.SIT_CODE):
                return NACOS_SIT_ADDR;
            case (AppConstant.TEST_CODE):
                return NACOS_TEST_ADDR;
            default:
                return NACOS_DEV_ADDR;
        }
    }

    /**
     * 动态获取sentinel地址
     *
     * @param profile 环境变量
     * @return addr
     */
    static String sentinelAddr(String profile) {
        switch (profile) {
//			case (AppConstant.PROD_CODE):
//				return SENTINEL_PROD_ADDR;
            case (AppConstant.TEST_CODE):
                return SENTINEL_TEST_ADDR;
            default:
                return SENTINEL_DEV_ADDR;
        }
    }

}
