package rahmatzulfikri.com.androidmediapath;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;

/**
 * Created by Rahmat Zulfikri on 22/09/2017.
 */

public class AndroidMediaPath extends ReactContextBaseJavaModule {
  public AndroidMediaPath(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  public String getName() {
    return "RNMediaPath";
  }

  private WritableMap makeErrorPayload(Exception ex) {
    WritableMap error = Arguments.createMap();
    error.putString("message", ex.getMessage());
    return error;
  }

  @ReactMethod
  public void getRealPathFromURI(String uriString, Promise promise) {
    Uri uri = Uri.parse(uriString);
    try {
      Context context = getReactApplicationContext();
      promise.resolve(getPath(context, uri));
    } catch (Exception ex) {
      ex.printStackTrace();
      promise.resolve(makeErrorPayload(ex));
    }
  }

  public static String getPath(Context context, Uri uri) {

    Cursor cursor = null;
    final String column = "_data";
    final String[] projection = {
      column
    };

    final String[] selectionArgs = new String[] {
      String.valueOf(ContentUris.parseId(uri))
    };

    try {
      cursor = context.getContentResolver().query(MediaStore.Files.getContentUri("external"), projection, "_id=?", selectionArgs,
        null);
      if (cursor != null && cursor.moveToFirst()) {
        final int column_index = cursor.getColumnIndexOrThrow(column);
        return cursor.getString(column_index);
      }
    } finally {
      if (cursor != null)
        cursor.close();
    }
    return null;
  }
}
