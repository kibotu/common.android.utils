package com.common.android.utils.input;

import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import com.common.android.utils.interfaces.ILogTag;

/**
 * Created by Jan Rabe on 14/08/15.
 * <p/>
 * <p/>
 * Remote control
 * <img src="../../../../../../res/drawable/remote_picture.png" />
 * <pre>
 *  ----------------------------------------------------------------------------------------------------
 * |    KEYCODE_MEDIA_REWIND    |       KEYCODE_MEDIA_RECORD        |       KEYCODE_MEDIA_FAST_FORWARD  |
 *  ----------------------------------------------------------------------------------------------------
 * |    KEYCODE_MEDIA_PREVIOUS  |       KEYCODE_MEDIA_PLAY_PAUSE    |       KEYCODE_MEDIA_NEXT          |
 *  ----------------------------------------------------------------------------------------------------
 * |                                                                                                    |
 *  ----------------------------------------------------------------------------------------------------
 * |    KEYCODE_BACK            |       KEYCODE_DPAD_UP             |       onPause()                   |
 *  ----------------------------------------------------------------------------------------------------
 * |    KEYCODE_DPAD_LEFT       |       KEYCODE_DPAD_CENTER         |       KEYCODE_DPAD_RIGHT          |
 *  ----------------------------------------------------------------------------------------------------
 * |    KEYCODE_F10             |       KEYCODE_DPAD_DOWN           |       KEYCODE_MENU                |
 *  ----------------------------------------------------------------------------------------------------
 * |                                                                                                    |
 *  ----------------------------------------------------------------------------------------------------
 * |    KEYCODE_VOLUME_UP      |        KEYCODE_VOLUME_MUTE         |       KEYCODE_PAGE_UP             |
 *  ----------------------------------------------------------------------------------------------------
 * |    KEYCODE_VOLUME_DOWN    |        KEYCODE_F5                  |       KEYCODE_PAGE_DOWN           |
 *  ----------------------------------------------------------------------------------------------------
 * |                                                                                                    |
 *  ----------------------------------------------------------------------------------------------------
 * |    KEYCODE_1               |       KEYCODE_2                   |       KEYCODE_3                   |
 *  ----------------------------------------------------------------------------------------------------
 * |    KEYCODE_4               |       KEYCODE_5                   |       KEYCODE_6                   |
 *  ----------------------------------------------------------------------------------------------------
 * |    KEYCODE_7               |       KEYCODE_8                   |       KEYCODE_9                   |
 *  ----------------------------------------------------------------------------------------------------
 * |    KEYCODE_F11             |       KEYCODE_0                   |       KEYCODE_F6                  |
 *  ----------------------------------------------------------------------------------------------------
 * |                                                                                                    |
 *  ----------------------------------------------------------------------------------------------------
 * |        KEYCODE_F1      |       KEYCODE_F2    |         KEYCODE_F3      |       KEYCODE_F4          |
 *  ----------------------------------------------------------------------------------------------------
 *  </pre>
 */
public class RemoteControlListener implements IKeyListener, View.OnKeyListener, DialogInterface.OnKeyListener, ILogTag {

    @Nullable
    private View currentView;

    @Override
    final public String tag() {
        return RemoteControlListener.class.getSimpleName();
    }

    @Nullable
    final protected View getCurrentView() {
        return currentView;
    }

    @Override
    final public boolean onKey(final View v, final int keyCode, final KeyEvent event) {
        this.currentView = v;
        return event.getAction() == KeyEvent.ACTION_DOWN
                ? onKeyDown(keyCode, event)
                : event.getAction() == KeyEvent.ACTION_UP ? onKeyUp(keyCode, event) : false;
    }

    @Override
    public boolean onKey(final DialogInterface dialog, final int keyCode, final KeyEvent event) {
        return event.getAction() == KeyEvent.ACTION_DOWN
                ? onKeyDown(keyCode, event)
                : event.getAction() == KeyEvent.ACTION_UP ? onKeyUp(keyCode, event) : false;
    }

