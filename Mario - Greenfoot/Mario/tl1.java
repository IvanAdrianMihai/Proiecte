import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class tl1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class tl1 extends treceri
{
    /**
     * Act - do whatever the tl1 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int timp=0,x,y;
    public static boolean personaj=false;
    public void act() 
    {
        if(timp!=0)
            timp++;
        if(timp!=0&&timp%4==0)
        {
            x=Greenfoot.getRandomNumber(201);
            y=Greenfoot.getRandomNumber(70);
            this.getWorld().addObject(new et1(),810+x,24+y);
        }
        if(this.isTouching(mario.class))
        {
             timp++;             
        }
        if(timp==80)
        {
            Level1.back.stop();
            Greenfoot.setWorld(new Level2());            
        }
    }    
}
