// MediaAIdlInterface.aidl
package com.example.maxiaowu.societyapp;
import com.example.maxiaowu.societyapp.service.MusicTrack;
// Declare any non-default types here with import statements

interface MediaAIdlInterface {
    void play();
    void pause();
    MusicTrack getCurrentTrack();
}
