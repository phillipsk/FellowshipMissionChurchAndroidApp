package io.fmc.ui.listen;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.jean.jcplayer.JcAudio;
import com.example.jean.jcplayer.JcPlayerService;
import com.example.jean.jcplayer.JcPlayerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fmc.R;
import io.fmc.db.AudioMessage;
import io.fmc.db.DaoSession;
import io.fmc.di.AppController;
import io.fmc.utils.Utilities;

import static com.example.jean.jcplayer.JcAudio.createFromURL;


/**
 * A simple {@link Fragment} subclass.
 */
public class AudiosFragment extends Fragment implements JcPlayerService.JcPlayerServiceListener {


//    public AudiosFragment() {
//        // Required empty public constructor
//    }

        // static MediaPlayer mediaPlayer;
        DaoSession daoSession;
//        DaoSession daoSessionII;
//        AppController AppController;
        Context context;
        BroadcastReceiver broadcastReceiver;
        List<AudioMessage> audioMessages = new ArrayList<>();

        SimpleStringRecyclerViewAdapter adapter;

        //@BindView(R.id.btn_control) ImageButton btn_control;
        @BindView(R.id.recyclerview)
        RecyclerView recyclerView;
        @BindView(R.id.jcplayer)
        JcPlayerView jcPlayer;

        ArrayList<JcAudio> jcAudios = new ArrayList<>();

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

//            AppController AppController = new AppController();

            context = getActivity();

            initBroadcastReceiver();
            daoSession = AppController.getInstance().getDaoSession();
//            daoSession = AppController.getInstance().getDaoSession();

//            daoSession = AppController.getDaoSession();


//            DaoSession daoSession = ((AppController) getApplication()).getDaoSession();

/*            AppController = AppController.getInstance();
            daoSession = AppController.getInstance().getDaoSession();*/

//        mediaPlayer = new MediaPlayer();
//        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }

        private void fetchLocalData() {
            audioMessages = daoSession.getAudioMessageDao().loadAll();


            initJCPlayer();

            updateList();

 /*JUL-20 refactored and replaced fetch call to AppController activity class onCreate method
  * in an effort to download-async audioMessages in the background, no performance improvement yet
 */
//            AppController.getInstance().fetchAudioMessage();
        }

