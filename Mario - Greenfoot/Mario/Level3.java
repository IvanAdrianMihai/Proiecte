import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Level3 extends greenfoot.World
{

    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    private boolean ok=true;
    public static GreenfootSound back2 =new GreenfootSound("back2.mp3");
    public Level3()
    {    
        super(1100, 850, 1); 
        mario.viteza=0;
        ciperca.directia=1;
        d1.distanta=0;
        d2.distanta=0;
        s1.distanta=0;
        s2.distanta=0;
        monstru.directia=-1;
        testoasa.directia=1;
        if(tl2.personaj==true)
            addObject(new maredr0(),517,305);
        else
            addObject(new micdr0(),517,305);
        prepare();
    }
    public void act()
    {
        if(ok&&!back2.isPlaying())
        { 
          back2.playLoop();
          ok=false;
        }
    }
    
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        chenar chenar = new chenar();
        addObject(chenar,520,425);
        nor1 nor1 = new nor1();
        addObject(nor1,390,302);
        nor1 nor12 = new nor1();
        addObject(nor12,691,473);
        nor1 nor13 = new nor1();
        addObject(nor13,591,331);
        nor1 nor14 = new nor1();
        addObject(nor14,406,484);
        nor1 nor15 = new nor1();
        addObject(nor15,494,405);
        nor1 nor16 = new nor1();
        addObject(nor16,311,467);
        nor1 nor17 = new nor1();
        addObject(nor17,723,376);
        nor1 nor18 = new nor1();
        addObject(nor18,526,528);
        tufis1 tufis1 = new tufis1();
        addObject(tufis1,374,549);
        tufis1 tufis12 = new tufis1();
        addObject(tufis12,661,549);
        tufis2 tufis2 = new tufis2();
        addObject(tufis2,521,549);        
        m6 m6 = new m6();
        addObject(m6,293,520);
        m6 m62 = new m6();
        addObject(m62,293,429);
        m6 m63 = new m6();
        addObject(m63,293,360);
        m6 m64 = new m6();
        addObject(m64,292,334);
        removeObject(m64);
        addObject(m64,293,333);
        m5 m5 = new m5();
        addObject(m5,747,519);
        m5 m52 = new m5();
        addObject(m52,747,428);
        m5 m53 = new m5();
        addObject(m53,747,350);
        m5 m54 = new m5();
        addObject(m54,747,333);
        m3 m3 = new m3();
        addObject(m3,332,579);
        m3 m32 = new m3();
        addObject(m32,427,579);
        m3 m33 = new m3();
        addObject(m33,522,578);
        m3 m34 = new m3();
        addObject(m34,616,579);
        removeObject(m34);
        addObject(m34,616,578);
        m3 m35 = new m3();
        addObject(m35,707,578);
        m3 m36 = new m3();
        addObject(m36,333,273);
        m3 m37 = new m3();
        addObject(m37,427,273);
        m3 m38 = new m3();
        addObject(m38,521,273);
        m3 m39 = new m3();
        addObject(m39,616,273);
        m3 m310 = new m3();
        addObject(m310,707,272);
        removeObject(m310);
        addObject(m310,707,274);
        removeObject(m310);
        m3 m311 = new m3();
        addObject(m311,707,273);
        m1 m1 = new m1();
        addObject(m1,332,563);
        m1 m12 = new m1();
        addObject(m12,425,563);
        m1 m13 = new m1();
        addObject(m13,518,563);
        m1 m14 = new m1();
        addObject(m14,611,562);
        removeObject(m14);
        addObject(m14,613,563);
        m1 m15 = new m1();
        addObject(m15,707,563);
        no2 no2 = new no2();
        addObject(no2,309,554);
        no2 no22 = new no2();
        addObject(no22,325,554);
        no2 no23 = new no2();
        addObject(no23,341,554);
        no2 no24 = new no2();
        addObject(no24,357,554);
        no2 no25 = new no2();
        addObject(no25,373,554);
        no2 no26 = new no2();
        addObject(no26,309,538);
        no2 no27 = new no2();
        addObject(no27,325,538);
        no2 no28 = new no2();
        addObject(no28,341,538);
        no2 no29 = new no2();
        addObject(no29,357,538);
        no2 no210 = new no2();
        addObject(no210,309,522);
        no2 no211 = new no2();
        addObject(no211,325,522);
        no2 no212 = new no2();
        addObject(no212,341,522);
        no2 no213 = new no2();
        addObject(no213,357,523);
        removeObject(no213);
        no2 no214 = new no2();
        addObject(no214,309,506);
        no2 no215 = new no2();
        addObject(no215,325,506);
        no2 no216 = new no2();
        addObject(no216,309,490);
        no2 no217 = new no2();
        addObject(no217,731,554);
        removeObject(no217);
        no2 no218 = new no2();
        addObject(no218,731,554);
        no2 no219 = new no2();
        addObject(no219,715,554);
        no2 no220 = new no2();
        addObject(no220,699,554);
        no2 no221 = new no2();
        addObject(no221,683,554);
        no2 no222 = new no2();
        addObject(no222,667,554);
        no2 no223 = new no2();
        addObject(no223,683,538);
        no2 no224 = new no2();
        addObject(no224,699,538);
        no2 no225 = new no2();
        addObject(no225,715,538);
        no2 no226 = new no2();
        addObject(no226,731,538);
        no2 no227 = new no2();
        addObject(no227,699,522);
        no2 no228 = new no2();
        addObject(no228,715,522);
        no2 no229 = new no2();
        addObject(no229,731,522);
        no2 no230 = new no2();
        addObject(no230,715,506);
        no2 no231 = new no2();
        addObject(no231,731,506);
        no2 no232 = new no2();
        addObject(no232,731,490);
        removeObject(no25);
        removeObject(no29);
        removeObject(no212);
        removeObject(no215);
        removeObject(no216);
        removeObject(no222);
        removeObject(no223);
        removeObject(no227);
        removeObject(no230);
        removeObject(no232);
        no1 no1 = new no1();
        addObject(no1,373,554);
        no1 no12 = new no1();
        addObject(no12,357,538);
        no1 no13 = new no1();
        addObject(no13,341,522);
        no1 no14 = new no1();
        addObject(no14,325,506);
        no1 no15 = new no1();
        addObject(no15,309,490);
        no1 no16 = new no1();
        addObject(no16,667,554);
        no1 no17 = new no1();
        addObject(no17,683,538);
        no1 no18 = new no1();
        addObject(no18,699,522);
        no1 no19 = new no1();
        addObject(no19,715,506);
        no1 no110 = new no1();
        addObject(no110,731,490);
        bloc bloc = new bloc();
        addObject(bloc,373,546);
        bloc bloc2 = new bloc();
        addObject(bloc2,357,530);
        bloc bloc3 = new bloc();
        addObject(bloc3,341,514);
        bloc bloc4 = new bloc();
        addObject(bloc4,325,498);
        bloc bloc5 = new bloc();
        addObject(bloc5,309,482);
        bloc bloc6 = new bloc();
        addObject(bloc6,667,546);
        bloc bloc7 = new bloc();
        addObject(bloc7,683,530);
        bloc bloc8 = new bloc();
        addObject(bloc8,699,514);
        bloc bloc9 = new bloc();
        addObject(bloc9,715,498);
        bloc bloc10 = new bloc();
        addObject(bloc10,731,482);
        m4 m4 = new m4();
        addObject(m4,378,445);
        m4 m42 = new m4();
        addObject(m42,474,445);
        m4 m43 = new m4();
        addObject(m43,569,445);
        m4 m44 = new m4();
        addObject(m44,662,445);
        removeObject(m44);
        addObject(m44,659,445);
        removeObject(m42);
        removeObject(m43);
        m4 m45 = new m4();
        addObject(m45,510,433);
        m4 m46 = new m4();
        addObject(m46,530,433);
        m1 m16 = new m1();
        addObject(m16,378,437);
        m1 m17 = new m1();
        addObject(m17,510,425);
        m1 m18 = new m1();
        addObject(m18,530,425);
        m1 m19 = new m1();
        addObject(m19,658,437);
        removeObject(m19);
        addObject(m19,659,437);
        t2 t2 = new t2();
        addObject(t2,378,420);
        t2 t22 = new t2();
        addObject(t22,658,421);
        t1 t1 = new t1();
        addObject(t1,658,404);
        t1 t12 = new t1();
        addObject(t12,378,404);
        bo1 bo1 = new bo1();
        addObject(bo1,343,345);
        bo1 bo12 = new bo1();
        addObject(bo12,403,346);
        removeObject(bo1);
        bo1 bo13 = new bo1();
        addObject(bo13,353,346);
        bo1 bo14 = new bo1();
        addObject(bo14,635,343);
        bo1 bo15 = new bo1();
        addObject(bo15,684,343);
        bloc bloc11 = new bloc();
        addObject(bloc11,684,335);
        bloc bloc12 = new bloc();
        addObject(bloc12,635,335);
        bloc bloc13 = new bloc();
        addObject(bloc13,403,338);
        bloc bloc14 = new bloc();
        addObject(bloc14,353,338);
        vieti vieti = new vieti();
        addObject(vieti,324,302);
        puncte puncte = new puncte();
        addObject(puncte,326,325);
        puncte.act();
        vieti.act();        
        ba1 ba1 = new ba1();
        addObject(ba1,723,411);
        ba2 ba2 = new ba2();
        addObject(ba2,723,433);
        ba3 ba3 = new ba3();
        addObject(ba3,723,459);
        ba1 ba12 = new ba1();
        addObject(ba12,314,415);
        ba2 ba22 = new ba2();
        addObject(ba22,314,438);
        ba3 ba32 = new ba3();
        addObject(ba32,314,462);
        ba1 ba13 = new ba1();
        addObject(ba13,444,450);
        ba2 ba23 = new ba2();
        addObject(ba23,434,473);
        ba2 ba24 = new ba2();
        addObject(ba24,452,473);
        ba1 ba14 = new ba1();
        addObject(ba14,593,450);
        ba2 ba25 = new ba2();
        addObject(ba25,580,471);
        ba2 ba26 = new ba2();
        addObject(ba26,601,470);
        removeObject(ba25);
        addObject(ba25,584,469);
        removeObject(ba23);
        removeObject(ba24);
        addObject(ba23,435,470);
        addObject(ba24,452,470);
        removeObject(m4);
        addObject(m4,383,445);
        removeObject(m16);
        addObject(m16,383,437);
        ms1 ms1 = new ms1();
        addObject(ms1,623,547);
        vietimon vietimon = new vietimon();
        addObject(vietimon,666,309);
        vietimon.act();
        removeObject(vieti);
        removeObject(puncte);
        puncte puncte2 = new puncte();
        addObject(puncte2,349,324);
        vieti vieti2 = new vieti();
        addObject(vieti2,336,302);
        vieti2.act();
        puncte2.act();
        removeObject(t22);
        removeObject(t1);
        addObject(t22,662,420);
        addObject(t1,662,404);
        removeObject(m44);
        addObject(m44,657,445);
        removeObject(m19);
        addObject(m19,657,438);
        removeObject(m44);
        addObject(m44,655,446);
        removeObject(m19);
        addObject(m19,655,438);
        removeObject(m4);
        addObject(m4,387,445);
        removeObject(m16);
        addObject(m16,388,438);
        removeObject(m16);
        addObject(m16,387,438);
    }
}