    @Override
    final public boolean onKeyUp(final int keyCode, final KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_MEDIA_REWIND:
                return onMediaRewind();
            case KeyEvent.KEYCODE_MEDIA_RECORD:
                return onMediaRecord();
            case KeyEvent.KEYCODE_MEDIA_FAST_FORWARD:
                return onMediaFastForward();
            case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                return onMediaPrevious();
            case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
                return onMediaPlayPause();
            case KeyEvent.KEYCODE_MEDIA_NEXT:
                return onMediaNext();
            case KeyEvent.KEYCODE_BACK:
                return onDpadBack();
            case KeyEvent.KEYCODE_DPAD_UP:
                return onDpadUp();
            case KeyEvent.KEYCODE_DPAD_LEFT:
                return onDpadLeft();
            case KeyEvent.KEYCODE_DPAD_CENTER:
                return onDpadCenter();
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                return onDpadRight();
            case KeyEvent.KEYCODE_F10:
                return onTvGuide();
            case KeyEvent.KEYCODE_DPAD_DOWN:
                return onDpadDown();
            case KeyEvent.KEYCODE_MENU:
                return onOption();
            case KeyEvent.KEYCODE_VOLUME_UP:
                return onVolumeUp();
            case KeyEvent.KEYCODE_VOLUME_MUTE:
                return onVolumeMute();
            case KeyEvent.KEYCODE_PAGE_UP:
                return onPageUp();
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                return onVolumeDown();
            case KeyEvent.KEYCODE_F5:
                return onF5();
            case KeyEvent.KEYCODE_PAGE_DOWN:
                return onPageDown();
            case KeyEvent.KEYCODE_1:
                return on1();
            case KeyEvent.KEYCODE_2:
                return on2ABC();
            case KeyEvent.KEYCODE_3:
                return on3DEF();
            case KeyEvent.KEYCODE_4:
                return on4GHI();
            case KeyEvent.KEYCODE_5:
                return on5JKL();
            case KeyEvent.KEYCODE_6:
                return on6MNO();
            case KeyEvent.KEYCODE_7:
                return on7PQRS();
            case KeyEvent.KEYCODE_8:
                return on8TUV();
            case KeyEvent.KEYCODE_9:
                return on9WXYZ();
            case KeyEvent.KEYCODE_F11:
                return onTxt();
            case KeyEvent.KEYCODE_0:
                return on0_();
            case KeyEvent.KEYCODE_F6:
                return onRadio();
            case KeyEvent.KEYCODE_F1:
                return onExtra();
            case KeyEvent.KEYCODE_F2:
                return onVideo();
            case KeyEvent.KEYCODE_F3:
                return onPremium();
            case KeyEvent.KEYCODE_F4:
                return onSport();
            default:
                return false;
        }
    }

    @Override
    public boolean onKeyDown(final int keyCode, final KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_MEDIA_REWIND:
                return onMediaRewindPressed();
            case KeyEvent.KEYCODE_MEDIA_RECORD:
                return onMediaRecordPressed();
            case KeyEvent.KEYCODE_MEDIA_FAST_FORWARD:
                return onMediaFastForwardPressed();
            case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                return onMediaPreviousPressed();
            case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
                return onMediaPlayPausePressed();
            case KeyEvent.KEYCODE_MEDIA_NEXT:
                return onMediaNextPressed();
            case KeyEvent.KEYCODE_BACK:
                return onDpadBackPressed();
            case KeyEvent.KEYCODE_DPAD_UP:
                return onDpadUpPressed();
            case KeyEvent.KEYCODE_DPAD_LEFT:
                return onDpadLeftPressed();
            case KeyEvent.KEYCODE_DPAD_CENTER:
                return onDpadCenterPressed();
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                return onDpadRightPressed();
            case KeyEvent.KEYCODE_F10:
                return onTvGuidePressed();
            case KeyEvent.KEYCODE_DPAD_DOWN:
                return onDpadDownPressed();
            case KeyEvent.KEYCODE_MENU:
                return onOptionPressed();
            case KeyEvent.KEYCODE_VOLUME_UP:
                return onVolumeUpPressed();
            case KeyEvent.KEYCODE_VOLUME_MUTE:
                return onVolumeMutePressed();
            case KeyEvent.KEYCODE_PAGE_UP:
                return onPageUpPressed();
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                return onVolumeDownPressed();
            case KeyEvent.KEYCODE_F5:
                return onF5Pressed();
            case KeyEvent.KEYCODE_PAGE_DOWN:
                return onPageDownPressed();
            case KeyEvent.KEYCODE_1:
                return on1Pressed();
            case KeyEvent.KEYCODE_2:
                return on2ABCPressed();
            case KeyEvent.KEYCODE_3:
                return on3DEFPressed();
            case KeyEvent.KEYCODE_4:
                return on4GHIPressed();
            case KeyEvent.KEYCODE_5:
                return on5JKLPressed();
            case KeyEvent.KEYCODE_6:
                return on6MNOPressed();
            case KeyEvent.KEYCODE_7:
                return on7PQRSPressed();
            case KeyEvent.KEYCODE_8:
                return on8TUVPressed();
            case KeyEvent.KEYCODE_9:
                return on9WXYZPressed();
            case KeyEvent.KEYCODE_F11:
                return onTxtPressed();
            case KeyEvent.KEYCODE_0:
                return on0_Pressed();
            case KeyEvent.KEYCODE_F6:
                return onRadioPressed();
            case KeyEvent.KEYCODE_F1:
                return onExtraPressed();
            case KeyEvent.KEYCODE_F2:
                return onVideoPressed();
            case KeyEvent.KEYCODE_F3:
                return onPremiumPressed();
            case KeyEvent.KEYCODE_F4:
                return onSportPressed();
            default:
                return false;
        }
    }

    protected boolean onSportPressed() {
        return false;
    }

    protected boolean onPremiumPressed() {
        return false;
    }

    protected boolean onVideoPressed() {
        return false;
    }

    protected boolean onExtraPressed() {
        return false;
    }

    protected boolean onRadioPressed() {
        return false;
    }

    protected boolean on0_Pressed() {
        return false;
    }

    protected boolean onTxtPressed() {
        return false;
    }

    protected boolean on9WXYZPressed() {
        return false;
    }

    protected boolean on8TUVPressed() {
        return false;
    }

    protected boolean on7PQRSPressed() {
        return false;
    }

    protected boolean on6MNOPressed() {
        return false;
    }

    protected boolean on5JKLPressed() {
        return false;
    }

    protected boolean on4GHIPressed() {
        return false;
    }

    protected boolean on3DEFPressed() {
        return false;
    }

    protected boolean on2ABCPressed() {
        return false;
    }

    protected boolean on1Pressed() {
        return false;
    }

    protected boolean onPageDownPressed() {
        return false;
    }

    protected boolean onF5Pressed() {
        return false;
    }

    protected boolean onVolumeDownPressed() {
        return false;
    }

    protected boolean onPageUpPressed() {
        return false;
    }

    protected boolean onVolumeMutePressed() {
        return false;
    }

    protected boolean onVolumeUpPressed() {
        return false;
    }

    protected boolean onOptionPressed() {
        return false;
    }

    protected boolean onDpadDownPressed() {
        return false;
    }

    protected boolean onTvGuidePressed() {
        return false;
    }

    protected boolean onDpadRightPressed() {
        return false;
    }

    protected boolean onDpadCenterPressed() {
        return false;
    }

    protected boolean onDpadLeftPressed() {
        return false;
    }

    protected boolean onDpadUpPressed() {
        return false;
    }

    protected boolean onDpadBackPressed() {
        return false;
    }

    protected boolean onMediaNextPressed() {
        return false;
    }

    protected boolean onMediaPlayPausePressed() {
        return false;
    }

    protected boolean onMediaPreviousPressed() {
        return false;
    }

    protected boolean onMediaFastForward() {
        return false;
    }

    protected boolean onMediaRecord() {
        return false;
    }

    protected boolean onMediaRewind() {
        return false;
    }

    protected boolean onSport() {
        return false;
    }

    protected boolean onPremium() {
        return false;
    }

    protected boolean onVideo() {
        return false;
    }

    protected boolean onExtra() {
        return false;
    }

    protected boolean onRadio() {
        return false;
    }

    protected boolean on0_() {
        return false;
    }

    protected boolean onTxt() {
        return false;
    }

    protected boolean on9WXYZ() {
        return false;
    }

    protected boolean on8TUV() {
        return false;
    }

    protected boolean on7PQRS() {
        return false;
    }

    protected boolean on6MNO() {
        return false;
    }

    protected boolean on5JKL() {
        return false;
    }

    protected boolean on4GHI() {
        return false;
    }

    protected boolean on3DEF() {
        return false;
    }

    protected boolean on2ABC() {
        return false;
    }

    protected boolean on1() {
        return false;
    }

    protected boolean onPageDown() {
        return false;
    }

    protected boolean onF5() {
        return false;
    }

    protected boolean onVolumeDown() {
        return false;
    }

    protected boolean onPageUp() {
        return false;
    }

    protected boolean onVolumeMute() {
        return false;
    }

    protected boolean onVolumeUp() {
        return false;
    }

    protected boolean onOption() {
        return false;
    }

    protected boolean onDpadDown() {
        return false;
    }

    protected boolean onTvGuide() {
        return false;
    }

    protected boolean onDpadRight() {
        return false;
    }

    protected boolean onDpadCenter() {
        return false;
    }

    protected boolean onDpadLeft() {
        return false;
    }

    protected boolean onDpadUp() {
        return false;
    }

    protected boolean onDpadBack() {
        return false;
    }

    protected boolean onMediaNext() {
        return false;
    }

    protected boolean onMediaPlayPause() {
        return false;
    }

    protected boolean onMediaPrevious() {
        return false;
    }

    protected boolean onMediaFastForwardPressed() {
        return false;
    }

    protected boolean onMediaRecordPressed() {
        return false;
    }

    protected boolean onMediaRewindPressed() {
        return false;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final RemoteControlListener that = (RemoteControlListener) o;

        return !(currentView != null ? !currentView.equals(that.currentView) : that.currentView != null);

    }

    @Override
    public int hashCode() {
        return currentView != null ? currentView.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "RemoteControlListener{" +
                "currentView=" + currentView +
                '}';
    }
}
