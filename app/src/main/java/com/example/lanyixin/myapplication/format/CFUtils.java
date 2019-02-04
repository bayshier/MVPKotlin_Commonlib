package com.example.lanyixin.myapplication.format;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.util.Log;
import com.example.lanyixin.myapplication.R;
import com.zrq.spanbuilder.SpanBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

//CurrencyFormattingUtils
public class CFUtils {

    //- 币作为单位时，第一位字母大写，后面都是小写 //例: 1.2345 Eth

    /**
     * 例子
     * CurrencyFormatter #,###.##
     builder.setnumber(vol)
     .setlega(true)
     .setword(true)
     .setformat(true)
     .build()
     .cfRules();
     * 几种情况
     * 1 币
     * 2 币 要词头 要格式
     * 3 币 要词头
     * 4 币 要格式
     */

    /**
     * @param number 数字
     * @param legalmoney 是否法币
     * @param wordhead 是否要词头
     * @param format 是否要格式化
     */
    //默认是法币格式 无词头 无格式化
    double number;

    boolean legalmoney;

    boolean wordhead;

    boolean format;

    //是否需要货币符号
    boolean unit;

    int unitSize; //货币符号大小

    //是否是流通数量 默认不是 开启则不进行汇率换算
    boolean quantity;

    //默认都是美元 根据美元汇率进行货币转化 ps：用户可自行传入货币单位
    String format_unit;

    //默认使用当前app保存的货币单位 ps：用户可自行传入需要的货币单位
    String mCurrenPriceShowType;

    private CFUtils(Builder builder) {
        this.number = builder.number;
        this.legalmoney = builder.legalmoney;
        this.wordhead = builder.wordhead;
        this.format = builder.format;
        this.unit = builder.unit;
        this.unitSize = builder.unitSize;
        this.quantity = builder.quantity;
        this.format_unit = builder.format_unit;
        this.mCurrenPriceShowType = builder.mCPType;
    }

    public static class Builder {

        double number;

        boolean legalmoney;

        boolean wordhead;

        boolean format;

        boolean unit;

        int unitSize; //货币符号大小

        boolean quantity;

        String format_unit;

        String mCPType;

        public Builder(Context context) {
            reset(context);
        }

        //初始化参数
        public void reset(Context context) {
            number = 0;
            legalmoney = true;
            wordhead = false;
            format = false;
            unit = true;
            unitSize = 12;
            quantity = false;
            format_unit = "USD";
            mCPType = "CNY";
        }

        public Builder setnumber(double number) {
            this.number = number;
            return this;
        }

        public Builder setlega(boolean legalmoney) {
            this.legalmoney = legalmoney;
            return this;
        }

        public Builder setword(boolean wordhead) {
            this.wordhead = wordhead;
            return this;
        }

        public Builder setformat(boolean format) {
            this.format = format;
            return this;
        }

        public Builder setunit(boolean unit) {
            this.unit = unit;
            return this;
        }

        public Builder setunitSize(int unitSize) {
            this.unitSize = unitSize;
            return this;
        }

        public Builder setquantity(boolean quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder setformat_unit(String format_unit) {
            this.format_unit = format_unit;
            return this;
        }

        public Builder setmCPType(String mCPType) {
            this.mCPType = mCPType;
            return this;
        }

        public CFUtils build() {
            return new CFUtils(this);
        }
    }

