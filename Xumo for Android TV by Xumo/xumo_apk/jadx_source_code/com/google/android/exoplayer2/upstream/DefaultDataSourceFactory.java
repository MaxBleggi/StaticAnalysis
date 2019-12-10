package com.google.android.exoplayer2.upstream;

import android.content.Context;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.upstream.DataSource.Factory;

public final class DefaultDataSourceFactory implements Factory {
    private final Factory baseDataSourceFactory;
    private final Context context;
    @Nullable
    private final TransferListener listener;

    public DefaultDataSourceFactory(Context context, String str) {
        this(context, str, null);
    }

    public DefaultDataSourceFactory(Context context, String str, @Nullable TransferListener transferListener) {
        this(context, transferListener, new DefaultHttpDataSourceFactory(str, transferListener));
    }

    public DefaultDataSourceFactory(Context context, Factory factory) {
        this(context, null, factory);
    }

    public DefaultDataSourceFactory(Context context, @Nullable TransferListener transferListener, Factory factory) {
        this.context = context.getApplicationContext();
        this.listener = transferListener;
        this.baseDataSourceFactory = factory;
    }

    public DefaultDataSource createDataSource() {
        DefaultDataSource defaultDataSource = new DefaultDataSource(this.context, this.baseDataSourceFactory.createDataSource());
        if (this.listener != null) {
            defaultDataSource.addTransferListener(this.listener);
        }
        return defaultDataSource;
    }
}
