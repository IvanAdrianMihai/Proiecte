package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Util {
    private Util() {
    }

    /**
     * Metoda retine in max lungimea maxima a drumului din componenta conexa ce contine nodul nod
     *
     * @param nod       de tip int
     * @param vizMuchie de tip boolean[][]/ retine muchiile vizitate
     * @param mat       de tip boolean[][]/ muchiile
     * @param size      de tip int
     * @param nr        de tip int
     * @param max       de tip AtomicInteger
     */
    public static void drumLungimeMaxima(int nod, boolean[][] vizMuchie, boolean[][] mat, int size, int nr, AtomicInteger max) {
        if (nr > max.get())
            max.set(nr);
        for (int i = 1; i <= size; i++)
            if (mat[nod][i] && !vizMuchie[nod][i]) {
                //daca am luat o muchie o marchez ca fiind prcurs
                vizMuchie[nod][i] = true;
                vizMuchie[i][nod] = true;
                nr++;
                drumLungimeMaxima(i, vizMuchie, mat, size, nr, max);
                //elimin muchia pe care am parcurs-o pentru a putea parcurge toate drumurile din componenta
                nr--;
                vizMuchie[nod][i] = false;
                vizMuchie[i][nod] = false;
            }
    }

    /**
     * @param size tipe int
     * @return returneaza un vector boolean de dimensiune size initializat cu false
     */
    public static boolean[] creareVectorFalse(int size) {
        boolean[] array = new boolean[size];
        for (int i = 0; i < size; i++)
            array[i] = false;
        return array;
    }

    /**
     * @param size tipe int
     * @return returneaza o matrice patratica booleana de dimensiune size initializata cu false
     */
    public static boolean[][] creareMatriceFalse(int size) {
        boolean[][] matrix = new boolean[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                matrix[i][j] = false;
        return matrix;
    }

    /**
     * @param x de tip boolean[]
     * @return primul nod care nu a fost inclus intr-o componenta conexa,
     * sau -1 daca au fost toate nodurile deja incluse
     */
    public static int isVizitat(boolean[] x) {
        for (int i = 1; i < x.length; i++)
            if (!x[i])
                return i;
        return -1;
    }

    /**
     * Metoda retine in parametrul l toate nodurile care fac parte din componenta conexa care include nodul nod
     *
     * @param nod  de tip int
     * @param viz  de tip boolean[]
     * @param mat  de tip boolean[][]
     * @param l    de tip List<Long>
     * @param size de tip int
     */
    public static void parcurgeRec(int nod, boolean[] viz, boolean[][] mat, List<Long> l, int size) {
        if (!viz[nod]) {
            viz[nod] = true;
            l.add((long) nod);
            for (int i = 1; i <= size; i++)
                if (mat[nod][i] && !viz[i])
                    parcurgeRec(i, viz, mat, l, size);
        }
    }

    /**
     * Metoda verifica daca exista elementule in list
     *
     * @param list de tip List<Long>
     * @param e    de tip Long
     * @return true daca exista/ false daca nu exista
     */
    public static boolean existaLong(List<Long> list, Long e) {
        for (Long lon : list)
            if (lon == e)
                return true;
        return false;
    }

    /**
     * @param length de tip Integer
     * @return un String de lenght cifre reprezentand un cod numeric
     */
    public static String numbersCode(Integer length) {
        Random rand = new Random();
        int x;
        char[] convert = new char[length + 1];
        for (int i = 0; i < length; i++)
            convert[i] = (char) ('0' + rand.nextInt(10));
        convert[length] = '\0';
        return String.valueOf(convert);
    }


    public static String getIfNeedNotification(LocalDate dateEveniment) {
        String text = "";
        long daysBetween = ChronoUnit.DAYS.between(LocalDate.now(), dateEveniment);
        switch ((int) daysBetween) {
            case 0:
                text = "astazi";
                break;
            case 1:
                text="maine";
                break;
            case 7:
                text="in data de "+dateEveniment.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                break;
        }
        return  text;
    }
}
