import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class peretim here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class peretim extends Actor
{
    /**
     * Act - do whatever the peretim wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public static int sus,jos,stanga,dreapta;
    public static int susc,josc,stangac,dreaptac;
    public static int sust,jost,stangat,dreaptat;
    public static int susta,josta,stangata,dreaptata;
    public static int susb,josb,stangab,dreaptab;
    public void act() 
    {
        // Add your action code here.
    }    
    public void limite()
    {
        if(this.isTouching(mario.class))
        {
            sus=this.getY()-this.getImage().getHeight()/2;
            jos=this.getY()+this.getImage().getHeight()/2;
            stanga=this.getX()-this.getImage().getWidth()/2;
            dreapta=this.getX()+this.getImage().getWidth()/2;
        }
        if(this.isTouching(ciperca.class))
        {
            susc=this.getY()-this.getImage().getHeight()/2;
            josc=this.getY()+this.getImage().getHeight()/2;
            stangac=this.getX()-this.getImage().getWidth()/2;
            dreaptac=this.getX()+this.getImage().getWidth()/2;
        }
        if(this.isTouching(testoasa.class))
        {
            sust=this.getY()-this.getImage().getHeight()/2;
            jost=this.getY()+this.getImage().getHeight()/2;
            stangat=this.getX()-this.getImage().getWidth()/2;
            dreaptat=this.getX()+this.getImage().getWidth()/2;
        }
        if(this.isTouching(aripi.class))
        {
            susta=this.getY()-this.getImage().getHeight()/2;
            josta=this.getY()+this.getImage().getHeight()/2;
            stangata=this.getX()-this.getImage().getWidth()/2;
            dreaptata=this.getX()+this.getImage().getWidth()/2;
        }
        if(this.isTouching(b3.class))
        {
            susb=this.getY()-this.getImage().getHeight()/2;
            josb=this.getY()+this.getImage().getHeight()/2;
            stangab=this.getX()-this.getImage().getWidth()/2;
            dreaptab=this.getX()+this.getImage().getWidth()/2;
        }
    }
}
