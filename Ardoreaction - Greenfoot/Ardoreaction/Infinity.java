import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.*;
/**
 * Write a description of class GhidareaLuiArdor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Infinity extends World
{
    private Counter theCounter;
    private int time=20;
    private int aparitie=0;
    private int distanta=1;
    /**
     * Constructor for objects of class GhidareaLuiArdor.
     * 
     */
    public Infinity()
    {    
        super(983,610, 1,false);
        GreenfootImage img=new GreenfootImage(983,610);
        img.fill();
        setBackground(img);
        addStars(1000);
        prepare();
    }

    public void act()
    {
        add();
        if(Ardor.vieti==0)
            if(Counter.totalCount>valG.nr2)
                valG.nr2=Counter.totalCount;
    }
    public Counter getCounter()
    {
        return theCounter;
    }

    public void addStars(int howMany)
    {
        for(int s=0; s<howMany;s++)
        {
            int x=Greenfoot.getRandomNumber(getWidth());
            int y=Greenfoot.getRandomNumber(getHeight());
            addObject(new Star(),x,y);
        }
    }
    private void add()
    {
        if(time>0)
            time--;
        else
            if(time==0)
                newmet();
    }
    private void primeste()
    {
        if(Ardor.vieti<3)
        {
            Ardor.vieti++;
            if (Ardor.vieti==2) 
            { 
                GreenfootSound sound = new GreenfootSound("Viata.mp3");
                if(Volum.vol==1)
                sound.play();
                Actor inima1=(Actor)getObjects(Inima5.class).get(0);     
                addObject(new Inima2(),inima1.getX(),inima1.getY());
                removeObject(inima1);

            }
            else
            if (Ardor.vieti==3) 
            { 
                GreenfootSound sound = new GreenfootSound("Viata.mp3");
                if(Volum.vol==1)
                sound.play();
                Actor inima1=(Actor)getObjects(Inima4.class).get(0);     
                addObject(new Inima1(),inima1.getX(),inima1.getY());
                removeObject(inima1);
            }
        }
    }
    private void newmet()
    {
        int k=Greenfoot.getRandomNumber(19);
        time=100;
        if(k>7)
         {   aparitie++;distanta++;
        if(aparitie%7==0&&distanta/15!=7)
        {
            addObject(new Punct1(),getWidth(),295);
            if(aparitie/7%6==0)
                primeste();
        }}
        else
        {
        if(k==0)
        {
            int p1=Greenfoot.getRandomNumber(3);
            int p2=Greenfoot.getRandomNumber(3);
            int y1=Greenfoot.getRandomNumber(getHeight());   
            int y2=Greenfoot.getRandomNumber(getHeight());   
            if(p1==0)
                addObject(new Mare1(),getWidth(),y1);
                else
            if(p1==1)
                addObject(new Mare2(),getWidth(),y1);
                else
            if(p1==2)
                addObject(new Mare3(),getWidth(),y1);
            if(p2==0)
                addObject(new Mediu1(),getWidth(),y2);
                else
            if(p2==1)
                addObject(new Mediu2(),getWidth(),y2);
                else
            if(p2==2)
                addObject(new Mediu3(),getWidth(),y2);
        }
        if(k==1)
        {
            int p1=Greenfoot.getRandomNumber(3);
            int p2=Greenfoot.getRandomNumber(3);
            int y1=Greenfoot.getRandomNumber(getHeight());   
            int y2=Greenfoot.getRandomNumber(getHeight());   
            if(p1==0)
                addObject(new Mare1(),getWidth(),y1);
                else
            if(p1==1)
                addObject(new Mare2(),getWidth(),y1);
                else
            if(p1==2)
                addObject(new Mare3(),getWidth(),y1);
            if(p2==0)
                addObject(new Mic1(),getWidth(),y2);
                else
            if(p2==1)
                addObject(new Mic2(),getWidth(),y2);
                else
            if(p2==2)
               addObject(new Mic3(),getWidth(),y2);
        };
        if(k==2)
        {
          
            int p1=Greenfoot.getRandomNumber(3);
            int y1=Greenfoot.getRandomNumber(getHeight());   
            if(p1==0)
                addObject(new Mare1(),getWidth(),y1);
            else
            if(p1==1)
                addObject(new Mare2(),getWidth(),y1);
            else
            if(p1==2)
                addObject(new Mare3(),getWidth(),y1);
        };
        if(k==3)
        {
            
            int p1=Greenfoot.getRandomNumber(3);
            int p2=Greenfoot.getRandomNumber(3);
            int y1=Greenfoot.getRandomNumber(getHeight());   
            int y2=Greenfoot.getRandomNumber(getHeight());   
            if(p1==0)
                addObject(new Mediu1(),getWidth(),y1);
                else
            if(p1==1)
                addObject(new Mediu2(),getWidth(),y1);
                else
            if(p1==2)
                addObject(new Mediu3(),getWidth(),y1);
            if(p2==0)
                addObject(new Mediu1(),getWidth(),y2);
                else
            if(p2==1)
                addObject(new Mediu2(),getWidth(),y2);
                else
            if(p2==2)
                addObject(new Mediu3(),getWidth(),y2);
        };
        if(k==4)
        {
            int p1=Greenfoot.getRandomNumber(3);
            int p2=Greenfoot.getRandomNumber(3);
            int y1=Greenfoot.getRandomNumber(getHeight());   
            int y2=Greenfoot.getRandomNumber(getHeight());   
            int p3=Greenfoot.getRandomNumber(3);
            int y3=Greenfoot.getRandomNumber(getHeight());  
            if(p1==0)
                addObject(new Mic1(),getWidth(),y1);
            else
            if(p1==1)
                addObject(new Mic2(),getWidth(),y1);
            else
            if(p1==2)
                addObject(new Mic3(),getWidth(),y1);
            if(p2==0)
                addObject(new Mic1(),getWidth(),y2);
            else
            if(p2==1)
                addObject(new Mic2(),getWidth(),y2);
            else
            if(p2==2)
                addObject(new Mic3(),getWidth(),y2);
            if(p3==0)
                addObject(new Mic1(),getWidth(),y3);
            else
            if(p3==1)
                addObject(new Mic2(),getWidth(),y3);
            else
            if(p3==2)
                addObject(new Mic3(),getWidth(),y3);
            
        };
        if(k==5)
        {
            int p1=Greenfoot.getRandomNumber(3);
            int p2=Greenfoot.getRandomNumber(3);
            int y1=Greenfoot.getRandomNumber(getHeight());   
            int y2=Greenfoot.getRandomNumber(getHeight());   
            if(p1==0)
                addObject(new Mic1(),getWidth(),y1);
                else
            if(p1==1)
                addObject(new Mic2(),getWidth(),y1);
                else
            if(p1==2)
                addObject(new Mic3(),getWidth(),y1);
            if(p2==0)
                addObject(new Mediu1(),getWidth(),y2);
                else
            if(p2==1)
                addObject(new Mediu2(),getWidth(),y2);
                else
            if(p2==2)
                addObject(new Mediu3(),getWidth(),y2);
        };
        if(k==6)
        {           
            int p1=Greenfoot.getRandomNumber(3);         
            int y1=Greenfoot.getRandomNumber(getHeight());         
            if(p1==0)
                addObject(new Mediu1(),getWidth(),y1);
                else
            if(p1==1)
                addObject(new Mediu2(),getWidth(),y1);
                else
            if(p1==2)
                addObject(new Mediu3(),getWidth(),y1);
        };
    }
}
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        Ardor ardor = new Ardor();
        addObject(ardor,84,293);
        Bord bord = new Bord();
        addObject(bord,390,561);
        bord.setLocation(381,553);
        Inima3 inima3 = new Inima3();
        addObject(inima3,252,561);
        inima3.setLocation(243,554);
        Inima2 inima2 = new Inima2();
        addObject(inima2,305,561);
        inima2.setLocation(301,554);
        Inima1 inima1 = new Inima1();
        addObject(inima1,365,562);
        inima1.setLocation(360,554);
        Stea stea = new Stea();
        addObject(stea,449,560);
        stea.setLocation(435,555);
        stea.setLocation(435,554);
        Counter counter = new Counter();
        addObject(counter,487,559);
        counter.setLocation(483,555);
    }
  }

