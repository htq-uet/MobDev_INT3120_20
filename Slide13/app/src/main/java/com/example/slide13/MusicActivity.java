package com.example.slide13;

        import android.media.AudioAttributes;
        import android.media.MediaPlayer;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;

        import androidx.appcompat.app.AppCompatActivity;

        import java.io.IOException;

public class MusicActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer = new MediaPlayer();
    Button playMusicButton, previousButton, nextButton;

    TextView songNameTextView;
    String[] url = {
            "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
            "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3",
            "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3"
    };
    int currentSongIndex = 0; // Chỉ số của bài hát hiện tại

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        songNameTextView = findViewById(R.id.textViewSongTitle);
        songNameTextView.setText("Song " + (currentSongIndex + 1));
        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
        );

        try {
            mediaPlayer.setDataSource(url[currentSongIndex]);
            mediaPlayer.prepare();
        } catch (IOException e) {
            throw new RuntimeException("Failed to prepare MediaPlayer.", e);
        }

        playMusicButton = findViewById(R.id.buttonPlayPause);
        playMusicButton.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                playMusicButton.setBackgroundResource(R.drawable.baseline_play_arrow_24);
            } else {
                mediaPlayer.start();
                playMusicButton.setBackgroundResource(R.drawable.baseline_pause_24);
            }
        });

        previousButton = findViewById(R.id.buttonPrevious);
        previousButton.setOnClickListener(v -> playPreviousSong());

        nextButton = findViewById(R.id.buttonNext);
        nextButton.setOnClickListener(v -> playNextSong());

        mediaPlayer.setOnCompletionListener(mp -> playNextSong());
    }

    private void playPreviousSong() {
        if (currentSongIndex > 0) {
            currentSongIndex--;
        } else {
            currentSongIndex = url.length - 1;
        }
        changeSongAndPlay();
    }

    private void playNextSong() {
        if (currentSongIndex < url.length - 1) {
            currentSongIndex++;
        } else {
            currentSongIndex = 0;
        }
        changeSongAndPlay();
    }

    private void changeSongAndPlay() {
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(url[currentSongIndex]);
            songNameTextView.setText("Song " + (currentSongIndex + 1));
            mediaPlayer.prepare();
            mediaPlayer.start();
            playMusicButton.setBackgroundResource(R.drawable.baseline_pause_24);
        } catch (IOException e) {
            throw new RuntimeException("Failed to change song and start MediaPlayer.", e);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mediaPlayer.isPlaying()) {
            playMusicButton.setBackgroundResource(R.drawable.baseline_pause_24);
        } else {
            playMusicButton.setBackgroundResource(R.drawable.baseline_play_arrow_24);
        }
    }
}



