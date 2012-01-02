package com.mojang.ld22.sound;

//import java.applet.Applet;
//import java.applet.AudioClip;

import java.util.ArrayList;

import com.allen_sauer.gwt.voices.client.SoundController;

public class Sound {
    private static ArrayList<Sound> allSounds = new ArrayList<Sound>();

    public static final Sound playerHurt = new Sound("playerhurt.mp3");
    public static final Sound playerDeath = new Sound("death.mp3");
    public static final Sound monsterHurt = new Sound("monsterhurt.mp3");
    public static final Sound test = new Sound("test.mp3");
    public static final Sound pickup = new Sound("pickup.mp3");
    public static final Sound bossdeath = new Sound("bossdeath.mp3");
    public static final Sound craft = new Sound("craft.mp3");

    private String url;
    private com.allen_sauer.gwt.voices.client.Sound gwtSound;

    private Sound(String url) {
        this.url = url;
        this.gwtSound = null;
        allSounds.add(this);
    }

    public static void initAllSounds() {
        SoundController gwtSoundController = new SoundController();
        for (Sound sound : allSounds) {
            sound.gwtSound = gwtSoundController.createSound(com.allen_sauer.gwt.voices.client.Sound.MIME_TYPE_AUDIO_MPEG_MP3,
                                                            "sound/" + sound.url);
        }

    }

    public void play() {
        if (gwtSound != null) {
            gwtSound.play();
        }
    }
}