package com.example.lanyixin.myapplication.format;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import com.example.lanyixin.myapplication.R;
import com.google.gson.reflect.TypeToken;
import com.kotlinmvp.utils.GsonUtils;

import java.util.List;
import java.util.Map;

public class CacheHelper {

    static CacheHelper mInstance;

    /**
     * Application#getApplicationContext
     */
    public static CacheHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new CacheHelper(context);
        }
        return mInstance;
    }

    public CacheHelper(Context context) {
        initHuilv(context);
        mLocalIcon = new ArrayMap<String, String>();
        initLocalIcons();
    }

    /**
     * 汇率生成
     */
    private Map<String, Double> mHuilvCache = new ArrayMap<>();

    public void putHuilv(String key, double num) {
        this.mHuilvCache.put(key, num);
    }

    public double getHuilv(String key) {
        Double obj = mHuilvCache.get(key);
        return obj != null ? obj.doubleValue() : 0;
    }

    //    初始化汇率集合
    public void initHuilv(Context context) {
        //保存的汇率列表
        List<RateBean> rateBeans = CacheManager.get("rate_list");
        if (isEmpty(rateBeans)) {
            rateBeans =
                    GsonUtils.changeGsonToList(PriceUtils.getConfigJson(R.raw.huilv,context),
                            new TypeToken<List<RateBean>>() {
                            }.getType());
        }
        for (RateBean rateBean : rateBeans) {
            putHuilv(rateBean.getSymbol().toUpperCase(), rateBean.getRate());
        }
    }

    public static <T> boolean isEmpty(List<T> paramList) {
        if ((paramList == null) || paramList.isEmpty()) {
            return true;
        }
        return false;
    }

    //    当前的货币单位
    private String mCurrenPriceShowType = "CNY";

    //    根据货币单位 获取货币符号
    public String getUnit(String market) {
        if ("BTC".equalsIgnoreCase(market)) {
            return "฿";
        } else if ("ETH".equalsIgnoreCase(market)) {
            return "ETH";
        } else if ("EUR".equalsIgnoreCase(market) || "BITEUR".equalsIgnoreCase(market)) {
            return "€";
        } else if ("CNY".equalsIgnoreCase(market)
                || "BITCNY".equalsIgnoreCase(market)
                || "QC".equalsIgnoreCase(market)
                || "CNH".equalsIgnoreCase(market)
                || "CNYT".equalsIgnoreCase(market)) {
            return "¥";
        } else if ("KRW".equalsIgnoreCase(market)) {
            return "₩";
        } else if ("RUB".equalsIgnoreCase(market)) {
            return "\u20BD";
        } else if ("JPY".equalsIgnoreCase(market)) {
            return "円";
        } else if ("AUD".equalsIgnoreCase(market)) {
            return "A$";
        } else if ("GBP".equalsIgnoreCase(market)) {
            return "￡";
        } else if ("SGD".equalsIgnoreCase(market)) {
            return "S$";
        } else if ("USD".equalsIgnoreCase(market)
                || "PAX".equalsIgnoreCase(market)
                || "USDT".equalsIgnoreCase(market)
                || "GUSD".equalsIgnoreCase(market)
                || "TUSD".equalsIgnoreCase(market)
                || "USDC".equalsIgnoreCase(market)
                || "BITUSD".equalsIgnoreCase(market)
                || "CK.USD".equalsIgnoreCase(market)
                || "CKUSD".equalsIgnoreCase(market)) {
            return "$";
        } else if ("INR".equalsIgnoreCase(market)) {
            return "₹";
        } else if ("CHF".equalsIgnoreCase(market)) {
            return "₣";
        } else if ("CAD".equalsIgnoreCase(market)) {
            return "C$";
        } else if ("IDR".equalsIgnoreCase(market)) {
            return "Rp";
        } else if ("BRL".equalsIgnoreCase(market)) {
            return "R$";
        } else if ("MXN".equalsIgnoreCase(market)) {
            return "MXN";
        } else if ("CNY".equalsIgnoreCase(market) || "CN".equalsIgnoreCase(market)) {
            return "¥";
        } else if ("HKD".equalsIgnoreCase(market)) {
            return "HK$";
        } else if ("CLP".equalsIgnoreCase(market)) {
            return "CLP";
        } else if ("COP".equalsIgnoreCase(market)) {
            return "Col$";
        } else if ("ILS".equalsIgnoreCase(market)) {
            return "₪";
        } else if ("ISK".equalsIgnoreCase(market)) {
            return "kr";
        } else if ("MYR".equalsIgnoreCase(market)) {
            return "RM";
        } else if ("NGN".equalsIgnoreCase(market)) {
            return "₦";
        } else if ("NZD".equalsIgnoreCase(market)) {
            return "NZ$";
        } else if ("PEN".equalsIgnoreCase(market)) {
            return "S/.";
        } else if ("PHP".equalsIgnoreCase(market)) {
            return "₱";
        } else if ("PLN".equalsIgnoreCase(market)) {
            return "zł";
        } else if ("THB".equalsIgnoreCase(market)) {
            return "T฿";
        } else if ("TRY".equalsIgnoreCase(market)) {
            return "₺";
        } else if ("TWD".equalsIgnoreCase(market)) {
            return "NT$";
        } else if ("UAH".equalsIgnoreCase(market)) {
            return "₴";
        } else if ("VND".equalsIgnoreCase(market)) {
            return "₫";
        } else {
            return market;
        }
    }

    /**
     * 汇率换算
     *
     * @param currencyValue 金额
     * @param market1 的货币类型
     * @param market2 要兑换的货币类型
     */
    public double currencyExchange(Context context,double currencyValue, String market1,
                                   String market2) {

        if (market1.equalsIgnoreCase(market2)) {
            //        传入的货币==用户设置的货币
            return currencyValue;
        } else {
            //         传入的是美元返回1，否者尝试取得汇率
            double rate = market1.equalsIgnoreCase("USD") ? 1 :
                    CacheHelper.getInstance(context).getHuilv(market1.toUpperCase());
            if (rate == 0) {
                return Double.NaN;
            }
            //            得到以美元为基准的转化后的原始金额数值
            double usdValue = currencyValue / rate;
            if ("USD".equalsIgnoreCase(market2)) {
                return usdValue;
            }
            //            不是美元 获取要越换的汇率
            rate = CacheHelper.getInstance(context).getHuilv(market2.toUpperCase());
            if (rate == 0) {
                return Double.NaN;
            }
            return usdValue * rate;
        }
    }

    //国家国旗
    private Map<String, String> mLocalIcon;

    private void initLocalIcons() {
        mLocalIcon.put("asny", "ic_aishaniya.png");
        mLocalIcon.put("alq", "ic_alq.png");
        mLocalIcon.put("aus", "ic_aus.png");
        mLocalIcon.put("brazil", "ic_brazil.png");
        mLocalIcon.put("pola", "ic_pola.png");
        mLocalIcon.put("danmark", "ic_danmarki.png");
        mLocalIcon.put("de", "ic_de.png");
        mLocalIcon.put("els", "ic_els.png");
        mLocalIcon.put("philippines", "ic_feilvbin.png");
        mLocalIcon.put("kr", "ic_kr.png");
        mLocalIcon.put("holland", "ic_holland.png");
        mLocalIcon.put("can", "ic_can.png");
        mLocalIcon.put("cayman", "ic_cayman.png");
        mLocalIcon.put("kldy", "ic_kldy.png");
        mLocalIcon.put("malta", "ic_malta.png");
        mLocalIcon.put("malaysia", "ic_malaysia.png");
        mLocalIcon.put("us", "ic_us.png");
        mLocalIcon.put("mengu", "ic_mengu.png");
        mLocalIcon.put("mesica", "ic_mesica.png");
        mLocalIcon.put("nanfei", "ic_nanfei.png");
        mLocalIcon.put("jp", "ic_jp.png");
        mLocalIcon.put("switzerland", "ic_switzerland.png");
        mLocalIcon.put("sser", "ic_sser.png");
        mLocalIcon.put("samora", "ic_samora.png");
        mLocalIcon.put("thailand", "ic_thailand.png");
        mLocalIcon.put("tw", "ic_tw.png");
        mLocalIcon.put("tsny", "ic_tsny.png");
        mLocalIcon.put("teq", "ic_teq.png");
        mLocalIcon.put("ic_unknow", "ic_unknow.png");
        mLocalIcon.put("wkl", "ic_wkl.png");
        mLocalIcon.put("hongkong", "ic_hongkong.png");
        mLocalIcon.put("spain", "ic_spain.png");
        mLocalIcon.put("singepo", "ic_singepo.png");
        mLocalIcon.put("newzeland", "ic_newzeland.png");
        mLocalIcon.put("ydl", "ic_ydl.png");
        mLocalIcon.put("india", "ic_india.png");
        mLocalIcon.put("uk", "ic_uk.png");
        mLocalIcon.put("ysl", "ic_yiselie.png");
        mLocalIcon.put("yuedan", "ic_yuedan.png");
        mLocalIcon.put("vietnam", "ic_yuenan.png");
        mLocalIcon.put("zl", "ic_zl.png");
        mLocalIcon.put("cn", "ic_cn.png");
    }

    public String getLocalIcon(String key) {
        return mLocalIcon.get(key);
    }
}
