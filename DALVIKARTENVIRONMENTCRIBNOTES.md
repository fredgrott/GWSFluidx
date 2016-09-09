Dalvik ART Environment Crib Notes
=================================

# Android 4.x Dalvik, ie Mr Hyde

1. GC passes take longer than 2ms, on low end devices its often 5ms.


# Android 5.x to &x ART, ie Mr Jekyll

1. GC passes are 2ms or less on low end devices.

# NOTE MUTABLE vs IMMUTABLE

1. For mutables both Dalvik and ART do at least two GC passes during 
   object creation.
   
2. For immutables both Dalvik and ART only do one GC pass.
