import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Desert2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Desert2 extends World
{

    /**
     * Constructor for objects of class Desert2.
     * 
     */
    private int t=0,q=0;
    private int op=10;
    public Desert2()
    {    
        super(783, 610, 1, false);
        addObject(new p1(),783,608);
        prepare();        
    }
    private void pamant()
    {
        op++;
        if(op==21)
        {
            op=0;
            addObject(new p1(),850,608);
        };         
    }
    public void act()
    {
        pamant();
        int h;
        if(scor1.sc1!=15)
        {
        t++;
        if(t==160)
        {
            t=0;
            h=Greenfoot.getRandomNumber(4);
            if(h==1)
            {
                addObject(new c1(),getWidth(),488);
                pun.ex=1;
                addObject(new pun(),getWidth(),201);
            }
            else
                if(h==2)
                {
                     addObject(new c2(),getWidth(),521);
                     pun.ex=1;
                     addObject(new pun(),getWidth(),201);
                }
                else
                    if(h==3)
                    {
                        addObject(new c3(),getWidth(),496);
                        pun.ex=1;
                        addObject(new pun(),getWidth(),201);
                    };
        }
        }
        else
        {
            q++;
            if(q==80)
                addObject(new arw(),850,500);
            if(q==160)
                addObject(new per2(),850,499);
        };
    };
    

    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        p1 p1 = new p1();
        addObject(p1,322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(322,588);
        p1.setLocation(112,583);
        p1.setLocation(81,572);
        p1.setLocation(41,574);
        p1.setLocation(6,574);
        p1.setLocation(6,574);
        p1.setLocation(6,574);
        p1.setLocation(6,574);
        p1.setLocation(6,574);
        p1.setLocation(6,574);
        p1.setLocation(6,574);
        p1.setLocation(6,574);
        p1.setLocation(6,574);
        p1.setLocation(6,574);
        p1.setLocation(6,574);
        p1.setLocation(6,574);
        p1.setLocation(6,574);
        p1.setLocation(6,574);
        p1.setLocation(6,574);
        p1.setLocation(6,574);
        p1.setLocation(6,574);
        p1.setLocation(6,574);
        p1.setLocation(6,574);
        p1.setLocation(6,574);
        p1.setLocation(6,574);
        p1.setLocation(6,574);
        p1.setLocation(6,574);
        p1.setLocation(6,574);
        p1.setLocation(6,574);
        p1.setLocation(6,574);
        p1.setLocation(6,574);
        p1.setLocation(6,574);
        p1.setLocation(6,574);
        p1.setLocation(6,574);
        p1.setLocation(6,574);
        p1.setLocation(6,574);
        p1.setLocation(6,604);
        p1.setLocation(6,604);
        p1.setLocation(6,604);
        p1.setLocation(6,604);
        p1.setLocation(6,604);
        p1.setLocation(6,604);
        p1.setLocation(6,604);
        p1.setLocation(6,604);
        p1.setLocation(6,604);
        p1.setLocation(6,604);
        p1.setLocation(6,604);
        p1.setLocation(6,604);
        p1.setLocation(6,604);
        p1.setLocation(6,604);
        p1.setLocation(6,604);
        p1.setLocation(6,604);
        p1.setLocation(6,604);
        p1.setLocation(6,604);
        p1.setLocation(6,604);
        p1.setLocation(6,604);
        p1.setLocation(6,604);
        p1.setLocation(6,604);
        p1.setLocation(6,604);
        p1.setLocation(6,604);
        p1.setLocation(6,604);
        p1.setLocation(6,604);
        p1.setLocation(6,604);
        p1.setLocation(6,604);
        p1.setLocation(6,604);
        p1.setLocation(6,604);
        p1.setLocation(6,604);
        p1.setLocation(61,608);
        p1.setLocation(61,608);
        p1.setLocation(61,608);
        p1.setLocation(61,608);
        p1.setLocation(61,608);
        p1.setLocation(61,608);
        p1.setLocation(61,608);
        p1.setLocation(61,608);
        p1.setLocation(61,608);
        p1.setLocation(61,608);
        p1 p12 = new p1();
        addObject(p12,271,567);
        p12.setLocation(271,567);
        p12.setLocation(271,567);
        p12.setLocation(271,567);
        p12.setLocation(271,567);
        p12.setLocation(271,567);
        p12.setLocation(271,567);
        p12.setLocation(271,567);
        p12.setLocation(271,567);
        p12.setLocation(271,567);
        p12.setLocation(271,567);
        p12.setLocation(271,567);
        p12.setLocation(271,567);
        p12.setLocation(271,567);
        p12.setLocation(271,567);
        p12.setLocation(271,567);
        p12.setLocation(271,567);
        p12.setLocation(271,567);
        p12.setLocation(271,567);
        p12.setLocation(271,567);
        p12.setLocation(271,567);
        p12.setLocation(271,567);
        p12.setLocation(271,567);
        p12.setLocation(271,567);
        p12.setLocation(271,567);
        p12.setLocation(271,567);
        p12.setLocation(271,567);
        p12.setLocation(271,567);
        p12.setLocation(271,567);
        p12.setLocation(271,567);
        p12.setLocation(271,567);
        p12.setLocation(188,608);
        p1 p13 = new p1();
        addObject(p13,355,575);
        p13.setLocation(355,575);
        p13.setLocation(355,575);
        p13.setLocation(355,575);
        p13.setLocation(355,575);
        p13.setLocation(355,575);
        p13.setLocation(355,575);
        p13.setLocation(323,608);
        p13.setLocation(323,608);
        p13.setLocation(323,608);
        p13.setLocation(323,608);
        p13.setLocation(323,608);
        p13.setLocation(323,608);
        p13.setLocation(323,608);
        p13.setLocation(323,608);
        p13.setLocation(323,608);
        p13.setLocation(323,608);
        p13.setLocation(323,608);
        p13.setLocation(323,608);
        p13.setLocation(323,608);
        p13.setLocation(323,608);
        p13.setLocation(323,608);
        p13.setLocation(323,608);
        p13.setLocation(323,608);
        p13.setLocation(316,608);
        p1 p14 = new p1();
        addObject(p14,499,499);
        p14.setLocation(474,566);
        p14.setLocation(455,608);
        p14.setLocation(443,608);
        p1 p15 = new p1();
        addObject(p15,653,520);
        p15.setLocation(653,520);
        p15.setLocation(653,520);
        p15.setLocation(653,520);
        p15.setLocation(653,520);
        p15.setLocation(653,520);
        p15.setLocation(606,593);
        p15.setLocation(606,593);
        p15.setLocation(606,593);
        p15.setLocation(606,593);
        p15.setLocation(606,593);
        p15.setLocation(606,593);
        p15.setLocation(606,593);
        p15.setLocation(571,608);
        p1 p16 = new p1();
        addObject(p16,715,534);
        p16.setLocation(703,595);
        p16.setLocation(699,608);
        p1 p17 = new p1();
        addObject(p17,697,483);
        p17.setLocation(697,483);
        p17.setLocation(697,483);
        p17.setLocation(697,483);
        p17.setLocation(697,483);
        p17.setLocation(697,483);
        p17.setLocation(697,483);
        p17.setLocation(697,483);
        p17.setLocation(729,609);
        p17.setLocation(736,609);
        p17.setLocation(736,609);
        p17.setLocation(736,608);
        r2 r2 = new r2();
        addObject(r2,162,480);
        r2.setLocation(168,484);
        arw arw = new arw();
        addObject(arw,422,508);
        arw.setLocation(432,500);
        bor bor = new bor();
        addObject(bor,701,46);
        bor.setLocation(691,39);
        viz viz = new viz();
        addObject(viz,661,44);
        viz.setLocation(651,39);
        scor1 scor1 = new scor1();
        addObject(scor1,705,43);
        bor.setLocation(691,40);
        scor1.setLocation(696,40);
        scor1.setLocation(699,40);
        p17.setLocation(445,275);
        removeObject(p17);
    }
}
