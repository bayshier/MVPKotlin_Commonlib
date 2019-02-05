package com.example.lanyixin.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.TextView;
import com.zzhoujay.richtext.RichText;

public class TxtActivity extends AppCompatActivity {

    private static final String IMAGE =
        "<img title=\"\" src=\"http://g.hiphotos.baidu.com/image/pic/item/241f95cad1c8a7866f726fe06309c93d71cf5087.jpg\"  style=\"cursor: pointer;\"><br><br>"
            + "<img src=\"http://img.ugirls.com/uploads/cooperate/baidu/20160519menghuli.jpg\" width=\"1080\" height=\"1620\"/><a href=\"http://www.baidu.com\">baidu</a>"
            + "hello asdkjfgsduk <a href=\"http://www.jd.com\">jd</a>";
    private static final String IMAGE1 =
        "<h1>RichText</h1><p>Android平台下的富文本解析器</p><img title=\"\" src=\"http://g.hiphotos.baidu.com/image/pic/item/241f95cad1c8a7866f726fe06309c93d71cf5087.jpg\"  style=\"cursor: pointer;\"><br><br>"
            + "<h3>点击菜单查看更多Demo</h3><img src=\"http://ww2.sinaimg.cn/bmiddle/813a1fc7jw1ee4xpejq4lj20g00o0gnu.jpg\" /><p><a href=\"http://www.baidu.com\">baidu</a>"
            + "hello asdkjfgsduk <a href=\"http://www.jd.com\">jd</a></p><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>bottom";

    private static final String GIF_TEST =
        "<img src=\"http://ww4.sinaimg.cn/large/5cfc088ejw1f3jcujb6d6g20ap08mb2c.gif\">";

    private static final String markdown_test =
        "image:![image](http://image.tianjimedia.com/uploadImages/2015/129/56/J63MI042Z4P8.jpg)\n[link](https://github.com/zzhoujay/RichText/issues)";

    private static final String gif_test =
        "<h3>Test1</h3><img src=\"http://www.aikf.com/ask/resources/images/facialExpression/qq/1.gif\" />"
            + "            <h3>Test2</h3><img src=\"http://www.aikf.com/ask/resources/images/facialExpression/qq/2.gif\" />"
            + "            <h3>Test3</h3><img src=\"http://www.aikf.com/ask/resources/images/facialExpression/qq/3.gif\" />"
            + "            <h3>Test4</h3><img src=\"http://www.aikf.com/ask/resources/images/facialExpression/qq/4.gif\" />"
            + "            <h3>Test5</h3><img src=\"http://www.aikf.com/ask/resources/images/facialExpression/qq/5.gif\" />"
            + "            <h3>Test6</h3><img src=\"http://www.aikf.com/ask/resources/images/facialExpression/qq/6.gif\" />"
            + "            <h3>Test7</h3><img src=\"http://www.aikf.com/ask/resources/images/facialExpression/qq/7.gif\" />"
            + "            <h3>Test8</h3><img src=\"http://www.aikf.com/ask/resources/images/facialExpression/qq/8.gif\" />"
            + "            <h3>Test9</h3><img src=\"http://www.aikf.com/ask/resources/images/facialExpression/qq/9.gif\" />";

    private static final String list_test = "<ol>\n"
        + "   <li>Coffee</li>\n"
        + "   <li>Tea</li>\n"
        + "   <li>Milk</li>\n"
        + "</ol>\n"
        + "\n"
        + "<ul>\n"
        + "   <li>Coffee</li>\n"
        + "   <li>Tea</li>\n"
        + "   <li>Milk</li>\n"
        + "</ul>";

    private static final String large_image =
        "<img src=\"http://static.tme.im/article_1_1471686391302fue\"/>";

