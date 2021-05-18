package com.tang.util;


import java.util.Calendar;

//处理智能合约创建后的时间状况。
public class ContractTimeDurationProcess {



    //获得数据库中所有正在评价的影评数据后，都检查一遍看是否触发相应的合约操作。
    //相关合约触发操作有：1.影评投票开始有效触发。（直接通过检查当前时间是否为合约投票期间即可）。这个时间段用户提交加密的评定数据。
    // 2.对第一次评定进行第二次名牌提交，通过截止时间来触发第二次提交。（这个为了方便可以系统自行提交，把第一次相关数据，不加密进行二次提交
    // 这些数据保存在各个用户的客户端。）
    //3.最后结算的触发应该和时间没有关系，这个由去中心化触发或者中心化触发。


    //检查系统时间是否为处于给定日期时间段。
    static public boolean CheckTimeOk(int StartYesr,int StartMouth,int StartDay,int EndYear,int EndMouth,int EndDay){
        boolean timeOK=false;

//        Calendar currentTime;
//        Calendar currentTime = Calendar.getInstance();
//        //获取系统的日期
////        int year = calendar.get(Calendar.YEAR);//年
////        int month = calendar.get(Calendar.MONTH)+1;//月
////        int day = calendar.get(Calendar.DAY_OF_MONTH);//日
//        long currentMillis=currentTime.getTimeInMillis();

        long currentMillis= System.currentTimeMillis();

        Calendar startDate = Calendar.getInstance();
        startDate.set(StartYesr, StartMouth, StartDay);
        long startDateMillis = startDate.getTimeInMillis();

        Calendar endDate = Calendar.getInstance();
        endDate.set(EndYear, EndMouth, EndDay);
        long endDateMillis = endDate.getTimeInMillis();


        if(((currentMillis-startDateMillis)>0)&&((endDateMillis-currentMillis)<0)){

            timeOK=true;

        }

        return timeOK;
    }



    //检查是否系统时间已处于合约开始时间。
    static public boolean CheckStratTimeOk(int StartYesr,int StartMouth,int StartDay){
        boolean startTimeOK=false;

        long currentMillis= System.currentTimeMillis();

        Calendar startDate = Calendar.getInstance();
        startDate.set(StartYesr, StartMouth, StartDay);
        long startDateMillis = startDate.getTimeInMillis();

        if((currentMillis-startDateMillis)>0){
            startTimeOK=true;

        }

        return startTimeOK;
    }


    //检查是否系统时间已处于合约开始时间。
    static public boolean CheckEndTimeOk(int EndYear,int EndMouth,int EndDay){
        boolean endTimeOK=false;
//        long currentMillis= System.currentTimeMillis();  //这个相方法获得的时间有问题。
//        Calendar calendartest = Calendar.getInstance();
//        calendartest.setTimeInMillis(currentMillis);


        Calendar calendar = Calendar.getInstance();
        //获取系统的日期
        int currentyear = calendar.get(Calendar.YEAR);//年
        int currentmonth = calendar.get(Calendar.MONTH)+1;//月
        int currentday = calendar.get(Calendar.DAY_OF_MONTH);//日


        Calendar endDate1 = Calendar.getInstance();
        endDate1.set(currentyear, currentmonth, currentday);
        long currentDateMillis = endDate1.getTimeInMillis();

//        long timeInMilliseconds = mDate.getTime();

        Calendar endDate = Calendar.getInstance();
        endDate.set(EndYear, EndMouth, EndDay);
        long endDateMillis = endDate.getTimeInMillis();

        if((endDateMillis-currentDateMillis)>0){
            endTimeOK=true;

        }
        return endTimeOK;
    }




//    /**
//     * 给定起始及结束日期，获得者之间的时间
//     *
//     */
//    public boolean getDaysCount(int StartYesr,int StartMouth,int StartDay,int EndYear,int EndMouth,int EndDay){
//
//        Calendar startDate = Calendar.getInstance();
//        startDate.set(StartYesr, StartMouth, StartDay);
//        long startDateMillis = startDate.getTimeInMillis();
//
//        Calendar endDate = Calendar.getInstance();
//        endDate.set(EndYear, EndMouth, EndDay);
//        long endDateMillis = endDate.getTimeInMillis();
//
//        long differenceMillis = endDateMillis - startDateMillis; //时间间隔，
//
//
//
//
////        int daysDifference = (int) (differenceMillis / (1000 * 60 * 60 * 24));
////        return daysDifference;
//        return differenceMillis;
//
//    }
//



}
