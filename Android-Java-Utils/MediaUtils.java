package com.udemy.javaexample.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.udemy.javaexample.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

public class MediaUtils {

    public static String getMediaType(String file_format) {
        if (file_format.startsWith("image")) {
            return "image";
        } else if (file_format.startsWith("video")) {
            return "video";
        } else {
            return "";
        }
    }

    public static String getFileType(File file) {

        String mimeType = URLConnection.guessContentTypeFromName(file.getPath());
        if (mimeType != null) {
            if (mimeType.startsWith("video")) {
                return "video";
            } else if (mimeType.startsWith("image")) {
                return "image";
            } else if (mimeType.startsWith("audio")) {
                return "audio";
            }
        }
        return "";
    }

    public static long getMediaIDFromFilePath(String path, Context context) {
        int id = 0;

        Uri uri = MediaStore.Files.getContentUri("external");
        String selection = MediaStore.Files.FileColumns.DATA;
        String[] selectionArgs = {path};
        String[] projection = {MediaStore.Files.FileColumns._ID};

        try {
            @SuppressLint("Recycle") Cursor cursor = context.getContentResolver().query(uri, projection, selection + "=?", selectionArgs, null);

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    id = cursor.getColumnIndex(MediaStore.Files.FileColumns._ID);
                }
            }
        } catch (Exception e) {
            Log.d("--media--", "getFilePathToMediaID: " + Log.getStackTraceString(e));
        }

        return id;
    }

    public static String getDuration(String path, String media_type) {
        if (media_type.startsWith("video") || media_type.startsWith("audio")) {
            try {
                MediaPlayer mMediaPlayer = new MediaPlayer();
                mMediaPlayer.setDataSource(path);
                mMediaPlayer.prepare();
                long duration = mMediaPlayer.getDuration();
                mMediaPlayer.release();
                return convertMillisToMinutes(String.valueOf(duration));
            } catch (Exception e) {
                Log.d("--media--", "getDuration: " + Log.getStackTraceString(e));
                e.printStackTrace();
                return "";
            }
        }
        return "";
    }

    public static String getMediaResolution(String path, String media_type) {
        if (media_type.equals("video")) {
            MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
            metaRetriever.setDataSource(path);
            String height = metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
            String width = metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);

            return width + "x" + height;
        } else if (media_type.equals("image")) {
            BitmapFactory.Options bitMapOption = new BitmapFactory.Options();
            bitMapOption.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, bitMapOption);
            int imageWidth = bitMapOption.outWidth;
            int imageHeight = bitMapOption.outHeight;

            return imageWidth + "x" + imageHeight;
        } else {
            return "";
        }
    }

    public static String getSizeInKbMbGb(long size) {

        DecimalFormat df = new DecimalFormat("0.00");

        float sizeKb = 1024.0f;
        float sizeMb = sizeKb * sizeKb;
        float sizeGb = sizeMb * sizeKb;
        float sizeTerra = sizeGb * sizeKb;

        if (size < sizeMb) {
            return df.format(size / sizeKb) + " KB";
        } else if (size < sizeGb) {
            return df.format(size / sizeMb) + " MB";
        } else if (size < sizeTerra) {
            return df.format(size / sizeGb) + " GB";
        }

        return "";
    }

    @SuppressLint("DefaultLocale")
    public static String convertMillisToMinutes(String duration) {
        try {
            int y = Integer.parseInt(duration);
            long ho = TimeUnit.MILLISECONDS.toHours(y);
            long mo = TimeUnit.MILLISECONDS.toMinutes(y) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(y));
            long so = TimeUnit.MILLISECONDS.toSeconds(y) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(y));

            if (ho >= 1) return String.format("%02d:%02d:%02d", ho, mo, so);
            else return String.format("%02d:%02d", mo, so);
        } catch (Exception e) {
            Log.d("--media--", "convertMillisToMinutes: " + Log.getStackTraceString(e));
            return "00:00";
        }
    }


    public static void saveImage(Activity activity, Bitmap finalBitmap) {
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/" + activity.getString(R.string.app_name);
        File filePath = new File(new File(path), String.valueOf(System.currentTimeMillis()));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            final ContentValues values = new ContentValues();
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, System.currentTimeMillis());
            values.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, "Download/" + activity.getString(R.string.app_name));

            final ContentResolver resolver = activity.getContentResolver();
            Uri uri = null;

            try {
                final Uri contentUri = MediaStore.Downloads.EXTERNAL_CONTENT_URI;
                uri = resolver.insert(contentUri, values);

                if (uri == null)
                    throw new IOException("Failed to create new MediaStore record.");

                try (final OutputStream stream = resolver.openOutputStream(uri)) {
                    if (stream == null) {
                        Toast.makeText(activity, "Failed to Save.", Toast.LENGTH_SHORT).show();
                        throw new IOException("Failed to open output stream.");
                    }

                    if (!finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)) {
                        Toast.makeText(activity, "Failed to Save.", Toast.LENGTH_SHORT).show();
                        throw new IOException("Failed to save bitmap.");
                    }
                }

                MediaScannerConnection.scanFile(activity, new String[]{filePath.toString()}, new String[]{"image/jpeg"}, new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {

                    }
                });
                Toast.makeText(activity, "Path: " + filePath.getAbsolutePath(), Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                if (uri != null) {
                    // Don't leave an orphan entry in the MediaStore
                    resolver.delete(uri, null, null);
                }
                Toast.makeText(activity, "Failed to Save.", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            Log.d("--filePath--", "saveImage: " + filePath.getAbsolutePath());

            File file = new File(filePath.getAbsolutePath());
            if (file.exists()) {
                file.delete();
            }
            try {
                FileOutputStream out = new FileOutputStream(file);
                finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();

                activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                        Uri.parse("file://" + Environment.getExternalStorageDirectory())));

                Toast.makeText(activity, "Path: " + filePath.getAbsolutePath(), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(activity, "Failed to Save.", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }
}
