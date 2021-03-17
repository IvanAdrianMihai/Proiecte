import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Clock here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Clock extends Cav
{
    private int scazator=70;
    /**
     * Act - do whatever the Clock wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        trece();
    }    
    private void trece()
    {
        if(scazator>0)
            scazator--;
        else
            if(scazator==0)
                k();
    }
    private void k()
    {
        scazator=70;
        Time.timp--;
    }
}
