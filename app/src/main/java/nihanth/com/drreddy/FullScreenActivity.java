package nihanth.com.drreddy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;

public class FullScreenActivity extends AppCompatActivity {
    SimpleExoPlayer exoPlayer;
    LoadControl loadControl;
    SimpleExoPlayerView exoPlayerView;
    DataSource.Factory datasource;
    String x;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullscreen_main);
        Intent intent = getIntent();

        x = intent.getStringExtra("video_link");

        exoPlayerView = findViewById(R.id.player_view_full);

        String userAgent = Util.getUserAgent(this, "DrReddy");
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        datasource = new DefaultDataSourceFactory(this,userAgent, (TransferListener<? super DataSource>) bandwidthMeter);
        initializePlayer(0);
        exoPlayerView.setPlayer(exoPlayer);

    }

    private void initializePlayer(long current) {


        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        loadControl = new DefaultLoadControl();
        TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveVideoTrackSelection.Factory(bandwidthMeter));
        exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
        exoPlayerView.setPlayer(exoPlayer);
        exoPlayer.setPlayWhenReady(false);
        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        if (x!=null) {
            Uri videoUri = Uri.parse(x);
            MediaSource mediaSource = new ExtractorMediaSource(videoUri,
                    datasource, extractorsFactory, null, null);
            if (current != C.TIME_UNSET){
                exoPlayer.seekTo(current);
            }
            exoPlayer.prepare(mediaSource);
        } else {
            Bitmap ic = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher);
            exoPlayerView.setDefaultArtwork(ic);
        }


    }

}
