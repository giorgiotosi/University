package com.example.demo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeekTest {

    @Test
    void testSetWeek() {
        Week week = Week.getInstance(true);
        //settare testWeekArray con i valori della settimana corrente
        int[] testWeekArray = {5, 6, 7, 8, 9, 10, 11};
        assertArrayEquals(week.getWeek(), testWeekArray);
    }

    @Test
    void testNextWeek() {
        Week week = Week.getInstance(true);
        int n_weeks_forward = 1;
        do{
            week.nextWeek();
            n_weeks_forward--;
        }while (n_weeks_forward != 0);
        //settare testWeekArray con i valori della n-esima settimana successiva
        int[] testWeekArray = {12, 13, 14, 15, 16, 17, 18};
        assertArrayEquals(week.getWeek(), testWeekArray);
    }

    @Test
    void testPreviousWeek() {
        Week week = Week.getInstance(true);
        int n_weeks_backward = 1;
        do{
            week.previousWeek();
            n_weeks_backward--;
        }while (n_weeks_backward != 0);
        //settare testWeekArray con i valori della n-esima settimana precedente
        int[] testWeekArray = {29, 30, 31, 1, 2, 3, 4};
        assertArrayEquals(week.getWeek(), testWeekArray);
    }
}