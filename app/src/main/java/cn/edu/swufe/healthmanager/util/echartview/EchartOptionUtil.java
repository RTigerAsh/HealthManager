package cn.edu.swufe.healthmanager.util.echartview;

import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.series.Scatter;

import java.util.ArrayList;

public class EchartOptionUtil {

    //无XY轴及其数据展示的折线图
    public static GsonOption getNoxyLineChartOptions(Object[] xAxis, Object[] yAxis,int max,int min) {
        GsonOption option = new GsonOption();

       // option.color("#8BC34A");

        ValueAxis valueAxis = new ValueAxis();
        valueAxis.splitLine().show(false);
        valueAxis.axisLabel().show(false);
        valueAxis.axisLine().show(false);
        valueAxis.min(min);
        valueAxis.max(max);
        option.yAxis(valueAxis);

        CategoryAxis categorxAxis = new CategoryAxis();
        categorxAxis.axisLine().onZero(false);
        categorxAxis.boundaryGap(true);
        categorxAxis.splitLine().show(false);
        categorxAxis.axisTick().show(false);
        categorxAxis.axisLine().show(false);
        categorxAxis.data(xAxis);
        option.xAxis(categorxAxis);

        Line line = new Line();
        line.data(yAxis);
        line.itemStyle().normal().color("#BBAEAE");//折线点颜色8BC34A
        line.itemStyle().normal().lineStyle().color("#2196F3");
        option.series(line);
        return option;
    }
    //无XY轴柱状图
    public static GsonOption getNoxyBarChartOptions(Object[] xAxis, Object[] yAxis) {
        GsonOption option = new GsonOption();
        option.color("#2196F3");
        ValueAxis valueAxis = new ValueAxis();
        valueAxis.splitLine().show(false);
        valueAxis.axisLabel().show(false);

        valueAxis.axisLine().show(false);
        valueAxis.axisLine().show(false);
        option.yAxis(valueAxis);

        CategoryAxis categorxAxis = new CategoryAxis();
        categorxAxis.axisLine().onZero(false);
        categorxAxis.boundaryGap(true);

        categorxAxis.splitLine().show(false);
        categorxAxis.axisTick().show(false);
        categorxAxis.axisLine().show(false);

        categorxAxis.data(xAxis);
        option.xAxis(categorxAxis);

        Bar bar = new Bar();
        bar.data(yAxis);
        bar.barCategoryGap("50%");
        option.series(bar);

        return option;
    }
    //无XY轴柱状图 横着
    public static GsonOption getBarChartOptions(Object[] xAxis, Object[] yAxis) {
        GsonOption option = new GsonOption();
        option.color("#2196F3");
        ValueAxis valueAxis = new ValueAxis();
        valueAxis.splitLine().show(false);
        valueAxis.axisLabel().show(false);

        valueAxis.axisLine().show(false);
        valueAxis.axisLine().show(false);


        CategoryAxis categorxAxis = new CategoryAxis();
        categorxAxis.axisLine().onZero(false);
        categorxAxis.boundaryGap(true);

        categorxAxis.splitLine().show(false);
        categorxAxis.axisTick().show(false);
        categorxAxis.axisLine().show(false);
        categorxAxis.axisLabel().show(false);
        categorxAxis.data(xAxis);

        option.xAxis(valueAxis);
        option.yAxis(categorxAxis);

        Bar bar = new Bar();
        bar.data(xAxis);
        //bar.barCategoryGap("50%");
        bar.barMaxWidth(10);
        option.series(bar);

        return option;
    }
    //weight柱状图
    public static GsonOption getweightLineChartOptions(Object[] xAxis, Object[] yAxis,int max,int min) {
        GsonOption option = new GsonOption();

        option.title().text("体重 单位/公斤");
        ValueAxis valueAxis = new ValueAxis();
        valueAxis.min(min);
        valueAxis.max(max);

        option.yAxis(valueAxis);

        CategoryAxis categorxAxis = new CategoryAxis();
        categorxAxis.axisLine().onZero(false);
        categorxAxis.boundaryGap(true);
        categorxAxis.data(xAxis);
        option.xAxis(categorxAxis);

        Line line = new Line();
        line.data(yAxis);
        option.series(line);
        //System.out.println(option);
        return option;
    }
    //有xy柱状图
    public static GsonOption getsleepBarChartOptions(Object[] xAxis, Object[] yAxis,String title) {
        GsonOption option = new GsonOption();
        option.title(title);
        option.color("#2196F3");
        ValueAxis valueAxis = new ValueAxis();
        option.yAxis(valueAxis);

        CategoryAxis categorxAxis = new CategoryAxis();
        categorxAxis.axisLine().onZero(false);
        categorxAxis.boundaryGap(true);
        categorxAxis.data(xAxis);
        option.xAxis(categorxAxis);

        Bar bar = new Bar();
        bar.data(yAxis);
        bar.barCategoryGap("50%");
        option.series(bar);

        return option;
    }

    //展示睡眠时间的散点图
    public static GsonOption getsleepdotChartOptions(Object[] data,String title) {
        GsonOption option = new GsonOption();
        option.title(title);
        option.color("#2196F3");

        ValueAxis valueAxis = new ValueAxis();
        option.yAxis(valueAxis);
        CategoryAxis categorxAxis = new CategoryAxis();
        option.xAxis(categorxAxis);

        Scatter scatter = new Scatter();
        scatter.data(data);
        scatter.symbolSize(20);
        option.series(scatter);

        return option;
    }
    //获取数组最大值，最小值

    public static int getMaxnum(Object[] aar) {
        int  aar_index=0,aar_Max=0;
        if (aar.length > 0) {
            aar_Max = (int)Double.parseDouble(aar[0]+"");
            aar_index = 0;
            for (int i = 0; i < aar.length; i++) {
                if ((int)Double.parseDouble(aar[i]+"") > aar_Max) {//比较后赋值
                    aar_Max = (int)Double.parseDouble(aar[i]+"");
                    aar_index = i;
                }
            }

        }
        return aar_Max;
    }

    public static int getMinnum(Object[] aar) {
        int  aar_index=0,aar_Min=0;;
        if (aar.length > 0) {
            aar_Min = (int)Double.parseDouble(aar[0]+"");
            aar_index = 0;
            for (int i = 0; i < aar.length; i++) {
                if ((int)Double.parseDouble(aar[i]+"") < aar_Min) {//比较后赋值
                    aar_Min = (int)Double.parseDouble(aar[i]+"");
                    aar_index = i;
                }
            }

        }
        return aar_Min;
    }

//    public static Object[] fomartTime (Object[] values){
//        ArrayList<String> data=new ArrayList<String>();
//        Object[] redata ;
//
//        if (values == null || values.length == 0) {
//            redata= data.toArray(new Object[data.size()]);
//            return redata;
//        }
//        for (Object value : values) {
//
//
//            data.add(value);
//        }
//        redata= data.toArray(new Object[data.size()]);
//
//        return redata;
//    }
}
