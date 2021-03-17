import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class printesa here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class printesa extends Actor
{
    /**
     * Act - do whatever the printesa wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int timp=0,x,y;
    public void act() 
    {
        if(puncte.punct>scormaxim.max)
            scormaxim.max=puncte.punct;
        ultscor.ult=puncte.punct;
        if(timp!=0&&timp%2==0)
        {
            x=Greenfoot.getRandomNumber(175);
            y=Greenfoot.getRandomNumber(82);
            this.getWorld().addObject(new et1(),428+x,326+y);
        }
        if(timp>0)
            timp++;
        if(this.isTouching(mario.class))
        {
            if(timp==0)
            {
                Greenfoot.playSound("yahoo.mp3");
                timp++;
                puncte.punct=puncte.punct+1000;
            }
        }
        if(timp==80)
         {   
             Level3.back2.stop();
             Greenfoot.setWorld(new Meniu());
         }
    }    
}
