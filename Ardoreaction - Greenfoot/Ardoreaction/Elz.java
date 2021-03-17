import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Elz here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Elz extends Desert
{
    /**
     * Act - do whatever the Elz wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int speed = 2; //movement speed  
    private int vSpeed = 0; //vertical speed  
    private int acceleration = 2; //gravity effect while falling  
    private int jumpStrength = -10; 
    public int st=0;
    public void sar() 
    {
        checkKeys();
        checkFall();
        jump();
        
    }  
    
    public void checkKeys()
    {
        if(Greenfoot.isKeyDown("up")) 
        {
            jump();
        }

    }
    
    public void jump()  
    {  
        if (Greenfoot.isKeyDown("space")&&st==0)  
        {  
            if(getY()<=100)
                st=1;
            vSpeed = jumpStrength;  
            fall(); 
        }  
        
    } 
   
    public void fall()  
    {  
        setLocation(getX(), getY()+vSpeed);  
        vSpeed = vSpeed + acceleration;  
    }  

    public boolean onPlatform()  
    {  
        Actor under = getOneObjectAtOffset (0, 50, p1.class);  
        return under != null;  
    }  

    public void checkFall()  
    {  
        if (onPlatform())  
        {  
            vSpeed = 0;  
        }  
        else  
        {  
            fall();  
        }  

       
    }
}