    //格式化 数字
    public SpannableStringBuilder cfRules(Context context) {

        //没有数据时用"--"表示，有货币符号的数据不要带货币符号 //例：--
        if (number == 0) {
            return new SpannableStringBuilder().append("--");
        }

        //1 进行汇率转化 ps：默认根据当前用户设置的货币进行转化，也可传入对应的货币符号进行转化
        //  传入的数值大部分以美元为基准，所以默认美元

        //支持基数词头适配的字段： 流通市值 | 流通数量 | 成交额 | 总市值 | 法币价格
        //根据汇率转化后的金额 ps：如果传入的数值是 流通数量就不要进行汇率换算
        double numberPrice = quantity ? number : CacheHelper.getInstance(context)
                .currencyExchange(context,number, format_unit, mCurrenPriceShowType);

        //------------------

        //2 对数值进行格式化操作 ps：小数都为0 不显示小数

        StringBuilder fmt = new StringBuilder();
        //是否要格式化数字
        fmt.append(format ? "#,##" : "");

        //2 是否法币
        if (legalmoney) {
            fmt.append(legalmoney(numberPrice));
        } else {
            fmt.append(idealmoney(numberPrice));
        }

        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(Locale.CHINA);
        //不要四舍五入
        df.setRoundingMode(RoundingMode.FLOOR);

        df.applyLocalizedPattern(fmt.toString());

        Log.i("df", fmt.toString());

        //最总返回的字符串
        String priceString = "";
        //是否要带词头
        if (wordhead) {
            priceString = formatFlow(df, numberPrice, legalmoney,context);
        } else {
            priceString = df.format(numberPrice);
        }

        //3 是否需要返回带货币单位符号的字符串 ps:如果是数量 值返回数值字符串 不要货币单位

        String ptC = CacheHelper.getInstance(context).getUnit(mCurrenPriceShowType);

        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();

        //币作为单位时，前面留一个空格 //例：1.2345 Eth
        //币作为单位时，第一位字母大写，后面都是小写 //例: 1.2345 Eth  ps:什么sb需求？？？
        if (legalmoney && ptC.equalsIgnoreCase("eth")) {
            spannableStringBuilder
                    .append(new SpanBuilder(priceString + " "))
                    .append(new SpanBuilder("E").setTextSize(unitSize))
                    .append(new SpanBuilder("th").setTextSize(unitSize));

        } else {
            spannableStringBuilder
                    .append(new SpanBuilder(ptC).setTextSize(unitSize))
                    .append(new SpanBuilder(priceString));
        }

        //是否需要货币单位
        return unit ?
                (quantity ? new SpannableStringBuilder().append(priceString) :
                        spannableStringBuilder) :
                new SpannableStringBuilder().append(priceString);
    }

    //法币规则转化
    public String legalmoney(double number) {
        if (number >= 100) {
            //100 <= x < 1000 0000 0	2位小数 //例：$12345.12
            return calculateProfit(number, 2) ? "0.00" : "#.##";
        } else if (number < 100 && number >= 0.1) {
            //- 0.1<= x < 100		4小数 //例：$0.1234
            return calculateProfit(number, 4) ? "0.0000" : "#.####";
        } else if (number < 0.1 && number >= 0.0001) {
            //- 0.0001 <= x < 0.1		6位小数 //例：$0.001234
            return calculateProfit(number, 6) ? "0.000000" : "#.######";
        } else if (number < 0.0001 && number >= 0.00000001) {
            //- 0.00000001 <= x < 0.0001	8位小数 //例：$0.00001234
            return calculateProfit(number, 8) ? "0.00000000" : "#.########";
        } else if (number < 0.00000001) {
            //x < 0.00000001		展示为0 //容错，暂时没有可能发生
            return "0";
        } else {
            return "";
        }
    }

    //虚拟币
    public String idealmoney(double number) {
        if (number >= 1000) {
            //1000 <= x < 1000 0000 0  4位小数 //例：12345.1234 Ht
            return calculateProfit(number, 4) ? "0.0000" : "#.####";
        } else if (number < 1000 && number >= 0.0001) {
            //0.0001 <= x < 1000	6位小数 //例: 12.123456 Eth
            return calculateProfit(number, 6) ? "0.000000" : "#.######";
        } else if (number < 0.0001 && number >= 0.000001) {
            //- 0.000001<= x < 0.0001	8位小数 //例: ฿0.00001234
            return calculateProfit(number, 8) ? "0.00000000" : "#.########";
        } else if (number < 0.000001 && number >= 0.0000000001) {
            //- 0.0000 0000 01 <= x < 0.000001	10位小数 //例：฿0.0000001234
            return calculateProfit(number, 10) ? "0.0000000000" : "#.##########";
        } else if (number < 0000000001) {
            //x < 0.0000 0000 01		展示为0 //容错，暂时没有可能发生
            return "0";
        } else {

        }

        return "";
    }

