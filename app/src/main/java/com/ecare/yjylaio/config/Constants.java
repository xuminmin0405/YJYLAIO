package com.ecare.yjylaio.config;

import com.ecare.yjylaio.BuildConfig;

/**
 * AndroidProject
 * <p>
 * Created by xuminmin on 2018/12/11.
 * Email: iminminxu@gmail.com
 * Copyright © 2018年 Hangzhou Gravity Cyberinfo. All rights reserved.
 * Constants
 */
public class Constants {

    //========== Api ==========

    public static final String API_BASE_URL = BuildConfig.DEBUG ? "https://xhky.hzxh.gov.cn/kyyth-mobile/" : "https://xhky.hzxh.gov.cn/kyyth-mobile/";
    public static final String RTC_API_BASE_URL = BuildConfig.DEBUG ? "https://xhky.hzxh.gov.cn/zhy-cms/" : "https://xhky.hzxh.gov.cn/zhy-cms/";
    public static final String TRANS_API_BASE_URL = BuildConfig.DEBUG ? "https://xhky.hzxh.gov.cn/ecare-trans-service/" : "https://xhky.hzxh.gov.cn/ecare-trans-service/";
    public static final int API_SUCCESS = 200;

    //========== Url ==========

    public static final String URL_MAIN = "https://sandun.tmp.kepai365.ltd/html/tablet/index.html#/main";
    public static final String URL_FITNESS_DETAIL = "https://xhky.hzxh.gov.cn/yjyl/#/fitnessDetail?paramsUserIdNo=";
    public static final String URL_SMART_MEAL = "https://xhky.hzxh.gov.cn/wisecare_saas_meal/mealApp/#/";
    public static final String URL_HEALTH_INDICATORS = "https://go.yjhealth.cn/fwlink/1647833802753";
    public static final String URL_CHINESE_MEDICINE = "qiaolz://";

    //========== Key ==========

    public static final String KEY_LECHANGE_APP_ID = "lc8f192561cd51452f";
    public static final String KEY_LECHANGE_APP_SECRET = "1687fc095ff6495e974ef89deb0e0e";

    //========== Paging ==========

    //默认分页初始页码
    public static final int PAGE_INDEX = 1;
    //默认分页加载数量
    public static final int PAGE_SIZE = 10;

    //========== INTENT ==========

    public static final String IT_CATEGORY = "category";
    public static final String IT_TITLE = "title";
    public static final String IT_URL = "url";
    public static final String IT_ID_CARD = "id_card";
    public static final String IT_NAME = "name";
    public static final String IT_ID = "id";
}
