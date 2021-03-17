import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class puteri here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class bonus
 extends Actor
{
    /**
     * Act - do whatever the puteri wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public static int sus,jos,stanga,dreapta;
    public void act() 
    {
        // Add your action code here.
    }  
    public void limite()
    {
        if(this.isTouching(peretim.class))
        {
            sus=this.getY()-this.getImage().getHeight()/2;
            jos=this.getY()+this.getImage().getHeight()/2;
            stanga=this.getX()-this.getImage().getWidth()/2;
            dreapta=this.getX()+this.getImage().getWidth()/2;
        }            
    }
}