    //判断小数点后指定位置的数字是否等于0  ps：double类型不以科学计数法表示 用 BigDecimal
    public boolean calculateProfit(double balance, int index) {
        BigDecimal db = new BigDecimal(Double.toString(balance));
        String balanceStr = db.toPlainString();

        //首先判断有没有小数位 小数的位数要大于等于需要判断的位数
        if (isNumber2(balanceStr) && getNumberDecimalDigits(balance) >= index) {
            int indexOf = balanceStr.indexOf(".");
            //字符长度-小数点起始位置=整数部分
            //balanceStr.indexOf("0", balanceStr.length() - indexOf + index + 1);
            String inxs = balanceStr.substring(indexOf + index, indexOf + index + 1);
            return inxs.equals("0");
        } else {
            return false;
        }
    }

    public boolean isNumber2(String str) {// 判断小数，与判断整型的区别在与d后面的小数点（红色）
        return str.matches("\\d+\\.\\d+$");
    }

    //判断小数位数
    public int getNumberDecimalDigits(Double balance) {
        int dcimalDigits = 0;
        BigDecimal db = new BigDecimal(Double.toString(balance));
        String balanceStr = db.toPlainString();
        int indexOf = balanceStr.indexOf(".");
        if (indexOf > 0) {
            dcimalDigits = balanceStr.length() - 1 - indexOf;
        }
        return dcimalDigits;
    }

    /**
     * 中文带词头
     * - 万    10<sup>4</sup> //默认>=10<sup>5</sup> 时适配，法币价格>=10<sup>6</sup> 时才适配
     * - 亿    10<sup>8</sup>
     * - 万亿  10<sup>12</sup> //国标；台，韩，日标准中称为“兆”
     */
    public String formatFlow(DecimalFormat df, double number, boolean legalmoney,Context context) {

        //是否是中文环境
        if (context.getResources().getConfiguration().locale.getLanguage()
                .startsWith("en")) {
            return formatFlowEn(df, number);
        }

        //        默认>=105 时适配，法币价格>=106 时才适配
        int num = legalmoney ? 1000000 : 100000;

        if (number < num) {
            return df.format(number);
        } else if (number < 100000000) {
            return df.format(number / 10000) + context
                    .getString(R.string.msg_unit_10_4);
        } else if (number < Math.pow(10, 12)) {//小于10^12
            return df.format(number / Math.pow(10, 8)) + context
                    .getString(R.string.msg_unit_billion);
        } else if (number < Math.pow(10, 16)) {
            return df.format(number / Math.pow(10, 12)) + context
                    .getString(R.string.msg_unit_10_12);
        } else {
            return df.format(number / Math.pow(10, 16)) + context
                    .getString(R.string.msg_unit_10_16);
        }
    }

    /**
     * 英文带词头
     */
    public String formatFlowEn(DecimalFormat df, double number) {
        if (number < 100000) {
            return df.format(number);
        } else if (number < 100 * 10000) {//小于100w
            return df.format(number / 1000) + "K";
        } else if (number < Math.pow(10, 9)) {//小于10亿
            return df.format(number / (100 * 10000)) + "M";
        } else {
            return df.format(number / Math.pow(10, 9)) + "B";
        }
    }

    /**
     * 百分比
     */
    public static String percentage(double number) {
        boolean isAdd = number > 0;
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(Locale.CHINA);
        String style = "0.00";
        if (number >= 10000) {
            style = "0";
        } else if (number >= 1000) {
            style = "0.0";
        }
        df.applyLocalizedPattern(style);
        String res = df.format(number) + "%";
        if (res.startsWith("0.00%") || res.startsWith("-0.00%")) {
            return res;
        }
        return isAdd ? ("+" + res) : res;
    }

    /**
     * 科学计数法转化成字符串
     */
    public static String numberToString(double number) {
        BigDecimal bd = new BigDecimal(String.valueOf(number));
        return bd.toPlainString();
    }
}

