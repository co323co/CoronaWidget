package com.cocojeong.coronawidget;

import android.appwidget.AppWidgetProvider;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class WidgetProvider extends AppWidgetProvider {

//     브로드캐스트를 수신할때, Override된 콜백 메소드가 호출되기 직전에 호출됨
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

//    위젯을 갱신할때 호출됨
//    주의 : Configure Activity를 정의했을때는 위젯 등록시 처음 한번은 호출이 되지 않습니다
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, getClass()));
        for (int i = 0; i < appWidgetIds.length; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }

    }

//     위젯이 처음 생성될때 호출됨
//     동일한 위젯이 생성되도 최초 생성때만 호출됨
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

//     위젯의 마지막 인스턴스가 제거될때 호출됨
//     onEnabled()에서 정의한 리소스 정리할때
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

//    위젯이 사용자에 의해 제거될때 호출됨
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId){

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mFormat = new SimpleDateFormat("MM월 dd일", Locale.KOREA);

        RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.layout_widget);

        updateViews.setTextViewText(R.id.tv_w_date, mFormat.format(calendar.getTime()));

        DataResult data=new DataResult();
        crawlingThread t = new crawlingThread(data);
        t.start();
        try { t.join(); } catch (InterruptedException e) { e.printStackTrace(); }

        updateViews.setTextViewText(R.id.tv_w_dom, "국내 : " + data.domestic);
        updateViews.setTextViewText(R.id.tv_w_over, "해외 : " + data.overseas);

        appWidgetManager.updateAppWidget(appWidgetId, updateViews);


    }


}



