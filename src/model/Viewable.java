package model;



import java.util.*;

public class Viewable
{   private static LinkedList<View> views = new LinkedList<View>();

    public static void attach(View view)
    {   views.add(view);   }
    
    public void detach(View view)
    {   views.remove(view); }
                
    public static void update()
    {   for (View view: views)
            view.update(); }
}
