package com.invariablestudio.nokiaringtone;

public class GSRingtoneFAV {
    public int AUDIO_ID;
    public String AUDIO_RAWFILENAME;
    public String AUDIO_SHOWFILENAME;
    public String AUDIO_MYNAME;
    public String AUDIO_FAVOURITE;
    public int AUDIO_RELCOUNT;

    public GSRingtoneFAV(int AUDIO_ID, String AUDIO_RAWFILENAME, String AUDIO_SHOWFILENAME, String AUDIO_MYNAME, String AUDIO_FAVOURITE, int AUDIO_RELCOUNT) {
        this.AUDIO_ID = AUDIO_ID;
        this.AUDIO_RAWFILENAME = AUDIO_RAWFILENAME;
        this.AUDIO_SHOWFILENAME = AUDIO_SHOWFILENAME;
        this.AUDIO_MYNAME = AUDIO_MYNAME;
        this.AUDIO_FAVOURITE = AUDIO_FAVOURITE;
        this.AUDIO_RELCOUNT = AUDIO_RELCOUNT;
    }
}