        private void initBroadcastReceiver() {
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String broadcast_type = intent.getExtras().getString("broadcast_type");

                    if (broadcast_type.toString().equals(AppController.BROADCAST_DOWNLOAD_AUDIO_FAILED)) {
                        Utilities.showToast(context,"Update failed");
                    }

                    if (broadcast_type.equals(AppController.BROADCAST_PLAY_MEDIA_AT_POSITION)) {
                        int position = intent.getExtras().getInt("position");
                        Log.e("position",position+"-");
                        playAudioFileFromServer(position);

                    }

                    if (broadcast_type.equals(AppController.BROADCAST_PAUSE_MEDIA_AT_POSITION)) {
                        int position = intent.getExtras().getInt("position");
                        audioMessages.get(position).setIs_playing(false);
                        updateList();
                    }

                    if (broadcast_type.toString().equals(AppController.BROADCAST_DOWNLOAD_AUDIO_SUCCESSFUL)) {
                        audioMessages.clear();

                        audioMessages.addAll(AppController.getInstance().audioMessages);
                        initJCPlayer();
                        updateList();
                    }

                }
            };
        }

        private void initJCPlayer(){
            jcAudios.clear();
            for(AudioMessage audioMessage:audioMessages){
                jcAudios.add(createFromURL(audioMessage.getName(),audioMessage.getPath()));
            }



            jcPlayer.initPlaylist(jcAudios);
            jcPlayer.createNotification();
        }

        private void updateList() {
            adapter.setItems(audioMessages);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onResume() {
            super.onResume();
            getActivity().registerReceiver(broadcastReceiver,new IntentFilter(AppController.backendBroadCast));
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            getActivity().unregisterReceiver(broadcastReceiver);

            try {
                jcPlayer.kill();
            }catch (Exception s){

            }
        }


        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view =  inflater.inflate(R.layout.audio_message_list, container, false);
            ButterKnife.bind(this,view);

            jcPlayer.registerServiceListener(this);


            setupRecyclerView();

            fetchLocalData();

            return view;
        }

        private void setupRecyclerView() {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            adapter = new SimpleStringRecyclerViewAdapter(context,audioMessages);
            recyclerView.setAdapter(adapter);
        }




        public void playAudioFileFromServer(final int position) {
            AudioMessage data = audioMessages.get(position);

            JcAudio jcAudio = JcAudio.createFromURL(data.getName(), data.getPath());
            jcPlayer.playAudio(jcAudio);
            undoAllPlay();

            audioMessages.get(position).setIs_playing(true);
            updateList();
        }

        private void undoAllPlay() {
            for(int a = 0;a < audioMessages.size();a++){
                audioMessages.get(a).setIs_playing(false);
            }
        }

        @Override
        public void onPreparedAudio(String audioName, int duration) {

        }

        @Override
        public void onCompletedAudio() {

        }

        @Override
        public void onPaused() {

        }

        @Override
        public void onContinueAudio() {

        }

        @Override
        public void onPlaying() {

        }

        @Override
        public void onTimeChanged(long currentTime) {

        }

        @Override
        public void updateTitle(String title) {

        }


        public static class SimpleStringRecyclerViewAdapter extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

            private List<AudioMessage> values;
            Context context;

            public void setItems(List<AudioMessage> items) {
                this.values = items;
            }

            public static class ViewHolder extends RecyclerView.ViewHolder {
                AudioMessage data;
                public final View view;
                public final TextView textView;
                public ImageButton btnPlay,btnPause;

                public ViewHolder(View view) {
                    super(view);
                    this.view = view;
                    textView = (TextView) view.findViewById(android.R.id.text1);
                    btnPlay = (ImageButton) view.findViewById(R.id.btn_play);
                    btnPause = (ImageButton) view.findViewById(R.id.btn_pause);
                }

                @Override
                public String toString() {
                    return super.toString() + " '" + textView.getText();
                }
            }

            public AudioMessage getValueAt(int position) {
                return values.get(position);
            }

            public SimpleStringRecyclerViewAdapter(Context context,List<AudioMessage> items) {
                this.context = context;
                values = items;
            }

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view =
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.audio_message_list_item, parent, false);
                return new ViewHolder(view);
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onBindViewHolder(final ViewHolder holder, final int position) {
                holder.data = values.get(position);
                holder.textView.setText(holder.data.getName());

                holder.btnPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppController.getInstance().sendLocalBroadcast(AppController.BROADCAST_PLAY_MEDIA_AT_POSITION,null,position);
                    }
                });

                holder.btnPause.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppController.getInstance().sendLocalBroadcast(AppController.BROADCAST_PAUSE_MEDIA_AT_POSITION,null,position);
                    }
                });

                if (holder.data.getIs_playing() != null) {
                    if(holder.data.getIs_playing()){
                        holder.btnPause.setVisibility(View.VISIBLE);
                        holder.btnPlay.setVisibility(View.INVISIBLE);
                    }else{
                        holder.btnPause.setVisibility(View.INVISIBLE);
//                        holder.btnPause.onVisibilityAggregated(false);
                        holder.btnPlay.setVisibility(View.VISIBLE);
//                        holder.btnPlay.onVisibilityAggregated(true);
                    }
                }else{
                    holder.btnPause.setVisibility(View.INVISIBLE);
                    holder.btnPlay.setVisibility(View.VISIBLE);
                }

//mysql -h {hostname} -u username -p {databasename}
//mysql -h localhost -u fmc_user -p fmc_db
//                godisgood11


            }

//        private void playAudioFileFromServer(AudioMessage data) {
//            Intent intent = new Intent(context, PlayerActivity.class);
//            intent.putExtra("video_url", data.getPath());
//            context.startActivity(intent);
//        }


            @Override
            public int getItemCount() {
                return values.size();
            }
        }


    }
