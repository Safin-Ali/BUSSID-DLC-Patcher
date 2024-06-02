package com.maleo.devsa;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipExtractor_DevSA {

	private static final int BUFFER_SIZE = 4096;

	public static void extractZipFile(Context context, Uri zipUri) {

		try {
			InputStream ism = context.getContentResolver().openInputStream(zipUri);

			File destinationDirectory = context.getFilesDir(); // or any other internal storage directory

			if (!destinationDirectory.exists()) {
				destinationDirectory.mkdirs();
			}

			try (ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(ism))) { // Changed zipFile to ism
				ZipEntry zipEntry;
				while ((zipEntry = zipInputStream.getNextEntry()) != null) {
					String entryName = zipEntry.getName();
					File entryFile = new File(destinationDirectory, entryName);

					if (zipEntry.isDirectory()) {
						entryFile.mkdirs();
					} else {
						try (BufferedOutputStream outputStream = new BufferedOutputStream(
								new FileOutputStream(entryFile))) {
							byte[] buffer = new byte[BUFFER_SIZE];
							int count;
							while ((count = zipInputStream.read(buffer)) != -1) {
								outputStream.write(buffer, 0, count);
							}
						}
					}
					zipInputStream.closeEntry();
				}
			}
		} catch (IOException e) {
		}
	}
}