    private static final String text = "";
    private static final String TAG = "MainActivityTest";
    private static final String assets_image_test =
        "<h1>Assets Image Test</h1><img src=\"file:///android_asset/doge.jpg\">";
    private static final String html = "#### 1. 法币价格数据展示规则\n"
        + "- x >= 1000 0 0000\t\t无小数 //容错，暂时没有可能发生\n"
        + "- 100 <= x < 1000 0 0000\t2位小数 //例：$12345.12\n"
        + "- 0.1<= x < 100\t\t4小数 //例：$0.1234\n"
        + "- 0.0001 <= x < 0.1\t\t6位小数 //例：$0.001234\n"
        + "- 0.00000001 <= x < 0.0001\t8位小数 //例：$0.00001234\n"
        + "- x < 0.00000001\t\t展示为0 //容错，暂时没有可能发生\n"
        + "- 没有数据时用\"--\"表示，有货币符号的数据不要带货币符号 //例：--\n"
        + "- 小数直接截断不4舍5入，小数点末位为0的不省略 //例：$54321.10\n"
        + "- 小数点全部为0的，隐藏 //例：$54321\n"
        + "\n"
        + "<br><br>\n"
        + "#### 2. 非法币价格数据展示规则\n"
        + "- x >= 1000 0 0000\t\t无小数 //容错，暂时没有可能发生\n"
        + "- 1000 <= x < 100000000  4位小数 //例：12345.1234 Ht\n"
        + "- 0.0001 <= x < 1000\t6位小数 //例: 12.123456 Eth\n"
        + "- 0.000001<= x < 0.0001\t8位小数 //例: ฿0.00001234\n"
        + "- 0.0000000001 <= x < 0.000001\t10位小数 //例：฿0.0000001234\n"
        + "- x < 0.0000000001\t\t展示为0 //容错，暂时没有可能发生\n"
        + "- 没有数据时用\"--\"表示，有货币符号的数据不要带货币符号 //例：--\n"
        + "- 小数直接截断不4舍5入，小数点末位为0的不省略 //例：0.0123400 Eth\n"
        + "- 小数点全部为0的，隐藏 //例：4321 Eth\n"
        + "- 币作为单位时，前面留一个空格 //例：1.2345 Eth\n"
        + "- 币作为单位时，第一位字母大写，后面都是小写 //例: 1.2345 Eth\n"
        + "\n"
        + "<br><br>\n"
        + "#### 3. 涨跌幅百分比（+/-x%）数据展示规则\n"
        + "- x >= 10000            无小数点\t//容错，小机率发生\n"
        + "- 0.01 <= x < 10000     保留2位小数\n"
        + "- x < 0.01              展示为0\n"
        + "- *没有数据时用\"--\"表示 //例：--\n"
        + "- *x=0时展示“0.00%”, 不展示\"+/-\"符号 //例：0.00%\n"
        + "- 小数直接截断不4舍5入，小数点末位为0的不省略 //例：12.10%\n"
        + "- 小数点全部为0的，展示 //例：12.00%\n"
        + "> *在旧版中，返回数据不能辨别x=0与没有数据，如果还是不能解决，按x=0处理，web端展示为\"0%\"，客户端为”0.00%“。\n"
        + "\n"
        + "<br><br>\n"
        + "#### 4. 基数词头的适配规则\n"
        + "- 万    10<sup>4</sup> //默认>=10<sup>5</sup> 时适配，法币价格>=10<sup>6</sup> 时才适配\n"
        + "- 亿    10<sup>8</sup>\n"
        + "- 万亿  10<sup>12</sup> //国标；台，韩，日标准中称为“兆”\n"
        + "- 支持基数词头适配的字段：\n"
        + "流通市值 | 流通数量 | 成交额 | 总市值 | 法币价格\n"
        + "- 基数词头适配后，保留2位小数 //例：123.12万\n"
        + "- 小数直接截断不4舍5入，小数点末位为0的不省略 //例：123.10万\n"
        + "- 小数点全部为0的，隐藏 //例：123万\n"
        + "\n"
        + "<br><br>\n"
        + "#### 5. 基数的三位逗号分隔符的适配规则\n"
        + "- *在简体中文语境下，没有适配基数词头的数字不采用三位逗号分隔。//例：54321； 5,432.12万\n"
        + "- 在简体中文语境下，非行情数据展示的数字不采用逗号分隔。//例：阅读数 54321;关注数 5432.12万\n"
        + "> *行情数据在其它非简体中文语境中，采用三位逗号分隔机制\n"
        + "\n"
        + "<br><br>\n"
        + "#### 6. 陆，港，台，日，韩，英文的基数词头的规范\n"
        + "NUM | CN | HK | TW | JP | KR | EN\n"
        + "---|---|---|---|---|---|---\n"
        + "10<sup>4</sup> |1万 | 1萬 | 1萬 | 1万 | 1만 | 10K,1K=10<sup>3</sup>\n"
        + "10<sup>8</sup> |1亿 | 1億 | 1億 | 1億 | 1억 | 100M,1M=10<sup>6</sup>\n"
        + "10<sup>12</sup> |1万亿 | 1萬億 | 1兆 | 1兆 | 1조 | 1000B,1B=10<sup>9</sup>";
    private final String issue142 =
        "<p><img src=\"http://image.wangchao.net.cn/it/1233190350268.gif?size=528*388\" width=\"528\" height=\"388\" /></p>";
    private final String issue143 =
        "<img src=\"file:///C:\\Users\\ADMINI~1\\AppData\\Local\\Temp\\ksohtml\\wpsB8DD.tmp.png\">";
    private final String issue147 = "<div class=\"pictureBox\"> \n"
        + " <img src=\"http://static.storeer.com/wlwb/productDetail/234be0ec-e90a-4eda-90bd-98d64b55280a_580x4339.jpeg\">\n"
        + "</div>";
    private final String issue149 = null;
    private final String issue150 =
        "<img src='http://cuncxforum-10008003.image.myqcloud.com/642def77-373f-434f-8e68-42dedbd9f880'/><br><img src='http://cuncxforum-10008003.image.myqcloud.com/bf153d9f-e8c3-4dcc-a23e-bfe0358cb429'/>";
    int loading = 0;
    int failure = 0;
    int ready = 0;
    int init = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_txt);

        RichText.initCacheDir(this);
        RichText.debugMode = true;

        final TextView textView = findViewById(R.id.text);

        String test_text_2 =
            "<B>Start</B> <img src='http://wx1.sinaimg.cn/mw690/eaaf2affly1fihvjpekzwj21el0qotfq.jpg' />"
                + "<img src='http://wx1.sinaimg.cn/mw690/eaaf2affly1fihvjpekzwj21el0qotfq.jpg' /><img src='http://wx1.sinaimg.cn/mw690/eaaf2affly1fihvjpekzwj21el0qotfq.jpg' />"
                + "<img src='http://wx1.sinaimg.cn/mw690/eaaf2affly1fihvjpekzwj21el0qotfq.jpg' /><img src='http://wx1.sinaimg.cn/mw690/eaaf2affly1fihvjpekzwj21el0qotfq.jpg' />"
                + "<img src='http://wx1.sinaimg.cn/mw690/eaaf2affly1fihvjpekzwj21el0qotfq.jpg' /><img src='http://wx1.sinaimg.cn/mw690/eaaf2affly1fihvjpekzwj21el0qotfq.jpg' />"
                + "<img src='http://wx1.sinaimg.cn/mw690/eaaf2affly1fihvjpekzwj21el0qotfq.jpg' /><img src='http://wx1.sinaimg.cn/mw690/eaaf2affly1fihvjpekzwj21el0qotfq.jpg' /><B>End</B>";

        //RichText.fromMarkdown(html).urlClick(new OnUrlClickListener() {
        //    @Override public boolean urlClicked(String url) {
        //        if (url.startsWith("code://")) {
        //            //Toast.makeText(MainActivity.this, url.replaceFirst("code://", ""),
        //            //    Toast.LENGTH_SHORT).show();
        //            return true;
        //        }
        //        return false;
        //    }
        //}).into(textView);

        //textView.post(new Runnable() {
        //    @Override public void run() {
        //        Spanned spanned = MarkDown.fromMarkdown(stream, new Html.ImageGetter() {
        //            @Override public Drawable getDrawable(String source) {
        //                Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
        //                drawable.setBounds(0, 0, 400, 400);
        //                return drawable;
        //            }
        //        }, textView);
        //        textView.setText(spanned);
        //    }
        //});

        RichText.fromMarkdown(html).into(textView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "RecyclerView");
        menu.add(0, 1, 1, "ListView");
        menu.add(0, 2, 2, "Gif");
        menu.add(0, 3, 3, "Test");
        return super.onCreateOptionsMenu(menu);
    }

    //@Override public boolean onOptionsItemSelected(MenuItem item) {
    //  //if (item.getItemId() == 0) {
    //  //  startActivity(new Intent(this, RecyclerViewActivity.class));
    //  //} else if (item.getItemId() == 1) {
    //  //  startActivity(new Intent(this, ListViewActivity.class));
    //  //} else if (item.getItemId() == 2) {
    //  //  startActivity(new Intent(this, GifActivity.class));
    //  //} else if (item.getItemId() == 3) {
    //  //  startActivity(new Intent(this, TestActivity.class));
    //  //}
    //  //return super.onOptionsItemSelected(item);
    //}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RichText.recycle();
    }

    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
