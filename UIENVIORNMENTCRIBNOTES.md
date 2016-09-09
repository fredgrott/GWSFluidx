UI ENVIRONMENT CRIB NOTES
=========================

This is a short summary of the TWO UI Environments we have when 
targeting api 16 to api 24. For more in-depth details see the references 
at end of this set of notes or my medium articles.

# Android 4.1-4.4.x, ie MR HYDE

1. Graphics Asynchronous API, allows un-finished objects in buffers to 
   be passed among the graphics sub-systems. It's why api 16 and higher 
   can use VSYNC to grab pre-frames.
   
2. Project Butter, ie VSYNC grabbing frames at different timing.

3. No RenderThread as we only have a UI-Thread. That means scrolling and 
   rendering is on the UI-thread.

4. Widgets AND THEIR accessory classes are usually on the UI-thread even 
   when doing expensive tasks that can be off-loaded off of the UI-thread.
   

   
# Android 5-7, ie MR Jekyll

1. Graphics Asynchronous API, allows un-finished objects in buffers to 
   be passed among the graphics sub-systems. It's why api 16 and higher 
   can use VSYNC to grab pre-frames.
   
2. Project Butter, ie VSYNC grabbing frames at different timing.

3. We have a render thread that does the rendering while the UI-thread still 
   handles scrolling. Material animations have moved over to the Render Thread.
   
4. Widgets AND THEIR accessory classes are usually on the UI-thread even 
   when doing expensive tasks that can be off-loaded off of the UI-thread.
   
# UI OPERATIONS

The UI has to complete these things in 0-15 milliseconds for the UI to be 
60 fps butter smooth:

1. Your code: provide a View tree.
    * Typically start by inflating a layout file, perhaps add or modify
      additional views
    * Set some values (e.g., setText).
2. Android code: process the View tree to create a rendering version of the
     tree. This includes:
    * Resolving values (e.g., text, colors, styles),
    * Pruning GONE views,
3. Rendering (often with the help of the GPU), for example:
    * Taking a string and drawing the letters of that string onto the screen in
      the right place

   
   
# REFERENCES

