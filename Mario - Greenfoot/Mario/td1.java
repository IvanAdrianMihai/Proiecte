import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class td1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class td1 extends testoasa
{
    /**
     * Act - do whatever the td1 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int timp;
    public void act() 
    {
        trecere();
        int x,y,a,b;
        x=this.getX();
        y=this.getY();
        a=mario.xx;
        b=mario.yy;
        if(this.isTouching(mario.class)&&y-10>b)
        {  
            Greenfoot.playSound("Kick.mp3");
         World world=this.getWorld();
         world.removeObject(this); 
         puncte.punct=puncte.punct+10;
         world.addObject(new misc(),x,y); 
         mario.viteza=-10;
        } 
        else
        if(timp==40)
        {
            timp=0;
            World world=this.getWorld();
            world.removeObject(this); 
            world.addObject(new td2(),x,y);
        }
    } 
    public void trecere()
    {
        timp++;
    }
    
}
