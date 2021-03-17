import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class per2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class per2 extends Desert
{
    /**
     * Act - do whatever the per2 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(!(isTouching(r1.class)||isTouching(r2.class)))
            move(-5);
       if(isTouching(r1.class)||isTouching(r2.class))
       {
       if(Peter.lb==1)
       {
       String response = Greenfoot.ask("Ce substanta dulce produc albinele?? (raspunsul va fi format din litere mici si nu va contine spatii, iar daca vrei sa te dai batut scrie 'stop')");
       if (response != null && "miere".equals(response))
       {
           valP.np1=valP.np1+scor1.sc1;
           if(Volum.vol==1)
            Greenfoot.setWorld(new PP6());
        }
        else
        if (response != null && "stop".equals(response))
        {
            valP.np1=valP.np1+scor1.sc1;
            if(Peter.lb==1)
                Greenfoot.setWorld(new PR13());    
            else
                Greenfoot.setWorld(new PE13());     
        }
        else
            if(Volum.vol==1)
                Greenfoot.playSound("Raspuns gresit.mp3");    
        }
       if(Peter.lb==2)
       {
           String response = Greenfoot.ask("What is the name of the sweet substance that bees make? (the answer will be in small letters and will not contain spaces, and if you want to give up write 'stop')");
       if (response != null && "honey".equals(response))
       {
           valP.np1=valP.np1+scor1.sc1;
           if(Volum.vol==1)
                Greenfoot.setWorld(new PP6());
        }
        else
        if (response != null && "stop".equals(response))
        {
            valP.np1=valP.np1+scor1.sc1;
             if(Peter.lb==1)
                Greenfoot.setWorld(new PR13());    
            else
                Greenfoot.setWorld(new PE13());      
        }
        else
        if(Volum.vol==1)
        Greenfoot.playSound("Raspuns gresit.mp3");
       }
     } 
   }
}
