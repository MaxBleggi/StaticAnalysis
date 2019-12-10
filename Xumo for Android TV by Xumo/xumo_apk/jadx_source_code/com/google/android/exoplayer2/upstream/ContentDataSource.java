package com.google.android.exoplayer2.upstream;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import androidx.annotation.Nullable;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;

public final class ContentDataSource extends BaseDataSource {
    @Nullable
    private AssetFileDescriptor assetFileDescriptor;
    private long bytesRemaining;
    @Nullable
    private FileInputStream inputStream;
    private boolean opened;
    private final ContentResolver resolver;
    @Nullable
    private Uri uri;

    public static class ContentDataSourceException extends IOException {
        public ContentDataSourceException(IOException iOException) {
            super(iOException);
        }
    }

    public ContentDataSource(Context context) {
        super(false);
        this.resolver = context.getContentResolver();
    }

    @Deprecated
    public ContentDataSource(Context context, @Nullable TransferListener transferListener) {
        this(context);
        if (transferListener != null) {
            addTransferListener(transferListener);
        }
    }

    public long open(DataSpec dataSpec) throws ContentDataSourceException {
        try {
            this.uri = dataSpec.uri;
            transferInitializing(dataSpec);
            this.assetFileDescriptor = this.resolver.openAssetFileDescriptor(this.uri, "r");
            if (this.assetFileDescriptor != null) {
                this.inputStream = new FileInputStream(this.assetFileDescriptor.getFileDescriptor());
                long startOffset = this.assetFileDescriptor.getStartOffset();
                long skip = this.inputStream.skip(dataSpec.position + startOffset) - startOffset;
                if (skip == dataSpec.position) {
                    if (dataSpec.length != -1) {
                        this.bytesRemaining = dataSpec.length;
                    } else {
                        startOffset = this.assetFileDescriptor.getLength();
                        if (startOffset == -1) {
                            FileChannel channel = this.inputStream.getChannel();
                            long size = channel.size();
                            if (size == 0) {
                                size = -1;
                            } else {
                                size -= channel.position();
                            }
                            this.bytesRemaining = size;
                        } else {
                            this.bytesRemaining = startOffset - skip;
                        }
                    }
                    this.opened = true;
                    transferStarted(dataSpec);
                    return this.bytesRemaining;
                }
                throw new EOFException();
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not open file descriptor for: ");
            stringBuilder.append(this.uri);
            throw new FileNotFoundException(stringBuilder.toString());
        } catch (DataSpec dataSpec2) {
            throw new ContentDataSourceException(dataSpec2);
        }
    }

    public int read(byte[] bArr, int i, int i2) throws ContentDataSourceException {
        if (i2 == 0) {
            return null;
        }
        if (this.bytesRemaining == 0) {
            return -1;
        }
        try {
            if (this.bytesRemaining != -1) {
                i2 = (int) Math.min(this.bytesRemaining, (long) i2);
            }
            bArr = this.inputStream.read(bArr, i, i2);
            if (bArr != -1) {
                if (this.bytesRemaining != -1) {
                    this.bytesRemaining -= (long) bArr;
                }
                bytesTransferred(bArr);
                return bArr;
            } else if (this.bytesRemaining == -1) {
                return -1;
            } else {
                throw new ContentDataSourceException(new EOFException());
            }
        } catch (byte[] bArr2) {
            throw new ContentDataSourceException(bArr2);
        }
    }

    @Nullable
    public Uri getUri() {
        return this.uri;
    }

    public void close() throws ContentDataSourceException {
        this.uri = null;
        try {
            if (this.inputStream != null) {
                this.inputStream.close();
            }
            this.inputStream = null;
            try {
                if (this.assetFileDescriptor != null) {
                    this.assetFileDescriptor.close();
                }
                this.assetFileDescriptor = null;
                if (this.opened) {
                    this.opened = false;
                    transferEnded();
                }
            } catch (IOException e) {
                throw new ContentDataSourceException(e);
            } catch (Throwable th) {
                this.assetFileDescriptor = null;
                if (this.opened) {
                    this.opened = false;
                    transferEnded();
                }
            }
        } catch (IOException e2) {
            throw new ContentDataSourceException(e2);
        } catch (Throwable th2) {
            this.inputStream = null;
            try {
                if (this.assetFileDescriptor != null) {
                    this.assetFileDescriptor.close();
                }
                this.assetFileDescriptor = null;
                if (this.opened) {
                    this.opened = false;
                    transferEnded();
                }
            } catch (IOException e22) {
                throw new ContentDataSourceException(e22);
            } catch (Throwable th3) {
                this.assetFileDescriptor = null;
                if (this.opened) {
                    this.opened = false;
                    transferEnded();
                }
            }
        }
    }
}
