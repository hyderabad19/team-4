package nihanth.com.drreddy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHold>{

    Context context;
    List<String> strings;
    SimpleExoPlayer exoPlayer;
    LoadControl loadControl;
    SimpleExoPlayerView exoPlayerView;
    DataSource.Factory datasource;


    VideoAdapter(Context context , List<String> stringList){
        this.context=context;
        this.strings=stringList;
        Log.d("hello",""+stringList);
    }

    @NonNull
    @Override
    public VideoAdapter.ViewHold onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.content_main_recycle, viewGroup, false);
        return new VideoAdapter.ViewHold(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.ViewHold viewHold, int i) {
        String userAgent = Util.getUserAgent(context, "DrReddy");
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        datasource = new DefaultDataSourceFactory(context,userAgent, (TransferListener<? super DataSource>) bandwidthMeter);
        initializePlayer(0,i);
        exoPlayerView.setPlayer(exoPlayer);
        final int is = i;

        viewHold.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                //intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, "nihanth876@gmail.com");
                context.startActivity(intent);
            }
        });
        viewHold.imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //final int s = i;
                Intent intent = new Intent(context,FullScreenActivity.class);
                intent.putExtra("video_link",strings.get(is));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    public class ViewHold extends RecyclerView.ViewHolder {

        //public TextView textView;

        public CardView cardView;
        public ImageView imageView;
        public ImageView imageView1;

        public ViewHold(View itemView) {
            super(itemView);
            exoPlayerView = itemView.findViewById(R.id.player_view);
            imageView = (ImageView)itemView.findViewById(R.id.flag);
            imageView1 = itemView.findViewById(R.id.full);
            cardView = itemView.findViewById(R.id.card);
            //imageView = itemView.findViewById(R.id.image_card_detail);
        }

    }



    private void initializePlayer(long current,int i) {


        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        loadControl = new DefaultLoadControl();
        TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveVideoTrackSelection.Factory(bandwidthMeter));
        exoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);
        exoPlayerView.setPlayer(exoPlayer);
        exoPlayer.setPlayWhenReady(false);
        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        if (strings!=null) {
            Uri videoUri = Uri.parse(strings.get(i));
            MediaSource mediaSource = new ExtractorMediaSource(videoUri,
                    datasource, extractorsFactory, null, null);
            if (current != C.TIME_UNSET){
                exoPlayer.seekTo(current);
            }
            exoPlayer.prepare(mediaSource);
        } else {
            Bitmap ic = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
            exoPlayerView.setDefaultArtwork(ic);
        }


    }


}
