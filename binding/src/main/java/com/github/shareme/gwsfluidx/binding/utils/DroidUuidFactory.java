package com.github.shareme.gwsfluidx.binding.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.UUID;

/**
 * DroidUuidFactory,
 *
 * Device Environment: many different device form factors with not
 *                     all having a simCard or telephony. And us devs do in fact use
 *                     phone devices without simCards to debug.
 *
 * User Environment: tablets have multi users since 4.2 with each user getting its
 *                   own unique ANDROID_ID.
 *
 *
 *
 * Quirks:  Factory-Resets for the most part generate a new ANDROID_ID but we cannot
 *          detect a factory-reset anyway so why worry about it, it is what it is.
 *
 *          Android Emulators have the same ANDROID_ID but so what as we are not tracking that stuff
 *          and know that Android ID to ignore in our reports.
 *
 * Thus, the desire is to get an encrypted Android ID and store it in shared-preferences so that
 * we can use it for application analytics, app logging, etc.
 *
 * I choose to use UUID as its 128 bit instead of Android_ID 64 bit and has the benefits of no leaking
 * the Android_ID as we never load Android_II in the first place. Another benefit is that we always
 * get a reliable UUID when using emulators, phone devices with no simCards, etc.
 *
 * And by using a 128 bit number we make sure to be clash proof under multiple users with tablet and other
 * multiple user devices.
 *
 * We still cannot detect a factory-reset, but that is what it is.
 *
 * Usage:
 *
 * in App onCreate:
 *
 * <code>
 *
 * </code>
 * Created by fgrott on 9/11/2016.
 */
@SuppressWarnings("unused")
public class DroidUuidFactory {

  protected static final String PREFS_FILE = "device_uuid.xml";
  protected static final String PREFS_DEVICE_UUID = "device_uuid";

  protected volatile static UUID uuid;

  public DroidUuidFactory(Context context){
    if( uuid ==null ) {
      synchronized (DroidUuidFactory.class) {
        if( uuid == null) {
          final SharedPreferences prefs = context.getSharedPreferences( PREFS_FILE, 0);
          final String id = prefs.getString(PREFS_DEVICE_UUID, null );
          if (id != null) {
            // Use the ids previously computed and stored in the prefs file
            uuid = UUID.fromString(id);

          } else {
            final UUID randomUUID;

            randomUUID = UUID.randomUUID();

            uuid = randomUUID;



            prefs.edit().putString(PREFS_DEVICE_UUID, uuid.toString() ).apply();


          }
        }
      }
    }


  }


}
