package com.example.demo;

import java.time.LocalDate;
import java.util.Arrays;

public class Week {

    LocalDate current = LocalDate.now();
    LocalDate nextMonth = LocalDate.now().plusMonths(1);
    int day;
    int month;
    int year;
    int currentDayOfWeek;
    boolean leapYear = false;
    boolean wasGoingBackward = false;
    boolean wasGoingForward = false;
    int[] weekArray = new int[7];
    int[] monthArray = new int[7];
    int[] monthDays = {31, 0, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};


    private Week(boolean operatore){
        //caso operatore questura
        if(operatore)
            setCurrentDate();
        //caso utente
        else
            setNextMonthDate();
    }

    public static Week getInstance(boolean operatore){
        return new Week(operatore);
    }

    public void setCurrentDate() {
        this.day = current.getDayOfMonth();
        this.month = current.getMonth().getValue();     //getMonth ritorna la enum, getValue ritorna l'int
        this.year = current.getYear();
        this.currentDayOfWeek = current.getDayOfWeek().getValue();
        isLeapYear(year);
        if(currentDayOfWeek == 1)           //se è lunedì
            setWeek();
        else if(currentDayOfWeek == 7)      //se è domenica
            setWeekBackwards();
        else
            setWeekInner();
    }

    public void setNextMonthDate() {
        this.day = nextMonth.getDayOfMonth();
        this.month = nextMonth.getMonth().getValue();
        this.year = nextMonth.getYear();
        this.currentDayOfWeek = nextMonth.getDayOfWeek().getValue();
        isLeapYear(year);
        if(currentDayOfWeek == 1)           //se è lunedì
            setWeek();
        else if(currentDayOfWeek == 7)      //se è domenica
            setWeekBackwards();
        else
            setWeekInner();
    }

    public void setWeekInner(){
        int tmpday = day;
        weekArray[currentDayOfWeek - 1] = day;
        monthArray[currentDayOfWeek - 1] = month;
        int i;

        for(i = currentDayOfWeek; i < 7; i++){
            tmpday ++;

            if(tmpday > monthDays[month - 1]){
                fixDateForward();
            }
            weekArray[i] = tmpday;
            monthArray[i]= month;
        }

        for(i = currentDayOfWeek - 1; i > 0; i--){
            day --;
            if(day < 1){
                fixDateBackward();
            }
            weekArray[i - 1] = day;
            monthArray[i-1]= month;
        }
    }

    public void setWeek() {
        for (int i = 0; i < 7; i++) {
            weekArray[i] = day;
            monthArray[i] = month;
            if (i != 6) {
                day++;
                if (day > monthDays[month - 1]) {
                    fixDateForward();
                }
            }
        }
    }


    private void setWeekBackwards() {
        for(int i = 7; i > 0; i--){
            weekArray[i - 1] = day;
            monthArray[i-1]= month;
            if(i != 1){
                day --;
                if(day < 1){
                    fixDateBackward();
                }
            }
        }
    }

    public void nextWeek(){
        if(wasGoingBackward && weekArray[6] < 7){
            weekArray[6] = weekArray[6] + monthDays[month - 1];
        }
        wasGoingBackward = false;
        wasGoingForward = true;
        this.day = weekArray[6] + 1;
        if(day > monthDays[month - 1]){
            fixDateForward();
        }
        setWeek();
    }

    public void previousWeek(){
        int dayproblem;
        if(month - 2 == -1)
            dayproblem = monthDays[11];
        else
            dayproblem = monthDays[month - 2];

        if(wasGoingForward && weekArray[0] > (dayproblem - 7)){
            weekArray[0] = weekArray[0] - dayproblem;
        }
        wasGoingForward = false;
        wasGoingBackward = true;
        this.day = weekArray[0] - 1;
        if(day < 1){
            fixDateBackward();
        }
        setWeekBackwards();
    }

    private void fixDateForward(){
        System.out.println("Giorni mese: " + monthDays[month - 1]);
        day %= monthDays[month - 1];
        month += 1;
        if(month > 12){
            month = 1;
            year += 1;
            isLeapYear(year);
        }
    }

    private void fixDateBackward() {
        month -= 1;
        if(month < 1){
            month = 12;
            year -= 1;
            isLeapYear(year);
        }
        day = monthDays[month - 1] + day;
        //day = 1 --> 1 - 7 = -6
        //day corretto se mese 31 giorni --> day = 25
        //day = 31 - 6 = 31 + (day)
    }

    private void isLeapYear(int year) {
        //verifica se l'anno è divisibile per 4
        if (year % 4 == 0) {
            //se l'anno è divisibile per 100
            if (year % 100 == 0) {
                //è bisestile solo se è anche divisibile per 400
                leapYear = year % 400 == 0;
                monthDays[1] = 29;
            } else {
                //se non è divisibile per 100 ma lo è per 4, è bisestile
                leapYear = true;
                monthDays[1] = 29;
            }
        }
        else{
            leapYear = false;
            monthDays[1] = 28;
        }
    }

    public int[] getWeek(){
        return weekArray;
    }

    public void weekToString(){
        System.out.println("Anno " + year);
        System.out.println("Mese " + month);
        System.out.println(weekArray[0] + " , " + weekArray[1] + " , " + weekArray[2] + " , " +
                weekArray[3] + " , " + weekArray[4] + " , " + weekArray[5] + " , " + weekArray[6]);
    }

    public String dateToString() {
        return day + "/" + month + "/" + year;
    }

    public String dayToString(){
        return Integer.toString(day);
    }

    public String monthToString(){
        return Integer.toString(day);
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    //set current to a specific date
    public void setCurrentDate(int day, int month, int year){
        this.day = day;
        this.month = month;
        this.year = year;
        this.currentDayOfWeek = LocalDate.of(year, month, day).getDayOfWeek().getValue();

        /*isLeapYear(year);
        if(currentDayOfWeek == 1)           //se è lunedì
            setWeek();
        else if(currentDayOfWeek == 7)      //se è domenica
            setWeekBackwards();
        else
            setWeekInner();*/
    }

    public String yearToString(){
        return Integer.toString(day);
    }

}
